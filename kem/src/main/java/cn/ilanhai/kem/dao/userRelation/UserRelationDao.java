package cn.ilanhai.kem.dao.userRelation;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.domain.userRelation.dto.DeleteUserRelationDto;
import cn.ilanhai.kem.domain.userRelation.dto.SearchUserRelationDto;
import cn.ilanhai.kem.util.LockUtil;

/**
 * 账户关系dao
 * 
 * @author hy
 *
 */
@Component("userRelationDao")
public class UserRelationDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public UserRelationDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof UserRelationEntity) {
			return saveUserRelation((UserRelationEntity) entity);
		}
		return super.save(entity);
	}

	@Override
	public List<Entity> queryList(Entity entity) throws DaoAppException {
		if (entity instanceof SearchUserRelationDto) {
			return searchUserRelation((SearchUserRelationDto) entity);
		}
		return super.queryList(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchUserRelationDto) {
			return searchOneUserRelation((SearchUserRelationDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteUserRelationDto) {
			return deleteUserRelation((DeleteUserRelationDto) entity);
		}
		return super.delete(entity);
	}

	private int deleteUserRelation(DeleteUserRelationDto dto) {
		if (dto == null || Str.isNullOrEmpty(dto.getUserId()) || dto.getIds() == null || dto.getIds().size() <= 0) {
			return 0;
		} else {
			sqlSession.delete(baseNamespace + "userRelation.deleteUserVoucher", dto);
			sqlSession.delete(baseNamespace + "userRelation.deleteUserInfo", dto);
			sqlSession.delete(baseNamespace + "userRelation.deleteUserIdentify", dto);
			sqlSession.delete(baseNamespace + "userRelation.deleteUserMain", dto);
			return sqlSession.delete(baseNamespace + "userRelation.deleteUserRelation", dto);
		}
	}

	private int saveUserRelation(UserRelationEntity entity) {
		SearchUserRelationDto dto = new SearchUserRelationDto();
		dto.setUserId(entity.getUserId());
		synchronized (LockUtil.getUserLock(entity.getUserId())) {
			if (searchOneUserRelation(dto) == null) {
				return sqlSession.insert(baseNamespace + "userRelation.insertUserRelation", entity);
			} else {
				return sqlSession.update(baseNamespace + "userRelation.updateUserRelation", entity);
			}
		}
	}

	private List<Entity> searchUserRelation(SearchUserRelationDto entity) {
		if (entity == null) {
			return null;
		}
		entity.setPageSize(300);
		return sqlSession.selectList(baseNamespace + "userRelation.selectUserRelation", entity);
	}

	private UserRelationEntity searchOneUserRelation(SearchUserRelationDto entity) {
		if (entity == null) {
			return null;
		}
		entity.setStartCount(0);
		entity.setPageSize(1);
		return sqlSession.selectOne(baseNamespace + "userRelation.selectUserRelation", entity);
	}
}
