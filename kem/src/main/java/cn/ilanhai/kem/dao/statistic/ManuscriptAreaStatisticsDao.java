package cn.ilanhai.kem.dao.statistic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptAreaEntity;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptAreaDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryStatsticAreaDto;

/**
 * 稿件区域统计数据访问对象
 * 
 * @author he
 *
 */
@Component("manuscriptareastatisticsDao")
public class ManuscriptAreaStatisticsDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public ManuscriptAreaStatisticsDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof ManuscriptAreaEntity) {
			return saveAreaDate((ManuscriptAreaEntity) entity);
		}
		return super.save(entity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryStatsticAreaDto) {
			List<Entity> top10 = queryTopTenAreaEntity((QueryStatsticAreaDto) entity);
			if (top10 == null) {
				return null;
			}
			return top10.iterator();
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryStatsticAreaDto) {
			ManuscriptAreaDto max = queryMaxAreaEntity((QueryStatsticAreaDto) entity);
			if (max == null) {
				return new ManuscriptAreaDto();
			}
			return max;
		}
		return super.query(entity, flag);
	}

	private List<Entity> queryTopTenAreaEntity(QueryStatsticAreaDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList("areaStatistic.queryData", entity);
	}

	private ManuscriptAreaDto queryMaxAreaEntity(QueryStatsticAreaDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectOne("areaStatistic.queryMaxData", entity);
	}

	/**
	 * 保存 区域访问信息
	 * 
	 * @param entity
	 * @return
	 */
	private int saveAreaDate(ManuscriptAreaEntity entity) {
		if (isExist(entity.getVisitUrl(), entity.getAreaName(), entity.getAreaCity(), entity.getAreaAddTime())) {
			return updateAreaEntity(entity);
		}
		return addAreaEntity(entity);
	}

	/**
	 * 新增地域记录
	 * 
	 * @param entity
	 * @return
	 */
	private int addAreaEntity(ManuscriptAreaEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert("areaStatistic.addData", entity);
	}

	/**
	 * 更新地域记录
	 * 
	 * @param entity
	 * @return
	 */
	private int updateAreaEntity(ManuscriptAreaEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert("areaStatistic.updateDate", entity);
	}

	/**
	 * 判断是否存在该记录 如果存在则数量+1 如果不存在则创建记录
	 * 
	 * @param url
	 *            浏览URL
	 * @param areaName
	 *            区域名称
	 * @return
	 */
	private boolean isExist(String url, String areaName, String areaCity, Date addTime) {
		QueryStatsticAreaDto queryStatsticAreaDto = new QueryStatsticAreaDto();
		queryStatsticAreaDto.setVisitUrl(url);
		queryStatsticAreaDto.setAreaName(areaName);
		queryStatsticAreaDto.setAreaCity(areaCity);
		queryStatsticAreaDto.setAreaAddTime(addTime);
		Integer count = sqlSession.selectOne("areaStatistic.queryExistData", queryStatsticAreaDto);
		if (count <= 0) {
			return false;
		}
		return true;
	}
}
