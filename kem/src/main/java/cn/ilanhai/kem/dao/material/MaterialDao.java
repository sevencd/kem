package cn.ilanhai.kem.dao.material;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.MaterialInfoType;
import cn.ilanhai.kem.domain.material.MaterialEntity;
import cn.ilanhai.kem.domain.material.MaterialImgEntity;
import cn.ilanhai.kem.domain.material.MaterialInfoEntity;
import cn.ilanhai.kem.domain.material.MaterialMainEntity;
import cn.ilanhai.kem.domain.material.SearchAllMaterialEntity;
import cn.ilanhai.kem.domain.material.SearchMaterailRequest;
import cn.ilanhai.kem.domain.material.SearchMaterialEntity;
import cn.ilanhai.kem.domain.material.classification.ClassificationRequest;
import cn.ilanhai.kem.domain.material.dto.DeleteMeterialDto;
import cn.ilanhai.kem.domain.material.dto.GetMaterialDto;
import cn.ilanhai.kem.domain.material.dto.QueryMaterialInfoDto;

@Component("materialDao")
public class MaterialDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MaterialDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(MaterialDao.class);

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return deleteMaterialImg((IdEntity<String>) entity);
		}
		if (entity instanceof DeleteMeterialDto) {
			return deleteMeterial((DeleteMeterialDto) entity);
		}
		return 0;
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof MaterialEntity) {
			if (isExists((MaterialEntity) entity)) {
				return update((MaterialEntity) entity);
			} else {
				return add((MaterialEntity) entity);
			}
		}
		if (entity instanceof MaterialImgEntity) {
			return add((MaterialImgEntity) entity);
		}

		if (entity instanceof MaterialInfoEntity) {
			return saveMaterialInfo((MaterialInfoEntity) entity);
		}

		if (entity instanceof MaterialMainEntity) {
			return saveMaterialMain((MaterialMainEntity) entity);
		}
		if (entity instanceof ClassificationRequest) {
			return saveClassifction((ClassificationRequest) entity);
		}
		return super.save(entity);
	}

	private int saveClassifction(ClassificationRequest entity) throws DaoAppException {
		logger.info("开始设置分类");
		this.isLegitimate(entity);
		this.deleteClassifcition(entity);
		if (entity.getCategoryIds().length > 0) {
			this.saveClassifcition(entity);
		}
		return 1;
	}

	/**
	 * 删除素材
	 * 
	 * @param dto
	 * @return
	 */
	private int deleteMeterial(DeleteMeterialDto dto) {
		if (dto == null) {
			return -1;
		}
		return sqlSession.delete("Meterial.deleteMeterial", dto.getIds());
	}

	private boolean isLegitimate(ClassificationRequest entity) throws DaoAppException {
		logger.info("验证素材分类id是否合法");
		SqlSession sqlSession = null;
		String materialId = null;
		String classifcitionId = null;
		try {
			sqlSession = this.getSqlSession();
			for (int i = 0; i < entity.getCategoryIds().length; i++) {
				if (entity.getCategoryIds()[i].length() <= 0) {
					throw new DaoAppException(-1, "请选择分类");
				}
				classifcitionId = sqlSession.selectOne("Meterial.selectclassifcitionisLegitimate",
						entity.getCategoryIds()[i]);
				if (classifcitionId == null) {
					throw new DaoAppException(-1, "素材分类Id=" + entity.getCategoryIds()[i] + "不存在");
				}
			}
			// 素材验证---后续添加
			// for (int i = 0; i < entity.getMaterialIds().length; i++) {
			// materialId =
			// sqlSession.selectOne("Meterial.selectmaterialidisLegitimate",
			// entity.getMaterialIds()[i]);
			// if (materialId == null) {
			// throw new DaoAppException(-1, "素材Id=" +
			// entity.getMaterialIds()[i] + "不存在");
			// }
			// }
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int saveClassifcition(ClassificationRequest entity) throws DaoAppException {
		logger.info("保存素材分类");
		SqlSession sqlSession = null;
		int val = 0;
		try {
			sqlSession = this.getSqlSession();
			for (int i = 0; i < entity.getMaterialIds().length; i++) {
				entity.setMaterialId(entity.getMaterialIds()[i]);
				val = sqlSession.insert("Meterial.insertclassifcition", entity);
				if (val <= 0) {
					throw new DaoAppException(-1, "保存素材" + entity.getMaterialId() + "失败");
				}
			}
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int deleteClassifcition(ClassificationRequest entity) throws DaoAppException {
		logger.info("删除素材分类");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.delete("Meterial.deleteClassifcition", entity.getMaterialIds());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int saveMaterialInfo(MaterialInfoEntity infoEntity) {
		if (infoEntity == null) {
			return -1;
		}
		if (!isExist(infoEntity)) {
			return updateMaterialInfo(infoEntity);
		}
		return addMaterialInfo(infoEntity);
	}

	private int saveMaterialMain(MaterialMainEntity mainEntity) {
		if (mainEntity == null) {
			return -1;
		}
		if (getMaterialMainEntity(mainEntity.getMaterialId()) != null) {
			return updateMaterialMain(mainEntity);
		}
		return addMaterialMain(mainEntity);
	}

	private MaterialMainEntity getMaterialMainEntity(String materialId) {
		if (Str.isNullOrEmpty(materialId)) {
			return null;
		}
		return sqlSession.selectOne("Meterial.queryMeterialMain", materialId);
	}

	private int addMaterialMain(MaterialMainEntity mainEntity) {
		if (mainEntity == null)
			return -1;
		return sqlSession.insert("Meterial.saveMeterialMain", mainEntity);
	}

	private int updateMaterialMain(MaterialMainEntity mainEntity) {
		if (mainEntity == null)
			return -1;
		return sqlSession.insert("Meterial.updateMeterialMain", mainEntity);
	}

	/**
	 * 验证是否存在该info
	 * 
	 * @param infoEntity
	 * @return
	 */
	private boolean isExist(MaterialInfoEntity infoEntity) {
		if (infoEntity == null)
			return false;
		QueryMaterialInfoDto dto = new QueryMaterialInfoDto();
		if (MaterialInfoType.manuscriptid.getValue().equals(infoEntity.getInfoKey())) {
			logger.info("素材Id:" + infoEntity.getMaterialId() + " 保存稿件id:" + infoEntity.getInfoValue());
			dto.setInfoValue(infoEntity.getInfoValue());
		}
		dto.setInfoKey(infoEntity.getInfoKey());
		dto.setMaterialId(infoEntity.getMaterialId());
		MaterialInfoEntity MaterialInfoEntity = queryMaterialInfo(dto);
		if (MaterialInfoEntity == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询素材信息
	 * 
	 * @param dto
	 * @return
	 */
	private MaterialInfoEntity queryMaterialInfo(QueryMaterialInfoDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectOne("Meterial.queryOneMeterialInfo", dto);
	}

	/**
	 * 查询素材信息
	 * 
	 * @param dto
	 * @return
	 */
	private List<Entity> queryMaterialInfos(QueryMaterialInfoDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("Meterial.queryMeterialInfos", dto);
	}

	/**
	 * 添加info
	 * 
	 * @param infoEntity
	 * @return
	 */
	private int addMaterialInfo(MaterialInfoEntity infoEntity) {
		if (infoEntity == null)
			return -1;
		return sqlSession.insert("Meterial.saveMeterialInfo", infoEntity);
	}

	/**
	 * 更新info
	 * 
	 * @param infoEntity
	 * @return
	 */
	private int updateMaterialInfo(MaterialInfoEntity infoEntity) {
		if (infoEntity == null)
			return -1;
		return sqlSession.update("Meterial.updateMeterialInfo", infoEntity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof SearchMaterialEntity) {
			return queryMaterial((SearchMaterialEntity) entity).iterator();
		} else if (entity instanceof SearchMaterailRequest) {
			return queryMaterial((SearchMaterailRequest) entity).iterator();
		}
		if (entity instanceof QueryMaterialInfoDto) {
			return queryMaterialInfos((QueryMaterialInfoDto) entity).listIterator();
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchMaterialEntity) {
			return queryMaterialCount((SearchMaterialEntity) entity);
		}
		if (entity instanceof IdEntity) {
			return queryMaterialType((IdEntity<String>) entity);
		}
		if (entity instanceof QueryMaterialInfoDto) {
			return queryMaterialInfo((QueryMaterialInfoDto) entity);
		}
		if (entity instanceof GetMaterialDto) {
			return getMaterial((GetMaterialDto) entity);
		}
		if (entity instanceof MaterialMainEntity) {
			MaterialMainEntity mainEntity = (MaterialMainEntity) entity;
			return getMaterialMainEntity(mainEntity.getMaterialId());
		}

		// TODO Auto-generated method stub
		return super.query(entity, flag);
	}

	private Entity getMaterial(GetMaterialDto getMaterialDto) {
		if (getMaterialDto == null) {
			return null;
		}
		return sqlSession.selectOne("Meterial.getmaterial", getMaterialDto);
	}

	private int add(MaterialImgEntity entity) throws DaoAppException {
		logger.info("新加图片素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("Meterial.insertmeterialimg", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int add(MaterialEntity entity) throws DaoAppException {
		logger.info("新加素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("Meterial.insertmeterial", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private int update(MaterialEntity entity) throws DaoAppException {
		logger.info("更新素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("Meterial.updatemeterial", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean isExists(MaterialEntity entity) throws DaoAppException {
		logger.info("查询素材是否存在");
		if (entity.getMaterial_id() == null) {
			if (!Str.isNullOrEmpty(entity.getMaterial_name()) && entity.getTerminal_type() != null
					&& entity.getUserId() != null) {
			} else {
				return false;
			}
		}
		SqlSession sqlSession = null;
		String meterialId = null;
		try {
			sqlSession = this.getSqlSession();
			meterialId = sqlSession.selectOne("Meterial.searchidfrommeterial", entity);
			if (meterialId == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	// 删除素材
	private int deleteMaterialImg(IdEntity<String> entity) throws DaoAppException {
		logger.info("删除素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.delete("Meterial.deleimgbyid", entity.getId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private List<Entity> queryMaterial(SearchMaterialEntity entity) throws DaoAppException {
		logger.info("查询素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("Meterial.searchmeterial", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private List<Entity> queryMaterial(SearchMaterailRequest entity) throws DaoAppException {
		logger.info("查询所有素材");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			CountDto count;
			if (Str.isNullOrEmpty(entity.getTemplateId())) {
				count = sqlSession.selectOne("Meterial.searchallmaterialcount", entity);
			} else {
				count = sqlSession.selectOne("Meterial.searchmanuscriptmaterialcount", entity);
			}
			entity.setCount(count.getCount());// searchTemplateDto.getServiceName()
			List<Entity> results;
			if (Str.isNullOrEmpty(entity.getTemplateId())) {
				results = sqlSession.selectList("Meterial.searchallmaterial", entity);
			} else {
				results = sqlSession.selectList("Meterial.searchmanuscriptmaterial", entity);
			}
			for (int i = 0; i < results.size(); i++) {
				SearchAllMaterialEntity result = (SearchAllMaterialEntity) results.get(i);
				if (result.getThumbnail() != null && result.getThumbnail().length() > 0
						&& !result.getThumbnail().contains("://")) {
					result.setThumbnail(entity.getServiceName() + result.getThumbnail());
				}
				if (result.getMaterialTemplate() != null && result.getMaterialTemplate().length() > 0
						&& !result.getMaterialTemplate().contains("://")) {
					result.setMaterialTemplate(entity.getServiceName() + result.getMaterialTemplate());
				}
				if (result.getPrepareThumbnail() != null && result.getPrepareThumbnail().length() > 0
						&& !result.getPrepareThumbnail().contains("://")) {
					result.setPrepareThumbnail(entity.getServiceName() + result.getPrepareThumbnail());
				}
			}
			return results;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryMaterialCount(SearchMaterialEntity entity) throws DaoAppException {
		logger.info("查询分类管理类型总条数");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Meterial.searchmeteralcount", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private Entity queryMaterialType(IdEntity<String> entity) throws DaoAppException {
		logger.info("查询分类管理类型");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Meterial.searchmeteralType", entity.getId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
}
