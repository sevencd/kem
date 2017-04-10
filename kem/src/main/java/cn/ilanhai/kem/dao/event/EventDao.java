package cn.ilanhai.kem.dao.event;


import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;

@Component("eventDao")
public class EventDao extends MybatisBaseDao  {
	public EventDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert(
					"cn.ilanhai.kem.dao.event.EventDao.addEventEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}
}
