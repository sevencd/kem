package cn.ilanhai.kem.dao.ver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.auth.AuthConfigEntity;
import cn.ilanhai.kem.domain.ver.DbVerEntity;

@Component("MyBatisDbVerDao")
public class MyBatisDbVerDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MyBatisDbVerDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		DbVerEntity dbVer = null;
		if (entity == null)
			return -1;
		dbVer = (DbVerEntity) entity;
		logger.info(dbVer.getId());
		if (!isExists(dbVer))
			return this.add(dbVer);
		return this.update(dbVer);
	}

	private boolean isExists(DbVerEntity dbVer) throws DaoAppException {

		IdEntity<Integer> id = new IdEntity<Integer>();
		id.setId(dbVer.getId());
		DbVerEntity entity = sqlSession.selectOne("DbVer.queryDbVerById", id);
		if (entity == null)
			return false;
		return true;
	}

	private int add(DbVerEntity dbVer) throws DaoAppException {
		return sqlSession.insert("DbVer.insert", dbVer);
	}

	private int update(DbVerEntity dbVer) throws DaoAppException {
		return sqlSession.update("DbVer.updateDbVer", dbVer);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		List<Entity> ls = sqlSession.selectList("DbVer.queryDbVer", entity);
		return ls.iterator();
	}

	private Logger logger = Logger.getLogger(MyBatisDbVerDao.class);
}
