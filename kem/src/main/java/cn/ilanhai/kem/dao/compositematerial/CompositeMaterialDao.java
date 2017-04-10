package cn.ilanhai.kem.dao.compositematerial;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.compositematerial.CompositeMaterialEntity;
import cn.ilanhai.kem.domain.compositematerial.QueryCMData;

@Component("compositeMaterialDao")
public class CompositeMaterialDao extends MybatisBaseDao {

	public CompositeMaterialDao() throws DaoAppException {
		super();
	}
	private Logger logger = Logger.getLogger(CompositeMaterialDao.class);
	
	@Override
	public int save(Entity entity) throws DaoAppException {
		if (!this.isExists((CompositeMaterialEntity) entity))
			return this.add((CompositeMaterialEntity) entity);
		else
			return this.update((CompositeMaterialEntity) entity);
	}
	
	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdEntity)
			return query((IdEntity<String>) entity);
		return null;
	}
	
	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryCMData)
			return query((QueryCMData) entity).iterator();
		return null;
	}

	private int update(CompositeMaterialEntity entity) throws DaoAppException {
		logger.info("更新组合素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("CompositeMaterial.updatecomposite", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(CompositeMaterialEntity entity) throws DaoAppException {
		logger.info("新增组合素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("CompositeMaterial.insertcomposite", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(CompositeMaterialEntity entity) throws DaoAppException {
		logger.info("判断组合素材:"+entity.getId()+",是否存在");
		String val = null;
		val = this.queryIsExists(entity);
		if (val != null) {
			logger.info("组合素材:"+entity.getId()+",存在");
			return true;
		}
		logger.info("组合素材:"+entity.getId()+",不存在");
		return false;
	}
	
	private String queryIsExists(CompositeMaterialEntity entity) throws DaoAppException {
		logger.info("查找组合素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("CompositeMaterial.searchidfromcomposite", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	
	private Entity query(IdEntity<String> entity) throws DaoAppException {
		logger.info("查找组合素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("CompositeMaterial.searchcomposite", entity.getId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	
	public List<Entity> query(QueryCMData entity) throws DaoAppException {
		logger.info("查找组合素材");
		SqlSession sqlSession = null;
		int recordCount = -1;
		try {
			sqlSession = this.getSqlSession();
			entity.setOrderBy(entity.getOrder().toString());
			recordCount = sqlSession.selectOne("CompositeMaterial.searchcompositescount", entity);
			entity.setRecordCount(recordCount);
			return sqlSession.selectList("CompositeMaterial.searchcomposites", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

}
