package cn.ilanhai.kem.dao.user.trafficuser;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.DrawPrizeRequestDto;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserTypeEntity;
@Component("trafficuserPluginDao")
public class TrafficuserPluginDao extends MybatisBaseDao {

	public TrafficuserPluginDao() throws DaoAppException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof DrawPrizeRequestDto) {
			return queryTrafficUserPlugin((DrawPrizeRequestDto)entity).iterator();
		}
		else if (entity instanceof QueryTrafficUserTypeEntity) {
			return queryTrafficUserType((QueryTrafficUserTypeEntity)entity).iterator();
		}
		return null;
	}
	
	
	private  List<Entity> queryTrafficUserPlugin(DrawPrizeRequestDto request) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return  sqlSession.selectList("TrafficUserPluginSql.querytrafficplugininfo", request);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	
	
	private List<Entity> queryTrafficUserType(QueryTrafficUserTypeEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return  sqlSession.selectList("TrafficUserPluginSql.querytrafficplugintype", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	
}
