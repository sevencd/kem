package cn.ilanhai.kem.dao.imgvalicode;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.imgvalicode.ImgVailcodeEntity;

@Component("imgvalicodeDao")
public class ImgValicodeDao extends MybatisBaseDao  {

	public ImgValicodeDao() throws DaoAppException {
		super();
	}
	private Logger logger = Logger.getLogger(ImgValicodeDao.class);
	
	@Override
	public int save(Entity enity) throws DaoAppException {
		ImgVailcodeEntity imgVailcodeEntity = null;
		imgVailcodeEntity = (ImgVailcodeEntity) enity;
		if (!this.isExists(imgVailcodeEntity))
			return this.add(imgVailcodeEntity);
		return this.update(imgVailcodeEntity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdDto) {
			return this.queryById((IdDto) entity);
		}
		return null;
	}
	

	private int update(ImgVailcodeEntity entity) throws DaoAppException {
		logger.info("更新验证码");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("ImgValicode.updateidentitycode", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(ImgVailcodeEntity entity) throws DaoAppException {
		logger.info("新加验证码");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("ImgValicode.insertidentitycode", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	@SuppressWarnings("unused")
	private boolean isExists(ImgVailcodeEntity entity) throws DaoAppException {
		logger.info("查询验证码是否存在");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer isexists = sqlSession.selectOne("ImgValicode.selectidfromidentitycode", entity);
			if (isexists == null) {
				return false;
			}
			return isexists > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
	

	private Entity queryById(IdDto entity) throws DaoAppException {
		logger.info("查询验证码");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("ImgValicode.searchidentifycode", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
}
