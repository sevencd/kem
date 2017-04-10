package cn.ilanhai.kem.bl.payment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.domain.payment.PaymentInfoDto;
import cn.ilanhai.kem.domain.payment.PaymentWay;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigloadRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.GetPaymentOrderByOrderIdQCondition;
import cn.ilanhai.kem.payment.AbstractPayment;
import cn.ilanhai.kem.payment.PaymentConfig;
import cn.ilanhai.kem.payment.PaymentInfo;
import cn.ilanhai.kem.payment.alipay_directpay.DirectPayPaymentConfig;
import cn.ilanhai.kem.payment.alipay_directpay.DirectPayment;
import cn.ilanhai.kem.payment.weixin_sacncodepay.ScanCodePayment;
import cn.ilanhai.kem.payment.weixin_sacncodepay.ScanCodePaymentConfig;

/**
 * 支付
 * 
 * @author he
 *
 */
@Component("payment")
public class PaymentImpl extends BaseBl implements Payment {
	private final Logger logger = Logger.getLogger(PaymentImpl.class);
                
	@Override
	public String pay(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		PaymentInfoDto dto = null;
		PaymentInfo paymentInfo = null;
		PaymentConfig cfg = null;
		AbstractPayment payment = null;
		String tmp = null;
		PaymentWay paymentWay;
		int v = -1;
		Dao dao = null;
		PayConfigloadRequestEntity configloadRequestEntity = null;
		Iterator<Entity> configResponseEntitys = null;
		PayConfigResponseEntity configResponseEntity = null;
		GetPaymentOrderByOrderIdQCondition getPaymentOrderByOrderIdQCondition = null;
		PaymentOrderEntity paymentOrderEntity = null;
		Entity entity = null;
		double amountPayable = 0;
		Map setting = null;
		ct = CodeTable.BL_PAYMENT_ERROR;
		try {
			dto = context.getDomain(PaymentInfoDto.class);
			if (dto == null)
				throw new AppException(ct.getValue(), "定单参数错误");
			tmp = dto.getOrderId();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "定单参数错误");
			v = dto.getPaymentWay();
			if (v < 0 || v > 1)
				throw new AppException(ct.getValue(), "定单参数错误");
			dao = this.daoProxyFactory.getDao(context, PaymentServiceDao.class);
			this.valiDaoIsNull(dao, "支付参数配置");
			getPaymentOrderByOrderIdQCondition = new GetPaymentOrderByOrderIdQCondition();
			getPaymentOrderByOrderIdQCondition.setOrderId(tmp);
			entity = dao.query(getPaymentOrderByOrderIdQCondition, false);
			// 初始化定单
			if (entity == null)
				throw new AppException(ct.getValue(), "定单数据错误");
			if (!(entity instanceof PaymentOrderEntity))
				throw new AppException(ct.getValue(), "定单数据错误");
			paymentOrderEntity = (PaymentOrderEntity) entity;
			paymentInfo = new PaymentInfo();
			paymentInfo.setOrderId(dto.getOrderId());
			tmp = paymentOrderEntity.getDescription();
			if (tmp == null || tmp.length() <= 0)
				throw new AppException(ct.getValue(), "定单名称错误");
			paymentInfo.setName(tmp);
			// paymentInfo.setDescription(tmp + "描述");
			amountPayable = paymentOrderEntity.getAmountPayable();
			if (amountPayable <= 0)
				throw new AppException(ct.getValue(), "定单金额错误");
			paymentInfo.setTotalFee(String.format("%s", amountPayable));
			paymentWay = PaymentWay.valueOf(v);
			if (paymentWay == PaymentWay.Alipay) {// 初始化支付宝配置参数
				DirectPayPaymentConfig config = null;
				payment = new DirectPayment();
				config = new DirectPayPaymentConfig();
				setting = context.getApplication().getConfigure().getSettings();
				Object value = setting.get("aliPayNodifyUrl");
				if (value == null || !(value instanceof String))
					throw new AppException(ct.getValue(), "通知地址错误");
				tmp = (String) value;
				if (tmp == null || tmp.length() <= 0)
					throw new AppException(ct.getValue(), "通知地址错误");
				config.setNotifyUrl(tmp);
				config.setReturnUrl(dto.getReturnUrl());
				configloadRequestEntity = new PayConfigloadRequestEntity();
				configloadRequestEntity.setType(0);
				configResponseEntitys = dao.query(configloadRequestEntity);
				if (configResponseEntitys == null
						|| !configResponseEntitys.hasNext())
					throw new AppException(ct.getValue(), "加载支付参数配置出错");
				while (configResponseEntitys.hasNext()) {
					configResponseEntity = (PayConfigResponseEntity) configResponseEntitys
							.next();
					if (configResponseEntity == null)
						continue;
					tmp = configResponseEntity.getSysKey();
					if (tmp == null)
						continue;
					logger.info(tmp);
					logger.info(configResponseEntity.getSysValue());
					// MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM+VOrCxwvHBAQyYnkl73qs7kFM5xCn0w6Uf2gQKqkIY7YU3HavDY7HNf9orKpTa+DRA3LNTWY0FWw2rz99AvNR3N6WA0WLOA+dHCUUd30QiimElAbg2yHTNoC6MJuWUh5eY3v0IOiwXSjEqXnOUXOT5aHcu0v1murSGxMMNeNPTAgMBAAECgYEAklJqkGp9M9QMXUinKr059BGEZmXttHVoA1k0YGz0Hqtboe975rtP5uEoIi0TUuIW2x5tc6ppEVKZIEXfvUrwzr02bKuaubU0T4i6yHURt23sYUbCHpn3zgWK6dWG23FYoXZknqFFUgteZuuq0RdSjvt3l0F24KOZQyRUxZtU3cECQQD6LFa8853vzHXmBGkL34gE4O2sxLcxIvzz/cbGo7p8wqI9dQSsCnL2YtHEj30PLLQXAhB60dDXSbDubplVvL/rAkEA1GryAnIIboJAHpLI4ABrUj3hwJ1xS2QIbyn0273QuB7wQ6PXTKuSHJ+KbvQcFJtf/7vtIvrjqdTFGV4ngCCpuQJAD69p/MzVcSyDk9lg8LKiJ5QmsrdeuQD1lSKrLNclIR9e5rWIhnTdQl9twYIxmBr4a5zghaLUEjt3kWtzx7Fe4wJARX6LmLM4APeKBLafE3HvqqNmNT8NLs4WWFAQtMd//ozYrDhxGrtS/RxRDQW+HhvLJ36TEYulag2bQjZkk+2buQJAP+yNfDkgXsdG/BzRiksodI8Ooa8Gj7AwwJLGnUn0aj4JLhCSvC3DSNQT1J5R3cDrNNx5Mdapsi+MEwJnFL2dzw==");
					// http://www.hao123.com/notify");
					// 2088311932748130");
					// lanhaijiye888@163.com");
					// MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM+VOrCxwvHBAQyYnkl73qs7kFM5xCn0w6Uf2gQKqkIY7YU3HavDY7HNf9orKpTa+DRA3LNTWY0FWw2rz99AvNR3N6WA0WLOA+dHCUUd30QiimElAbg2yHTNoC6MJuWUh5eY3v0IOiwXSjEqXnOUXOT5aHcu0v1murSGxMMNeNPTAgMBAAECgYEAklJqkGp9M9QMXUinKr059BGEZmXttHVoA1k0YGz0Hqtboe975rtP5uEoIi0TUuIW2x5tc6ppEVKZIEXfvUrwzr02bKuaubU0T4i6yHURt23sYUbCHpn3zgWK6dWG23FYoXZknqFFUgteZuuq0RdSjvt3l0F24KOZQyRUxZtU3cECQQD6LFa8853vzHXmBGkL34gE4O2sxLcxIvzz/cbGo7p8wqI9dQSsCnL2YtHEj30PLLQXAhB60dDXSbDubplVvL/rAkEA1GryAnIIboJAHpLI4ABrUj3hwJ1xS2QIbyn0273QuB7wQ6PXTKuSHJ+KbvQcFJtf/7vtIvrjqdTFGV4ngCCpuQJAD69p/MzVcSyDk9lg8LKiJ5QmsrdeuQD1lSKrLNclIR9e5rWIhnTdQl9twYIxmBr4a5zghaLUEjt3kWtzx7Fe4wJARX6LmLM4APeKBLafE3HvqqNmNT8NLs4WWFAQtMd//ozYrDhxGrtS/RxRDQW+HhvLJ36TEYulag2bQjZkk+2buQJAP+yNfDkgXsdG/BzRiksodI8Ooa8Gj7AwwJLGnUn0aj4JLhCSvC3DSNQT1J5R3cDrNNx5Mdapsi+MEwJnFL2dzw==");
					if (tmp.equalsIgnoreCase("aliPublicKey")) {
						config.setPublicKey(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("aliNotifyUrl")) {
						// config.setNotifyUrl(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("aliPartner")) {
						config.setPartner(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("aliSellerId")) {
						config.setSellerId(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("aliPrivateKey")) {
						config.setPrivateKey(configResponseEntity.getSysValue());
					}
				}
				cfg = config;
			} else if (paymentWay == PaymentWay.WeiXin) {// 初始化微信配置参数
				ScanCodePaymentConfig config = null;
				payment = new ScanCodePayment();
				config = new ScanCodePaymentConfig();
				setting = context.getApplication().getConfigure().getSettings();
				Object value = setting.get("weixinPayNodifyUrl");
				if (value == null || !(value instanceof String))
					throw new AppException(ct.getValue(), "通知地址错误");
				tmp = (String) value;
				if (tmp == null || tmp.length() <= 0)
					throw new AppException(ct.getValue(), "通知地址错误");
				config.setNotifyUrl(tmp);
				configloadRequestEntity = new PayConfigloadRequestEntity();
				configloadRequestEntity.setType(1);
				configResponseEntitys = dao.query(configloadRequestEntity);
				if (configResponseEntitys == null
						|| !configResponseEntitys.hasNext())
					throw new AppException(ct.getValue(), "加载支付参数配置出错");
				while (configResponseEntitys.hasNext()) {
					configResponseEntity = (PayConfigResponseEntity) configResponseEntitys
							.next();
					if (configResponseEntity == null)
						continue;
					tmp = configResponseEntity.getSysKey();
					if (tmp == null)
						continue;
					// wx798c7df19560a294
					// 92d27e2b0378ab772e152fca7f9ba38f
					// 1262162601
					// http://www.hao123.com/nodify
					logger.info(tmp);
					logger.info(configResponseEntity.getSysValue());
					if (tmp.equalsIgnoreCase("wxAppId")) {
						config.setAppId(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("wxAppKey")) {
						config.setAppKey(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("wxMchId")) {
						config.setAppMchId(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("wxNotifyUrl")) {
						// config.setNotifyUrl(configResponseEntity.getSysValue());
					} else if (tmp.equalsIgnoreCase("wxKey")) {
						config.setKey(configResponseEntity.getSysValue());
					}
				}
				cfg = config;
			}
			// 发起支付
			return payment.pay(cfg, paymentInfo);
		} catch (BlAppException e) {
			throw new BlAppException(ct.getValue(), e.getMessage(), e);
		} catch (DaoAppException e) {
			throw new BlAppException(ct.getValue(), e.getMessage(), e);
		} catch (Exception e) {
			throw new BlAppException(ct.getValue(), e.getMessage(), e);
		}
	}

	/*
	 * 支付宝通知
	 * 
	 * @see
	 * cn.ilanhai.kem.bl.payment.Payment#alipaynodify(cn.ilanhai.framework.app
	 * .RequestContext)
	 */
	@Override
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean alipaynodify(RequestContext context) throws BlAppException,
			DaoAppException {
		DirectPayment directPayment = null;
		Map<String, String> params = null;
		String tmp = null;
		Iterator<String> keys = null;
		String key = null;
		CodeTable ct = CodeTable.BL_PAYMENT_ERROR;
		try {
			logger.info("支付宝通知处理");
			params = context.getDomain(Map.class);
			if (params == null)
				return false;
			keys = params.keySet().iterator();
			while (keys.hasNext()) {
				key = keys.next();
				logger.info(String.format("%s=%s", key, params.get(key)));
			}
			directPayment = new DirectPayment();
			tmp = directPayment.nodify(params);
			if (Str.isNullOrEmpty(tmp))
				return false;
			return this.nodify(context, tmp);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
	}

	/*
	 * 微信通知
	 * 
	 * @see
	 * cn.ilanhai.kem.bl.payment.Payment#weixinpaynodify(cn.ilanhai.framework
	 * .app.RequestContext)
	 */
	@Override
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean weixinpaynodify(RequestContext context)
			throws BlAppException, DaoAppException {
		ScanCodePayment scanCodePayment = null;
		Map<String, String> params = null;
		String tmp = null;
		CodeTable ct = CodeTable.BL_PAYMENT_ERROR;
		try {
			logger.info("微信通知处理");
			tmp = context.getDomain(String.class);
			logger.info(tmp);
			if (tmp == null || tmp.length() <= 0)
				return false;
			params = new HashMap<String, String>();
			params.put("xml", tmp);
			scanCodePayment = new ScanCodePayment();
			tmp = scanCodePayment.nodify(params);
			if (Str.isNullOrEmpty(tmp))
				return false;
			return this.nodify(context, tmp);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
	}

	/**
	 * 通知回调
	 * 
	 * @param ctx
	 * @param orderId
	 * @return
	 * @throws AppException
	 * @throws SessionContainerException 
	 */
	private boolean nodify(RequestContext ctx, String orderId)
			throws AppException, SessionContainerException {
		OrderManager orderManager = null;
		if (Str.isNullOrEmpty(orderId))
			return false;
		orderManager = new OrderManager(ctx);
		return orderManager.payNodify(orderId);
	}

}
