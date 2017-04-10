package cn.ilanhai.kem.dao.statistic;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.dto.CountManuscriptDataStatisticsDto;
import cn.ilanhai.kem.domain.statistic.dto.CountSessionManuscriptVisitDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryLimitManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryTotalManuscriptStatisticDto;

/**
 * 稿件数据统计数据访问对象
 * 
 * @author he
 *
 */
@Component("manuscriptdatastatisticsDao")
public class ManuscriptDataStatisticsDao extends MybatisBaseDao {
	public ManuscriptDataStatisticsDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		if (entity == null)
			return -1;
		if (!(entity instanceof ManuscriptDataStatisticsEntity))
			return -1;
		manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) entity;
		if (!isExists(manuscriptDataStatisticsEntity))
			return add(manuscriptDataStatisticsEntity);
		return update(manuscriptDataStatisticsEntity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			return null;
		if (entity instanceof QueryTotalManuscriptStatisticDto) {
			QueryTotalManuscriptStatisticDto totalManuscriptStatisticDto = (QueryTotalManuscriptStatisticDto) entity;
			this.total(totalManuscriptStatisticDto);
			return null;
		} else if (entity instanceof QueryLimitManuscriptStatisticDto) {
			QueryLimitManuscriptStatisticDto limitManuscriptStatisticDto = (QueryLimitManuscriptStatisticDto) entity;
			return this.query2(limitManuscriptStatisticDto).iterator();
		} else if (entity instanceof QueryManuscriptStatisticDto) {
			QueryManuscriptStatisticDto manuscriptStatisticDto = (QueryManuscriptStatisticDto) entity;
			return this.query(manuscriptStatisticDto).iterator();
		}
		if (entity instanceof CountSessionManuscriptVisitDto) {
			CountManuscriptDataStatisticsDto countManuscriptDataStatisticsDto = (CountManuscriptDataStatisticsDto) entity;
			this.countQuantity(countManuscriptDataStatisticsDto);
			return null;
		} else {
			return null;
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			return null;
		if (!(entity instanceof QueryOneManuscriptStatisticDto))
			return null;
		QueryOneManuscriptStatisticDto oneManuscriptStatisticDto = (QueryOneManuscriptStatisticDto) entity;
		return this.queryOne(oneManuscriptStatisticDto);
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int add(ManuscriptDataStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.insert("cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.addManuscriptDataStatisticsEntity",
							entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int update(ManuscriptDataStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.insert("cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.updateManuscriptDataStatisticsEntity",
							entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * 是否存在,根据浏览url、数据类型、添加时间
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExists(ManuscriptDataStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Long ret = null;
		try {
			sqlSession = this.getSqlSession();
			ret = sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.isExistsManuscriptDataStatisticsEntity",
							entity);
			if (ret == null)
				return false;
			if (ret <= 0)
				return false;
			return true;

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * 统计数量,根据浏览url（模糊匹配）、 数据类型
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private long total(QueryTotalManuscriptStatisticDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Long ret = null;
		try {
			sqlSession = this.getSqlSession();
			ret = sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.totalManuscriptDataStatisticsEntity",
							entity);

			if (ret == null)
				ret = new Long(0);
			entity.setTotalQuantity(ret);
			return ret;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * 查询数据,根据浏览url（模糊匹配）、 数据类型
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> query(QueryManuscriptStatisticDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.selectList(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.queryManuscriptDataStatisticsEntity",
							entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * 查询数据(取最近2条),根据浏览url（模糊匹配）、 数据类型
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> query2(QueryLimitManuscriptStatisticDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;

		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.selectList(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.query2ManuscriptDataStatisticsEntity",
							entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryOne(QueryOneManuscriptStatisticDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;

		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.oneManuscriptDataStatisticsEntity",
							entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	/**
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private long countQuantity(CountManuscriptDataStatisticsDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Long ret = null;
		try {
			sqlSession = this.getSqlSession();
			ret = sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao.countQuantityManuscriptDataStatisticsEntity",
							entity);

			if (ret == null)
				ret = new Long(0);
			entity.setCountQuantity(ret);
			return ret;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

}
