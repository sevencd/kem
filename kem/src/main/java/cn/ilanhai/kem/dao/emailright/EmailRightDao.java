package cn.ilanhai.kem.dao.emailright;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.email.AddEmailQuantityEntity;
import cn.ilanhai.kem.domain.email.MailEntity;
import cn.ilanhai.kem.domain.email.MailInfoEntity;
import cn.ilanhai.kem.domain.email.QueryEmailContractDto;
import cn.ilanhai.kem.domain.email.QueryEmailContractRequest;
import cn.ilanhai.kem.domain.email.QueryEmailGroupDto;
import cn.ilanhai.kem.domain.email.QueryEmailGroupRequest;
import cn.ilanhai.kem.domain.email.QueryMailInfoDto;
import cn.ilanhai.kem.domain.email.QueryOneMailDto;
import cn.ilanhai.kem.domain.email.QueryOneMailInfoDto;
import cn.ilanhai.kem.domain.email.SaveContractRequest;
import cn.ilanhai.kem.domain.email.SearchEmailQuantityByUser;

@Component("emailrightDao")
public class EmailRightDao extends MybatisBaseDao {

	public EmailRightDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(EmailRightDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof AddEmailQuantityEntity) {
			return saveEmail((AddEmailQuantityEntity) entity);
		} else if (entity instanceof MailEntity) {
			MailEntity mailEntity = (MailEntity) entity;
			return this.saveMail(mailEntity);
		} else if (entity instanceof MailInfoEntity) {
			MailInfoEntity mailInfoEntity = (MailInfoEntity) entity;
			return saveMailInfo(mailInfoEntity);
		} else if (entity instanceof SaveContractRequest) {
			return saveContract((SaveContractRequest) entity);
		}
		return super.save(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchEmailQuantityByUser) {
			return queryEmailQuantity((SearchEmailQuantityByUser) entity);
		} else if (entity instanceof QueryOneMailDto) {
			QueryOneMailDto queryOneMailDto = (QueryOneMailDto) entity;
			return this.queryOneMailByEmailId(queryOneMailDto);

		} else if (entity instanceof QueryOneMailInfoDto) {
			QueryOneMailInfoDto queryOneMailInfoDto = (QueryOneMailInfoDto) entity;
			return this.queryOneMailInfoEntityByMailIdAndKey(queryOneMailInfoDto);

		}
		return super.query(entity, flag);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryMailInfoDto) {
			return queryMailInfoByEmailId((QueryMailInfoDto) entity).iterator();
		} else if (entity instanceof QueryEmailContractRequest) {
			return queryEmailContract((QueryEmailContractRequest) entity).iterator();
		} else if (entity instanceof QueryEmailContractDto) {
			QueryEmailContractDto queryEmailContractDto = (QueryEmailContractDto) entity;
			return this.queryEmailContract(queryEmailContractDto).iterator();
		} else if (entity instanceof QueryEmailGroupDto) {
			return queryEmailGroup((QueryEmailGroupDto) entity).iterator();
		} else if (entity instanceof QueryEmailGroupRequest) {
			return queryEmailGroup((QueryEmailGroupRequest) entity).iterator();
		}
		return super.query(entity);
	}

	private int saveContract(SaveContractRequest entity) throws DaoAppException {
		logger.info("保存邮件群组联系人");
		SqlSession sqlSession = null;
		String emailId;
		try {
			sqlSession = this.getSqlSession();
			logger.info("验证邮件id：" + entity.getEmailId() + "是否存在");
			emailId = sqlSession.selectOne("EmailRight.searchisexistsemailid", entity.getEmailId());
			if (emailId == null) {
				return -100;
			}
			sqlSession.delete("EmailRight.deletegroups", entity.getEmailId());
			return sqlSession.insert("EmailRight.insertgroup", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), e.getMessage());
		} finally {
		}
	}

	private int saveEmail(AddEmailQuantityEntity entity) throws DaoAppException {
		if (!isExists((AddEmailQuantityEntity) entity)) {
			return this.add((AddEmailQuantityEntity) entity);
		} else {
			return this.update((AddEmailQuantityEntity) entity);
		}
	}

	private List<Entity> queryEmailContract(QueryEmailContractRequest entity) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		logger.info("查询邮件id=" + entity.getEmailId() + "联系人");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer count = sqlSession.selectOne("EmailRight.searchemailcontractscount", entity);
			entity.setCount(count);
			return sqlSession.selectList("EmailRight.searchemailcontracts", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}

	}

	private List<Entity> queryEmailGroup(QueryEmailGroupRequest entity) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		logger.info("查询邮件id=" + entity.getEmailId() + "联系人");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer count = sqlSession.selectOne("EmailRight.queryEmailGroupByEmailIdLimitCount", entity);
			entity.setCount(count);
			return sqlSession.selectList("EmailRight.queryEmailGroupByEmailIdLimit", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}

	}

	private List<Entity> queryEmailContract(QueryEmailContractDto entity) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		logger.info("查询邮件id=" + entity.getEmailId() + "联系人");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("EmailRight.queryEmailContractByEmailId", entity.getEmailId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}

	}

	private List<Entity> queryEmailGroup(QueryEmailGroupDto entity) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		logger.info("查询邮件id=" + entity.getEmailId() + "群组");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("EmailRight.queryEmailGroupByEmailId", entity.getEmailId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}

	}

	private int update(AddEmailQuantityEntity entity) throws DaoAppException {
		logger.info("更新邮件剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("EmailRight.updateemailquantity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(AddEmailQuantityEntity entity) throws DaoAppException {
		logger.info("新增邮件剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("EmailRight.insertemailquantity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(AddEmailQuantityEntity entity) throws DaoAppException {
		logger.info("查询用户：" + entity.getUserId() + "是否有邮件");
		SqlSession sqlSession = null;
		Integer val;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("EmailRight.searchisexistsuseremail", entity.getUserId());
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

	private Entity queryEmailQuantity(SearchEmailQuantityByUser entity) throws DaoAppException {
		logger.info("查询邮件剩余数量");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("EmailRight.searchemailquantity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 保存邮件
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveMail(MailEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (!isExistsMail(entity))
			return this.addMail(entity);
		return this.updateMail(entity);
	}

	/**
	 * 邮件是否存
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExistsMail(MailEntity entity) throws DaoAppException {
		if (entity == null)
			return false;
		SqlSession sqlSession = null;
		String tmp = null;
		try {
			sqlSession = this.getSqlSession();
			tmp = sqlSession.selectOne("EmailRight.isExistsMailEntity", entity);
			if (Str.isNullOrEmpty(tmp))
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 添加邮件
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int addMail(MailEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("EmailRight.addMailEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 修改邮件
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int updateMail(MailEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("EmailRight.updateMailEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 通过 mailId 查询邮件
	 * 
	 * @param dto
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryOneMailByEmailId(QueryOneMailDto dto) throws DaoAppException {
		if (dto == null)
			return null;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("EmailRight.selectOneMailEntityByMailId", dto.getEmailId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 保存邮件信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveMailInfo(MailInfoEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (!isExistsMailInfo(entity))
			return this.addMailInfo(entity);
		return this.updateMailInfo(entity);
	}

	/**
	 * 邮件信息是否存在
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExistsMailInfo(MailInfoEntity entity) throws DaoAppException {
		if (entity == null)
			return false;
		SqlSession sqlSession = null;
		Integer tmp = null;
		try {
			sqlSession = this.getSqlSession();
			tmp = sqlSession.selectOne("EmailRight.isExistsMailInfoEntity", entity);
			if (tmp == null || tmp <= 0)
				return false;
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 邮件信息添加
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int addMailInfo(MailInfoEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("EmailRight.addMailInfoEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 保存邮件修改
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int updateMailInfo(MailInfoEntity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("EmailRight.updateMailInfoEntity", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 查询邮件信息
	 * 
	 * @param dto
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> queryMailInfoByEmailId(QueryMailInfoDto dto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("EmailRight.selectMailEntityByEmailId", dto.getEmailId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryOneMailInfoEntityByMailIdAndKey(QueryOneMailInfoDto dto) throws DaoAppException {
		if (dto == null)
			return null;
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("EmailRight.queryOneMailInfoEntityByMailIdAndKey", dto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

}
