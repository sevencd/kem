package cn.ilanhai.kem.dao.statistic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptStatisticDataEntity;

@Component("statisticDao")
public class ManuscriptStatisticDao extends MybatisBaseDao {

	public ManuscriptStatisticDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof ManuscriptStatisticDataEntity) {
			ManuscriptStatisticDataEntity manuscriptStatisticDataEntity = (ManuscriptStatisticDataEntity) entity;
			SqlSession sqlSession = this.getSqlSession();
			sqlSession.insert("manuscriptStatistic.addData", manuscriptStatisticDataEntity);
		}
		return super.save(entity);
	}

}
