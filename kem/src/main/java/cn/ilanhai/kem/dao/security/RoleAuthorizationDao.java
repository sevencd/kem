package cn.ilanhai.kem.dao.security;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;

@Component("roleauthorizationDao")
public class RoleAuthorizationDao extends MybatisBaseDao {
	public RoleAuthorizationDao() throws DaoAppException {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		List<Entity> entities = null;
		try {
			sqlSession = this.getSqlSession();
			entities = sqlSession
					.selectList(
							"cn.ilanhai.kem.dao.security.RoleAuthorizationDao.selectRoleAuthorizationByResourceURIAndGroupId",
							entity);
			if (entities == null)
				return null;
			return entities.iterator();
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}
}
