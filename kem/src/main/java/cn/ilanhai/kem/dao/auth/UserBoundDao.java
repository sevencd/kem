package cn.ilanhai.kem.dao.auth;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.auth.QueryUserBound;
import cn.ilanhai.kem.domain.auth.UserBoundEntity;

@Component("userboundDao")
public class UserBoundDao extends MybatisBaseDao {

	public UserBoundDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(UserBoundDao.class);

	@Override
	public int save(Entity enity) throws DaoAppException {
		UserBoundEntity userBoundEntity = null;
		userBoundEntity = (UserBoundEntity) enity;
		if (!this.isExists(userBoundEntity)) {
			return this.add(userBoundEntity);
		}
		return this.update(userBoundEntity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof QueryUserBound) {
			return queryData((QueryUserBound) entity);
		}
		return null;
	}

	private int update(UserBoundEntity userBoundEntity) throws DaoAppException {
		logger.info("更新用户绑定数据");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("UserBound.updateuserbound",
					userBoundEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(UserBoundEntity userBoundEntity) throws DaoAppException {
		logger.info("新加用户绑定数据");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("UserBound.insertuserbound",
					userBoundEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(UserBoundEntity userBoundEntity)
			throws DaoAppException {
		logger.info("查询用户id = " + userBoundEntity.getId() + "是否绑定");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer userBond = sqlSession.selectOne(
					"UserBound.searchuserboundid", userBoundEntity);
			if (userBond == null || userBond == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryData(QueryUserBound entity) throws DaoAppException {
		logger.info("查询type = " + entity.getType() + "tag = " + entity.getTag()
				+ "的绑定用户");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("UserBound.searchuserboundbytype",
					entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
}
