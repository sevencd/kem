package cn.ilanhai.kem.dao.user.trafficuser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserEntity;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;

@Component("mybatisTrafficuserDao")
public class MyBatisTrafficuserDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MyBatisTrafficuserDao() throws DaoAppException {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryTrafficUserEntity) {
			return this.queryTrafficuser((QueryTrafficUserEntity) entity);
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return this.queryTrafficuser((IdEntity) entity);
		} else if (entity instanceof QueryTrafficUserEntity) {
			return this.queryTrafficuserForCount((QueryTrafficUserEntity) entity);
		} else if (entity instanceof TrafficuserEntity) {
			return this.queryTrafficuser((TrafficuserEntity) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof TrafficuserEntity) {
			IdEntity<Integer> id = new IdEntity<Integer>();
			Integer trafficuserId = ((TrafficuserEntity) entity).getTrafficuserId();
			if (trafficuserId != null) {
				id.setId(trafficuserId);
				if (queryTrafficuser(id) != null) {
					return this.updateTrafficuser((TrafficuserEntity) entity);
				}
			}
			return this.saveTrafficuser((TrafficuserEntity) entity);
		}
		return super.save(entity);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return this.deleteTrafficuser((IdEntity) entity);
		}
		return super.delete(entity);
	}

	private int saveTrafficuser(final TrafficuserEntity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert(this.baseNamespace+"TrafficuserDao.insert", entity);
	}

	private int updateTrafficuser(TrafficuserEntity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return sqlSession.update(this.baseNamespace+"TrafficuserDao.updateTrafficuser", entity);

	}

	private int deleteTrafficuser(IdEntity<Integer> id) throws DaoAppException {
		if (id == null) {
			return -1;
		}
		return sqlSession.update(this.baseNamespace+"TrafficuserDao.deleteByPrimaryKey", id);
	}

	private Iterator<Entity> queryTrafficuser(QueryTrafficUserEntity trafficuser) throws DaoAppException {
		List<Entity> list = sqlSession.selectList(this.baseNamespace+"TrafficuserDao.queryTrafficuserForPage", trafficuser);
		return list.iterator();
	}

	private Entity queryTrafficuserForCount(QueryTrafficUserEntity trafficuser) throws DaoAppException {
		Integer count = sqlSession.selectOne(this.baseNamespace+"TrafficuserDao.queryTrafficuserForCount", trafficuser);
		CountDto countDto = new CountDto();
		countDto.setCount(count);
		return countDto;
	}

	private TrafficuserEntity queryTrafficuser(IdEntity<Integer> id) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace+"TrafficuserDao.queryTrafficuserById", id);
	}

	private TrafficuserEntity queryTrafficuser(TrafficuserEntity trafficuserEntity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace+"TrafficuserDao.queryTrafficuser", trafficuserEntity);

	}

}
