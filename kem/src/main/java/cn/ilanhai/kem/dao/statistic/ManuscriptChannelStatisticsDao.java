package cn.ilanhai.kem.dao.statistic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptChannelStatisticsEntity;

import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptChannelStatisticsDto;

/**
 * 稿件渠道统计数据访问对象
 * 
 * @author he
 *
 */
@Component("manuscriptchannelstatisticsDao")
public class ManuscriptChannelStatisticsDao extends MybatisBaseDao {
	public ManuscriptChannelStatisticsDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		ManuscriptChannelStatisticsEntity manuscriptChannelStatisticsEntity = null;
		if (entity == null)
			return -1;
		if (!(entity instanceof ManuscriptChannelStatisticsEntity))
			return -1;
		manuscriptChannelStatisticsEntity = (ManuscriptChannelStatisticsEntity) entity;
		if (!isExists(manuscriptChannelStatisticsEntity))
			return add(manuscriptChannelStatisticsEntity);
		return update(manuscriptChannelStatisticsEntity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			return null;
		if (!(entity instanceof QueryOneManuscriptChannelStatisticsDto))
			return null;
		QueryOneManuscriptChannelStatisticsDto oneManuscriptChannelStatisticsDto = (QueryOneManuscriptChannelStatisticsDto) entity;
		return this.queryOne(oneManuscriptChannelStatisticsDto);
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int add(ManuscriptChannelStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.insert("cn.ilanhai.kem.dao.statistic.ManuscriptChannelStatisticsDao.addManuscriptChannelStatisticsEntity",
							entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private boolean isExists(ManuscriptChannelStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Long ret = null;
		try {
			sqlSession = this.getSqlSession();
			ret = sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptChannelStatisticsDao.isExistsManuscriptChannelStatisticsEntity",
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

	private int update(ManuscriptChannelStatisticsEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.insert("cn.ilanhai.kem.dao.statistic.ManuscriptChannelStatisticsDao.updateManuscriptChannelStatisticsEntity",
							entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private Entity queryOne(QueryOneManuscriptChannelStatisticsDto entity)
			throws DaoAppException {
		SqlSession sqlSession = null;

		try {
			sqlSession = this.getSqlSession();
			return sqlSession
					.selectOne(
							"cn.ilanhai.kem.dao.statistic.ManuscriptChannelStatisticsDao.oneManuscriptChannelStatisticsEntity",
							entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

}
