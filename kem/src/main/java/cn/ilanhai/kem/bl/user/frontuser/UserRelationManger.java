package cn.ilanhai.kem.bl.user.frontuser;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.userRelation.UserRelationDao;
import cn.ilanhai.kem.dao.userRelation.UserRelationResourceDao;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.user.frontuser.dto.ZdsLogDto;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.domain.userRelation.dto.DeleteUserRelationDto;
import cn.ilanhai.kem.domain.userRelation.dto.SearchUserRelationDto;
import cn.ilanhai.kem.util.LockUtil;

public class UserRelationManger {
	private static Class<?> currentclass = UserRelationDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	/**
	 * 创建用户账号关系
	 * 
	 * @param context
	 * @param entity
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static int saveUserRelation(RequestContext context, UserRelationEntity entity)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiPara(entity);
		// 获取数据库资源
		Dao dao = daoFactory.getDao(context, currentclass);
		synchronized (LockUtil.getUserLock(entity.getFatherUserId())) {
			return dao.save(entity);
		}
	}

	/**
	 * 创建主账号关系
	 * 
	 * @param context
	 * @param entity
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	public static int saveMainUserRelation(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		UserRelationEntity relationEntity = new UserRelationEntity();
		String userId = BLContextUtil.getSessionUserId(context);
		synchronized (LockUtil.getUserLock(userId)) {
			relationEntity.setId(0);
			relationEntity.setFatherUserId(userId);
			relationEntity.setUserId(userId);
			relationEntity.setState(UserStatus.ENABLE.getValue());
			// 主账号
			relationEntity.setUserType(UserRelationType.MAINUSER.getValue());
			return UserRelationManger.saveUserRelation(context, relationEntity);
		}
	}

	/**
	 * 创建子账号关系
	 * 
	 * @param context
	 * @param entity
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	public static int saveSubUserRelation(RequestContext context, UserStatus userStatus, String userId)
			throws BlAppException, DaoAppException, SessionContainerException {
		UserRelationEntity relationEntity = new UserRelationEntity();
		relationEntity.setId(0);
		relationEntity.setFatherUserId(BLContextUtil.getSessionUserId(context));
		relationEntity.setUserId(userId);
		relationEntity.setState(UserStatus.ENABLE.getValue());
		// 主账号
		relationEntity.setUserType(UserRelationType.SUBUSER.getValue());
		return UserRelationManger.saveUserRelation(context, relationEntity);
	}

	public static int deleteUserRelation(RequestContext context, DeleteUserRelationDto dto)
			throws BlAppException, DaoAppException, SessionContainerException {
		UserRelationManger.checkMainUser(context);
		BLContextUtil.valiPara(dto);
		UserRelationEntity userRelationEntity = currentUserRelation(context);
		List<Integer> ids = dto.getIds();
		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i).equals(userRelationEntity.getId())) {
				ids.remove(i);
			}
		}
		// 获取数据库资源
		Dao dao = daoFactory.getDao(context, currentclass);
		return dao.delete(dto);
	}

	/**
	 * 获取当前用户账户关系
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws SessionContainerException
	 * @throws DaoAppException
	 */
	public static UserRelationEntity currentUserRelation(RequestContext context)
			throws BlAppException, SessionContainerException, DaoAppException {
		String userId = BLContextUtil.getSessionUserId(context);
		synchronized (LockUtil.getUserLock(userId)) {
			SearchUserRelationDto dto = new SearchUserRelationDto();
			dto.setUserId(userId);
			Dao dao = daoFactory.getDao(context, currentclass);
			UserRelationEntity reult = (UserRelationEntity) dao.query(dto, true);
			if (reult == null) {
				UserRelationManger.saveMainUserRelation(context);
				reult = (UserRelationEntity) dao.query(dto, true);
			}
			return reult;
		}
	}

	/**
	 * 获取当前用户账户关系
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws SessionContainerException
	 * @throws DaoAppException
	 */
	public static UserRelationEntity getUserRelation(RequestContext context, String userid)
			throws BlAppException, DaoAppException {
		if (Str.isNullOrEmpty(userid)) {
			return null;
		}
		SearchUserRelationDto dto = new SearchUserRelationDto();
		dto.setUserId(userid);
		Dao dao = daoFactory.getDao(context, currentclass);
		synchronized (LockUtil.getUserLock(userid)) {
			UserRelationEntity reult = (UserRelationEntity) dao.query(dto, true);
			if (reult == null) {
				reult = new UserRelationEntity();
				reult.setId(0);
				reult.setFatherUserId(userid);
				reult.setUserId(userid);
				reult.setState(UserStatus.ENABLE.getValue());
				// 主账号
				reult.setUserType(UserRelationType.MAINUSER.getValue());
				UserRelationManger.saveUserRelation(context, reult);
			}
			return reult;
		}
	}

	public static List<Entity> userRelations(RequestContext context, Integer state)
			throws BlAppException, SessionContainerException, DaoAppException {
		SearchUserRelationDto dto = new SearchUserRelationDto();
		UserRelationEntity serRelationEntity = UserRelationManger.currentUserRelation(context);
		if (serRelationEntity == null) {
			UserRelationManger.saveMainUserRelation(context);
			dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
		} else {
			if (UserRelationType.MAINUSER.getValue() != serRelationEntity.getUserType()) {
				dto.setFatherUserId(serRelationEntity.getFatherUserId());
			} else {
				dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
			}
		}
		dto.setState(state);
		Dao dao = daoFactory.getDao(context, currentclass);
		return dao.queryList(dto);
	}

	public static List<String> userRelationId(RequestContext context, String userid)
			throws BlAppException, DaoAppException {
		if (Str.isNullOrEmpty(userid)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		UserRelationEntity serRelationEntity = getUserRelation(context, userid);
		SearchUserRelationDto dto = new SearchUserRelationDto();
		if (serRelationEntity == null) {
			serRelationEntity = new UserRelationEntity();
			serRelationEntity.setId(0);
			serRelationEntity.setFatherUserId(userid);
			serRelationEntity.setUserId(userid);
			serRelationEntity.setState(UserStatus.ENABLE.getValue());
			// 主账号
			serRelationEntity.setUserType(UserRelationType.MAINUSER.getValue());
			UserRelationManger.saveUserRelation(context, serRelationEntity);
			dto.setFatherUserId(userid);
		} else {
			if (UserRelationType.MAINUSER.getValue() != serRelationEntity.getUserType()) {
				dto.setFatherUserId(serRelationEntity.getFatherUserId());
			} else {
				dto.setFatherUserId(userid);
			}
		}
		dto.setState(null);
		Dao dao = daoFactory.getDao(context, currentclass);
		List<Entity> lists = dao.queryList(dto);
		for (Entity entity : lists) {
			if (entity instanceof UserRelationEntity) {
				UserRelationEntity userRelationEntity = (UserRelationEntity) entity;
				result.add(userRelationEntity.getUserId());
			}
		}
		return result;
	}

	public static List<String> userRelationIds(RequestContext context, Integer state)
			throws BlAppException, SessionContainerException, DaoAppException {
		SearchUserRelationDto dto = new SearchUserRelationDto();
		UserRelationEntity serRelationEntity = UserRelationManger.currentUserRelation(context);
		if (serRelationEntity == null) {
			UserRelationManger.saveMainUserRelation(context);
			dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
		} else {
			if (UserRelationType.MAINUSER.getValue() != serRelationEntity.getUserType()) {
				dto.setFatherUserId(serRelationEntity.getFatherUserId());
			} else {
				dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
			}
		}
		dto.setState(state);
		Dao dao = daoFactory.getDao(context, currentclass);
		List<Entity> list = dao.queryList(dto);
		List<String> resutl = new ArrayList<String>();
		UserRelationEntity relationEntity = null;
		for (Entity entity : list) {
			if (entity instanceof UserRelationEntity) {
				relationEntity = (UserRelationEntity) entity;
				resutl.add(relationEntity.getUserId());
			}
		}
		return resutl;
	}

	/**
	 * 通过主账号得到子账号，复制了userRelationIds的代码，加了判断条件过滤主账号
	 * 
	 * @date 2017-03-28
	 * @author csz
	 */
	public static List<UserRelationEntity> getSubAccountUser(RequestContext context, Integer state)
			throws BlAppException, SessionContainerException, DaoAppException {
		SearchUserRelationDto dto = new SearchUserRelationDto();
		UserRelationEntity serRelationEntity = UserRelationManger.currentUserRelation(context);
		if (serRelationEntity == null) {
			UserRelationManger.saveMainUserRelation(context);
			dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
		} else {
			if (UserRelationType.MAINUSER.getValue() != serRelationEntity.getUserType()) {
				dto.setFatherUserId(serRelationEntity.getFatherUserId());
			} else {
				dto.setFatherUserId(BLContextUtil.getSessionUserId(context));
			}
		}
		dto.setState(state);
		Dao dao = daoFactory.getDao(context, currentclass);
		List<Entity> list = dao.queryList(dto);
		List<UserRelationEntity> resutl = new ArrayList<UserRelationEntity>();
		UserRelationEntity relationEntity = null;
		for (Entity entity : list) {
			if (entity instanceof UserRelationEntity) {
				relationEntity = (UserRelationEntity) entity;
				// 添加子账号到list
				if (UserRelationType.SUBUSER.getValue() == relationEntity.getUserType()) {
					resutl.add(relationEntity);
				}
			}
		}
		return resutl;
	}

	/**
	 * 验证是否为主账户
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	public static void checkMainUser(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		UserRelationEntity serRelationEntity = UserRelationManger.currentUserRelation(context);
		if (serRelationEntity == null) {
			UserRelationManger.saveMainUserRelation(context);
		} else {
			if (UserRelationType.MAINUSER.getValue() != serRelationEntity.getUserType()) {
				CodeTable ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
		}
	}

	public static void zdsLog(RequestContext context, String userId, String param, String result, String exception) {
		ZdsLogDto zdsLogDto = new ZdsLogDto();
		zdsLogDto.setUserId(userId);
		zdsLogDto.setParam(param);
		zdsLogDto.setResult(result);
		zdsLogDto.setException(exception);
		try {
			BLContextUtil.getDao(context, UserRelationResourceDao.class).save(zdsLogDto);
		} catch (Exception e) {
		}
	}
}
