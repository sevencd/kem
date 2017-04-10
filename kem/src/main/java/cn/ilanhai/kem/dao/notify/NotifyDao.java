package cn.ilanhai.kem.dao.notify;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.notify.NotifyEntity;
import cn.ilanhai.kem.domain.notify.QueryUserNotifyData;
import cn.ilanhai.kem.domain.notify.UserNotifyCount;

@Component("notifyDao")
public class NotifyDao extends MybatisBaseDao {

	public NotifyDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(NotifyDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (!this.isExists((NotifyEntity) entity))
			return this.add((NotifyEntity) entity);
		else
			return this.update((NotifyEntity) entity);

	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdDto)
			return query((IdDto) entity);
		else if (entity instanceof UserNotifyCount) {
			query((UserNotifyCount) entity);
			return null;
		}
		return null;
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryUserNotifyData)
			return query((QueryUserNotifyData) entity).iterator();
		return null;

	}

	private int update(NotifyEntity entity) throws DaoAppException {
		logger.info("更新notify");
		SqlSession sqlSession = null;
		try {
			if (entity.getId() == null)
				entity.setId(0);
			sqlSession = this.getSqlSession();
			return sqlSession.update("Notify.updatenotify", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(NotifyEntity entity) throws DaoAppException {
		logger.info("新增notify");
		SqlSession sqlSession = null;
		try {
			if (entity.getId() == null)
				entity.setId(0);
			sqlSession = this.getSqlSession();
			return sqlSession.insert("Notify.insertnotify", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(NotifyEntity entity) throws DaoAppException {
		logger.info("查询notify中id=" + entity.getId() + "是否存在");
		SqlSession sqlSession = null;
		try {
			if (entity.getId() == null)
				entity.setId(0);
			sqlSession = this.getSqlSession();
			Integer val = sqlSession.selectOne("Notify.searchidfromnotify",
					entity);
			if (val == null) {
				return false;
			}
			return val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity query(IdDto entity) throws DaoAppException {
		logger.info("查询notify中id=" + entity.getId());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Notify.searchnotifybyid", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private void query(UserNotifyCount count) throws DaoAppException {
		if (count == null)
			return;
		count.setCount(this.count(count));
		count.setReadCount(this.readCount(count));
	}

	private Integer readCount(UserNotifyCount entity) throws DaoAppException {
		logger.info("统计target=" + entity.getTarget());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Notify.searchtargerreadcount", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Integer count(UserNotifyCount entity) throws DaoAppException {
		logger.info("统计read = 0,target = " + entity.getTarget());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Notify.searchtargetcount", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	public List<Entity> query(QueryUserNotifyData entity)
			throws DaoAppException {
		logger.info("查询target = " + entity.getTarget());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer count = sqlSession.selectOne("Notify.searchtargetcount",
					entity);
			entity.setRecordCount(count);
			return sqlSession.selectList("Notify.searchnotifys", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
}
