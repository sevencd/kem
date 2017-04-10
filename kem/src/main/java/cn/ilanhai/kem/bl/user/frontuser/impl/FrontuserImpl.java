package cn.ilanhai.kem.bl.user.frontuser.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.consts.ModuleCode;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.RedisSession;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.SessionFactory;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.imgvalicode.ImgValicodeManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.bl.session.SessionImplManger;
import cn.ilanhai.kem.bl.sms.SmsValicodeManager;
import cn.ilanhai.kem.bl.user.frontuser.Frontuser;
import cn.ilanhai.kem.bl.user.frontuser.FrontuserManger;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.dao.user.backuser.BackuserDao;
import cn.ilanhai.kem.dao.user.backuser.frontuser.BackuserManagerFrontDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.dao.userRelation.UserRelationResourceDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.MapEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.member.UserResourcesEntity;
import cn.ilanhai.kem.domain.member.dto.SearchUserResourcesDto;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoLoadEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.IsExistResponseDto;
import cn.ilanhai.kem.domain.user.frontuser.MyMarketingDto;
import cn.ilanhai.kem.domain.user.frontuser.QueryByPhoneNoConditionEntity;
import cn.ilanhai.kem.domain.user.frontuser.UpdateUserInfoRequestDto;
import cn.ilanhai.kem.domain.user.frontuser.UpdateUserLoginPwdDto;
import cn.ilanhai.kem.domain.user.frontuser.UserInfoDtoResponse;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginRequestDto;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginResponseDto;
import cn.ilanhai.kem.domain.user.frontuser.dto.LoadUserInfoDto;
import cn.ilanhai.kem.domain.user.frontuser.dto.QueryFrontUserByCompany;
import cn.ilanhai.kem.domain.user.manageuser.QueryCondition.GetUserDetailQCondition;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.domain.userRelation.dto.DeleteUserRelationDto;
import cn.ilanhai.kem.domain.userRelation.dto.SaveAccountDto;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.event.args.FrontUserLoginEvent;
import cn.ilanhai.kem.event.args.FrontUserRegistEvent;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.modules.work.BoolWorkState;
import cn.ilanhai.kem.modules.work.RegistWork;
import cn.ilanhai.kem.modules.work.RegistsetpwdWork;
import cn.ilanhai.kem.modules.work.Work;
import cn.ilanhai.kem.modules.work.WorkFactory;
import cn.ilanhai.kem.util.LockUtil;
import cn.ilanhai.kem.util.MD5Util;
import cn.ilanhai.kem.util.StringVerifyUtil;
import cn.ilanhai.kem.util.TimeUtil;

@Component("frontuser")
public class FrontuserImpl extends BaseBl implements Frontuser {

	private Logger logger = Logger.getLogger(FrontuserImpl.class);

	private SmsValicodeManager smsValicodeManager = new SmsValicodeManager(this.daoProxyFactory);
	private ImgValicodeManager imgValicodeManager = new ImgValicodeManager(this.daoProxyFactory);
	private final String EMAIL_REGULAR = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private final String PHONE_REGULAR = "^(((13[0-9])|(14[57])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}|(\\d{2,4}-?)?\\d{7,8})$";
	private final String ZIPCODE_REGULAR = "^([1-9]\\d{5}(?!\\d))$";

	/**
	 * 验证参数的正确性
	 * 
	 * @param context
	 * @throws BlAppException
	 */
	private Session getSessionAndCheckPreamer(RequestContext context) throws SessionContainerException, BlAppException {
		Session session = context.getSession();
		if (session == null) {
			throw new BlAppException(-1, "session无效");
		}

		String phoneNo = (String) session.getParameter(Constant.KEY_SESSION_PHONENO);

		// 电话号码合法
		if (!ExpressionMatchUtil.isPhoneNo(phoneNo)) {
			throw new BlAppException(-1, "电话号码错误");
		}
		return session;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity exist(RequestContext context) throws BlAppException, DaoAppException {
		return new IsExistResponseDto(isExist(context));
	}

	private boolean isExist(RequestContext context) throws BlAppException, DaoAppException {
		// 获取入参
		String phoneNo = context.getDomain(String.class);
		return FrontuserManger.isExist(context, phoneNo);
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean regist(RequestContext context) throws SessionContainerException, BlAppException, DaoAppException {
		CodeTable ct;
		// 获取入参
		FrontUserEntity request = context.getDomain(FrontUserEntity.class);
		if (request == null) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}

		if (request.getLoginPwd() == null || request.getLoginPwd().isEmpty()) {
			throw new BlAppException(-1, "密码不能为空");
		}
		// 电话号码合法
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {
			throw new BlAppException(-1, "电话号码错误");
		}
		Work registWork = WorkFactory.createWork(RegistWork.REGISTWORK);
		// 校验短信验证码
		// 获取短信验证码的recordId
		Integer recordId = (Integer) context.getSession()
				.getParameter(ModuleCode.FRONTUSER_RESGIST.toString() + "sms_code" + request.getPhoneNo());

		// 如果验证码未进行校验，则进行校验
		if (smsValicodeManager.getStatus(request.getSmsCode(), recordId, context).equals(ValidateStatus.NOT_VALIDATE)) {
			// 校验验证码
			smsValicodeManager.verify(request.getSmsCode(), recordId, context);
		}
		registWork.setState(new BoolWorkState("smsValicode", true));
		if (FrontuserManger.isExist(context, request.getPhoneNo())) {
			ct = CodeTable.BL_FRONTUSER_PHONE_REPEAT;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		// 调用app方法处理参数
		// 不存在已注册电话号才可以进行注册
		registWork.setState(new BoolWorkState("phoneNoExist", true));

		if (!registWork.isFinished()) {
			ct = CodeTable.BL_WORK_ISNOT_FINISHED;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		// 获取数据库资源
		Dao dao = this.daoProxyFactory.getDao(context);

		// 获取userId
		request.setUserID(KeyFactory.newKey(KeyFactory.KEY_USER));
		// 注册后启用该用户
		request.setStatus(UserStatus.ENABLE);
		// 注册后用户类型为普通用户
		request.setUserType(UserType.GENERAL_USER);
		// 创建时间
		request.setCreatetime(new Date());
		request.modifyLoginPwd(request.getLoginPwd());

		List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
		FrontUserInfoEntity frontUserInfoEntity_phone = new FrontUserInfoEntity();
		frontUserInfoEntity_phone.setUserID(request.getUserID());
		frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
		frontUserInfoEntity_phone.setInfo(request.getPhoneNo());
		frontUserInfoEntity_phone.setInfoState(request.getStatus().getValue());
		infos.add(frontUserInfoEntity_phone);
		FrontUserInfoEntity frontUserInfoEntity_name = new FrontUserInfoEntity();
		frontUserInfoEntity_name.setUserID(request.getUserID());
		frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
		frontUserInfoEntity_name.setInfo(request.getPhoneNo());
		frontUserInfoEntity_name.setInfoState(UserStatus.ENABLE.getValue());
		infos.add(frontUserInfoEntity_name);
		request.setInfos(infos);

		// 保存用户信息
		boolean result = dao.save(request) == 1 ? true : false;

		if (result) {
			FrontUserRegistEvent frontUserRegistEvent = new FrontUserRegistEvent();
			frontUserRegistEvent.setEmail(request.getEmail());
			frontUserRegistEvent.setPhoneNo(request.getPhoneNo());
			frontUserRegistEvent.setUserId(request.getUserID());
			frontUserRegistEvent.setUsername(request.getUserName());
			frontUserRegistEvent.setUserType(request.getUserType());

			try {
				DomainEventManager.getInstance().raise(context, frontUserRegistEvent);
			} catch (JMSAppException e) {
				throw new BlAppException(-1, "事件发布失败");
			}
		}

		return result;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public List<Entity> saveaccount(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		CodeTable ct;
		boolean result = false;
		try {
			checkFrontUserLogined(context);
			// 验证是否为主账户
			UserRelationManger.checkMainUser(context);
			// 获取入参
			SaveAccountDto account = context.getDomain(SaveAccountDto.class);
			this.valiPara(account);
			Integer state = account.getState();// 子账户状态
			UserStatus userStatus = UserStatus.getUserStatus(state);
			if (userStatus == null || UserStatus.Trial.equals(userStatus)) {
				userStatus = UserStatus.ENABLE;
			}
			String userId = account.getUserId();// 账户id
			String pwd = account.getPwd();// 子账户密码
			if (!Str.isNullOrEmpty(pwd)) {
				StringVerifyUtil.pwdVerify(pwd);// 验证密码
			}
			String phone = account.getPhone();// 获取子账户手机号
			synchronized (LockUtil.getUserLock(getSessionUserId(context))) {
				if (Str.isNullOrEmpty(userId)) {
					// 电话号码合法
					if (!ExpressionMatchUtil.isPhoneNo(phone)) {
						ct = CodeTable.BL_BACKTUSER_LOGINNAME_ERROR;
						throw new BlAppException(ct.getValue(), "电话号码格式错误");
					}
					userId = KeyFactory.newKey(KeyFactory.KEY_USER);
					if (FrontuserManger.isExist(context, phone)) {
						ct = CodeTable.BL_FRONTUSER_PHONE_REPEAT;
						throw new BlAppException(ct.getValue(), ct.getDesc());
					}
					// 创建子账户
					result = createSubUser(context, account, userStatus, userId, getSessionUserId(context));
				} else if (UserRelationManger.userRelationIds(context, null).contains(userId)) {
					// 修改子账户
					result = modiySubUser(context, account, userId, userStatus);
				} else {
					// 电话号码合法
					if (!ExpressionMatchUtil.isPhoneNo(phone)) {
						ct = CodeTable.BL_BACKTUSER_LOGINNAME_ERROR;
						throw new BlAppException(ct.getValue(), "电话号码格式错误");
					}
					userId = KeyFactory.newKey(KeyFactory.KEY_USER);
					if (FrontuserManger.isExist(context, phone)) {
						ct = CodeTable.BL_FRONTUSER_PHONE_REPEAT;
						throw new BlAppException(ct.getValue(), ct.getDesc());
					}
					// 创建子账户
					result = createSubUser(context, account, userStatus, userId, getSessionUserId(context));
				}
				// 创建子账户关系 默认关系
				if (result) {
					UserRelationManger.saveSubUserRelation(context, userStatus, userId);
				}
				return UserRelationManger.userRelations(context, null);
			}
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public int deleteaccounts(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		CodeTable ct;
		try {
			checkFrontUserLogined(context);
			UserRelationManger.checkMainUser(context);
			String userId = getSessionUserId(context);
			synchronized (LockUtil.getUserLock(userId)) {
				// 获取入参
				DeleteUserRelationDto dto = context.getDomain(DeleteUserRelationDto.class);
				this.valiPara(dto);
				dto.setUserId(this.getSessionUserId(context));
				int val = UserRelationManger.deleteUserRelation(context, dto);
				MemberManager.addUserServiceResources(context, userId,
						PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.SUBACCOUNT), val);
				return val;
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadaccounts(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		CodeTable ct;
		try {
			checkFrontUserLogined(context);
			LoadUserInfoDto result = new LoadUserInfoDto();
			Integer state = context.getDomain(Integer.class);
			String userId = getSessionUserId(context);
			Integer serviceId = PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.SUBACCOUNT);
			UserRelationEntity userRelationEntity = UserRelationManger.currentUserRelation(context);
			userId = userRelationEntity.getResouseUserId(serviceId);
			SearchUserResourcesDto dto = new SearchUserResourcesDto();
			dto.setServiceId(serviceId);
			dto.setUserId(userId);
			UserResourcesEntity resourcesEntity = (UserResourcesEntity) BLContextUtil
					.getDao(context, UserRelationResourceDao.class).query(dto, false);
			result.setLists(UserRelationManger.userRelations(context, state));
			result.setSubUserCount(resourcesEntity == null ? 0
					: resourcesEntity.getPackageServiceTimes() + resourcesEntity.getServiceTimes());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	/**
	 * 修改子账户
	 * 
	 * @param context
	 * @param account
	 * @param userId
	 * @param pwd
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private boolean modiySubUser(RequestContext context, SaveAccountDto account, String userId, UserStatus status)
			throws BlAppException, DaoAppException {
		CodeTable ct;
		boolean result;
		Dao dao = this.daoProxyFactory.getDao(context);
		this.valiDaoIsNull(dao, "前端用户");
		IdEntity<String> idDto = null;
		idDto = new IdEntity<String>();
		idDto.setId(userId);
		FrontUserEntity frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
		this.valiDomainIsNull(frontUserEntity, "前端用户");
		String pwd = account.getPwd();
		if (!Str.isNullOrEmpty(pwd)) {
			String newPwd = MD5Util.getEncryptedPwd(pwd);
			if (frontUserEntity.getLoginPwd().equals(newPwd)) {
				ct = CodeTable.BL_FRONTUSER_OLDEQUSENEW_PASSWORD;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			// 修改密码
			frontUserEntity.setLoginPwd(newPwd);
		}
		frontUserEntity.setStatus(status);
		result = dao.save(frontUserEntity) == 1 ? true : false;
		return result;
	}

	private boolean createSubUser(RequestContext context, SaveAccountDto account, UserStatus userStatus, String userId,
			String fatherId) throws DaoAppException, BlAppException {
		boolean result;
		// 获取数据库资源
		Dao dao = this.daoProxyFactory.getDao(context);
		String phone = account.getPhone();
		String pwd = account.getPwd();
		FrontUserEntity request = new FrontUserEntity();
		// 获取userId
		request.setUserID(userId);
		// 注册后启用该用户
		request.setStatus(userStatus);
		// 注册后用户类型为普通用户
		request.setUserType(UserType.GENERAL_USER);
		// 创建时间
		request.setCreatetime(new Date());
		request.setPhoneNo(phone);
		request.modifyLoginPwd(pwd);
		List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
		FrontUserInfoEntity frontUserInfoEntity_phone = new FrontUserInfoEntity();
		frontUserInfoEntity_phone.setUserID(userId);
		frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
		frontUserInfoEntity_phone.setInfo(phone);
		frontUserInfoEntity_phone.setInfoState(userStatus.getValue());
		infos.add(frontUserInfoEntity_phone);
		FrontUserInfoEntity frontUserInfoEntity_name = new FrontUserInfoEntity();
		frontUserInfoEntity_name.setUserID(userId);
		frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
		frontUserInfoEntity_name.setInfo(phone);
		frontUserInfoEntity_name.setInfoState(userStatus.getValue());
		infos.add(frontUserInfoEntity_name);
		request.setInfos(infos);
		// 保存用户信息
		result = dao.save(request) == 1 ? true : false;
		if (result) {
			// 消耗子账号次数
			MemberManager.useUserServiceResources(context, fatherId,
					PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.SUBACCOUNT), 1);
		}
		return result;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean resetpwdcheckidentity(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		// 获取入参
		FrontUserEntity request = context.getDomain(FrontUserEntity.class);
		CodeTable ct;
		if (request == null) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		if (request.getImgCode() == null || request.getImgCode().isEmpty()) {
			throw new BlAppException(-1, "验证码不能为空");
		}
		if (request.getSmsCode() == null || request.getSmsCode().isEmpty()) {
			throw new BlAppException(-1, "短信验证码不能为空");
		}
		// 电话号码合法
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {
			throw new BlAppException(-1, "电话号码错误");
		}
		// 获取session 并检测参数
		Session session = context.getSession();
		if (session == null) {
			throw new BlAppException(-1, "session无效");
		}

		// 获取短信验证码的recordId

		Integer smsRecordId = (Integer) context.getSession()
				.getParameter(ModuleCode.FORGET_PWD.toString() + "sms_code" + request.getPhoneNo());

		// 校验验证码
		smsValicodeManager.verify(request.getSmsCode(), smsRecordId, context);

		// 获取短信验证码的recordId
		Integer imgRecordId = (Integer) context.getSession()
				.getParameter(ModuleCode.FORGET_PWD.toString() + "img_code");
		imgValicodeManager.verify(request.getImgCode(), imgRecordId, context);
		session.setParameter(Constant.KEY_SESSION_PHONENO, request.getPhoneNo());

		return true;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean resetpwdsetpwd(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		Work registsetpwdWork = WorkFactory.createWork(RegistsetpwdWork.REGISTSETPWDWORK);
		CodeTable ct;
		// 获取入参
		FrontUserEntity request = context.getDomain(FrontUserEntity.class);
		if (request == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_NOT_NULL;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		// 获取session 并检测参数
		Session session = getSessionAndCheckPreamer(context);
		String phoneNo = (String) session.getParameter(Constant.KEY_SESSION_PHONENO);
		// 获取短信验证码的recordId
		Integer smsRecordId = (Integer) context.getSession()
				.getParameter(ModuleCode.FORGET_PWD.toString() + "sms_code" + phoneNo);
		// 如果验证码未进行校验，则进行校验
		boolean smsCodeState = false;
		boolean imgCodeState = false;
		if (smsValicodeManager.getStatus("", smsRecordId, context).equals(ValidateStatus.VERIFIED)) {
			smsCodeState = true;
		}
		// 获取短信验证码的recordId
		Integer imgRecordId = (Integer) context.getSession()
				.getParameter(ModuleCode.FORGET_PWD.toString() + "img_code");
		// 如果验证码未进行校验，则进行校验
		if (imgValicodeManager.getStatus("", imgRecordId, context).equals(ValidateStatus.VERIFIED)) {
			imgCodeState = true;
		}
		// 获取电话号码
		registsetpwdWork.setState(new BoolWorkState("smsValicode", smsCodeState));
		registsetpwdWork.setState(new BoolWorkState("imgValicode", imgCodeState));
		StringVerifyUtil.pwdVerify(request.getNewPwd());

		if (!registsetpwdWork.isFinished()) {
			ct = CodeTable.BL_WORK_ISNOT_FINISHED;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		// 获取需要修改密码的电话号码
		request.setPhoneNo(phoneNo);
		// 获取数据库资源
		Dao dao = this.daoProxyFactory.getDao(context);
		FrontUserEntity oldRequest = (FrontUserEntity) dao.query(request, true);
		this.valiDomainIsNull(oldRequest, "无效用户", false);
		oldRequest.modifyLoginPwd(request.getNewPwd());
		// 保存用户信息
		return dao.save(oldRequest) == 1 ? true : false;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public UserLoginResponseDto userlogin(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {

		logger.info("开始用户前台用户登录处理");
		Dao dao = this.daoProxyFactory.getDao(context);
		// 获取入参
		UserLoginRequestDto request = context.getDomain(UserLoginRequestDto.class);
		UserLoginResponseDto response = new UserLoginResponseDto();

		logger.info("开始用户请求校验");
		// 请求内容必须存在
		if (request == null) {

			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(),
					CodeTable.BL_FRONTUSER_LOGIN_ERROR.getDesc());
		}
		logger.info("校验当前会话状态");
		RedisSession session = (RedisSession) context.getSession();
		// 判断会话状态，用户是否可以登录
		BaseSessionState currentState = session.getSessionState();

		BaseSessionState destState = SessionFactory
				.createSessionState(SessionStateType.stringtoEnum(RedisSession.STATE_FRONTUSER_LOGINED));
		String userNumber = request.getPhoneNo();
		FrontUserEntity user = null;
		if (!Str.isNullOrEmpty(userNumber) && userNumber.contains(":")) {
			// 企业用户登录
			user = companyLogin(context, request);
		} else {
			// 个人用户登录
			user = frontUserLogin(context, dao, request);
		}
		// 校验用户密码
		if (user.verifyUser(request.getLoginPwd())) {
			logger.info("用户可登陆");

			if (!destState.verify(context.getQueryString("ClientType"), currentState)) {
				SessionImplManger.logout(context);
			}

			session.setSessionState(destState);

			session.setParameter(Constant.KEY_SESSION_USERID, user.getUserID());
			session.setParameter(Constant.KEY_SESSION_USERTYPE, user.getUserType());
			UserRelationEntity userRelationEntity = UserRelationManger.currentUserRelation(context);
			if (userRelationEntity.getUserType() == 1) {
				MemberEntity memberEntity = MemberManager.getMemberInfo(context, userRelationEntity.getFatherUserId());
				if (memberEntity == null || memberEntity.getStatus() == 0) {
					throw new BlAppException(CodeTable.BL_COMMON_USER_MEMBER.getValue(),
							CodeTable.BL_COMMON_USER_MEMBER.getDesc());
				}
				// 查询用户
				FrontUserEntity frontUserEntity = FrontuserManger.getUser(context,
						userRelationEntity.getFatherUserId());
				if (frontUserEntity == null) {
					throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "主账户未注册");
				}
				if (frontUserEntity.getStatus().equals(UserStatus.DISABLE)) {
					throw new BlAppException(CodeTable.BL_COMMON_USER_DISABLE.getValue(), "主账户已禁用，无法登陆");
				}
				if (frontUserEntity.getStatus().equals(UserStatus.Trial)) {
					throw new BlAppException(CodeTable.BL_COMMON_USER_TRIAL.getValue(), "主账户还在审核中,请耐心等待");
				}
			}
			// 获取用户详情
			MapEntity userDetail = this.getUserDetail(context, user.getUserID());

			response.setUserID(user.getUserID());
			response.setEmail(user.getEmail());
			response.setUsername(user.getUserName());
			response.setUserType(user.getUserType().getValue());
			response.setPhoneNo(user.getPhoneNo());
			response.setStatus(user.getStatus());
			response.setHasPwd(true);
			response.setCompanyCode(user.getCompanyCode());
			response.setMemberStatus(userDetail.getData().get("memberStatus").toString());
			if (user.getLoginPwd() == null || user.getLoginPwd().length() <= 0)
				response.setHasPwd(false);
			/*
			 * response.setUserName(user.getUserName());
			 * response.setUserType(user.getUserType());
			 */
			FrontUserLoginEvent frontUserLoginEvent = new FrontUserLoginEvent();
			frontUserLoginEvent.setEmail(user.getEmail());
			frontUserLoginEvent.setPhoneNo(user.getPhoneNo());
			frontUserLoginEvent.setUserId(user.getUserID());
			frontUserLoginEvent.setUsername(user.getUserName());
			frontUserLoginEvent.setUserType(user.getUserType());

			try {
				DomainEventManager.getInstance().raise(context, frontUserLoginEvent);
			} catch (JMSAppException e) {
				throw new BlAppException(-1, "事件发布失败");
			}

			logger.info("用户登录成功");
			return response;
		} else {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "用户名密码错误");
		}

	}

	private FrontUserEntity frontUserLogin(RequestContext context, Dao dao, UserLoginRequestDto request)
			throws BlAppException, DaoAppException {
		FrontUserEntity user;
		// 校验电话号码
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {

			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "电话号码错误");

		}
		// 校验登录密码
		if (request.getLoginPwd() == null || request.getLoginPwd().isEmpty()) {

			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "密码不可为空");

		}

		// 查询用户
		QueryByPhoneNoConditionEntity queryContdition = new QueryByPhoneNoConditionEntity();
		queryContdition.setPhoneNo(request.getPhoneNo());
		user = (FrontUserEntity) dao.query(queryContdition, true);
		if (user == null) {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "用户未注册");
		}
		if (user.getStatus().equals(UserStatus.DISABLE)) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_DISABLE.getValue(), "用户已禁用，无法登陆");
		}
		if (user.getStatus().equals(UserStatus.Trial)) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_TRIAL.getValue(), "用户还在审核中,请耐心等待");
		}
		// 验证是否过期
		if (new Date().after(TimeUtil.calculateEndtime(FrontuserManger.getMemberEndTime(context, user), 15))) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_TRIALMEMBER.getValue(), "版本已过期");
		}
		return user;
	}

	private MapEntity getUserDetail(RequestContext context, String userId) throws DaoAppException, BlAppException {
		Dao dao = null;
		dao = BLContextUtil.getDao(context, BackuserManagerFrontDao.class);
		this.valiDaoIsNull(dao, "加载用户信息");

		GetUserDetailQCondition condition = new GetUserDetailQCondition();
		condition.setUserId(userId);

		// 组织返回值
		MapEntity response = (MapEntity) dao.query(condition, false);
		// 返回值不可为空
		BLContextUtil.valiDomainIsNull(response, "用户信息");

		return response;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity currentuserinfo(RequestContext context) throws BlAppException, DaoAppException {
		IdEntity<String> idDto = null;
		FrontUserEntity frontUserEntity = null;
		CodeTable ct;
		Dao dao = null;
		UserInfoDtoResponse userInfoDto = null;
		String id = "";
		try {
			this.logger.debug("beign currentuserinfo");
			this.checkFrontUserLogined(context);
			id = this.getSessionUserId(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "前端用户");
			idDto = new IdEntity<String>();
			idDto.setId(id);
			frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
			this.valiDomainIsNull(frontUserEntity, "前端用户");
			userInfoDto = new UserInfoDtoResponse();
			userInfoDto.setEmail(frontUserEntity.getEmail());
			userInfoDto.setPhoneNo(frontUserEntity.getPhoneNo());
			userInfoDto.setUsername(frontUserEntity.getUserName());
			userInfoDto.setUserType(frontUserEntity.getUserType().getValue());
			userInfoDto.setUserId(frontUserEntity.getUserID());
			userInfoDto.setHasPwd(true);
			userInfoDto.setUserState(frontUserEntity.getStatus().getValue());

			userInfoDto.setCompany(FrontuserManger.getUserInfoByType(context, UserInfoType.COMPANY, id));
			userInfoDto.setCompanyAddress(FrontuserManger.getUserInfoByType(context, UserInfoType.COMPANYADDRESS, id));
			userInfoDto.setCompanyPhoneNo(FrontuserManger.getUserInfoByType(context, UserInfoType.COMPANYPHONENO, id));
			userInfoDto.setContact(FrontuserManger.getUserInfoByType(context, UserInfoType.CONTACT, id));
			userInfoDto.setContactPhoneNo(FrontuserManger.getUserInfoByType(context, UserInfoType.CONTACTPHONENO, id));
			userInfoDto.setZipCode(FrontuserManger.getUserInfoByType(context, UserInfoType.ZIPCODE, id));
			UserRelationEntity userRelationEntity = UserRelationManger.currentUserRelation(context);
			userInfoDto.setUserRelation(userRelationEntity.getUserId().equals(userRelationEntity.getFatherUserId()));
			MemberEntity memberEntity = MemberManager.getMemberInfo(context, id);
			if (memberEntity == null) {
				userInfoDto.setUserPackageId(0);
				userInfoDto.setUserPackageName(null);
				userInfoDto.setUserTrial(true);
			} else {
				Integer packageId = memberEntity.getPackageServiceId();
				PayInfoServiceEntity payInfoServiceEntity = null;
				if (packageId != null) {
					payInfoServiceEntity = OrderManager.getPackageServiceInfoById(context, packageId);
				}
				if (payInfoServiceEntity != null) {
					userInfoDto.setUserPackageId(packageId);
					userInfoDto.setUserPackageName(payInfoServiceEntity.getName());
					userInfoDto.setUserTrial(false);
				} else {

					GetUserDetailQCondition condition = new GetUserDetailQCondition();
					condition.setUserId(id);
					// 组织返回值
					MapEntity userDetail = (MapEntity) BLContextUtil.getDao(context, BackuserDao.class).query(condition,
							false);
					if (userDetail != null && userDetail.getData() != null) {
						Object memberType = userDetail.getData().get("packageServiceId");
						Integer memberState = (Integer) userDetail.getData().get("memberState");
						if (memberType == null) {
							if ((int) userDetail.getData().get("status") == UserStatus.Trial.getValue()) {
								userInfoDto.setUserPackageName(MemberType.ApplyTrial.getName());
							} else {
								userInfoDto.setUserPackageName(MemberType.TrialEdition.getName());
							}
							userInfoDto.setUserTrial(true);
						} else {
							if (memberState.equals(0)) {
								userInfoDto.setUserPackageName(MemberType.TrialEdition.getName());
								userInfoDto.setUserTrial(true);
							} else {
								userInfoDto.setUserPackageName(userDetail.getData().get("memberType").toString());
								userInfoDto.setUserTrial(false);
							}
						}
						userInfoDto.setUserPackageId((Integer) (memberType == null ? 0 : memberType));
					}
				}
				PayInfoLoadEntity request = new PayInfoLoadEntity();
				request.setId(packageId);
				dao = BLContextUtil.getDao(context, PaymentServiceDao.class);
				this.valiDaoIsNull(dao, "付费信息配置");
				PayInfoResponseEntity response = (PayInfoResponseEntity) dao.query(request, false);
				userInfoDto.setPayInfo(response);
			}

			if (frontUserEntity.getLoginPwd() == null || frontUserEntity.getLoginPwd().length() <= 0)
				userInfoDto.setHasPwd(false);
			this.logger.debug("end currentuserinfo");

			return userInfoDto;

		} catch (BlAppException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean modifycurrentuserinfo(RequestContext context) throws BlAppException, DaoAppException {
		UpdateUserInfoRequestDto requestDto = null;
		FrontUserEntity frontUserEntity = null;
		CodeTable ct;
		Dao dao = null;
		String id = null;
		QueryByPhoneNoConditionEntity queryByPhoneNoConditionEntity = null;
		try {
			this.logger.debug("begin modifycurrentuserinfo");
			this.checkFrontUserLogined(context);
			id = this.getSessionUserId(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "前端用户");
			IdEntity<String> idDto = null;
			idDto = new IdEntity<String>();
			idDto.setId(id);
			frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
			this.valiDomainIsNull(frontUserEntity, "前端用户");
			// 信息列表
			List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
			requestDto = context.getDomain(UpdateUserInfoRequestDto.class);
			this.valiPara(requestDto);
			frontUserEntity.setInfos(infos);
			// info 表添加用户名
			if (requestDto.getUsername() != null && requestDto.getUsername().length() > 0) {
				this.valiParaItemStrLength(requestDto.getUsername(), 20, "用户名");
				valiName(requestDto.getUsername(), "用户名", 20);
				FrontUserInfoEntity frontUserInfoEntity_name = new FrontUserInfoEntity();
				frontUserInfoEntity_name.setUserID(id);
				frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
				frontUserInfoEntity_name.setInfo(requestDto.getUsername());
				frontUserInfoEntity_name.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_name);
			}

			// info 表添加邮件
			if (requestDto.getEmail() != null && requestDto.getEmail().length() > 0) {
				this.valiParaItemStrLength(requestDto.getEmail(), 100, "邮件");
				this.valiParaItemStrRegular(EMAIL_REGULAR, requestDto.getEmail(), "邮件");

				FrontUserInfoEntity frontUserInfoEntity_email = new FrontUserInfoEntity();
				frontUserInfoEntity_email.setUserID(id);
				frontUserInfoEntity_email.setInfoType(UserInfoType.EMAIL.getValue());
				frontUserInfoEntity_email.setInfo(requestDto.getEmail());
				frontUserInfoEntity_email.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_email);
			}

			// info 表公司名称
			String companyName = requestDto.getCompany();
			if (!Str.isNullOrEmpty(companyName)) {
				this.valiParaItemStrLength(companyName, 50, "公司名称");
				FrontUserInfoEntity frontUserInfoEntity_companyName = new FrontUserInfoEntity();
				frontUserInfoEntity_companyName.setUserID(id);
				frontUserInfoEntity_companyName.setInfoType(UserInfoType.COMPANY.getValue());
				frontUserInfoEntity_companyName.setInfo(companyName);
				frontUserInfoEntity_companyName.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_companyName);
			}

			// info 表公司电话
			String companyPhone = requestDto.getCompanyPhoneNo();
			if (!Str.isNullOrEmpty(companyPhone)) {
				this.valiParaItemStrRegular(PHONE_REGULAR, companyPhone, "公司电话");
				FrontUserInfoEntity frontUserInfoEntity_companyPhone = new FrontUserInfoEntity();
				frontUserInfoEntity_companyPhone.setUserID(id);
				frontUserInfoEntity_companyPhone.setInfoType(UserInfoType.COMPANYPHONENO.getValue());
				frontUserInfoEntity_companyPhone.setInfo(companyPhone);
				frontUserInfoEntity_companyPhone.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_companyPhone);
			}
			// info 表公司地址
			String companyAddress = requestDto.getCompanyAddress();
			if (!Str.isNullOrEmpty(companyAddress)) {
				this.valiParaItemStrLength(companyAddress, 50, "公司地址");
				FrontUserInfoEntity frontUserInfoEntity_companyAddress = new FrontUserInfoEntity();
				frontUserInfoEntity_companyAddress.setUserID(id);
				frontUserInfoEntity_companyAddress.setInfoType(UserInfoType.COMPANYADDRESS.getValue());
				frontUserInfoEntity_companyAddress.setInfo(companyAddress);
				frontUserInfoEntity_companyAddress.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_companyAddress);
			}

			// info 表公司邮编
			String zipCode = requestDto.getZipCode();
			if (!Str.isNullOrEmpty(zipCode)) {
				this.valiParaItemStrRegular(ZIPCODE_REGULAR, zipCode, "公司邮编");
				FrontUserInfoEntity frontUserInfoEntity = new FrontUserInfoEntity();
				frontUserInfoEntity.setUserID(id);
				frontUserInfoEntity.setInfoType(UserInfoType.ZIPCODE.getValue());
				frontUserInfoEntity.setInfo(zipCode);
				frontUserInfoEntity.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity);
			}

			// info 表公司联系人
			String contact = requestDto.getContact();
			if (!Str.isNullOrEmpty(contact)) {
				valiName(contact, "联系人", 20);
				FrontUserInfoEntity frontUserInfoEntity = new FrontUserInfoEntity();
				frontUserInfoEntity.setUserID(id);
				frontUserInfoEntity.setInfoType(UserInfoType.CONTACT.getValue());
				frontUserInfoEntity.setInfo(contact);
				frontUserInfoEntity.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity);
			}

			// info 表公司联系人电话
			String contactPhone = requestDto.getContactPhoneNo();
			if (!Str.isNullOrEmpty(contactPhone)) {
				this.valiParaItemStrRegular(PHONE_REGULAR, contactPhone, "联系人电话");
				FrontUserInfoEntity frontUserInfoEntity = new FrontUserInfoEntity();
				frontUserInfoEntity.setUserID(id);
				frontUserInfoEntity.setInfoType(UserInfoType.CONTACTPHONENO.getValue());
				frontUserInfoEntity.setInfo(contactPhone);
				frontUserInfoEntity.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity);
			}

			// userSource =
			// context.getSession().getParameter(Constant.KEY_SESSION_USERSOURCE,
			// String.class);
			// if (userSource != null && userSource.length() > 0) {
			String ePhone = frontUserEntity.getPhoneNo();
			String rPhone = requestDto.getPhoneNo();
			if (rPhone != null && rPhone.length() > 0)
				if (!ExpressionMatchUtil.isPhoneNo(rPhone))
					throw new BlAppException(-1, "电话号码错误");
			if (ePhone == null || ePhone.length() <= 0) {
				if (rPhone != null && rPhone.length() > 0) {
					FrontUserEntity temp = null;
					queryByPhoneNoConditionEntity = new QueryByPhoneNoConditionEntity();
					queryByPhoneNoConditionEntity.setPhoneNo(rPhone);
					temp = (FrontUserEntity) dao.query(queryByPhoneNoConditionEntity, false);
					if (temp != null)
						throw new BlAppException(-1, "电话号码已被使用");
				}
			}
			if (ePhone != null && ePhone.length() > 0)
				if (rPhone != null && rPhone.length() > 0)
					if (!ePhone.equals(rPhone))
						throw new BlAppException(-1, "您的输入电话号与存在的电话号不相等");

			if (Str.isNullOrEmpty(ePhone)) {
				// 更新标识
				frontUserEntity.setPhoneNo(rPhone);

				// 更新info
				FrontUserInfoEntity frontUserInfoEntity_phone = new FrontUserInfoEntity();
				frontUserInfoEntity_phone.setUserID(frontUserEntity.getUserID());
				frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
				frontUserInfoEntity_phone.setInfo(frontUserEntity.getPhoneNo());
				frontUserInfoEntity_phone.setInfoState(frontUserEntity.getStatus().getValue());
				infos.add(frontUserInfoEntity_phone);
			}
			// }

			frontUserEntity.setEmail(requestDto.getEmail());
			frontUserEntity.setUserName(requestDto.getUsername());
			int val = dao.save(frontUserEntity);
			this.valiSaveDomain(val, "前端用户");
			this.logger.debug("end modifycurrentuserinfo");
			return true;
		} catch (BlAppException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean modifycurrentuserloginpwd(RequestContext context) throws BlAppException, DaoAppException {
		UpdateUserLoginPwdDto requestDto = null;
		FrontUserEntity frontUserEntity = null;
		CodeTable ct;
		Dao dao = null;
		String id = null;
		String userSource = null;
		try {
			this.logger.debug("begin modifycurrentuserloginpwd");
			this.checkFrontUserLogined(context);
			id = this.getSessionUserId(context);
			requestDto = context.getDomain(UpdateUserLoginPwdDto.class);
			this.valiPara(requestDto);
			userSource = context.getSession().getParameter(Constant.KEY_SESSION_USERSOURCE, String.class);
			if (userSource == null || userSource.length() <= 0) {
				this.valiParaItemStrNullOrEmpty(requestDto.getOldLoginpwd(), "旧密码");
				this.valiParaItemNumBetween(6, 20, requestDto.getOldLoginpwd().length(), "旧密码");
			}
			this.valiParaItemStrNullOrEmpty(requestDto.getNewLoginpwd(), "新密码");
			this.valiParaItemNumBetween(6, 20, requestDto.getNewLoginpwd().length(), "新密码");
			StringVerifyUtil.pwdVerify(requestDto.getNewLoginpwd());
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "前端用户");
			IdEntity<String> idDto = null;
			idDto = new IdEntity<String>();
			idDto.setId(id);
			frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
			this.valiDomainIsNull(frontUserEntity, "前端用户");
			String userLoginPwd = frontUserEntity.getLoginPwd();
			String tmp = null;
			if (userSource != null && userSource.length() > 0) {
				if (userLoginPwd != null && userLoginPwd.length() > 0) {
					tmp = requestDto.getOldLoginpwd();
					if (tmp == null)
						throw new BlAppException(-1, "旧密码不能空");
					tmp = MD5Util.getEncryptedPwd(tmp);
					if (!userLoginPwd.equals(tmp)) {
						ct = CodeTable.BL_FRONTUSER_MODIFY_LOGIN_PWD_ERROR;
						throw new BlAppException(ct.getValue(), ct.getDesc());
					}
					tmp = requestDto.getOldLoginpwd();
					String newPwd = requestDto.getNewLoginpwd();
					if (tmp.equals(newPwd)) {
						ct = CodeTable.BL_FRONTUSER_OLDEQUSENEW_PASSWORD;
						throw new BlAppException(ct.getValue(), ct.getDesc());
					}
				}
			} else {
				tmp = requestDto.getOldLoginpwd();
				tmp = MD5Util.getEncryptedPwd(tmp);
				if (!userLoginPwd.equals(tmp)) {
					ct = CodeTable.BL_FRONTUSER_MODIFY_LOGIN_PWD_ERROR;
					throw new BlAppException(ct.getValue(), ct.getDesc());
				}
				tmp = requestDto.getOldLoginpwd();
				String newPwd = requestDto.getNewLoginpwd();
				if (tmp.equals(newPwd)) {
					ct = CodeTable.BL_FRONTUSER_OLDEQUSENEW_PASSWORD;
					throw new BlAppException(ct.getValue(), ct.getDesc());
				}
			}
			// 修改密码
			frontUserEntity.modifyLoginPwd(requestDto.getNewLoginpwd());
			int val = dao.save(frontUserEntity);
			this.valiSaveDomain(val, "前端用户");
			this.logger.debug("end modifycurrentuserloginpwd");
			return true;
		} catch (BlAppException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public Entity getMyMarketing(RequestContext context) throws BlAppException {
		MemberManager memberManager = new MemberManager(context);
		this.checkFrontUserLogined(context);
		// 获取dao
		try {
			// 得到用户类别,如果是试用用户和申请试用，抛出异常
			Dao dao = null;
			dao = BLContextUtil.getDao(context, BackuserDao.class);
			this.valiDaoIsNull(dao, "加载用户信息");
			String userId = this.getSessionUserId(context);

			GetUserDetailQCondition condition = new GetUserDetailQCondition();
			condition.setUserId(userId);
			// dao查询数据库
			MapEntity response = (MapEntity) dao.query(condition, false);
			// 返回值不可为空
			BLContextUtil.valiDomainIsNull(response, "用户信息");
			// 初始化返回的MyMarketingDto
			UserResourcesEntity entity1 = new UserResourcesEntity();
			UserResourcesEntity entity2 = new UserResourcesEntity();
			UserResourcesEntity entity3 = new UserResourcesEntity();
			UserResourcesEntity entity4 = new UserResourcesEntity();
			UserResourcesEntity entity5 = new UserResourcesEntity();
			UserResourcesEntity entity6 = new UserResourcesEntity();
			UserResourcesEntity entity7 = new UserResourcesEntity();
			MyMarketingDto dto = new MyMarketingDto(entity1, entity2, entity3, entity4, entity5, entity6, entity7);

			Map<String, Object> data = response.getData();
			Object memberType = data.get("packageServiceId");// 是否购买套餐
			Integer state = (int) data.get("status");// 用户状态， 参考 UserStatus
			Integer accountType = (int) data.get("accountType");// 主账号子账号
			if (UserRelationType.SUBUSER.getValue() == accountType) {
				dto.setMemberType(MemberType.SubAccount.getCode());
			} else {// 主账号
				if (memberType == null) { // 如果不是会员，设置会员等级
					if (state == UserStatus.Trial.getValue()) {
						dto.setMemberType(MemberType.ApplyTrial.getCode());
					} else {
						dto.setMemberType(MemberType.TrialEdition.getCode());
					}
				} else {
					dto.setMemberType(MemberType.VipUser.getCode());
				}
				UserResourcesEntity weChatResouce = new UserResourcesEntity();
				MemberEntity memberEntity = MemberManager.getMemberInfo(context, userId);
				if (memberEntity != null) {
					weChatResouce.setEndtime(memberEntity.getEndtime());
					weChatResouce.setStarttime(memberEntity.getStarttime());
				}
				dto.setWeChatResouce(weChatResouce);
			}
			// 如果该账号所有资源
			// 通过子账号得到父账号
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
				userId = userRelationEntity.getFatherUserId();
			}
			// 查询父账号的所有资源
			List<UserResourcesEntity> list = memberManager.getUserAllResources(userId);
			// 设置邮件，短信，B2B，微信相关数据
			for (UserResourcesEntity resource : list) {
				// 得到服务id
				int serviceId = resource.getServiceId();
				// 得到服务类别
				int type = PayInfoServiceInfoEntity.getTypeByServiceId(serviceId);
				if (type == PayInfoServiceInfoEntity.EMAIL) {
					dto.setEmailResouce(resource);
					continue;
				}
				if (type == PayInfoServiceInfoEntity.SUBACCOUNT) {
					dto.setSubAccount(resource);
					continue;
				}
				if (type == PayInfoServiceInfoEntity.SMS) {
					dto.setSmsResouce(resource);
					continue;
				}
				if (type == PayInfoServiceInfoEntity.B2B) {
					dto.setB2bResouce(resource);
					continue;
				}
				if (type == PayInfoServiceInfoEntity.CUSTOMERCLUE) {
					dto.setCralwerResouce(resource);
					continue;
				}
				if (type == PayInfoServiceInfoEntity.PUBLISHNUM) {
					dto.setPublishResouce(resource);
					continue;
				}
			}
			return dto;
		} catch (SessionContainerException e) {
			throw new BlAppException(CodeTable.BL_SESSION_STATE_ERROR.getValue(),
					CodeTable.BL_SESSION_STATE_ERROR.getDesc(), e);
		} catch (DaoAppException e) {
			throw new BlAppException(e);
		}
	}

	/**
	 * 企业用户登录
	 * 
	 * @param user
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private FrontUserEntity companyLogin(RequestContext context, UserLoginRequestDto request)
			throws DaoAppException, BlAppException {
		String id = request.getPhoneNo();
		// 校验电话号码
		if (Str.isNullOrEmpty(id)) {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "企业账号错误");
		}
		// 校验登录密码
		if (Str.isNullOrEmpty(request.getLoginPwd())) {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "密码不可为空");

		}
		String[] companyInfo = id.split(":");
		if (companyInfo == null || companyInfo.length != 2) {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "企业账号错误");
		}
		// 查询用户
		QueryFrontUserByCompany queryContdition = new QueryFrontUserByCompany();
		queryContdition.setCompanyCode(companyInfo[0]);
		queryContdition.setCompanyId(companyInfo[1]);
		FrontUserEntity user = (FrontUserEntity) BLContextUtil.getDao(context, FrontuserDao.class)
				.query(queryContdition, true);
		if (user == null) {
			throw new BlAppException(CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(), "用户未注册");
		}
		if (user.getStatus().equals(UserStatus.DISABLE)) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_DISABLE.getValue(), "用户已禁用，无法登陆");
		}
		if (user.getStatus().equals(UserStatus.Trial)) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_TRIAL.getValue(), "用户还在审核中,请耐心等待");
		}
		// 验证是否过期
		if (new Date().after(TimeUtil.calculateEndtime(FrontuserManger.getMemberEndTime(context, user), 15))) {
			throw new BlAppException(CodeTable.BL_COMMON_USER_TRIALMEMBER.getValue(), "版本已过期");
		}
		return user;
	}
}
