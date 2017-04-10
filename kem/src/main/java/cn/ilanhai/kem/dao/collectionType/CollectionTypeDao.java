package cn.ilanhai.kem.dao.collectionType;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.collectionType.CollectionTypeEntity;
import cn.ilanhai.kem.domain.collectionType.dto.LoadAllCollectionTypeDto;
import cn.ilanhai.kem.domain.collectionType.dto.ResultAllCollectionTypeDto;

/**
 * 采集类型dao
 * 
 * @author hy
 *
 */
@Component("collectiontypeDao")
public class CollectionTypeDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public CollectionTypeDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return super.save(entity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null) {
			return searchCollectionType();
		} else if (entity instanceof LoadAllCollectionTypeDto) {
			return searchAllCollectionType((LoadAllCollectionTypeDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		return super.delete(entity);
	}

	private List<CollectionTypeEntity> searchCollectionType(String typeNum) {
		List<CollectionTypeEntity> list = sqlSession
				.selectList(baseNamespace + "collectionType.queryCollectionTypeByParentNum", typeNum);
		for (CollectionTypeEntity collectionTypeEntity : list) {
			collectionTypeEntity.setChildrenType(searchCollectionType(collectionTypeEntity.getTypeNum()));
		}
		return list;
	}

	private Entity searchCollectionType() {
		ResultAllCollectionTypeDto result = new ResultAllCollectionTypeDto();
		List<Entity> list = sqlSession.selectList(baseNamespace + "collectionType.queryBascCollectionType");
		for (Entity entity : list) {
			if (entity instanceof CollectionTypeEntity) {
				CollectionTypeEntity collectionTypeEntity = (CollectionTypeEntity) entity;
				collectionTypeEntity.setChildrenType(searchCollectionType(collectionTypeEntity.getTypeNum()));
			}
		}
		result.setTypes(list);
		return result;
	}

	private Entity searchAllCollectionType(LoadAllCollectionTypeDto dto) {
		ResultAllCollectionTypeDto result = new ResultAllCollectionTypeDto();
		result.setPageSize(dto.getPageSize());
		result.setStartCount(dto.getStartCount());
		result.setTotalCount(
				(Integer) sqlSession.selectOne(baseNamespace + "collectionType.queryAllCollectionTypeCount", dto));
		List<Entity> list = sqlSession.selectList(baseNamespace + "collectionType.queryAllCollectionType", dto);
		result.setTypes(list);
		return result;
	}
	@Override
	public List<Entity> queryList(Entity entity) throws DaoAppException {
		//LoadAllCollectionTypeDto dto=(LoadAllCollectionTypeDto)entity;
		 //加载所有二级行业
		List<Entity> list = sqlSession.selectList(baseNamespace + "collectionType.queryAllChildCollectionType");
		return list;
	}
}
