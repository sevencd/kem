package cn.ilanhai.kem.dao.userRelation;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.member.UserResourcesEntity;
import cn.ilanhai.kem.domain.member.dto.DeleteUserResourceDto;
import cn.ilanhai.kem.domain.member.dto.DeleteUserResourcesDto;
import cn.ilanhai.kem.domain.member.dto.SearchUserResourcesDto;
import cn.ilanhai.kem.domain.user.frontuser.dto.ZdsLogDto;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.domain.userRelation.dto.DeleteUserRelationDto;
import cn.ilanhai.kem.domain.userRelation.dto.SearchUserRelationDto;

/**
 * 账户关系资源dao
 * 
 * @author hy
 *
 */
@Component("userRelationResourceDao")
public class UserRelationResourceDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public UserRelationResourceDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof UserResourcesEntity) {
			return saveUserRelation((UserResourcesEntity) entity);
		}
		if (entity instanceof ZdsLogDto) {
			sqlSession.insert(baseNamespace + "userZdslog.insertZdsLog", entity);
		}
		return super.save(entity);
	}

	@Override
	public List<Entity> queryList(Entity entity) throws DaoAppException {
		if (entity instanceof SearchUserResourcesDto) {
			return searchUserRelation((SearchUserResourcesDto) entity);
		}
		return super.queryList(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchUserResourcesDto) {
			return searchOneUserRelation((SearchUserResourcesDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteUserResourcesDto) {
			return deleteUserRelation((DeleteUserResourcesDto) entity);
		}
		return super.delete(entity);
	}

	private int deleteUserRelation(DeleteUserResourcesDto dto) {
		if (dto == null || dto.getUserIds() == null || dto.getUserIds().size() <= 0 || dto.getServiceIds() == null
				|| dto.getServiceIds().size() <= 0) {
			return 0;
		} else {
			DeleteUserResourceDto delete = new DeleteUserResourceDto();
			for (String userId : dto.getUserIds()) {
				for (Integer serviceId : dto.getServiceIds()) {
					delete.setServiceId(serviceId);
					delete.setUserId(userId);
					sqlSession.insert(baseNamespace + "userRelationResourcelog.deleteUserRelationPackageResource",
							delete);
				}
			}
			return sqlSession.delete(baseNamespace + "userRelationResource.deleteUserRelationPackageResource", dto);
		}
	}

	private int saveUserRelation(UserResourcesEntity entity) {
		SearchUserResourcesDto dto = new SearchUserResourcesDto();
		dto.setUserId(entity.getUserId());
		dto.setServiceId(entity.getServiceId());
		if (searchOneUserRelationCount(dto) <= 0) {
			sqlSession.insert(baseNamespace + "userRelationResourcelog.insertUserRelationResource", entity);
			return sqlSession.insert(baseNamespace + "userRelationResource.insertUserRelationResource", entity);
		} else {
			sqlSession.insert(baseNamespace + "userRelationResourcelog.updateUserRelationResource", entity);
			return sqlSession.update(baseNamespace + "userRelationResource.updateUserRelationResource", entity);
		}
	}

	private List<Entity> searchUserRelation(SearchUserResourcesDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList(baseNamespace + "userRelationResource.selectUserRelationResource", entity);
	}

	private Integer searchOneUserRelationCount(SearchUserResourcesDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectOne(baseNamespace + "userRelationResource.selectUserRelationResourceCount", entity);
	}

	private Entity searchOneUserRelation(SearchUserResourcesDto entity) {
		if (entity == null || entity.getServiceId() == null) {
			return null;
		}
		return sqlSession.selectOne(baseNamespace + "userRelationResource.selectUserRelationResource", entity);
	}
}
