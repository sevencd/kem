package cn.ilanhai.kem.dao.rights;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.rights.UnRightsLogEntity;
import cn.ilanhai.kem.domain.rights.dto.QueryUnRightsLogDto;
import cn.ilanhai.kem.domain.rights.dto.QueryUnRightsTimesDto;
import cn.ilanhai.kem.domain.rights.dto.SaveUnRightsTimesDto;
import cn.ilanhai.kem.domain.rights.dto.UseUnRightsTimesDto;

@Component("rightsDao")
public class RightsDao extends MybatisBaseDao {
	public RightsDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof SaveUnRightsTimesDto) {
			return saveUnRightsTimes((SaveUnRightsTimesDto) entity);
		} else if (entity instanceof UseUnRightsTimesDto) {
			return useUnRightsTimes((UseUnRightsTimesDto) entity);
		} else if (entity instanceof UnRightsLogEntity) {
			return saveUnRightsLog((UnRightsLogEntity) entity);
		}
		return super.save(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryUnRightsLogDto) {
			return queryUnRightsLog(((QueryUnRightsLogDto) entity).getManuscriptId());
		} else if (entity instanceof QueryUnRightsTimesDto) {
			return queryUnRightsTimes(((QueryUnRightsTimesDto) entity).getUserId());
		}
		return super.query(entity, flag);
	}

	private Entity queryUnRightsLog(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("rights.queryUnRightsLog", manuscriptId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Entity queryUnRightsTimes(String userId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("rights.queryUnRightsTimes", userId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int saveUnRightsTimes(SaveUnRightsTimesDto saveUnRightsTimesDto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			if (queryUnRightsTimes(saveUnRightsTimesDto.getUserId()) == null) {
				return sqlSession.insert("rights.saveUnRightsTimes", saveUnRightsTimesDto);
			} else {
				return sqlSession.update("rights.updateUnRightsTimes", saveUnRightsTimesDto);
			}

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int saveUnRightsLog(UnRightsLogEntity unRightsLogEntity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			if (queryUnRightsLog(unRightsLogEntity.getManuscriptId()) == null) {
				return sqlSession.insert("rights.saveUnRightsLog", unRightsLogEntity);
			} else {
				return sqlSession.update("rights.updateUnRightsLog", unRightsLogEntity);
			}
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int useUnRightsTimes(UseUnRightsTimesDto useUnRightsTimesDto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("rights.useUnRightsTimes", useUnRightsTimesDto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}
}
