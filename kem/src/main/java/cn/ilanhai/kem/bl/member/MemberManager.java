package cn.ilanhai.kem.bl.member;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.integrate.zds.ZdsIntegrateManager;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.bl.user.frontuser.FrontuserManger;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.member.MemberDao;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.dao.userRelation.UserRelationResourceDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.member.UserResourcesEntity;
import cn.ilanhai.kem.domain.member.QueryCondition.GetMemberInfoByUserIdQCondition;
import cn.ilanhai.kem.domain.member.QueryCondition.MemberStatusQCondition;
import cn.ilanhai.kem.domain.member.dto.DeleteUserResourcesDto;
import cn.ilanhai.kem.domain.member.dto.MemberStatusDto;
import cn.ilanhai.kem.domain.member.dto.SearchUserResourcesDto;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.paymentservice.pricerange.GetpriveEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.Airth;
import cn.ilanhai.kem.util.LockUtil;
import cn.ilanhai.kem.util.TimeUtil;

/**
 * 会员管理类 2016-11-17
 * 
 * @author Nature
 *
 */
public class MemberManager {

	private Logger logger = Logger.getLogger(MemberManager.class);

	private RequestContext context;

	public MemberManager(RequestContext context) {
		this.context = context;
	}

	/**
	 * 获取会员套餐实体
	 * 
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public MemberEntity currentMemberInfo(String userId)
			throws DaoAppException, BlAppException, SessionContainerException {
		Dao dao = BLContextUtil.getDao(context, MemberDao.class);
		BLContextUtil.valiDaoIsNull(dao, "会员");
		// 获取该userId的会员信息
		GetMemberInfoByUserIdQCondition condition = new GetMemberInfoByUserIdQCondition();
		condition.setUserId(userId);
		return (MemberEntity) dao.query(condition, false);
	}

	/**
	 * 获取套餐价格
	 * 
	 * @param packageId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public double upgradePackageAmount(Integer packageId, String userId)
			throws DaoAppException, BlAppException, SessionContainerException {
		logger.info("开始计算价格--------------------------------");
		MemberEntity memberEntity = currentMemberInfo(userId);
		PayInfoServiceEntity packageService;
		packageService = getPackageService(packageId);
		logger.info(userId + ":(" + packageId + ")----" + memberEntity);
		// 如果不是会员 则支付会员价格
		if (memberEntity == null) {
			packageService = getPackageService(packageId);
			return packageService.getPrice();
		} else if (memberEntity.getStatus() == 0) {// 如果会员到期则开通新会员
			packageService = getPackageService(packageId);
			return packageService.getPrice();
		} else if (memberEntity.getStatus() == 1) {// 如果会员等级一致则续费
			Integer currentPackageId = memberEntity.getPackageServiceId();
			if (currentPackageId == null) {// 如果是前会员 则直接升级套餐服务
				packageService = getPackageService(packageId);
				return packageService.getPrice();
			}
			PayInfoServiceEntity currentpackageService = getPackageService(currentPackageId);
			if (currentpackageService == null) {
				throw new BlAppException(-1, "不支持该操作");
			}
			Map<Integer, PayInfoServiceInfoEntity> payInfoMap = buildInfoMap(currentpackageService);
			if (currentPackageId.equals(packageId)) {
				return packageService.getPrice();
			} else if (!currentPackageId.equals(packageId)
					&& memberEntity.getPackageServicePrice() > packageService.getPrice()) {// 如果等级下降则报错
				throw new BlAppException(-1, "不支持该操作");
			} else {// 等级上升则进行升级流程
				// 购买的会员价格
				logger.info("抵扣开始----------------------------");
				double currentPrice = memberEntity.getPackageServicePrice();
				// 现在的会员价格
				double packagePrice = packageService.getPrice();
				logger.info("当前用户套餐价格currentPrice:" + currentPrice);
				logger.info("升级套餐价格packagePrice:" + packagePrice);
				boolean doTime = true;
				double tempPrice = 0;
				List<UserResourcesEntity> userResourcesEntitys = getUserAllResources(userId);
				UserResourcesEntity userResourcesEntity = null;
				PayInfoServiceInfoEntity payInfoServiceInfoEntity = null;
				for (int i = 0; i < userResourcesEntitys.size(); i++) {
					userResourcesEntity = userResourcesEntitys.get(i);
					Integer serviceId = userResourcesEntity.getServiceId();
					logger.info("计算:" + serviceId + "服务的价格");
					if (payInfoMap.containsKey(serviceId)) {
						payInfoServiceInfoEntity = payInfoMap.get(serviceId);
						int monday = TimeUtil.getMonthSpace(new Date(), memberEntity.getStarttime());
						int yearday = TimeUtil.getYearSpace(memberEntity.getEndtime(), memberEntity.getStarttime());
						logger.info("服务" + serviceId + "时间间隔 月:" + monday + ",年:" + yearday);
						if (serviceId.equals(PayInfoServiceInfoEntity.EMAIL + 1)
								|| serviceId.equals(PayInfoServiceInfoEntity.SMS + 1)
								|| serviceId.equals(PayInfoServiceInfoEntity.B2B + 1)
								|| serviceId.equals(PayInfoServiceInfoEntity.CUSTOMERCLUE + 1)) {
							logger.info("服务" + serviceId + "按次数计算");
							int useTimes = 0;
							if (payInfoServiceInfoEntity.getTimeMode().equals(1)) {// 按月
								logger.info("服务" + serviceId + "按月推送,则使用次数为:套餐次数*月间隔-套餐剩余次数");
								useTimes = payInfoServiceInfoEntity.getQuantity() * monday
										- userResourcesEntity.getPackageServiceTimes();

							} else if (payInfoServiceInfoEntity.getTimeMode().equals(2)) {// 按年
								logger.info("服务" + serviceId + "按年推送,则使用次数为:套餐次数*年间隔-套餐剩余次数");
								useTimes = payInfoServiceInfoEntity.getQuantity() * yearday
										- userResourcesEntity.getPackageServiceTimes();
							}
							if (useTimes < 0) {
								useTimes = 0;
							}
							logger.info("服务" + serviceId + "使用了的次数:" + useTimes);
							GetpriveEntity entity = new GetpriveEntity();
							entity.setPackageServiceId(currentPackageId);
							entity.setType(serviceId - 1);
							double usePrice = OrderManager.getprice(context, entity);
							logger.info("服务" + serviceId + "的价格:" + usePrice);
							logger.info("服务" + serviceId + "消耗的价格:" + (useTimes * usePrice));
							tempPrice = Airth.add(tempPrice, Airth.mul(useTimes, usePrice));
							logger.info("tempPrice:" + tempPrice);
						} else {
							if (!doTime) {
								continue;
							}
							logger.info("服务" + serviceId + "按时间计算");
							if (new Date().after(memberEntity.getStarttime())) {
								double dayPrice = Airth.div(Airth.mul(monday, currentPrice), (12 * yearday));
								if (dayPrice < 0) {
									dayPrice = 0;
								}
								tempPrice = Airth.add(tempPrice, dayPrice);
								logger.info("tempPrice:" + tempPrice);
								doTime = false;
							} else {
								throw new BlAppException(-1, "不支持该操作");
							}
						}
					}

				}
				logger.info("计算结束 根据结果计算需要支付的价格currentPrice:" + currentPrice + "----tempPrice:" + tempPrice);
				String price = BLContextUtil.getValue(context, "price");
				if (!Str.isNullOrEmpty(price)) {
					double dprice = Double.parseDouble(price);
					if (tempPrice > dprice) {
						tempPrice = dprice;
					}
				}
				if (currentPrice - tempPrice <= 0) {
					return Airth.round(packagePrice, 2);
				} else {
					return Airth.round(packagePrice - (currentPrice - tempPrice), 2);
				}

			}
		}
		return 0;
	}

	private Map<Integer, PayInfoServiceInfoEntity> buildInfoMap(PayInfoServiceEntity packageService) {
		List<PayInfoServiceInfoEntity> infos = packageService.getInfo();
		Map<Integer, PayInfoServiceInfoEntity> payInfo = new HashMap<Integer, PayInfoServiceInfoEntity>();
		for (int i = 0; i < infos.size(); i++) {
			PayInfoServiceInfoEntity info = infos.get(i);
			payInfo.put(info.getServiceId(), info);
		}
		return payInfo;
	}

	private PayInfoServiceEntity getPackageService(Integer packageId) throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
		BLContextUtil.valiDaoIsNull(dao, "支付服务");
		PayInfoServiceEntity packageService;
		// 获得套餐内容
		packageService = OrderManager.getPackageServiceInfoById(packageId, dao);
		BLContextUtil.valiDomainIsNull(packageService, "套餐服务");
		return packageService;
	}

	/**
	 * 保存用户资源信息
	 * 
	 * @throws DaoAppException
	 * @throws BlAppException
	 * 
	 */
	public int saveUserResource(RequestContext context, UserResourcesEntity entity)
			throws DaoAppException, BlAppException {
		BLContextUtil.valiPara(entity);
		// 获取数据库资源
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		return dao.save(entity);
	}

	/**
	 * 创建用户资源信息
	 * 
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 * 
	 */
	public static int createPackageUserResource(RequestContext context, PayInfoServiceInfoEntity entity, String userId,
			MemberEntity Member) throws DaoAppException, BlAppException {
		BLContextUtil.valiPara(entity);
		synchronized (LockUtil.getUserLock(userId)) {
			UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
			if (userRelationEntity == null) {
				userRelationEntity = new UserRelationEntity();
				userRelationEntity.setId(0);
				userRelationEntity.setFatherUserId(userId);
				userRelationEntity.setUserId(userId);
				userRelationEntity.setState(UserStatus.ENABLE.getValue());
				// 主账号
				userRelationEntity.setUserType(UserRelationType.MAINUSER.getValue());
				UserRelationManger.saveUserRelation(context, userRelationEntity);
			}
			Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
			userId = userRelationEntity.getResouseUserId(entity.getServiceId());
			SearchUserResourcesDto dto = new SearchUserResourcesDto();
			dto.setServiceId(entity.getServiceId());
			dto.setUserId(userId);
			UserResourcesEntity resourcesEntity = (UserResourcesEntity) dao.query(dto, false);
			if (resourcesEntity == null) {
				resourcesEntity = new UserResourcesEntity();
				resourcesEntity.setUserId(userId);
			}
			int packageServiceTimes = resourcesEntity.getPackageServiceTimes();
			int packageServiceTotalTimes = resourcesEntity.getPackageServiceTotalTimes();
			// int serviceTimes = resourcesEntity.getServiceTimes();
			// int serviceTotalTimes =
			// resourcesEntity.getServiceTotalTimes();
			resourcesEntity.setPackageServiceTimes(entity.getQuantity() + packageServiceTimes);
			resourcesEntity.setPackageServiceTotalTimes(entity.getQuantity() + packageServiceTotalTimes);
			resourcesEntity.setServiceId(entity.getServiceId());
			resourcesEntity.setStarttime(Member.getStarttime());
			resourcesEntity.setEndtime(Member.getEndtime());
			// 获取数据库资源
			return dao.save(resourcesEntity);
		}
	}

	/**
	 * 创建用户资源信息
	 * 
	 * @throws DaoAppException
	 * @throws BlAppException
	 * 
	 */
	public int createUserResource(PayInfoServiceInfoEntity entity, String userId)
			throws DaoAppException, BlAppException {
		BLContextUtil.valiPara(entity);
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		if (userRelationEntity == null) {
			CodeTable ct = CodeTable.BL_USER_RESOURCE_DOAMIN;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		userId = userRelationEntity.getResouseUserId(entity.getServiceId());
		synchronized (LockUtil.getUserLock(userId)) {
			SearchUserResourcesDto dto = new SearchUserResourcesDto();
			dto.setServiceId(entity.getServiceId());
			dto.setUserId(userId);
			UserResourcesEntity resourcesEntity = (UserResourcesEntity) dao.query(dto, false);
			if (resourcesEntity == null) {
				resourcesEntity = new UserResourcesEntity();
			}
			// int packageServiceTimes =
			// resourcesEntity.getPackageServiceTimes();
			// int packageServiceTotalTimes =
			// resourcesEntity.getPackageServiceTotalTimes();
			int serviceTimes = resourcesEntity.getServiceTimes();
			int serviceTotalTimes = resourcesEntity.getServiceTotalTimes();
			resourcesEntity.setServiceTimes(entity.getQuantity() + serviceTimes);
			resourcesEntity.setServiceTotalTimes(entity.getQuantity() + serviceTotalTimes);
			resourcesEntity.setServiceId(entity.getServiceId());
			// 获取数据库资源
			return dao.save(resourcesEntity);
		}
	}

	/**
	 * 获取用户资源
	 * 
	 * @param payInfoServiceId
	 *            资源类型
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public UserResourcesEntity getUserResourcesById(int payInfoServiceId, String userId)
			throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(userId)) {
			return null;
		}
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		SearchUserResourcesDto dto = new SearchUserResourcesDto();
		dto.setServiceId(payInfoServiceId);
		dto.setUserId(userId);
		UserResourcesEntity resource = (UserResourcesEntity) dao.query(dto, false);
		// 计算剩余次数
		resource.setRemainingNum(resource.getPackageServiceTimes() + resource.getServiceTimes());
		return resource;
	}

	/**
	 * 获取当前用户所有资源
	 * 
	 * * @return
	 * 
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public List<UserResourcesEntity> getUserAllResources(String userId) throws DaoAppException, BlAppException {
		List<UserResourcesEntity> result = new ArrayList<UserResourcesEntity>();
		if (Str.isNullOrEmpty(userId)) {
			return result;
		}

		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		SearchUserResourcesDto dto = new SearchUserResourcesDto();
		dto.setUserId(userId);
		List<Entity> list = dao.queryList(dto);
		for (Entity entity : list) {
			if (entity instanceof UserResourcesEntity) {
				UserResourcesEntity userResourcesEntity = (UserResourcesEntity) entity;
				UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
				if (userRelationEntity != null) {
					result.add(getUserResourcesById(userResourcesEntity.getServiceId(),
							userRelationEntity.getResouseUserId(userResourcesEntity.getServiceId())));
				}
			}
		}
		return result;
	}

	/**
	 * 获取当前用户所有资源
	 * 
	 * * @return
	 * 
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public List<Integer> getUserAllResourcesId(String userId) throws DaoAppException, BlAppException {
		List<Integer> result = new ArrayList<Integer>();
		if (Str.isNullOrEmpty(userId)) {
			return result;
		}
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		SearchUserResourcesDto dto = new SearchUserResourcesDto();
		dto.setUserId(userId);
		List<Entity> list = dao.queryList(dto);
		for (Entity entity : list) {
			if (entity instanceof UserResourcesEntity) {
				result.add(((UserResourcesEntity) entity).getServiceId());
			}
		}
		return result;
	}

	/**
	 * 消耗资源一份
	 * 
	 * @param context
	 *            全局上下文
	 * @param userId
	 *            用户id
	 * @param payInfoServiceId
	 *            类型id type+1
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static void useUserServiceResources(RequestContext context, String userId, int payInfoServiceId, int amont)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		if (Str.isNullOrEmpty(userId)) {
			return;
		}
		if (amont <= 0) {
			return;
		}
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		userId = userRelationEntity.getResouseUserId(payInfoServiceId);
		// TODO 添加用户锁 该状态只能一个用户使用 待测试 只支持单部署环境 多部署环境再修改
		synchronized (LockUtil.getUserLock(userId)) {
			SearchUserResourcesDto dto = new SearchUserResourcesDto();
			dto.setServiceId(payInfoServiceId);
			dto.setUserId(userId);
			UserResourcesEntity resourcesEntity = (UserResourcesEntity) dao.query(dto, false);
			if (resourcesEntity == null) {
				CodeTable ct = CodeTable.BL_USER_RESOURCE_DOAMIN;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			int packageServiceTimes = resourcesEntity.getPackageServiceTimes();
			// int packageServiceTotalTimes =
			// resourcesEntity.getPackageServiceTotalTimes();
			int serviceTimes = resourcesEntity.getServiceTimes();
			// int serviceTotalTimes = resourcesEntity.getServiceTotalTimes();
			// int packageServiceload = packageServiceTotalTimes -
			// packageServiceTimes;
			// int serviceload = serviceTotalTimes - serviceTimes;
			// if (packageServiceload >= amont) {
			// userResourcesEntity.setPackageServiceTimes(packageServiceTimes +
			// amont);
			// } else {
			// if (amont > serviceload + packageServiceload) {
			// CodeTable ct = CodeTable.BL_USER_RESOURCE_DOAMIN;
			// throw new BlAppException(ct.getValue(), ct.getDesc());
			// } else {
			// resourcesEntity.setServiceTimes(serviceTimes + (amont -
			// packageServiceload));
			// resourcesEntity.setPackageServiceTimes(packageServiceTimes +
			// packageServiceload);
			// }
			// }
			if (packageServiceTimes < amont) {
				if (serviceTimes < amont - packageServiceTimes) {
					CodeTable ct = CodeTable.BL_USER_RESOURCE_DOAMIN;
					throw new BlAppException(ct.getValue(), ct.getDesc());
				} else {
					resourcesEntity.setServiceTimes(serviceTimes - (amont - packageServiceTimes));
					resourcesEntity.setPackageServiceTimes(0);
				}
			} else {
				resourcesEntity.setPackageServiceTimes(packageServiceTimes - amont);
			}
			resourcesEntity.setUserId(userId);
			resourcesEntity.setServiceId(payInfoServiceId);
			dao.save(resourcesEntity);
		}
	}

	/**
	 * 增加消耗资源
	 * 
	 * @param context
	 *            全局上下文
	 * @param userId
	 *            用户id
	 * @param payInfoServiceId
	 *            类型id type+1
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static void addUserServiceResources(RequestContext context, String userId, int payInfoServiceId, int amont)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		if (Str.isNullOrEmpty(userId)) {
			return;
		}
		if (amont <= 0) {
			return;
		}
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		userId = userRelationEntity.getResouseUserId(payInfoServiceId);
		synchronized (LockUtil.getUserLock(userId)) {
			SearchUserResourcesDto dto = new SearchUserResourcesDto();
			dto.setServiceId(payInfoServiceId);
			dto.setUserId(userId);
			UserResourcesEntity resourcesEntity = (UserResourcesEntity) dao.query(dto, false);
			UserResourcesEntity userResourcesEntity = new UserResourcesEntity();
			int packageServiceTimes = resourcesEntity.getPackageServiceTimes();
			// int packageServiceTotalTimes =
			// resourcesEntity.getPackageServiceTotalTimes();
			userResourcesEntity.setPackageServiceTimes(packageServiceTimes + amont);
			userResourcesEntity.setUserId(userId);
			userResourcesEntity.setServiceId(payInfoServiceId);
			dao.save(userResourcesEntity);
		}
	}

	/**
	 * 清空当前用户的套餐资源
	 * 
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void deleteUserServiceResources(String userId) throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, UserRelationResourceDao.class);
		DeleteUserResourcesDto deleteUserResourcesDto = new DeleteUserResourcesDto();
		deleteUserResourcesDto.setServiceIds(getUserAllResourcesId(userId));
		deleteUserResourcesDto.setUserIds(UserRelationManger.userRelationId(context, userId));
		dao.delete(deleteUserResourcesDto);
	}

	/**
	 * 开通会员套餐
	 * 
	 * @param memberService
	 * @param userId
	 * @param price
	 * @throws AppException
	 */
	public void proveTobeMember(PayInfoServiceEntity memberService, String userId, double price) throws AppException {
		synchronized (LockUtil.getUserLock(userId)){
		MemberEntity memberInfo = MemberManager.getMemberInfo(context, userId);
		logger.info("开始开通套餐时间");
		ZdsIntegrateManager zds = new ZdsIntegrateManager();
		zds.init(context);
		Map<Integer, PayInfoServiceInfoEntity> map = new HashMap<Integer, PayInfoServiceInfoEntity>();
		for (PayInfoServiceInfoEntity info : memberService.getInfo()) {
			map.put(info.getType(), info);
		}
		// 获取会员Dao
		Dao dao = BLContextUtil.getDao(context, MemberDao.class);
		BLContextUtil.valiDaoIsNull(dao, "套餐");
		PayInfoServiceInfoEntity info = map.get(PayInfoServiceInfoEntity.MEMBER);
		// for (PayInfoServiceInfoEntity info : memberService.getInfo()) {
		BLContextUtil.valiDomainIsNull(info, "套餐配置信息");
		if (info.getType().equals(PayInfoServiceInfoEntity.MEMBER)) {
			if (memberInfo == null) {
				// 如果没有开通过会员，则开通
				memberInfo = new MemberEntity();
				memberInfo.setMemberId(this.newMemberId());
				memberInfo.setStatus(MemberEntity.ENABLE);
				memberInfo.setStarttime(new Date());
				memberInfo.setUserId(userId);
				memberInfo.setEndtime(this.calculateEndtime(memberInfo.getStarttime(), info.getQuantity()));
				openB2b(userId, memberInfo, zds, map);
			}
			// memberInfo.setMemberId(memberId);
			else if (memberInfo.getStatus() == MemberEntity.DISABLE) {
				// 如果会员已过期，则启用并续期
				memberInfo.setStatus(MemberEntity.ENABLE);
				memberInfo.setStarttime(new Date());
				memberInfo.setUserId(userId);
				memberInfo.setEndtime(this.calculateEndtime(memberInfo.getStarttime(), info.getQuantity()));
				openB2b(userId, memberInfo, zds, map);
			} else if (memberInfo.getStatus() == MemberEntity.ENABLE) {
				Integer currentPackageId = memberInfo.getPackageServiceId();
				if (currentPackageId == null) {// 如果是前会员则从新开通 //TODO 待定
					openB2b(userId, memberInfo, zds, map);
					memberInfo.setStatus(MemberEntity.ENABLE);
					memberInfo.setStarttime(new Date());
					memberInfo.setUserId(userId);
					memberInfo.setEndtime(this.calculateEndtime(memberInfo.getStarttime(), info.getQuantity()));
				} else {
					PayInfoServiceEntity currentpackageService = getPackageService(currentPackageId);
					if (currentpackageService == null) {
						throw new BlAppException(-1, "不支持该操作");//
					}
					if (currentPackageId.equals(memberService.getId())
							&& memberInfo.getPackageServicePrice() == memberService.getPrice()) {
						openB2b(userId, memberInfo, zds, map);
						// 如果已开通会员，则续期
						memberInfo.setStatus(MemberEntity.ENABLE);
						memberInfo.setUserId(userId);
						memberInfo.setEndtime(this.calculateEndtime(memberInfo.getEndtime(), info.getQuantity()));
					} else if (memberService.getPrice() < memberInfo.getPackageServicePrice()) {// 如果等级下降则报错
						throw new BlAppException(-1, "不支持该操作");// 处理方式待定
					} else {
						// 清空用户剩余资源 已抵扣
						deleteUserServiceResources(userId);
						Date endTime = this.calculateEndtime(memberInfo.getStarttime(), info.getQuantity());
						int day = 0;
						if (!memberInfo.getEndtime().after(endTime)) {
							try {
								day = daysBetween(memberInfo.getEndtime(), endTime);
							} catch (ParseException e) {
								day = 0;
							}
						}
						zds.register(getFrontUser(userId));
						zds.openZdsCoverage(userId, day);
						PayInfoServiceInfoEntity b2btimes = map.get(PayInfoServiceInfoEntity.B2B);
						if (b2btimes != null) {
							zds.openZdsCode(userId, b2btimes.getQuantity());
						}
						// 等级上升则进行升级流程
						memberInfo.setStatus(MemberEntity.ENABLE);
						memberInfo.setStarttime(new Date());
						memberInfo.setUserId(userId);
						memberInfo.setEndtime(endTime);
					}
				}
			}
		}
		memberInfo.setPackageServiceId(memberService.getId());
		memberInfo.setPackageServicePrice(memberService.getPrice());
		// 保存会员信息
		dao.save(memberInfo);
		// 继续添加套餐其余信息
		for (PayInfoServiceInfoEntity payInfo : memberService.getInfo()) {
			if (!payInfo.getType().equals(PayInfoServiceInfoEntity.MEMBER)) {
				createPackageUserResource(context, payInfo, userId, memberInfo);
			}
		}
		}
	}

	private void openB2b(String userId, MemberEntity memberInfo, ZdsIntegrateManager zds,
			Map<Integer, PayInfoServiceInfoEntity> map) throws DaoAppException, BlAppException {
		zds.register(getFrontUser(userId));
		zds.openZdsCoverage(userId, TimeUtil.getYearSpace(memberInfo.getStarttime(), memberInfo.getEndtime()));
		PayInfoServiceInfoEntity b2btimes = map.get(PayInfoServiceInfoEntity.B2B);
		if (b2btimes != null) {
			zds.openZdsCode(userId, b2btimes.getQuantity());
		}
	}

	private FrontUserEntity getFrontUser(String userId) throws DaoAppException, BlAppException {
		Dao userDao = BLContextUtil.getDao(context, FrontuserDao.class);
		BLContextUtil.valiDaoIsNull(userDao, "前台用户");
		IdEntity<String> queryUser = new IdEntity<String>();
		queryUser.setId(userId);
		FrontUserEntity user = (FrontUserEntity) userDao.query(queryUser, false);
		return user;
	}

	/**
	 * 开通服务
	 * 
	 * @param memberService
	 * @param userId
	 * @param price
	 * @throws AppException
	 */
	public void proveTobeService(PayInfoServiceEntity memberService, String userId) throws AppException {
		// 继续添加套餐其余信息
		for (PayInfoServiceInfoEntity info : memberService.getInfo()) {
			if (PayInfoServiceInfoEntity.MEMBER != info.getType()) {
				createUserResource(info, userId);
			}
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算结束时间
	 * 
	 * @param starttime
	 *            开始时间
	 * @param quantity
	 *            数量（月数）
	 * @return
	 */
	private Date calculateEndtime(Date starttime, int quantity) {

		Calendar calender = Calendar.getInstance();
		calender.setTime(starttime);
		calender.add(Calendar.MONTH, quantity);

		return calender.getTime();
	}

	/**
	 * 生成新的会员ID
	 * 
	 * @return
	 * @throws AppException
	 */
	private String newMemberId() throws AppException {
		logger.info("获得订单ID");
		String memberId = KeyFactory.getInstance().getKey(KeyFactory.KEY_MEMBER);
		BLContextUtil.valiParaNotNull(memberId, "会员ID");
		return memberId;
	}

	public MemberStatusDto status(String userId) throws BlAppException, DaoAppException {
		logger.info("获得入参userId：" + userId);
		// 用户ID不可为空
		BLContextUtil.valiParaNotNull(userId, "用户ID");
		// 获得dao
		Dao dao = BLContextUtil.getDao(context, MemberDao.class);
		BLContextUtil.valiDaoIsNull(dao, "会员");
		// 获取用户状态并返回
		MemberStatusQCondition condition = new MemberStatusQCondition();
		condition.setUserId(userId);
		MemberStatusDto response = (MemberStatusDto) dao.query(condition, false);

		return response;
	}

	public static MemberEntity getMemberInfo(RequestContext context, String userId)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, MemberDao.class);
		MemberEntity memberEntity = null;
		// 获取该userId的会员信息
		GetMemberInfoByUserIdQCondition condition = new GetMemberInfoByUserIdQCondition();
		condition.setUserId(userId);
		memberEntity = (MemberEntity) dao.query(condition, false);

		return memberEntity;
	}

}
