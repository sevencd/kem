package cn.ilanhai.kem.bl.bindhost;

import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.dao.bindhost.BindHostDao;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.bindhost.BindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostResponseEntity;
import cn.ilanhai.kem.domain.bindhost.QueryExtensionByUserResponse;
import cn.ilanhai.kem.domain.bindhost.QueryExtensionByuserRequest;
import cn.ilanhai.kem.domain.bindhost.SearchSysBind;
import cn.ilanhai.kem.domain.bindhost.UpdateUserHostStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.member.dto.MemberStatusDto;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigResponseEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;

public class BindHostManager {
	private Logger logger = Logger.getLogger(BindHostManager.class);

	private RequestContext context;

	public BindHostManager(RequestContext context) {
		this.context = context;
	}

	public String saveUserHost(BindHostRequestEntity request) throws SessionContainerException, AppException {
		logger.info("初始化dao");
		Dao dao = BLContextUtil.getDao(context, BindHostDao.class);
		BLContextUtil.valiDaoIsNull(dao, "保存用户域名");
		// 获得当前用户ID
		String userId = BLContextUtil.getSessionUserId(context);
		BLContextUtil.valiParaNotNull(userId, "用户ID");
		// 校验用户合法性
		this.checkGeneralUser(userId);
		// 验证是否为会员
		MemberManager manager = new MemberManager(context);
		//获取userid的父账号
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		MemberStatusDto userStatus = null;
		if (userRelationEntity != null) {
			userStatus = manager.status(userRelationEntity.getFatherUserId());
		} else {
			userStatus = manager.status(userId);
		}
		if (userStatus.getStatus() == 0) {
			throw new AppException(-1, "用户不是会员");
		}
		request.setUserId(userId);
		request.setStatus(1);
		// 保存会员与域名信息
		request.setHost(this.standardization(request.getHost()));
		int status = dao.save((BindHostRequestEntity) request);
		if (status <= 0) {
			throw new AppException(-1, "保存失败");
		}
		return userId;
	}

	public void updateBindStatus(BindHostRequestEntity request)
			throws SessionContainerException, AppException, CacheContainerException {
		logger.info("更新绑定状态");
		Dao dao = BLContextUtil.getDao(context, BindHostDao.class);
		BLContextUtil.valiDaoIsNull(dao, "更新用户状态");
		String userId = BLContextUtil.getSessionUserId(context);
		// 校验用户合法性
		this.checkGeneralUser(userId);
		// 验证是否为会员
		logger.info("验证是否为会员");
		MemberManager manager = new MemberManager(context);
		//获取userid的父账号
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		MemberStatusDto userStatus = null;
		if (userRelationEntity != null) {
			userStatus = manager.status(userRelationEntity.getFatherUserId());
		} else {
			userStatus = manager.status(userId);
		}
		if (userStatus.getStatus() == 0) {
			throw new AppException(-1, "用户不是会员");
		}
		logger.info("用户：" + userId);
		request.setUserId(userId);
		request.setStatus(0);

		// 保存会员与域名信息
		request.setHost(this.standardization(request.getHost()));
		logger.info("host：" + request.getHost());
		int val = dao.save((BindHostRequestEntity) request);
		if (val <= 0) {
			return;
		}
		this.disableHost(userId);
	}

	// 查询会员用户的所有没禁用的推广
	public void queryentensionbyuser(String user, String host) throws AppException, CacheContainerException {
		Iterator<Entity> extensionInfo = this.queryExtensionByUser(user);
		Integer number = Integer.parseInt(BLContextUtil.getValue(context, "cacheIndex"));
		// 组合key
		while (extensionInfo.hasNext()) {
			QueryExtensionByUserResponse extension = (QueryExtensionByUserResponse) extensionInfo.next();
			// 组合redis的key并添加到List中
			String key = BLContextUtil.redisKeyForPublish(extension.getExtensionId());
			String url = this.getRedisHost(host, extension.getExtensionId());
			Map<String, String> value = BLContextUtil.redisValueForPublish(extension.getExtensionId(),
					extension.getExtensionType(), BLContextUtil.getValue(context, "apihost"), url);
			logger.info("向redis中写入" + key + ",value = " + value);
			context.getApplication().getCacheManager().getCache(number).set(key, value, -1);
		}
	}

	// 发布模板的时候调用此功能，实现发布的模板绑定域名
	public String hasHostwithuser(String extensionId, Integer extensionType)
			throws BlAppException, DaoAppException, CacheContainerException, SessionContainerException {
		String userId = BLContextUtil.getSessionUserId(context);
		MemberManager manager = new MemberManager(context);
		Dao dao = null;
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		MemberStatusDto userStatus = null;
		if (userRelationEntity != null) {
			logger.info("userId:" + userRelationEntity.getFatherUserId());
			userStatus = manager.status(userRelationEntity.getFatherUserId());
		} else {
			logger.info("userId:" + userId);
			userStatus = manager.status(userId);
		}
		if (userStatus.getStatus() == 0) {
			return null;
		}
		// 查询host
		QueryBindHostRequestEntity request = new QueryBindHostRequestEntity();
		request.setUserId(userId);
		dao = BLContextUtil.getDao(context, BindHostDao.class);
		QueryBindHostResponseEntity host = (QueryBindHostResponseEntity) dao.query(request, false);
		logger.info("host:" + host);
		if (host != null) {
			logger.info("host.getHost():" + host.getHost());
			if (!Str.isNullOrEmpty(host.getHost())) {
				String url = this.getRedisHost(host.getHost(), extensionId);
				logger.info("url:" + url);
				return url;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	// 用户禁用或过期调用，处理绑定域名相关操作
	public void disableHost(String userId) throws AppException, CacheContainerException, SessionContainerException {
		logger.info("会员过期或禁用");
		Dao dao = null;
		UpdateUserHostStatus statusInfo = new UpdateUserHostStatus();
		statusInfo.setUserId(userId);
		statusInfo.setStatus(0);
		dao = BLContextUtil.getDao(context, BindHostDao.class);
		dao.save(statusInfo);
		// 删除该用户下所有redis中的key
		logger.info("查询用户下的推广");
		Iterator<Entity> extensionInfo = this.queryExtensionByUser(userId);
		logger.info("查询用户下的推广为："+extensionInfo);
		// 组合key
		while (extensionInfo.hasNext()) {
			QueryExtensionByUserResponse extension = (QueryExtensionByUserResponse) extensionInfo.next();
			// 组合redis的key并添加到List中
			this.rewriteRedis(extension.getExtensionId(), extension.getExtensionType());
		}
	}

	// 禁用推广使用该接口
	public void disableExtensionHost(String extensionId) throws BlAppException, CacheContainerException {
		String key1 = BLContextUtil.redisKeyForPublish(extensionId);
		this.delRedisKey(key1);
	}

	private String standardization(String url) {
		if (url.startsWith("http://")) {
			return url.substring(7);
		} else if (url.startsWith("https://")) {
			return url.substring(8);
		} else {
			return url;
		}
	}

	private Iterator<Entity> queryExtensionByUser(String user) throws AppException {
		logger.info("开始查询推广");
		QueryExtensionByuserRequest request = new QueryExtensionByuserRequest();
		if (user != null && user != "") {
			request.setUserId(user);
		} else {
			throw new AppException(-1, "获取用户id异常");
		}
		Dao dao = BLContextUtil.getDao(context, BindHostDao.class);
		BLContextUtil.valiDaoIsNull(dao, "查询推广");
		// 查询推广
		Iterator<Entity> extensionInfo = dao.query(request);
		logger.info("查询推广成功");
		return extensionInfo;
	}

	// 向redis中写入零时域名的value
	private void rewriteRedis(String extensionId, Integer type) throws BlAppException, CacheContainerException {
		logger.info("向redis中写入临时域名的value");
		Integer number = Integer.parseInt(BLContextUtil.getValue(context, "cacheIndex"));
		String browerUrl = String.format(this.getDeployPublishUrl(context), extensionId, this.getType(type));
		String key = BLContextUtil.redisKeyForPublish(extensionId);
		Map<String, String> value = BLContextUtil.redisValueForPublish(extensionId, type,
				BLContextUtil.getValue(context, "apihost"), browerUrl);
		logger.info("向redis中写入" + key + "value = " + value);
		context.getApplication().getCacheManager().getCache(number).set(key, value, -1);
	}

	protected String getDeployPublishUrl(RequestContext context) {
		return BLContextUtil.getValue(context, "deployPublishUrl");
	}

	protected String getType(int type) {
		switch (type) {
		case 1:
			return "pc";
		case 2:
			return "wap";
		default:
			return null;
		}
	}

	// 删除redis中的key
	private void delRedisKey(String key) throws CacheContainerException {
		Integer number = Integer.parseInt(BLContextUtil.getValue(context, "cacheIndex"));
		context.getApplication().getCacheManager().getCache(number).set(key, "1", 1);
	}

	private String getRedisHost(String host, String extensionId) throws DaoAppException, BlAppException {
		Dao dao = null;
		dao = BLContextUtil.getDao(context, BindHostDao.class);
		SearchSysBind sysKey = new SearchSysBind();
		sysKey.setKey("hostConfig");
		PayConfigResponseEntity sysConfigHost = (PayConfigResponseEntity) dao.query(sysKey, false);
		String url = "http://" + host + sysConfigHost.getSysValue() + extensionId;
		return url;
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
}
