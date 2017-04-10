package cn.ilanhai.kem.dao.key;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.key.KeyConfigEntity;
import cn.ilanhai.kem.domain.user.frontuser.dto.SaveUserCodeDto;

@Component("keyConfigDao")
public class KeyConfigDao extends MybatisBaseDao {

	public KeyConfigDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(KeyConfigDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (entity instanceof KeyConfigEntity)
			return this.save((KeyConfigEntity) entity);
		if (entity instanceof SaveUserCodeDto)
			return this.save((SaveUserCodeDto) entity);
		return -1;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof KeyConfigEntity) {
			return queryOne((KeyConfigEntity) entity);
		}
		if (entity instanceof IdEntity) {
			return queryOne((IdEntity) entity);
		}
		if (entity instanceof SaveUserCodeDto) {
			return queryOne((SaveUserCodeDto) entity);
		}
		return null;
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null) {
			return this.queryOne().iterator();
		}
		return null;
	}

	private Entity queryOne(KeyConfigEntity entity) throws DaoAppException {
		logger.info("查找key_config id = " + entity.getId());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(this.baseNamespace + "KeyConfig.searchkeyconfig", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
	
	private Entity queryOne(SaveUserCodeDto entity) throws DaoAppException {
		logger.info("查找code" + entity.getCode());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(this.baseNamespace + "KeyConfig.searchCode", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryOne(IdEntity<String> entity) throws DaoAppException {
		logger.info("查找userid = " + entity.getId());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(this.baseNamespace + "KeyConfig.searchUserCode", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private List<Entity> queryOne() throws DaoAppException {
		logger.info("查找key_config");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList(this.baseNamespace + "KeyConfig.searchallkeyconfig");
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int save(SaveUserCodeDto entity) throws DaoAppException {
		logger.info("保存code:" + entity.getUserId() + "_" + entity.getCode());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			logger.info("更新entity:" + entity);
//			sqlSession.update(this.baseNamespace + "KeyConfig.saveCodeforUser", entity);
			int val = sqlSession.insert(this.baseNamespace + "KeyConfig.saveCode", entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int save(KeyConfigEntity entity) throws DaoAppException {
		logger.info("更新key_config id = " + entity.getId());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			logger.info("更新entity:" + entity);
			int val = sqlSession.update(this.baseNamespace + "KeyConfig.updatekeyconfig", entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
}
