package cn.ilanhai.kem.dao.statistic;

import java.util.Iterator;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.domain.statistic.dto.CountSessionManuscriptVisitDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptChannelStatisticsDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptVisitDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryTotalManuscriptStatisticDto;

/**
 * 稿件浏览数据访问对象
 * 
 * @author he
 *
 */
@Component("manuscriptvisitDao")
public class ManuscriptVisitDao extends MybatisBaseDao {
	public ManuscriptVisitDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		ManuscriptVisitEntity manuscriptVisitEntity = null;
		if (entity == null)
			return -1;
		if (!(entity instanceof ManuscriptVisitEntity))
			return -1;
		manuscriptVisitEntity = (ManuscriptVisitEntity) entity;

		return add(manuscriptVisitEntity);

	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			return null;
		if (entity instanceof CountSessionManuscriptVisitDto) {
			CountSessionManuscriptVisitDto countSessionManuscriptVisitDto = (CountSessionManuscriptVisitDto) entity;
			this.countSession(countSessionManuscriptVisitDto);
			return null;
		}
		return null;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			return null;
		if (!(entity instanceof QueryOneManuscriptVisitDto))
			return null;
		QueryOneManuscriptVisitDto oneManuscriptVisitDto = (QueryOneManuscriptVisitDto) entity;
		return this.queryOne(oneManuscriptVisitDto);
	}

	private int add(ManuscriptVisitEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.insert("cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao.addManuscriptVisitEntity",
							entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private Entity queryOne(QueryOneManuscriptVisitDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;

		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao.queryOneManuscriptVisitEntity",
							entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private long countSession(CountSessionManuscriptVisitDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Integer ret = null;
		try {
			sqlSession = this.getSqlSession();
			ret = sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao.countSessionManuscriptVisitEntity",
							entity);

			if (ret == null)
				ret = new Integer(0);
			entity.setQuantity(ret);
			return ret;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}
}
