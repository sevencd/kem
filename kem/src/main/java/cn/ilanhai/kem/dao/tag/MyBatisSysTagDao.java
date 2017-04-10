
package cn.ilanhai.kem.dao.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.tag.DeleteTagRequestEntity;
import cn.ilanhai.kem.domain.tag.ResponseTagEntity;
import cn.ilanhai.kem.domain.tag.SearchTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SetSelectionTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SysTagEntity;

@Component("mybatisTagDao")
public class MyBatisSysTagDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MyBatisSysTagDao() throws DaoAppException {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchTagRequestEntity) {
			return queryData((SearchTagRequestEntity) entity);
		}
		return null;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SysTagEntity) {
			return queryData((SysTagEntity) entity);
		}
		if (entity instanceof SetSelectionTagRequestEntity) {
			return queryData((SetSelectionTagRequestEntity) entity);
		}
		if (entity instanceof SearchTagRequestEntity) {
			count.setCount(queryDataForCount((SearchTagRequestEntity) entity));
			return count;
		}
		return null;
	}

	private Iterator<Entity> queryData(SearchTagRequestEntity entity) throws DaoAppException {
		// 获取排序模式
		final String orderMode = SearchTagRequestEntity.choseModle(entity.getOrderMode());
		entity.setOrderModeSQL(orderMode);
		List<Entity> list = sqlSession.selectList(this.baseNamespace+"SysTagDao.querySysTagForPage", entity);
		return list.iterator();
	}

	/**
	 * 查询标签的条数
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int queryDataForCount(SearchTagRequestEntity entity) throws DaoAppException {
		final String orderMode = SearchTagRequestEntity.choseModle(entity.getOrderMode());
		entity.setOrderModeSQL(orderMode);
		Integer count = sqlSession.selectOne(this.baseNamespace+"SysTagDao.querySysTagForCount", entity);
		return count;
	}

	private Entity queryData(SysTagEntity entity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace+"SysTagDao.querySysTag2", entity);
	}

	private Entity queryData(SetSelectionTagRequestEntity entity) throws DaoAppException {
		IdEntity<Integer> tagEntity=new IdEntity<Integer>();
		tagEntity.setId(entity.getTagId());
		return sqlSession.selectOne(this.baseNamespace+"SysTagDao.querySysTagById", tagEntity);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		IdEntity<Integer> tagEntity=new IdEntity<Integer>();
		DeleteTagRequestEntity deleteTagRequestEntity=(DeleteTagRequestEntity)entity;
		tagEntity.setId(deleteTagRequestEntity.getTagId());
		return sqlSession.delete(this.baseNamespace+"SysTagDao.deleteByPrimaryKey", tagEntity);
	}

	@SuppressWarnings("unchecked")
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof SysTagEntity) {
			return save((SysTagEntity) entity);
		} else if (entity instanceof IdEntity) {
			return addQuteNum(((IdEntity<String>) entity).getId());
		}
		return -1;
	}

	private int save(SysTagEntity entity) throws DaoAppException {
		SysTagEntity sysTagEntity = null;
		try {
			sysTagEntity = (SysTagEntity) entity;
			if (entity.getTagId() == null || entity.getTagId() == 0) {
				if (!this.isExists(sysTagEntity))
					return this.add(sysTagEntity);
				return 1;
			} else {
				if (!this.isExistsForId(sysTagEntity))
					return this.add(sysTagEntity);
				return this.update(sysTagEntity);
			}

		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int addQuteNum(String tagName) throws DaoAppException {
		return sqlSession.update(this.baseNamespace+"SysTagDao.updateSysTagQuoteNum", tagName);

	}

	private int add(final SysTagEntity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert(this.baseNamespace+"SysTagDao.insert", entity);
	}

	private int update(final SysTagEntity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return sqlSession.update(this.baseNamespace+"SysTagDao.updateSysTag", entity);

	}

	/**
	 * 判断该标签是否存在
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExists(final SysTagEntity sysTagEntity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace+"SysTagDao.querySysTag", sysTagEntity) != null ? true : false;
	}

	/**
	 * 判断该标签是否存在
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExistsForId(final SysTagEntity sysTagEntity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace+"SysTagDao.querySysTag", sysTagEntity) != null ? true : false;
	}
}
