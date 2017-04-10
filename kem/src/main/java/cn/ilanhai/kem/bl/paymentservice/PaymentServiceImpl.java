package cn.ilanhai.kem.bl.paymentservice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.emailright.EmailRightDao;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.dao.rights.RightsDao;
import cn.ilanhai.kem.dao.smsright.SmsRightDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.BooleanEntity;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.PackageServiceExistQCondtion;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.SearchOrderQCondition;
import cn.ilanhai.kem.domain.paymentservice.dto.PlaceOrderRequestDto;
import cn.ilanhai.kem.domain.paymentservice.dto.PlaceOrderResponseDto;
import cn.ilanhai.kem.domain.paymentservice.dto.SearchOrderRequestDto;
import cn.ilanhai.kem.domain.paymentservice.pricerange.PriceRangeEntity;
import cn.ilanhai.kem.domain.paymentservice.pricerange.PriceRangeResponse;
import cn.ilanhai.kem.domain.paymentservice.pricerange.PriveRangeRequest;
import cn.ilanhai.kem.domain.rights.UnRightsTimesEntity;
import cn.ilanhai.kem.domain.rights.dto.QueryUnRightsTimesDto;
import cn.ilanhai.kem.domain.smsright.SearchSmsQuantityByUser;
import cn.ilanhai.kem.domain.smsright.SmsRightEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.domain.PageResponse;
import cn.ilanhai.kem.domain.email.EmailRightEntity;
import cn.ilanhai.kem.domain.email.SearchEmailQuantityByUser;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigResponse;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigloadRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoLoadEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;

/**
 * 支付相关实现类 2016-11-16
 * 
 * @author Nature
 *
 */
@Component("paymentservice")
public class PaymentServiceImpl extends BaseBl implements PaymentService {

	private Logger logger = Logger.getLogger(PaymentServiceImpl.class);

	/**
	 * 下单
	 * 
	 * @throws AppException
	 * @throws SessionContainerException
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity placeorder(RequestContext context) throws AppException, SessionContainerException {

		// 获取入参
		PlaceOrderRequestDto request = context.getDomain(PlaceOrderRequestDto.class);
		// 入参不可为空
		BLContextUtil.valiDomainIsNull(request, "下单参数");
		logger.info("进入下单请求，参数为：" + request.toString());

		// 订单类型不可为空
		BLContextUtil.valiParaNotNull(request.getType(), "订单类型");
		// 套餐编号必须存在
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);

		BLContextUtil.valiDaoIsNull(dao, "支付服务");

		// 套餐ID为空，则数量不可为空
		if (request.getPackageServiceId() == null) {
			// 数量不可为空
			BLContextUtil.valiParaNotNull(request.getAmount(), "数量");
			// 数量不能小于1
			BLContextUtil.valiParaItemNumLassThan(1, request.getAmount(), "数量");
			// 服务类型不可为空
			BLContextUtil.valiParaNotNull(request.getServiceType(), "服务类型");
		} else {
			// 套餐ID不为空则判断套餐是否存在
			PackageServiceExistQCondtion packageServiceExistQCondtion = new PackageServiceExistQCondtion();
			packageServiceExistQCondtion.setPackageServiceId(request.getPackageServiceId());

			BooleanEntity entity = (BooleanEntity) dao.query(packageServiceExistQCondtion, false);
			if (!entity.getValue()) {
				throw new BlAppException(-1, "服务套餐不存在");
			}
		}
		// 检查用户是否为禁用状态，禁用则不可下单

		// 获得userId
		String userId = request.getUserId();
		if (userId == null || userId.isEmpty()) {
			userId = BLContextUtil.getSessionUserId(context);
		}

		Dao frontuserDao = BLContextUtil.getDao(context, FrontuserDao.class);
		IdEntity<String> queryFrontuserCondition = new IdEntity<String>();
		queryFrontuserCondition.setId(userId);
		// 加载用户
		FrontUserEntity frontUser = (FrontUserEntity) frontuserDao.query(queryFrontuserCondition, false);
		// 检查用户是否存在
		BLContextUtil.valiDomainIsNull(frontUser, "用户");
		// 如果用户已禁用则无法下单
		if (frontUser.getStatus().equals(UserStatus.DISABLE)) {
			throw new BlAppException(-1, "用户已禁用，无法下单");
		}

		OrderManager manager = new OrderManager(context);
		PaymentOrderEntity order = new PaymentOrderEntity();
		// 如果是线上订单，则走线上订单流程
		if (request.getType().equals(1)) {
			// 支付方式不可为空
			BLContextUtil.valiParaNotNull(request.getType(), "支付方式");

			order = manager.placeOrderOnline(request.getPackageServiceId(), request.getPayWay(), request.getAmount(),
					request.getServiceType());

		}
		// 如果是线下订单，则走线下订单流程
		else if (request.getType().equals(0)) {
			// 进后台用户可以创建线下订单
			BLContextUtil.checkBackUserLogined(context);
			// 如果设置了实际支付金额，则必须大于等于零
			if (request.getPayAmount() != null) {
				if (request.getPayAmount() < 0) {
					throw new BlAppException(-1, "实际支付金额不可小于零");
				}
			}
			//后台下单支付为0 by 黄毅 bug 9110
//			request.setPayAmount(0d);
			// 用户ID不可为空
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getUserId(), "用户ID");

			// 线下下单
			order = manager.placeOrderOffline(request.getPackageServiceId(), request.getPayAmount(),
					request.getUserId(), request.getAmount(), request.getServiceType());

		}

		PlaceOrderResponseDto response = new PlaceOrderResponseDto();
		response.setOrderId(order.getOrderId());

		DecimalFormat df = new DecimalFormat("0.00");
		response.setTotalFee(df.format(order.getPayAmount()));
		return response;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean savepayinfo(RequestContext context) throws BlAppException, DaoAppException {
		PayInfoRequestEntity request = null;
//		PayInfoRequestEntity payInfoRequestEntity = null;
		CodeTable ct;
		Dao dao = null;
		try {
			this.checkBackUserLogined(context);
			request = context.getDomain(PayInfoRequestEntity.class);
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			this.valiDaoIsNull(dao, "付费信息配置");
			this.valiPara(request);
			// 单价不能为空
			Pattern patt = Pattern.compile("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$", Pattern.CASE_INSENSITIVE);
			boolean isMatch = false;
			List<PayInfoServiceEntity> packageService = request.getPackageService();
			// 遍历套餐
			for (int i = 0; i < packageService.size(); i++) {
				PayInfoServiceEntity payInfo = packageService.get(i);
//				if (payInfo.getType() == null) {
//					continue;
//				}
				this.valiParaItemStrNullOrEmpty(payInfo.getName(), "套餐名字不能为空");
				this.valiParaItemIntegerNull(payInfo.getType(), "套餐类型不能为空");
				this.valiParaItemIntegerNull(payInfo.getPrice(), "套餐价格不能为空");
				Matcher matcher = patt.matcher(Double.toString(payInfo.getPrice()));
				isMatch = matcher.matches();
				if (!isMatch) {
					throw new BlAppException(-1, "价格最多保留两位小数并且大于0");
				}
				if (payInfo.getPrice() <= 0) {
					throw new BlAppException(-1, "价格最多保留两位小数并且大于0");
				}
				List<PayInfoServiceInfoEntity> info = payInfo.getInfo();
				// 遍历套餐详情
				for (int j = 0; j < info.size(); j++) {
					PayInfoServiceInfoEntity infoDetails = info.get(j);
					this.valiParaItemIntegerNull(infoDetails.getQuantity(), "服务数量不能为空");
					if (infoDetails.getQuantity() < 0) {
						throw new BlAppException(-1,"服务数量不能为负数");
					}
					this.valiParaItemIntegerNull(infoDetails.getType(), "服务类型不能为空");
					this.valiParaItemIntegerNull(infoDetails.getTimeMode(), "服务支付类型不能为空");
					if (infoDetails.getQuantity() <= 0) {
						throw new BlAppException(-100, "数量必须大于0");
					}
				}

			}
			// 保存
			dao.save(request);

			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);

		}

	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Entity loadpayinfo(RequestContext context) throws BlAppException, DaoAppException {
		PayInfoLoadEntity request = new PayInfoLoadEntity();
		CodeTable ct;
		Dao dao = null;
		try {
//			Integer type = context.getDomain(Integer.class);
//			request.setType(type);
//			this.valiParaItemIntegerNull(request.getType(), "套餐类型不能为空");
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			this.valiDaoIsNull(dao, "付费信息配置");
			PayInfoResponseEntity response = (PayInfoResponseEntity) dao.query(request, false);
			this.valiPara(response);
			return response;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	public boolean savepayconfigure(RequestContext context) throws BlAppException, DaoAppException {
		PayConfigRequestEntity request = null;
		CodeTable ct;
		Dao dao = null;
		try {
			request = context.getDomain(PayConfigRequestEntity.class);
			this.valiPara(request);
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			this.valiDaoIsNull(dao, "支付配置");
			dao.save(request);
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	@Override
	public Entity loadpayconfigure(RequestContext context) throws AppException, SessionContainerException {
		logger.info("进入方法");
		PayConfigloadRequestEntity request = new PayConfigloadRequestEntity();
		CodeTable ct;
		Dao dao = null;
		try {
			Integer type = context.getDomain(Integer.class);
			logger.info("参数解析成功");
			request.setType(type);
			BLContextUtil.valiParaItemIntegerNull(request.getType(), "支付类型不能为空");
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			this.valiDaoIsNull(dao, "支付配置");
			logger.info("开始查询");
			List<Entity> info = transformation(dao.query(request));
			logger.info("开始成功");
			PayConfigResponse response = new PayConfigResponse();
			response.setInfo(info);
			logger.info("返回");
			return response;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 查询用户订单信息
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Entity searchorder(RequestContext context) throws BlAppException, DaoAppException {
		// 获取请求对象
		SearchOrderRequestDto request = context.getDomain(SearchOrderRequestDto.class);
		List<String> userIdList = null;
		
		if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			//后台查询订单
			logger.info("后台查询订单");
			userIdList = new ArrayList<String>();
			userIdList.add(request.getUserId());
		} else if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			//前端查询订单
			logger.info("前端查询订单");
			String userId = null;
			try {
				userId = BLContextUtil.getSessionUserId(context);
				UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
				if (userRelationEntity != null && userRelationEntity.getUserId().equals(userRelationEntity.getFatherUserId())) {
					//如果是主账号，查询并保存所有子账号
					logger.info("userId:" + userId + " 为主账号");
					userIdList = UserRelationManger.userRelationIds(context, null);
				} else {
					//如果是子账号，查询自己的订单
					logger.info("userId:" + userId + " 为子账号");
					userIdList = new ArrayList<String>();
					userIdList.add(userId);
				}
			} catch (SessionContainerException e) {
				
				e.printStackTrace();
			}
		} else {
			throw new BlAppException(-1,"用户没有登录");
		}
		BLContextUtil.valiDomainIsNull(request, "请求对象");

		// 校验请求参数
		logger.info("请求的userid为：" + userIdList);
		// 获取dao
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		this.valiDaoIsNull(dao, "支付配置");
		// 组织查询条件
		SearchOrderQCondition condition = new SearchOrderQCondition();
		condition.setCreatetimeEnd(request.getCreatetimeEnd());
		condition.setCreatetimeStart(request.getCreatetimeStart());
		condition.setKeyword(request.getKeyword());
		if (!Str.isNullOrEmpty(request.getPayway())) {
			condition.setPayway(Arrays.asList(request.getPayway().split(",")));
		}
		condition.setPayStatus(request.getPayStatus());
		condition.setUserId(userIdList);
		condition.setStartCount(request.getStartCount());
		condition.setPageSize(request.getPageSize());
		
		// 获取数据
		Iterator<Entity> list = dao.query(condition);
		// 获取总条数
		CountDto count = (CountDto) dao.query(condition, false);
		PageResponse response = new PageResponse();
		response.setList(list);
		response.setTotalCount(count.getCount());
		response.setPageSize(request.getPageSize());
		response.setStartCount(request.getStartCount());

		return response;
	}

	/**
	 * 查询订单状态
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Integer searchpaystatus(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		this.checkFrontUserLogined(context);
		this.checkFrontUserType(context, UserType.GENERAL_USER);
		OrderManager orderManager = new OrderManager(context);
		String orderId = context.getDomain(String.class);
		if (Str.isNullOrEmpty(orderId)) {
			return -1;
		}
		return orderManager.getPayStatus(orderId);
	}

	/**
	 * 查询服务状态
	 * 
	 * @throws SessionContainerException
	 */
	@Override
	public Integer getrightstatus(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		// 获取入参
		Integer type = context.getDomain(Integer.class);
		BLContextUtil.valiParaItemObjectNull(type, "类型");
		// 获取当前用户ID
		BLContextUtil.checkFrontUserLogined(context);
		String userId = BLContextUtil.getSessionUserId(context);
		
		Dao dao = null;
		// 根据类型获得返回值
		switch (type) {
		// 去版权
		case PayInfoServiceInfoEntity.UNRIGHTS:
			dao = BLContextUtil.getDao(context, RightsDao.class);
			QueryUnRightsTimesDto queryUnRightsTimesDto = new QueryUnRightsTimesDto();
			queryUnRightsTimesDto.setUserId(userId);
			UnRightsTimesEntity unRightsTimesEntity = (UnRightsTimesEntity) dao.query(queryUnRightsTimesDto, false);
			if(unRightsTimesEntity==null){
				return 0;
			}else{
				return unRightsTimesEntity.getUnrightsTimes();
			}
			
		// 邮件
		case PayInfoServiceInfoEntity.EMAIL:
			dao = BLContextUtil.getDao(context, EmailRightDao.class);
			SearchEmailQuantityByUser condition=new SearchEmailQuantityByUser();
			condition.setUserId(userId);
			EmailRightEntity emailRightEntity =(EmailRightEntity) dao.query(condition,false);
			if(emailRightEntity==null){
				return 0;
			}else{
				return emailRightEntity.getRemainTimes();
			}
			
		// 短信
		case PayInfoServiceInfoEntity.SMS:
			dao = BLContextUtil.getDao(context, SmsRightDao.class);
			SearchSmsQuantityByUser searchSms = new SearchSmsQuantityByUser();
			searchSms.setUserId(userId);
			SmsRightEntity sms = (SmsRightEntity) dao.query(searchSms, false);
			if(sms==null){
				return 0;
			}else{
				return sms.getQuantity();
			}
		}
		return null;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean saveservicepricerange(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		CodeTable ct;
		PriveRangeRequest request = null;
		Dao dao = null;
		try {
			this.checkBackUserLogined(context);
			request = context.getDomain(PriveRangeRequest.class);
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			BLContextUtil.valiParaItemIntegerNull(request.getType(), "服务类型不能为空");
			for (int i = 0; i < request.getList().size(); i++) {
				PriceRangeEntity priceRangeEntity = (PriceRangeEntity) request.getList().get(i);
				BLContextUtil.isPrice(priceRangeEntity.getPrice());
				BLContextUtil.valiParaItemIntegerNull(priceRangeEntity.getPackageServiceId(),"套餐类型id不能为空");
				Date date = new Date();
				priceRangeEntity.setCreateTime(date);
			}
			int val = dao.save(request);
			if (val <= 0) {
				throw new BlAppException(-1, "保存价格区间失败");
			}
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Entity loadpricerange(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Integer request = null;
		Dao dao = null;
		try {
			request = context.getDomain(Integer.class);
			logger.info("查询type为：" + request + "的付费配置");
			BLContextUtil.valiParaItemIntegerNull(request, "服务类型不能为空");
			IdEntity<Integer> id = new IdEntity<Integer>();
			id.setId(request);
			dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
			
			List<Entity> list = transformation(dao.query(id));
			
			PriceRangeResponse PriceRangeResponse = new PriceRangeResponse();
			PriceRangeResponse.setList(list);
			PriceRangeResponse.setType(request);
			
			return PriceRangeResponse;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
}
