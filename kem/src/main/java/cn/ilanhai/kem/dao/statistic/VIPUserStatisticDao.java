
package cn.ilanhai.kem.dao.statistic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.dto.StatisticRequestDto;
import cn.ilanhai.kem.domain.statistic.dto.VIPUserStatisticResponseDto;

@Component("vipUserStatisticDao")
public class VIPUserStatisticDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public VIPUserStatisticDao() throws DaoAppException {
		super();
	}


	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof StatisticRequestDto) {
			return queryData((StatisticRequestDto) entity);
		}
		return null;
	}

	
	/**
	 * 会员统计总览和明细
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private VIPUserStatisticResponseDto queryData(StatisticRequestDto entity) throws DaoAppException {

		VIPUserStatisticResponseDto count = sqlSession.selectOne(this.baseNamespace+"VIPUserStatisticDao.selectStatisticForCount", entity);
		List<Entity> list=queryDataDetail((StatisticRequestDto) entity);
		count.setDetail(list);
		count.setTotalCount(queryTotalCount(entity));
		count.setStartCount(entity.getStartCount());
		count.setPageSize(entity.getPageSize());
		
		return count;
	}
	private List<Entity> queryDataDetail(StatisticRequestDto entity) throws DaoAppException {
		List<Entity> list = sqlSession.selectList(this.baseNamespace+"VIPUserStatisticDao.selectVIPUserForPage", entity);
		return list;
	}
	private int queryTotalCount(StatisticRequestDto entity) throws DaoAppException {
		int count = sqlSession.selectOne(this.baseNamespace+"VIPUserStatisticDao.selectVIPUserForTotalCount", entity);
		return count;
	}
}
