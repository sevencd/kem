package cn.ilanhai.kem.dao.auth;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;

@Component("authConfigDao")
public class AuthConfigDao extends MybatisBaseDao {

	public AuthConfigDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(AuthConfigDao.class);

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null) {
			return queryOne().iterator();
		}
		return null;
	}

	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return queryOne((IdEntity<Integer>) entity);
		}
		return entity;
	}

	

	private List<Entity> queryOne() throws DaoAppException {
		logger.info("查询第三方登录平台配置");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("AuthConfig.searchauthconfig");
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
	
	private Entity queryOne(IdEntity<Integer> entity) throws DaoAppException {
		logger.info("查询第三方登录平台type = "+entity.getId()+"配置");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("AuthConfig.searchoneauthconfig",entity.getId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

}
