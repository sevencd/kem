package cn.ilanhai.kem.dao.user.backuser.frontuser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.MapEntity;
import cn.ilanhai.kem.domain.user.frontuser.BackuserManageFromtUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.LoadUserInfoByEditionDto;
import cn.ilanhai.kem.domain.user.frontuser.LoadUserInfoByType;
import cn.ilanhai.kem.domain.user.manageuser.QueryCondition.GetUserDetailQCondition;;

@Component("manageuserDao")
public class BackuserManagerFrontDao extends MybatisBaseDao {

	private Logger logger = Logger.getLogger(BackuserManagerFrontDao.class);

	public BackuserManagerFrontDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof BackuserManageFromtUserInfoEntity)
			return this.updateStatus((BackuserManageFromtUserInfoEntity) entity);
		return -1;
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof LoadUserInfoByEditionDto) {
			return this.loadUserInfo((LoadUserInfoByEditionDto) entity).iterator();
		}
		if (entity instanceof LoadUserInfoByType) {
			return this.loadUserInfo((LoadUserInfoByType) entity).iterator();
		}
		return null;
	}

	/**
	 * 按版本加载用户信息，查询结果分页
	 */
	private List<Entity> loadUserInfo(LoadUserInfoByEditionDto entity) throws DaoAppException {
		SqlSession sqlSession = this.getSqlSession();
		try {
			return sqlSession.selectList(this.baseNamespace + "BackUser.queryUserInfoForPage", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		} finally {
		}
	}

	/**
	 * 按版本加载用户信息，查询总数
	 */
	private int queryDataForTotal(LoadUserInfoByEditionDto entity) throws DaoAppException {
		SqlSession sqlSession = this.getSqlSession();
		try {
			return sqlSession.selectOne(this.baseNamespace + "BackUser.queryUserInfoForCount", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		} finally {
		}
	}

	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof LoadUserInfoByEditionDto) {
			CountDto count = new CountDto();
			count.setCount(queryDataForTotal((LoadUserInfoByEditionDto) entity));
			return count;
		}
		if (entity instanceof LoadUserInfoByType) {
			IdEntity<Integer> entitys = new IdEntity<Integer>();
			entitys.setId(this.userInfocounts((LoadUserInfoByType) entity));
			return entitys;
		}
		// 查询用户详情
		else if (entity instanceof GetUserDetailQCondition) {
			return this.getUserDetail((GetUserDetailQCondition) entity);
		}
		return null;
	}

	private MapEntity getUserDetail(GetUserDetailQCondition condition) throws DaoAppException {

		logger.info("查询用户详情：" + condition);

		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Map<String, Object> data = sqlSession.selectOne(this.baseNamespace + "FrontuserSql.getUserDetail",
					condition);
			MapEntity result = new MapEntity();
			logger.info("查询结果：" + data);
			result.setData(data);
			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	/**
	 * 后台用户查询用户列表
	 * 
	 * @param request
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> loadUserInfo(LoadUserInfoByType request) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList(this.baseNamespace + "FrontuserSql.queryuserinfo", request);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	/**
	 * 更新用户状态
	 * 
	 * @param request
	 * @return
	 * @throws DaoAppException
	 */
	private int updateStatus(BackuserManageFromtUserInfoEntity request) throws DaoAppException {

		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update(this.baseNamespace + "FrontuserSql.updateStatus", request);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
			// if (sqlSession != null)
			// sqlSession.close();
		}

	}

	/**
	 * 查询用户数量
	 * 
	 * @param request
	 * @return
	 * @throws DaoAppException
	 */
	private int userInfocounts(LoadUserInfoByType request) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(this.baseNamespace + "FrontuserSql.queryuserinfocount", request);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
}
