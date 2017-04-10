package cn.ilanhai.kem.bl.user.backuser.impl;

import java.util.ArrayList;
import java.util.List;

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
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.RedisSession;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.SessionFactory;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.bl.session.SessionImplManger;
import cn.ilanhai.kem.bl.user.backuser.Backuser;
import cn.ilanhai.kem.bl.user.frontuser.FrontuserManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.user.backuser.BackuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.user.backuser.BackUserEntity;
import cn.ilanhai.kem.domain.user.backuser.CurrentUserResponseEntity;
import cn.ilanhai.kem.domain.user.backuser.QueryByUserNameNoConditionEntity;
import cn.ilanhai.kem.domain.user.backuser.UserLoginRequestDto;
import cn.ilanhai.kem.domain.user.frontuser.CompanyUserDto;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginResponseDto;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.event.args.BackUserLoginEvent;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.LockUtil;
import cn.ilanhai.kem.util.MD5Util;
import cn.ilanhai.kem.util.StringVerifyUtil;

@Component("backuser")
public class BackuserImpl extends BaseBl implements Backuser {
	private Logger logger = Logger.getLogger(BackuserImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity userlogin(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		logger.info("开始后台用户登录处理");
		try {
			// 获取入参
			UserLoginRequestDto request = context.getDomain(UserLoginRequestDto.class);
			logger.info("开始用户请求校验");
			if (request == null) {
				ct = CodeTable.BL_COMMON_GET_DOAMIN;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			Dao dao = this.daoProxyFactory.getDao(context);
			logger.info("开始用户数据资源访问校验");
			if (dao == null) {
				ct = CodeTable.BL_COMMON_GET_DAO;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			logger.info("校验当前登录用户名");
			String userName = request.getUserName();
			// 校验用户名
			StringVerifyUtil.backUserNameVerify(userName);
			logger.info("校验当前登录密码");
			String pwd = request.getLoginPwd();
			StringVerifyUtil.pwdVerify(pwd);
			// 查询用户
			QueryByUserNameNoConditionEntity queryContdition = new QueryByUserNameNoConditionEntity();
			queryContdition.setUserName(userName);
			BackUserEntity user = (BackUserEntity) dao.query(queryContdition, true);
			if (user == null) {
				throw new BlAppException(CodeTable.BL_BACKUSER_LOGIN_ERROR.getValue(), "用户未注册");
			}
			if (user.getStatus().equals(UserStatus.DISABLE)) {
				throw new BlAppException(CodeTable.BL_BACKUSER_LOGIN_ERROR.getValue(), "用户已禁用，无法登陆");
			}

			// session 处理
			Session session = context.getSession();
			logger.info("校验当前会话状态");
			if (session == null) {
				throw new BlAppException(-1, "当前会话无效");
			}
			// 判断会话状态，用户是否可以登录
			BaseSessionState currentState = session.getSessionState();

			BaseSessionState destState = SessionFactory
					.createSessionState(SessionStateType.stringtoEnum(RedisSession.STATE_BACKUSER_LOGINED));
			// 校验用户密码
			if (user.verifyUser(pwd)) {
				logger.info("用户可登陆");

				if (!destState.verify(context.getQueryString("ClientType"), currentState)) {
					SessionImplManger.logout(context);
				}

				session.setParameter(Constant.KEY_SESSION_USERID, user.getUserID());
				session.setParameter(Constant.KEY_SESSION_USERNAME, user.getUserName());
				session.setParameter(Constant.KEY_SESSION_USERTYPE, UserType.BACK_USER);
				session.setSessionState(destState);
				logger.info("用户登录成功");
				UserLoginResponseDto response = new UserLoginResponseDto();
				response.setUserID(user.getUserID());
				response.setUsername(user.getUserName());
				response.setStatus(user.getStatus());
				response.setUserType(UserType.BACK_USER.getValue());
				String tmpString = user.getLogin_pwd();
				response.setHasPwd(true);
				if (tmpString == null || tmpString.length() <= 0)
					response.setHasPwd(false);

				/*
				 * response.setUserName(user.getUserName());
				 * response.setUserType(user.getUserType());
				 */
				BackUserLoginEvent backUserLoginEvent = new BackUserLoginEvent();
				backUserLoginEvent.setUsername(response.getUsername());
				DomainEventManager.getInstance().raise(context, backUserLoginEvent);
				logger.info("用户登录成功");
				return response;
			} else {
				throw new BlAppException(-1, "密码错误");
			}
		} catch (BlAppException e) {
			throw e;
		} catch (JMSAppException e) {
			throw new BlAppException(-1, "事件发布失败");
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public CurrentUserResponseEntity currentuserinfo(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		logger.info("开始获取当前后台用户处理");
		try {
			logger.info("校验当前会话状态");
			this.checkBackUserLogined(context);
			Dao dao = this.daoProxyFactory.getDao(context);
			if (dao == null) {
				ct = CodeTable.BL_COMMON_GET_DAO;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			// session 处理
			Session session = context.getSession();
			String userId = (String) session.getParameter(Constant.KEY_SESSION_USERID);
			if (userId == null) {
				throw new BlAppException(-1, "当前会话无法获取用户信息");
			}
			// 获取入参
			IdEntity<String> request = new IdEntity<String>();
			request.setId(userId);
			BackUserEntity user = (BackUserEntity) dao.query(request, true);
			if (user != null) {
				logger.info("获取当前用户信息成功");
				// 返回用户信息
				return new CurrentUserResponseEntity(user.getUserName());
			} else {
				throw new BlAppException(-1, "无法获取当前用户信息");
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public int saveUser(RequestContext context) throws BlAppException {
		// 验证后台用户是否已登录
		this.checkBackUserLogined(context);
		// 获取入参
		CompanyUserDto request = context.getDomain(CompanyUserDto.class);
		// 验证入参是否为空
		valiPara(request);
		// 验证手机号，公司名称，职位
		this.valiParaItemStringNull(request.getLoginPwd(), "密码");
		// 电话号码合法
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {
			throw new BlAppException(-1, "电话号码错误");
		}
		try {
			if (FrontuserManger.isExist(context, request.getPhoneNo())) {
				throw new BlAppException(CodeTable.BL_COMPANYUSER_PHONE_REPEAT.getValue(),
						CodeTable.BL_COMPANYUSER_PHONE_REPEAT.getDesc());
			}
			// 获取userId
			request.setUserId(KeyFactory.newKey(KeyFactory.KEY_USER));
			request.setStatus(UserStatus.ENABLE.getValue());
			request.setLoginPwd(MD5Util.getEncryptedPwd(request.getLoginPwd()));
			// 设置公司编码
			request.setCompanyCode(KeyFactory.getInstance().newCode(request.getUserId()));

			List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
			FrontUserInfoEntity frontUserInfoEntity_phone = new FrontUserInfoEntity();
			frontUserInfoEntity_phone.setUserID(request.getUserId());
			frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
			frontUserInfoEntity_phone.setInfo(request.getPhoneNo());
			frontUserInfoEntity_phone.setInfoState(request.getStatus());
			infos.add(frontUserInfoEntity_phone);
			if (!Str.isNullOrEmpty(request.getCompany())) {
				FrontUserInfoEntity frontUserInfoEntity_company = new FrontUserInfoEntity();
				frontUserInfoEntity_company.setUserID(request.getUserId());
				frontUserInfoEntity_company.setInfoType(UserInfoType.COMPANY.getValue());
				frontUserInfoEntity_company.setInfo(request.getCompany());
				frontUserInfoEntity_company.setInfoState(request.getStatus());
				infos.add(frontUserInfoEntity_company);
			}
			if (!Str.isNullOrEmpty(request.getJob())) {
				FrontUserInfoEntity frontUserInfoEntity_job = new FrontUserInfoEntity();
				frontUserInfoEntity_job.setUserID(request.getUserId());
				frontUserInfoEntity_job.setInfoType(UserInfoType.JOB.getValue());
				frontUserInfoEntity_job.setInfo(request.getJob());
				frontUserInfoEntity_job.setInfoState(request.getStatus());
				infos.add(frontUserInfoEntity_job);
			}
			if (!Str.isNullOrEmpty(request.getContact())) {
				FrontUserInfoEntity frontUserInfoEntity_contact = new FrontUserInfoEntity();
				frontUserInfoEntity_contact.setUserID(request.getUserId());
				frontUserInfoEntity_contact.setInfoType(UserInfoType.CONTACT.getValue());
				frontUserInfoEntity_contact.setInfo(request.getContact());
				frontUserInfoEntity_contact.setInfoState(request.getStatus());
				infos.add(frontUserInfoEntity_contact);
			}
			if (!Str.isNullOrEmpty(request.getContactPhoneNo())) {
				FrontUserInfoEntity frontUserInfoEntity_contactPhoneNo = new FrontUserInfoEntity();
				frontUserInfoEntity_contactPhoneNo.setUserID(request.getUserId());
				frontUserInfoEntity_contactPhoneNo.setInfoType(UserInfoType.CONTACTPHONENO.getValue());
				frontUserInfoEntity_contactPhoneNo.setInfo(request.getContactPhoneNo());
				frontUserInfoEntity_contactPhoneNo.setInfoState(request.getStatus());
				infos.add(frontUserInfoEntity_contactPhoneNo);
			}
			request.setInfos(infos);
			// 获取数据连接资源
			Dao dao = this.daoProxyFactory.getDao(context, BackuserDao.class);
			valiDaoIsNull(dao, "添加用户");
			// 返回结果
			int result;
			synchronized (LockUtil.getUserLock(request.getUserId())) {
				result = dao.save(request);
			}
			valiSaveDomain(result, "添加用户");
			// 添加购买版本
			OrderManager manager = new OrderManager(context);
			if (request.getPackageServiceId() != null && request.getPackageServiceId() != 0) {
				try {
					manager.placeOrderOffline(request.getPackageServiceId(), 0D, request.getUserId(), 0, 0);
				} catch (AppException | SessionContainerException e) {
					throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
				}
			}
			return result;
		} catch (DaoAppException e) {
			try {
				KeyFactory.inspects();
			} catch (DaoAppException e1) {
				throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
			}
			throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
		}
	}
}
