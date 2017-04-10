package cn.ilanhai.kem.dao.smsright;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.smsright.AddSmsQuantityEntity;
import cn.ilanhai.kem.domain.smsright.SearchSmsQuantityByUser;
import cn.ilanhai.kem.domain.smsright.SmsEntity;
import cn.ilanhai.kem.domain.smsright.SmsInfoEntity;
import cn.ilanhai.kem.domain.smsright.dto.QueryOneSmsEntityDto;
import cn.ilanhai.kem.domain.smsright.dto.QueryOneSmsInfoEntityDto;
import cn.ilanhai.kem.domain.smsright.dto.QuerySmsInfoEntityDto;

@Component("smsrightDao")
public class SmsRightDao extends MybatisBaseDao {

	public SmsRightDao() throws DaoAppException {
		super();
		// TODO Auto-generated constructor stub
	}

	private Logger logger = Logger.getLogger(SmsRightDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof AddSmsQuantityEntity) {
			return saveSms((AddSmsQuantityEntity) entity);
		} else if (entity instanceof SmsEntity) {
			SmsEntity smsEntity = (SmsEntity) entity;
			return this.saveSmsEntity(smsEntity);
		}
		if (entity instanceof SmsInfoEntity) {
			SmsInfoEntity smsInfoEntity = (SmsInfoEntity) entity;
			return this.saveSmsInfoEntity(smsInfoEntity);

		}
		return super.save(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchSmsQuantityByUser) {
			return queryEmailQuantity((SearchSmsQuantityByUser) entity);
		} else if (entity instanceof QueryOneSmsEntityDto) {
			QueryOneSmsEntityDto queryOneSmsEntityDto = (QueryOneSmsEntityDto) entity;
			return queryOneSmsEntityBySmsId(queryOneSmsEntityDto);

		} else if (entity instanceof QueryOneSmsInfoEntityDto) {
			QueryOneSmsInfoEntityDto queryOneSmsInfoEntityDto = (QueryOneSmsInfoEntityDto) entity;
			return this
					.queryOneSmsInfoEntityByInfoKeyAndSmsId(queryOneSmsInfoEntityDto);

		}
		return super.query(entity, flag);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QuerySmsInfoEntityDto) {
			QuerySmsInfoEntityDto querySmsInfoEntityDto = (QuerySmsInfoEntityDto) entity;
			return this.querySmsInfoEntityBySmsId(querySmsInfoEntityDto)
					.iterator();

		}
		return super.query(entity);
	}

	private int saveSms(AddSmsQuantityEntity entity) throws DaoAppException {
		if (!isExists((AddSmsQuantityEntity) entity)) {
			return this.add((AddSmsQuantityEntity) entity);
		} else {
			return this.update((AddSmsQuantityEntity) entity);
		}
	}

	private int update(AddSmsQuantityEntity entity) throws DaoAppException {
		logger.info("更新短信剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("SmsRight.updatesmsquantity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(AddSmsQuantityEntity entity) throws DaoAppException {
		logger.info("新增短信剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("SmsRight.insertsmsquantity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(AddSmsQuantityEntity entity)
			throws DaoAppException {
		logger.info("查询用户：" + entity.getUserId() + "是否有短信");
		SqlSession sqlSession = null;
		Integer val;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("SmsRight.searchisexistsusersms",
					entity.getUserId());
			if (val == null || val == 0) {
				logger.info("没有找到用户：" + entity.getUserId());
				return false;
			}
			logger.info("找到用户：" + entity.getUserId() + "id=" + val);
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryEmailQuantity(SearchSmsQuantityByUser entity)
			throws DaoAppException {
		logger.info("查询短信剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("SmsRight.selectsmsquantity",
					entity.getUserId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int saveSmsEntity(SmsEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (!isExistsSmsEntity(entity))
			return this.addSmsEntity(entity);
		return this.updateSmsEntity(entity);
	}

	private int addSmsEntity(SmsEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("SmsRight.addSmsEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int updateSmsEntity(SmsEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("SmsRight.updateSmsEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExistsSmsEntity(SmsEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		String tmp = null;
		try {
			sqlSession = this.getSqlSession();
			tmp = sqlSession.selectOne("SmsRight.isExistsSmsEntity",
					entity.getUserId());
			if (Str.isNullOrEmpty(tmp))
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryOneSmsEntityBySmsId(QueryOneSmsEntityDto dto)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("SmsRight.queryOneSmsEntityBySmsId",
					dto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int saveSmsInfoEntity(SmsInfoEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (!isExistsSmsInfoEntity(entity))
			return this.addSmsInfoEntity(entity);
		return this.updateSmsInfoEntity(entity);
	}

	private int addSmsInfoEntity(SmsInfoEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("SmsRight.addSmsInfoEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int updateSmsInfoEntity(SmsInfoEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("SmsRight.updateSmsInfoEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExistsSmsInfoEntity(SmsInfoEntity entity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		Integer tmp = 0;
		try {
			sqlSession = this.getSqlSession();
			tmp = sqlSession
					.selectOne("SmsRight.isExistsSmsInfoEntity", entity);
			if (tmp == null || tmp <= 0)
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryOneSmsInfoEntityByInfoKeyAndSmsId(
			QueryOneSmsInfoEntityDto dto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(
					"SmsRight.queryOneSmsInfoEntityByInfoKeyAndSmsId", dto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private List<Entity> querySmsInfoEntityBySmsId(QuerySmsInfoEntityDto dto)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("SmsRight.querySmsInfoEntityBySmsId",
					dto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

}
