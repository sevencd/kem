package cn.ilanhai.kem.bl.paymentservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.emailright.EmailRightManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.rights.UnRightsManger;
import cn.ilanhai.kem.bl.smsright.SmsRightManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.PayStatus;
import cn.ilanhai.kem.domain.enums.PayWay;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderEntity;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderInfoEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoLoadEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.GetPackageServiceByIdQCondition;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.GetPaymentOrderByOrderIdQCondition;
import cn.ilanhai.kem.domain.paymentservice.pricerange.GetpriveEntity;
import cn.ilanhai.kem.domain.paymentservice.pricerange.PriceRangeEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;

/**
 * 订单管理类 该类完成订单的管理内容
 * 
 * @author Nature
 *
 */
public class OrderManager {

	private static Logger logger = Logger.getLogger(OrderManager.class);

	private RequestContext context;

	public OrderManager(RequestContext context) {
		this.context = context;
	}

	// 线上订单下单
	public PaymentOrderEntity placeOrderOnline(Integer packageServiceId, int payWay, Integer amount,
			Integer serviceType) throws SessionContainerException, AppException {

		logger.info("初始化dao");
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");

		// 获得当前用户ID
		String userId = BLContextUtil.getSessionUserId(context);
		BLContextUtil.valiParaNotNull(userId, "用户ID");
		// 校验用户合法性
		this.checkGeneralUser(userId);
		// 获得套餐内容
		PayInfoServiceEntity packageService = this.getPayInfoServiceEntity(packageServiceId, serviceType, amount,
				userId);
		// this.getPackageServiceInfoById(packageServiceId, dao);

		String orderId = this.newOrderId();

		logger.info("生成对应的订单");
		PaymentOrderEntity order = this.newOrder(orderId, packageService, userId, packageService.getPrice(),
				PayStatus.WAITPAY.getValue(), payWay);
		// 保存订单
		dao.save(order);

		return order;
	}

	/**
	 * 线下订单下单
	 * 
	 * @param packageServiceId
	 *            服务套餐编号
	 * @throws AppException
	 * @throws SessionContainerException
	 */
	public PaymentOrderEntity placeOrderOffline(Integer packageServiceId, Double payAmount, String userId,
			Integer amount, Integer serviceType) throws AppException, SessionContainerException {

		logger.info("初始化dao");
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");

		// 校验是否是合法的普通用户
		this.checkGeneralUser(userId);
		// 获得套餐内容
		PayInfoServiceEntity packageService = this.getPayInfoServiceEntity(packageServiceId, serviceType, amount,
				userId);
		// this.getPackageServiceInfoById(packageServiceId, dao);

		String orderId = this.newOrderId();

		logger.info("生成对应的订单");
		if (payAmount == null) {
			payAmount = packageService.getPrice();
		}
		PaymentOrderEntity order = this.newOrder(orderId, packageService, userId, payAmount, PayStatus.PAYED.getValue(),
				PayWay.OTHER.getValue());
		dao.save(order);

		// 更新相关服务
		this.updateService(packageService, userId);

		return order;
	}

	/**
	 * 在该方法中，各服务识别自己的更新内容，走自己的更新流程
	 * 
	 * @param payinfoService
	 * @throws AppException
	 */
	private void updateService(PayInfoServiceEntity payinfoService, String userId) throws AppException {
		MemberManager memberManager = new MemberManager(context);
		if (PayInfoServiceInfoEntity.MEMBER == payinfoService.getType()) {
			memberManager.proveTobeMember(payinfoService, userId, payinfoService.getPrice());
		} else {
			memberManager.proveTobeService(payinfoService, userId);
		}
	}

	/**
	 * 校验用户是否是合法的普通用户
	 * 
	 * @param userId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private void checkGeneralUser(String userId) throws DaoAppException, BlAppException {
		logger.info("判断被服务用户是否合法");

		Dao userDao = BLContextUtil.getDao(context, FrontuserDao.class);
		BLContextUtil.valiDaoIsNull(userDao, "前台用户");

		IdEntity<String> queryUser = new IdEntity<String>();
		queryUser.setId(userId);
		FrontUserEntity user = (FrontUserEntity) userDao.query(queryUser, false);
		BLContextUtil.valiDomainIsNull(user, "前台用户");
		if (!user.getUserType().equals(UserType.GENERAL_USER)) {
			throw new BlAppException(-1, "用户需要为普通用户");
		}
	}

	/**
	 * 根据ID获得服务套餐信息
	 * 
	 * @param packageServiceId
	 * @param dao
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static PayInfoServiceEntity getPackageServiceInfoById(int packageServiceId, Dao dao)
			throws DaoAppException, BlAppException {
		logger.info("根据服务套餐编号获取套餐内容");
		GetPackageServiceByIdQCondition condition = new GetPackageServiceByIdQCondition();
		condition.setPackageServiceId(packageServiceId);
		PayInfoServiceEntity packageService = (PayInfoServiceEntity) dao.query(condition, false);
		BLContextUtil.valiParaNotNull(packageService, "服务套餐");

		return packageService;
	}

	/**
	 * 生成新的OrderId
	 * 
	 * @return orderId
	 * @throws AppException
	 */
	private String newOrderId() throws AppException {
		logger.info("获得订单ID");
		String orderId = KeyFactory.getInstance().getKey(KeyFactory.KEY_ORDER);
		BLContextUtil.valiParaNotNull(orderId, "订单ID");
		return orderId;
	}

	/**
	 * 生成新的订单
	 * 
	 * @param orderId
	 *            订单编号
	 * @param packageService
	 *            套餐内容
	 * @param userId
	 *            用户ID
	 * @param payAmount
	 *            支付金额
	 */
	private PaymentOrderEntity newOrder(String orderId, PayInfoServiceEntity packageService, String userId,
			double payAmount, int pauStatus, int payWay) {
		PaymentOrderEntity order = new PaymentOrderEntity();
		order.setOrderId(orderId);
		order.setAmountPayable(packageService.getPrice());
		order.setCreateTime(new Date());
		order.setPayAmount(payAmount);
		order.setPayStatus(pauStatus);
		order.setPayTime(order.getCreateTime());
		order.setPayWay(payWay);
		order.setUpdateTime(order.getCreateTime());
		order.setUserId(userId);
		order.setPackageServiceId(packageService.getId());
		// 填写订单备注
		switch (packageService.getType()) {
		case PayInfoServiceInfoEntity.MEMBER:
			order.setDescription(packageService.getName());
			break;
		case PayInfoServiceInfoEntity.UNRIGHTS:
			order.setDescription(PaymentOrderEntity.UNRIGHT);
			break;
		case PayInfoServiceInfoEntity.EMAIL:
			order.setDescription(PaymentOrderEntity.EMAIL);
			break;
		case PayInfoServiceInfoEntity.SMS:
			order.setDescription(PaymentOrderEntity.SMS);
			break;
		case PayInfoServiceInfoEntity.CUSTOMERCLUE:
			order.setDescription(PaymentOrderEntity.CUSTOMERCLUE);
			break;
		case PayInfoServiceInfoEntity.B2B:
			order.setDescription(PaymentOrderEntity.B2B);
			break;
		case PayInfoServiceInfoEntity.SUBACCOUNT:
			order.setDescription(PaymentOrderEntity.SUBACCOUNT);
			break;
		case PayInfoServiceInfoEntity.PUBLISHNUM:
			order.setDescription(PaymentOrderEntity.PUBLISHNUM);
			break;
		}

		// 生成订单详情
		for (PayInfoServiceInfoEntity info : packageService.getInfo()) {
			PaymentOrderInfoEntity orderInfo = new PaymentOrderInfoEntity();
			orderInfo.setCreateTime(order.getCreateTime());
			orderInfo.setOrderId(order.getOrderId());
			orderInfo.setPackageServiceId(packageService.getId());
			orderInfo.setQuantity(info.getQuantity());
			orderInfo.setServiceId(info.getServiceId());
			orderInfo.setUnit(info.getType());
			orderInfo.setUpdateTime(order.getUpdateTime());

			order.getOrderInfo().add(orderInfo);
		}

		return order;
	}

	/**
	 * 支付通知 当支付异步通知发生时，该接口会被调用
	 * 
	 * @param orderId
	 * @return
	 * @throws AppException
	 * @throws SessionContainerException
	 */
	public boolean payNodify(String orderId) throws AppException, SessionContainerException {
		logger.info("初始化dao");
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");
		// 加载订单
		GetPaymentOrderByOrderIdQCondition condition = new GetPaymentOrderByOrderIdQCondition();
		condition.setOrderId(orderId);
		PaymentOrderEntity order = (PaymentOrderEntity) dao.query(condition, false);

		// 判断订单是否已支付，未支付，则设置为已支付，并进行后续流程
		if (PayStatus.WAITPAY.getValue().equals(order.getPayStatus())) {
			logger.info("更改订单状态");
			// 更改支付状态，并保存
			order.setPayStatus(PayStatus.PAYED.getValue());
			order.setPayTime(new Date());

			BLContextUtil.valiSaveDomain(dao.save(order), "订单");
			// 加载服务套餐
			logger.info("更改订单状态");

			// 获取type
			Integer type = null;
			// 获取数量
			Integer amount = null;
			if (order.getPackageServiceId() == null || order.getPackageServiceId() == 0) {

				switch (order.getDescription()) {
				case PaymentOrderEntity.MEMBER:
					type = 0;
					break;
				case PaymentOrderEntity.UNRIGHT:
					type = 1;
					break;
				case PaymentOrderEntity.EMAIL:
					type = 2;
					break;
				case PaymentOrderEntity.SMS:
					type = 3;
					break;
				case PaymentOrderEntity.CUSTOMERCLUE:
					type = 4;
					break;
				case PaymentOrderEntity.B2B:
					type = 5;
					break;
				case PaymentOrderEntity.SUBACCOUNT:
					type = 6;
					break;
				case PaymentOrderEntity.PUBLISHNUM:
					type = 7;
					break;

				}

				amount = order.getOrderInfo().get(0).getQuantity();
			}

			PayInfoServiceEntity packageService = this.getPayInfoServiceEntity(order.getPackageServiceId(), type,
					amount, order.getUserId());
			// this.getPackageServiceInfoById(order.getPackageServiceId(), dao);

			// 更新相关服务
			this.updateService(packageService, order.getUserId());

			return true;
		} else {
			logger.info("订单已被支付");
			return false;
		}

	}

	/**
	 * 获取服务套餐实体
	 * 
	 * @param packageServiceId
	 *            服务套餐ID，为空时，type与amount参数生效
	 * @param type
	 *            服务类型，0 会员；1 去版权
	 * @param amount
	 *            数量
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	private PayInfoServiceEntity getPayInfoServiceEntity(Integer packageServiceId, Integer type, Integer amount,
			String userId) throws DaoAppException, BlAppException, SessionContainerException {
		// 获取dao
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");
		PayInfoServiceEntity packageService;
		if (packageServiceId != null) {
			// 获得套餐内容
			packageService = this.getPackageServiceInfoById(packageServiceId, dao);
			// 计算价格
			MemberManager manager = new MemberManager(context);
			packageService.setPrice(manager.upgradePackageAmount(packageServiceId, userId));
		} else {

			BLContextUtil.valiParaNotNull(type, "服务类型");
			packageService = new PayInfoServiceEntity();
			// 获取单价
			PayInfoLoadEntity condition = new PayInfoLoadEntity();
			condition.setType(type);
			// 老的价格获取
			// PayInfoResponseEntity payinfo = (PayInfoResponseEntity)
			// dao.query(condition, false);
			// double price = payinfo.getPrice();
			// 1.5版本价格获取
			PayInfoServiceEntity payInfoServiceEntity = getServiceEntity(context, userId);

			GetpriveEntity getpriveEntity = new GetpriveEntity();
			if (payInfoServiceEntity != null) {
				getpriveEntity.setPackageServiceId(payInfoServiceEntity.getId());
			}
			getpriveEntity.setNumber(amount);
			getpriveEntity.setType(type);
			double price = getprice(context, getpriveEntity);
			// 创建套餐
			packageService.setPrice(price * amount);
			packageService.setType(type);

			PayInfoServiceInfoEntity payInfoServiceInfoEntity = new PayInfoServiceInfoEntity();
			payInfoServiceInfoEntity.setQuantity(amount);
			payInfoServiceInfoEntity.setType(type);
			payInfoServiceInfoEntity.setServiceId(PayInfoServiceInfoEntity.getServiceIdByType(type));
			// payInfoServiceInfoEntity.setUnit(payinfo.getUnit());

			List<PayInfoServiceInfoEntity> info = new ArrayList<PayInfoServiceInfoEntity>();
			info.add(payInfoServiceInfoEntity);
			packageService.setInfo(info);

		}
		return packageService;
	}

	public int getPayStatus(String orderId) throws DaoAppException, BlAppException {
		logger.info("初始化dao 查询支付状态");
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");
		// 加载订单
		GetPaymentOrderByOrderIdQCondition condition = new GetPaymentOrderByOrderIdQCondition();
		condition.setOrderId(orderId);
		PaymentOrderEntity order = (PaymentOrderEntity) dao.query(condition, false);
		if (order == null) {
			logger.info("订单查询为null");
			return -1;
		}
		logger.info("支付状态:" + order.getPayStatus());
		return order.getPayStatus();
	}

	/**
	 * 获取服务类型相对应的价格
	 * 
	 * @param context
	 * @param entity
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static double getprice(RequestContext context, GetpriveEntity entity)
			throws BlAppException, DaoAppException {
		Dao dao = null;
		dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		logger.info("参数：" + entity);
		List<Entity> list = BLContextUtil.transformation(dao.query(entity));
		if (list == null || list.size() == 0) {
			entity.setPackageServiceId(null);
			List<Entity> allList = BLContextUtil.transformation(dao.query(entity));
			if (allList.size() <= 0) {
				throw new BlAppException(-1, "后台没有配置付费价格"); 
			}
			PriceRangeEntity priceRangeEntity = (PriceRangeEntity) allList.get(0);
			return priceRangeEntity.getPrice();
		} else if (list.size() == 1) {
			logger.info("查询到一个后台配置");
			PriceRangeEntity priceRangeEntity = (PriceRangeEntity) list.get(0);
			return priceRangeEntity.getPrice();
		} else if (list.size() > 1) {
			//sql排序，最大值在前面，返回最大值即可
			logger.info("查询到多个后台配置");
			PriceRangeEntity priceRangeEntity = (PriceRangeEntity) list.get(0);
			return priceRangeEntity.getPrice();
		}
		return 0;
	}

	public static PayInfoServiceEntity getServiceEntity(RequestContext context, String userId)
			throws DaoAppException, BlAppException {
		MemberEntity memberEntity = MemberManager.getMemberInfo(context, userId);
		if (memberEntity == null || memberEntity.getPackageServiceId() == null) {
			return null;
		}
		PayInfoServiceEntity packageService = getPackageServiceInfoById(context, memberEntity.getPackageServiceId());
		return packageService;
	}

	public static PayInfoServiceEntity getPackageServiceInfoById(RequestContext context, int packageServiceId)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		GetPackageServiceByIdQCondition condition = new GetPackageServiceByIdQCondition();
		condition.setPackageServiceId(packageServiceId);
		PayInfoServiceEntity packageService = (PayInfoServiceEntity) dao.query(condition, false);
		return packageService;
	}
}
