package cn.ilanhai.kem.dao.member;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.member.QueryCondition.GetMemberInfoByMemberIdQCondition;
import cn.ilanhai.kem.domain.member.QueryCondition.GetMemberInfoByUserIdQCondition;
import cn.ilanhai.kem.domain.member.QueryCondition.MemberStatusQCondition;
import cn.ilanhai.kem.domain.member.QueryCondition.QueryNewExpiredMemberQCondition;
import cn.ilanhai.kem.domain.member.dto.MemberStatusDto;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderInfoEntity;

/**
 * 会员模块的支撑Dao
 * 
 * @author Nature
 *
 */
@Component("memberDao")
public class MemberDao extends MybatisBaseDao {

	private Logger logger = Logger.getLogger(MemberDao.class);

	public MemberDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		// 如果ID不存在，则新增，如果ID已存在则更新
		if (entity instanceof MemberEntity) {
			MemberEntity memberInfo = (MemberEntity) entity;
			if (this.memberIsExists(memberInfo.getMemberId())) {
				return this.update(memberInfo);
			} else {
				// 保存会员信息
				return this.insert(memberInfo);
			}
		}
		return super.save(entity);
	}

	/**
	 * 插入会员信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int insert(MemberEntity entity) throws DaoAppException {
		logger.info("会员信息：" + entity);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			// 保存订单
			int result = sqlSession.insert("member.insertMemberEntity", entity);

			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 更新会员信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int update(MemberEntity entity) throws DaoAppException {
		logger.info("会员信息：" + entity);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			// 保存订单
			int result = sqlSession.insert("member.updateMemberEntity", entity);

			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 查询单个实体 根据入参类型进行不同的查询
	 */
	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		logger.info("进入MemberDao.query方法");
		if (entity instanceof MemberStatusQCondition) {
			// 查询会员服务状态
			return this.queryOne((MemberStatusQCondition) entity);
		} else if (entity instanceof GetMemberInfoByUserIdQCondition) {
			// 根据用户ID查询会员服务信息
			return this.queryOne((GetMemberInfoByUserIdQCondition) entity);
		} else if (entity instanceof GetMemberInfoByMemberIdQCondition) {
			// 根据会员ID获取会员信息
			return this.queryOne((GetMemberInfoByMemberIdQCondition) entity);
		}
		return null;
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		logger.info("进入MemberDao.queryList方法");
		if (entity instanceof QueryNewExpiredMemberQCondition) {
			// 查询新过期会员
			return this.queryNewExpiredMemberQCondition((QueryNewExpiredMemberQCondition) entity).iterator();
		} else if (entity == null) {
			return this.queryOneMonthCondition().iterator();
		}
		return super.query(entity);
	}

	/**
	 * 根据会员ID判断会员是否存在
	 * 
	 * @param memberId
	 * @return
	 * @throws DaoAppException
	 */
	private boolean memberIsExists(String memberId) throws DaoAppException {
		GetMemberInfoByMemberIdQCondition condition = new GetMemberInfoByMemberIdQCondition();
		condition.setMemberId(memberId);
		MemberEntity entity = (MemberEntity) this.queryOne(condition);
		if (entity != null)
			return true;
		else
			return false;
	}

	/**
	 * 根据会员ID获取会员信息
	 * 
	 * @throws DaoAppException
	 */
	private Entity queryOne(GetMemberInfoByMemberIdQCondition condition) throws DaoAppException {
		logger.info("进入会员状态查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			MemberEntity result = sqlSession.selectOne("member.getMemberInfoByMemberId", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error("e.getMessage()");
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 进入会员状态查询方法
	 * 
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryOne(MemberStatusQCondition condition) throws DaoAppException {
		logger.info("进入会员状态查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			MemberStatusDto result = sqlSession.selectOne("member.queryMemberStatus", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 进入会员信息查询方法
	 * 
	 * @return
	 * @throws DaoAppException
	 */
	private Entity queryOne(GetMemberInfoByUserIdQCondition condition) throws DaoAppException {
		logger.info("进入会员信息查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			MemberEntity result = sqlSession.selectOne("member.getMemberInfoByUserId", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 查询新过期的会员
	 * 
	 * @param condition
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> queryNewExpiredMemberQCondition(QueryNewExpiredMemberQCondition condition)
			throws DaoAppException {
		logger.info("进入会员信息查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			List<Entity> result = sqlSession.selectList("member.queryNewExpiredMemberQCondition", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 查询新过期的会员
	 * 
	 * @param condition
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> queryOneMonthCondition() throws DaoAppException {
		logger.info("进入该月内会员信息查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			List<Entity> result = sqlSession.selectList("member.queryOneMonthCondition");
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}
}
