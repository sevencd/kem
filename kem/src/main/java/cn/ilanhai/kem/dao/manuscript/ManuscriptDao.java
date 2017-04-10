package cn.ilanhai.kem.dao.manuscript;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptCollectionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptContentEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptSettingEntity;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptContentDto;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptMainDto;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptParameterDto;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptSettingDto;
import cn.ilanhai.kem.domain.manuscript.dto.SaveManuscriptParamsDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchBackManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchFrontManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptCollectionDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptResultDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchTagsDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchTagsResultDto;

@Component("manuscriptDao")
public class ManuscriptDao extends MybatisBaseDao {
	SqlSession sqlSession = this.getSqlSession();

	public ManuscriptDao() throws DaoAppException {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null) {
			return null;
		} else if (entity instanceof SearchManuscriptDto) {
			return getManuscriptByTag((SearchManuscriptDto) entity).iterator();
		} else if (entity instanceof SearchManuscriptCollectionDto) {
			return getCollectionManuscript((SearchManuscriptCollectionDto) entity).iterator();
		} else if (entity instanceof GetManuscriptParameterDto) {
			return getManuscriptParameterById(((GetManuscriptParameterDto) entity).getId()).iterator();
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null) {
			return null;
		} else if (entity instanceof SearchTagsDto) {
			return new SearchTagsResultDto(getParamerByManuscriptType((SearchTagsDto) entity));
		} else if (entity instanceof IdEntity) {
			return getManuscriptById(((IdEntity<String>) entity).getId());
		} else if (entity instanceof SearchManuscriptDto) {
			return getManuscriptByTagCount((SearchManuscriptDto) entity);
		} else if (entity instanceof SearchManuscriptCollectionDto) {
			return getCollectionManuscriptCount((SearchManuscriptCollectionDto) entity);
		} else if (entity instanceof GetManuscriptContentDto) {
			return getContent(((GetManuscriptContentDto) entity).getId());
		} else if (entity instanceof GetManuscriptSettingDto) {
			return getSettingById(((GetManuscriptSettingDto) entity).getId());
		} else if (entity instanceof GetManuscriptParameterDto) {
			return getManuscriptParameter((GetManuscriptParameterDto) entity);
		} else if (entity instanceof GetManuscriptMainDto) {
			return getManuscriptMainById(((GetManuscriptParameterDto) entity).getId());
		} else if (entity instanceof SearchFrontManuscriptDto) {
			return searchFrontManuscript((SearchFrontManuscriptDto) entity);
		} else if (entity instanceof SearchBackManuscriptDto) {
			return searchBackManuscript((SearchBackManuscriptDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof ManuscriptEntity) {
			ManuscriptEntity manuscriptEntity = (ManuscriptEntity) entity;
			if (!Str.isNullOrEmpty(manuscriptEntity.getManuscriptId())) {
				if (isExistManuscript(manuscriptEntity.getManuscriptId())) {
					return updateManuscript(manuscriptEntity);
				} else {
					return saveManuscript(manuscriptEntity);
				}
			}
		} else if (entity instanceof ManuscriptParameterEntity) {
			ManuscriptParameterEntity manuscriptParameterEntity = (ManuscriptParameterEntity) entity;
			if (!Str.isNullOrEmpty(manuscriptParameterEntity.getManuscriptId())) {
				SqlSession sqlSession = this.getSqlSession();
				if (isExistParamer(manuscriptParameterEntity)) {
					sqlSession.insert("manuscript.updateParameter", manuscriptParameterEntity);
				} else {
					sqlSession.insert("manuscript.saveParameter", manuscriptParameterEntity);
				}
			}
		} else if (entity instanceof ManuscriptContentEntity) {
			ManuscriptContentEntity manuscriptContentEntity = (ManuscriptContentEntity) entity;
			if (!Str.isNullOrEmpty(manuscriptContentEntity.getManuscriptId())) {
				SqlSession sqlSession = this.getSqlSession();
				if (isExistContent(manuscriptContentEntity.getManuscriptId())) {
					sqlSession.insert("manuscript.updateContent", manuscriptContentEntity);
				} else {
					sqlSession.insert("manuscript.saveContent", manuscriptContentEntity);
				}
			}
		} else if (entity instanceof SaveManuscriptParamsDto) {
			SaveManuscriptParamsDto saveManuscriptTagsDto = (SaveManuscriptParamsDto) entity;
			if (!Str.isNullOrEmpty(saveManuscriptTagsDto.getManuscriptId())) {
				SqlSession sqlSession = this.getSqlSession();
				sqlSession.insert("manuscript.deleteParameter", saveManuscriptTagsDto);
				for (String param : saveManuscriptTagsDto.getParams()) {
					ManuscriptParameterEntity manuscriptParameterEntity = new ManuscriptParameterEntity();
					manuscriptParameterEntity.setManuscriptId(saveManuscriptTagsDto.getManuscriptId());
					manuscriptParameterEntity.setEnableState(EnableState.enable.getValue());
					manuscriptParameterEntity.setParameterType(saveManuscriptTagsDto.getParamType());
					manuscriptParameterEntity.setParameter(param);
					manuscriptParameterEntity.setCreateTime(new Date());
					sqlSession.insert("manuscript.saveParameter", manuscriptParameterEntity);
				}
			}
		} else if (entity instanceof ManuscriptCollectionEntity) {
			ManuscriptCollectionEntity manuscriptCollectionEntity = (ManuscriptCollectionEntity) entity;
			ManuscriptCollectionEntity manuscriptCollectionEntityNew = queryManuscriptCollectionModel(
					manuscriptCollectionEntity);
			if (manuscriptCollectionEntityNew != null) {
				manuscriptCollectionEntityNew.setCollectionState(manuscriptCollectionEntity.getCollectionState());
				return this.updateteManuscriptCollectionModel(manuscriptCollectionEntityNew);
			} else {
				return this.saveManuscriptCollectionModel(manuscriptCollectionEntity);
			}
		}

		return super.save(entity);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity != null) {
			if (entity instanceof IdEntity) {
				IdEntity<String> id = (IdEntity) entity;
				if (!Str.isNullOrEmpty(id.getId())) {
					SqlSession sqlSession = this.getSqlSession();
					sqlSession.delete("manuscript.deleteParameterById", id.getId());
					sqlSession.delete("manuscript.deleteContentById", id.getId());
					// sqlSession.delete("manuscript.deleteSettingById",
					// id.getId());
					return sqlSession.delete("manuscript.deleteManuscriptById", id.getId());
				}
			}
		}
		return super.delete(entity);
	}

	private List<Entity> getManuscriptByTag(SearchManuscriptDto searchManuscriptDto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("manuscript.queryManuscriptByTag", searchManuscriptDto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private ManuscriptCollectionEntity queryManuscriptCollectionModel(
			ManuscriptCollectionEntity manuscriptCollectionEntity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("manuscript_collection.queryCollection", manuscriptCollectionEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int updateteManuscriptCollectionModel(ManuscriptCollectionEntity manuscriptCollectionEntity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("manuscript_collection.updateCollection", manuscriptCollectionEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int saveManuscriptCollectionModel(ManuscriptCollectionEntity manuscriptCollectionEntity)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("manuscript_collection.saveCollection", manuscriptCollectionEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Entity getManuscriptByTagCount(SearchManuscriptDto searchManuscriptDto) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			CountDto countDto = new CountDto();
			countDto.setCount(
					(Integer) sqlSession.selectOne("manuscript.queryManuscriptByTagCount", searchManuscriptDto));
			return countDto;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private List<Entity> getCollectionManuscript(SearchManuscriptCollectionDto searchManuscriptDto)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("manuscript.queryCollection", searchManuscriptDto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Entity getCollectionManuscriptCount(SearchManuscriptCollectionDto searchManuscriptDto)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			CountDto countDto = new CountDto();
			countDto.setCount((Integer) sqlSession.selectOne("manuscript.queryCollectionCount", searchManuscriptDto));
			return countDto;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private List<String> getTags(SearchTagsDto searchTagsDto) throws DaoAppException {
		try {
			searchTagsDto.setParameterType(ManuscriptParameterType.tag.getValue());
			searchTagsDto.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());
			return getParamerByManuscriptType(searchTagsDto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private boolean isExistManuscript(String manuscriptId) throws DaoAppException {
		return getManuscriptById(manuscriptId) != null;
	}

	private ManuscriptEntity getManuscriptMainById(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			if (Str.isNullOrEmpty(manuscriptId)) {
				return null;
			}
			sqlSession = this.getSqlSession();
			ManuscriptEntity manuscriptEntity = sqlSession.selectOne("manuscript.queryManuscriptById", manuscriptId);
			if (manuscriptEntity == null) {
				return manuscriptEntity;
			}
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
		return null;
	}

	private ManuscriptEntity getManuscriptById(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			ManuscriptEntity manuscriptEntity = sqlSession.selectOne("manuscript.queryManuscriptById", manuscriptId);
			if (manuscriptEntity == null) {
				return manuscriptEntity;
			}
			manuscriptEntity.setManuscriptSetting(getSettingById(manuscriptId));
			manuscriptEntity.setManuscriptParameters(getParamerByManuscriptId(manuscriptId));
			manuscriptEntity.setManuscriptContent(getContent(manuscriptId));
			return manuscriptEntity;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private List<Entity> getManuscriptParameterById(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("manuscript.queryParameterByManuscriptId", manuscriptId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private ManuscriptParameterEntity getManuscriptParameter(GetManuscriptParameterDto getManuscriptParameterDto)
			throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("manuscript.getParameter", getManuscriptParameterDto);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private boolean isExistContent(String manuscriptId) throws DaoAppException {
		return getContent(manuscriptId) != null;
	}

	private ManuscriptContentEntity getContent(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("manuscript.queryContentByManuscriptId", manuscriptId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	// private boolean isExistSetting(String manuscriptId) throws
	// DaoAppException {
	// return getSettingById(manuscriptId) != null;
	// }

	private ManuscriptSettingEntity getSettingById(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("manuscript.querySettingByManuscriptId", manuscriptId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private boolean isExistParamer(ManuscriptParameterEntity manuscriptParameterEntity) throws DaoAppException {
		return getParamerByManuscriptIdAndType(manuscriptParameterEntity) != null;
	}

	/**
	 * 根据稿件id和参数类型获取参数
	 * 
	 * @param manuscriptParameterEntity
	 * @return
	 * @throws DaoAppException
	 */
	private ManuscriptParameterEntity getParamerByManuscriptIdAndType(
			ManuscriptParameterEntity manuscriptParameterEntity) throws DaoAppException {
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			Object obj = sqlSession.selectOne("manuscript.queryParameterByManuscriptIdAndType",
					manuscriptParameterEntity);
			return (ManuscriptParameterEntity) obj;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 根据稿件类型 稿件参数类型 获取稿件参数
	 * 
	 * @param searchTagsDto
	 * @return
	 * @throws DaoAppException
	 */
	private List<String> getParamerByManuscriptType(SearchTagsDto searchTagsDto) throws DaoAppException {
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			// 收藏
			if (new Integer(3).equals(searchTagsDto.getTagType())) {
				searchTagsDto.setParameterType(ManuscriptParameterType.systag.getValue());
				return sqlSession.selectList("manuscript.queryParameterByCollection", searchTagsDto);
			}
			// 上架/个人的
			else {
				if(ManuscriptParameterType.systag.getValue().equals(searchTagsDto.getParameterType())){
					searchTagsDto.setUserId(null);
				}
				return sqlSession.selectList("manuscript.queryParameterByManuscriptType", searchTagsDto);
			}
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 根据稿件编号 获取参数
	 * 
	 * @param manuscriptId
	 * @return
	 * @throws DaoAppException
	 */
	private List<ManuscriptParameterEntity> getParamerByManuscriptId(String manuscriptId) throws DaoAppException {
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("manuscript.queryParameterByManuscriptId", manuscriptId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int saveManuscript(ManuscriptEntity manuscriptEntity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			// 保存内容
			if (manuscriptEntity.getManuscriptContent() != null) {
				sqlSession.insert("manuscript.saveContent", manuscriptEntity.getManuscriptContent());
			}
			// // 保存设置
			// if (manuscriptEntity.getManuscriptSetting() != null) {
			// sqlSession.insert("manuscript.saveSetting",
			// manuscriptEntity.getManuscriptSetting());
			// }
			// 保存参数
			List<ManuscriptParameterEntity> manuscriptParameterEntitys = manuscriptEntity.getManuscriptParameters();
			if (manuscriptParameterEntitys != null && manuscriptParameterEntitys.size() > 0) {
				for (ManuscriptParameterEntity manuscriptParameterEntity : manuscriptParameterEntitys) {
					sqlSession.insert("manuscript.saveParameter", manuscriptParameterEntity);
				}
			}
			return sqlSession.insert("manuscript.saveManuscript", manuscriptEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int updateManuscript(ManuscriptEntity manuscriptEntity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			String manuscriptId = manuscriptEntity.getManuscriptId();

			// 保存内容
			if (manuscriptEntity.getManuscriptContent() != null) {
				if (isExistContent(manuscriptId)) {
					sqlSession.insert("manuscript.updateContent", manuscriptEntity.getManuscriptContent());
				} else {
					sqlSession.insert("manuscript.saveContent", manuscriptEntity.getManuscriptContent());
				}
			}
			// 保存设置
			// if (manuscriptEntity.getManuscriptSetting() != null) {
			// if (isExistSetting(manuscriptId)) {
			// sqlSession.insert("manuscript.updateSetting",
			// manuscriptEntity.getManuscriptSetting());
			// } else {
			// sqlSession.insert("manuscript.saveSetting",
			// manuscriptEntity.getManuscriptSetting());
			// }
			// }
			// 保存参数
			List<ManuscriptParameterEntity> manuscriptParameterEntitys = manuscriptEntity.getManuscriptParameters();
			if (manuscriptParameterEntitys != null && manuscriptParameterEntitys.size() > 0) {
				for (ManuscriptParameterEntity manuscriptParameterEntity : manuscriptParameterEntitys) {
					if (isExistParamer(manuscriptParameterEntity)) {
						sqlSession.insert("manuscript.updateParameter", manuscriptParameterEntity);
					} else {
						sqlSession.insert("manuscript.saveParameter", manuscriptParameterEntity);
					}
				}
			}
			return sqlSession.insert("manuscript.updateManuscript", manuscriptEntity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity searchFrontManuscript(SearchFrontManuscriptDto searchDto) {
		if (searchDto == null) {
			return null;
		}
		SearchManuscriptResultDto result = new SearchManuscriptResultDto();
		List<Entity> list = sqlSession.selectList(this.baseNamespace + "searchManuscript.queryFrontManuscript",
				searchDto);
		Integer count = sqlSession.selectOne(this.baseNamespace + "searchManuscript.queryFrontManuscriptCount",
				searchDto);
		result.setList(list.iterator());
		result.setTotalCount(count);
		result.setPageSize(searchDto.getPageSize());
		result.setStartCount(searchDto.getStartCount());
		return result;
	}

	private Entity searchBackManuscript(SearchBackManuscriptDto searchDto) {
		if (searchDto == null) {
			return null;
		}
		SearchManuscriptResultDto result = new SearchManuscriptResultDto();
		List<Entity> list = sqlSession.selectList(this.baseNamespace + "searchManuscript.queryBackManuscript",
				searchDto);
		Integer count = sqlSession.selectOne(this.baseNamespace + "searchManuscript.queryBackManuscriptCount",
				searchDto);
		result.setList(list.iterator());
		result.setTotalCount(count);
		result.setPageSize(searchDto.getPageSize());
		result.setStartCount(searchDto.getStartCount());
		return result;
	}
}
