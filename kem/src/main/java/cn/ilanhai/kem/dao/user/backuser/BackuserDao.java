package cn.ilanhai.kem.dao.user.backuser;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.MapEntity;
import cn.ilanhai.kem.domain.user.backuser.QueryByUserNameNoConditionEntity;
import cn.ilanhai.kem.domain.user.manageuser.QueryCondition.GetUserDetailQCondition;

@Component("backuserDao")
public class BackuserDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public BackuserDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		int result = this.insertUserMain(entity);
		this.insertUserVoucher(entity);
		this.insertUserIdentity(entity);
		this.insertUserInfo(entity);
		return result;

	}

	private int insertUserMain(Entity entity) throws DaoAppException {
		try {
			return sqlSession.insert(this.baseNamespace + "BackUser.insertUserMain", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	private int insertUserInfo(Entity entity) throws DaoAppException {
		try {
			return sqlSession.insert(this.baseNamespace + "BackUser.insertUserInfo", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	private int insertUserIdentity(Entity entity) throws DaoAppException {
		try {
			return sqlSession.insert(this.baseNamespace + "BackUser.insertUserIdentity", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	private int insertUserVoucher(Entity entity) throws DaoAppException {
		try {
			return sqlSession.insert(this.baseNamespace + "BackUser.insertUserVoucher", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof QueryByUserNameNoConditionEntity) {
			return queryData((QueryByUserNameNoConditionEntity) entity);
		} else if (entity instanceof IdEntity) {
			return queryData((IdEntity<String>) entity);
		}
		// 查询用户详情
		else if (entity instanceof GetUserDetailQCondition) {
			return this.getUserDetail((GetUserDetailQCondition) entity);
		}
		return null;
	}

	private Entity getUserDetail(GetUserDetailQCondition entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Map<String, Object> data = sqlSession.selectOne(this.baseNamespace + "BackUser.queryUserDetailByUserId",
					entity);
			MapEntity result = new MapEntity();
			logger.info("查询结果：" + data);
			result.setData(data);
			return result;
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	/**
	 * 根据UserId查询用户信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryData(IdEntity<String> entity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace + "BackUser.queryDataByUserId", entity);
	}

	/**
	 * 根据用户名查询数据
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryData(QueryByUserNameNoConditionEntity entity) throws DaoAppException {
		return sqlSession.selectOne(this.baseNamespace + "BackUser.queryDataByUserName", entity);
	}
}
