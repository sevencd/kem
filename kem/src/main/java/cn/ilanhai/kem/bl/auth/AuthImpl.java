package cn.ilanhai.kem.bl.auth;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.CT;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.RedisSession;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.SessionFactory;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.auth.AuthConfigDao;
import cn.ilanhai.kem.dao.auth.UserBoundDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.auth.AuthConfigEntity;
import cn.ilanhai.kem.domain.auth.AuthDto;
import cn.ilanhai.kem.domain.auth.PlatformDto;
import cn.ilanhai.kem.domain.auth.QueryUserBound;
import cn.ilanhai.kem.domain.auth.UserBoundEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginResponseDto;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("auth")
public class AuthImpl extends BaseBl implements Auth {
	private Logger logger = Logger.getLogger(AuthImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Iterator<Entity> platform(RequestContext context)
			throws BlAppException, DaoAppException {
		CT ct;
		ArrayList<Entity> al = null;
		Iterator<Entity> iterator = null;
		Dao dao = null;
		Entity entity = null;
		try {
			dao = this.daoProxyFactory.getDao(context, AuthConfigDao.class);
			this.valiDaoIsNull(dao, "认证配置");
			iterator = dao.query(null);
			if (iterator == null)
				return null;
			al = new ArrayList<Entity>();
			while (iterator.hasNext()) {
				entity = this.getPlatformDto(iterator.next());
				if (entity != null)
					al.add(entity);
			}
			return al.iterator();
		} catch (BlAppException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			ct = CT.APP_BL_UNH_EXCEPTION;
			throw new BlAppException(ct.getVal(), ct.getDesc(), e);
		}
	}

	private Entity getPlatformDto(Entity entity) {
		AuthConfigEntity authConfigEntity = null;
		Autho.MediaType mediaType = null;
		PlatformDto platformDto = null;
		String tmp = null;
		String appid = null;
		String redirectUri = null;
		try {
			if (entity == null)
				return null;
			if (!(entity instanceof AuthConfigEntity))
				return null;
			authConfigEntity = (AuthConfigEntity) entity;
			mediaType = Autho.MediaType.valueOf(authConfigEntity.getType());
			if (mediaType == null)
				return null;
			appid = authConfigEntity.getAppId();
			if (appid == null || appid.length() <= 0)
				return null;
			redirectUri = authConfigEntity.getRedirectUri();
			if (redirectUri == null || redirectUri.length() <= 0)
				return null;
			platformDto = new PlatformDto();
			platformDto.setType(authConfigEntity.getType());
			platformDto.setEnable(authConfigEntity.getEnable());
			if (mediaType.getValue() == Autho.MediaType.sinaweibo.getValue()) {
				tmp = String.format(SinaWeiBoAutho.AUTHORIZE_URL, appid,
						URLEncoder.encode(redirectUri, "utf-8"),
						mediaType.getValue());
			} else if (mediaType.getValue() == Autho.MediaType.qq.getValue()) {
				tmp = String.format(QQAutho.AUTHORIZE_PC_WAP, appid,
						URLEncoder.encode(redirectUri, "utf-8"),
						mediaType.getValue());
			} else if (mediaType.getValue() == Autho.MediaType.wx.getValue()) {
				tmp = String.format(WXAutho.AUTHORIZE_URL, appid,
						URLEncoder.encode(redirectUri, "utf-8"),
						mediaType.getValue());
			} else {
				return null;
			}
			platformDto.setAuthUri(tmp);
			return platformDto;

		} catch (Exception e) {
			return null;
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity auth(RequestContext context) throws BlAppException,
			DaoAppException {
		AuthDto auth = null;
		CT ct = null;
		UserBoundEntity userBound = null;
		AuthConfigEntity authConfig = null;
		FrontUserEntity frontUser = null;
		Autho.MediaType mediaType = null;
		Autho.Source src = null;
		JSONObject res = null;
		Session session = null;
		String openId = null;
		String accessToke = null;
		UserLoginResponseDto response = null;
		try {
			this.logger.debug("begin auth");
			auth = context.getDomain(AuthDto.class);
			this.valiPara(auth);
			this.valiParaItemStrNullOrEmpty(auth.getCode(), "授权码");
			this.valiParaItemNumBetween(0, 2, auth.getState(), "状态码");
			auth.setSource(0);
			this.valiParaItemNumBetween(0, 1, auth.getSource(), "来源");
			mediaType = Autho.MediaType.valueOf(auth.getState());
			src = Autho.Source.valueOf(auth.getSource());
			authConfig = this.getAuthConfig(context, mediaType);
			this.valiDomainIsNull(authConfig, "认证配置");
			// 获取AccessToken
			res = this.getAccessToken(auth.getCode(), src, authConfig);
			if (res == null)
				throw new BlAppException(-1, "获取AccessToke失败");
			if (res.getInteger("code") != 0)
				throw new BlAppException(-1, res.getString("msg"));
			if (!res.containsKey("data"))
				throw new BlAppException(-1, "获取AccessToke失败");
			openId = res.getJSONObject("data").getString("openid");
			accessToke = res.getJSONObject("data").getString("accessToken");
			logger.info(res.toJSONString());
			logger.info(openId);
			logger.info(accessToke);
			if (openId == null || accessToke == null || openId.length() <= 0
					|| accessToke.length() <= 0)
				throw new BlAppException(-1, "获取AccessToke失败");
//			 获取绑定用户
			userBound = this.getUserBound(context, mediaType, openId);
			// 如果为空进行用户绑定
			if (userBound == null)
				userBound = this.bindFrontUser(context, authConfig, mediaType,
						accessToke, openId, res);
			if (userBound == null)
				throw new BlAppException(-1, "认证用户失败");
			// 获取前端用户
			frontUser = this.getFrontUser(context, userBound.getUserId());
			this.valiDomainIsNull(frontUser, "用户");

			if (frontUser.getStatus().equals(UserStatus.DISABLE)) {
				throw new BlAppException(
						CodeTable.BL_FRONTUSER_LOGIN_ERROR.getValue(),
						"用户已禁用，无法登陆");
			}
			session = context.getSession();
			BaseSessionState destState = SessionFactory
					.createSessionState(SessionStateType
							.stringtoEnum(RedisSession.STATE_FRONTUSER_LOGINED));
			session.setSessionState(destState);
			session.setParameter(Constant.KEY_SESSION_USERID,
					frontUser.getUserID());
			session.setParameter(Constant.KEY_SESSION_USERTYPE,
					frontUser.getUserType());
			context.getSession().setParameter(Constant.KEY_SESSION_USERSOURCE,
					"auth");
			response = new UserLoginResponseDto();
			response.setUserID(frontUser.getUserID());
			response.setEmail(frontUser.getEmail());
			response.setUsername(frontUser.getUserName());
			response.setUserType(frontUser.getUserType().getValue());
			response.setPhoneNo(frontUser.getPhoneNo());
			response.setStatus(frontUser.getStatus());
			response.setHasPwd(true);
			if (frontUser.getLoginPwd() == null
					|| frontUser.getLoginPwd().length() <= 0)
				response.setHasPwd(false);
			return response;

		} catch (BlAppException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			ct = CT.APP_BL_UNH_EXCEPTION;
			throw new BlAppException(ct.getVal(), ct.getDesc(), e);
		}
	}

	/**
	 * 获取前端用户
	 * 
	 * @param context
	 * @param userId
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private FrontUserEntity getFrontUser(RequestContext context, String userId)
			throws BlAppException, DaoAppException {
		Dao authConfigDao = null;
		IdEntity<String> id = null;
		Entity entity = null;
		authConfigDao = this.daoProxyFactory
				.getDao(context, FrontuserDao.class);
		this.valiDaoIsNull(authConfigDao, "认证配置");
		id = new IdEntity<String>();
		id.setId(userId);
		entity = authConfigDao.query(id, false);
		if (!(entity instanceof FrontUserEntity))
			return null;
		return (FrontUserEntity) entity;

	}

	/**
	 * 绑定前端用户
	 * 
	 * @param context
	 * @param mediaType
	 * @param tag
	 * @param data
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private UserBoundEntity bindFrontUser(RequestContext context,
			AuthConfigEntity authConfigEntity, Autho.MediaType mediaType,
			String accesssToken, String openId, JSONObject data)
			throws BlAppException, DaoAppException {
		Dao userBoundDao = null;
		Dao userDao = null;
		FrontUserEntity frontUser = null;
		UserBoundEntity userBound = null;
		JSONObject res = null;
		String nickname = null;
		// 获取第三主用户信息
		res = this.getUserInfo(authConfigEntity, accesssToken, openId);
		if (res == null || (!(res.containsKey("code")))
				|| (res.getInteger("code") != 0))
			throw new BlAppException(-1, "获取用户信息失败");
		if (!res.containsKey("data"))
			throw new BlAppException(-1, "获取用户信息失败");
		nickname = res.getJSONObject("data").getString("nickname");
		data.put("userInfo", res);
		userDao = this.daoProxyFactory.getDao(context, FrontuserDao.class);
		this.valiDaoIsNull(userDao, "前端用户");
		frontUser = new FrontUserEntity();
		frontUser.setCreatetime(new Date());
		frontUser.setStatus(UserStatus.ENABLE);
		frontUser.setUserType(UserType.GENERAL_USER);
		frontUser.setUserID(KeyFactory.newKey(KeyFactory.KEY_USER));
		frontUser.setUserName(nickname);
		List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
		FrontUserInfoEntity frontUserInfoEntity_name = new FrontUserInfoEntity();
		frontUserInfoEntity_name.setUserID(frontUser.getUserID());
		frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
		frontUserInfoEntity_name.setInfo(nickname);
		frontUserInfoEntity_name.setInfoState(UserStatus.ENABLE.getValue());
		infos.add(frontUserInfoEntity_name);
		frontUser.setInfos(infos);
		int val = 0;
		val = userDao.save(frontUser);
		this.valiSaveDomain(val, "前端用户");
		userBoundDao = this.daoProxyFactory.getDao(context, UserBoundDao.class);
		this.valiDaoIsNull(userBoundDao, "前端用户");
		userBound = new UserBoundEntity();
		userBound.setAddTime(new Date());
		userBound.setUpdateTime(new Date());

		userBound.setAuthData(data.toJSONString());
		userBound.setUserId(frontUser.getUserID());
		userBound.setTag(openId);
		userBound.setType(mediaType.name());

		userBound.setAtUpdateTime(new Date());
		userBound.setAt("");
		userBound.setAtData("");
		userBound.setAtExpiredTime(0);
		userBound.setAtExpiryDate(0);
		val = userBoundDao.save(userBound);
		try {
			// 校验
			this.valiSaveDomain(val, "用户绑定");
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		}
		return userBound;
	}

	/**
	 * 获取认证配置
	 * 
	 * @param context
	 * @param mediaType
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private AuthConfigEntity getAuthConfig(RequestContext context,
			Autho.MediaType mediaType) throws BlAppException, DaoAppException {
		Dao authConfigDao = null;
		IdEntity<Integer> type = null;
		Entity entity = null;
		authConfigDao = this.daoProxyFactory.getDao(context,
				AuthConfigDao.class);
		this.valiDaoIsNull(authConfigDao, "认证配置");
		type = new IdEntity<Integer>();
		type.setId(mediaType.getValue());
		entity = authConfigDao.query(type, false);
		if (!(entity instanceof AuthConfigEntity))
			return null;
		return (AuthConfigEntity) entity;
	}

	/**
	 * 获取绑定前端用户
	 * 
	 * @param context
	 * @param mediaType
	 * @param tag
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private UserBoundEntity getUserBound(RequestContext context,
			Autho.MediaType mediaType, String tag) throws BlAppException,
			DaoAppException {
		Dao userBoundDao = null;
		UserBoundEntity bound = null;
		QueryUserBound queryUserBound = null;
		Entity entity = null;
		userBoundDao = this.daoProxyFactory.getDao(context, UserBoundDao.class);
		this.valiDaoIsNull(userBoundDao, "用户绑定");
		queryUserBound = new QueryUserBound();
		queryUserBound.setType(mediaType.name());
		queryUserBound.setTag(tag);
		entity = userBoundDao.query(queryUserBound, false);
		if (!(entity instanceof UserBoundEntity))
			return null;
		return (UserBoundEntity) entity;
	}

	/**
	 * 获取AccessToken
	 * 
	 * @param code
	 * @param src
	 * @param authConfig
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private JSONObject getAccessToken(String code, Autho.Source src,
			AuthConfigEntity authConfig) throws BlAppException, DaoAppException {
		Autho autho = null;
		Autho.MediaType mediaType = null;
		String appid = null;
		String appsecret = null;
		int type = -1;
		String redirectUri = null;
		try {
			if (src == null)
				throw new BlAppException(-1, "来源错误");
			if (code == null || code.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台授权码错误");
			if (authConfig == null)
				throw new BlAppException(-1, "第三方登陆平台配置错误");
			appid = authConfig.getAppId();
			if (appid == null || appid.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置AppId错误");
			appsecret = authConfig.getAppSecret();
			if (appsecret == null || appsecret.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置AppSecret错误");
			type = authConfig.getType();
			mediaType = Autho.MediaType.valueOf(type);
			if (mediaType == null)
				throw new BlAppException(-1, "第三方登陆平台配置类型错误");
			redirectUri = authConfig.getRedirectUri();
			if (appsecret == null || appsecret.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置回调错误");
			autho = Autho.createAuth(mediaType);
			if (autho == null)
				throw new BlAppException(-1, "创建认证失败");
			autho.setAppId(appid);
			autho.setAppSecret(appsecret);
			autho.setCode(code);
			try {
				autho.setRedirectUri(URLEncoder.encode(redirectUri, "utf-8"));
			} catch (Exception e) {
				throw new BlAppException(-1, "回调转码错误");
			}
			autho.setSource(src);
			return autho.getAccessToken();
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage());
		}
	}

	/**
	 * 获取用户第三方信息
	 * 
	 * @param accesssToken
	 * @param authConfig
	 * @param openId
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private JSONObject getUserInfo(AuthConfigEntity authConfig,
			String accesssToken, String openId) throws BlAppException,
			DaoAppException {
		Autho autho = null;
		Autho.MediaType mediaType = null;
		String tmp = null;
		int type = 0;
		try {
			if (accesssToken == null || accesssToken.length() <= 0)
				throw new BlAppException(-1, "accesssToken错误");
			if (openId == null || openId.length() <= 0)
				throw new BlAppException(-1, "openId错误");
			if (authConfig == null)
				throw new BlAppException(-1, "第三方登陆平台配置错误");
			tmp = authConfig.getAppId();
			if (tmp == null || tmp.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置AppId错误");
			tmp = authConfig.getAppSecret();
			if (tmp == null || tmp.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置AppSecret错误");
			tmp = authConfig.getRedirectUri();
			if (tmp == null || tmp.length() <= 0)
				throw new BlAppException(-1, "第三方登陆平台配置回调错误");
			type = authConfig.getType();
			mediaType = Autho.MediaType.valueOf(type);
			if (mediaType == null)
				throw new BlAppException(-1, "第三方登陆平台配置类型错误");
			autho = Autho.createAuth(mediaType);
			if (autho == null)
				throw new BlAppException(-1, "创建认证失败");
			autho.setAppId(authConfig.getAppId());
			autho.setAppSecret(authConfig.getAppSecret());
			try {
				autho.setRedirectUri(URLEncoder.encode(
						authConfig.getRedirectUri(), "utf-8"));
			} catch (Exception e) {
				throw new BlAppException(-1, "回调转码错误");
			}
			return autho.getUserInfo(accesssToken, openId);
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage());
		}
	}
}
