package cn.ilanhai.kem.bl.material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.material.MaterialDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.MaterialInfoType;
import cn.ilanhai.kem.domain.material.MaterialEntity;
import cn.ilanhai.kem.domain.material.MaterialImgEntity;
import cn.ilanhai.kem.domain.material.MaterialInfoEntity;
import cn.ilanhai.kem.domain.material.MaterialMainEntity;
import cn.ilanhai.kem.domain.material.dto.DeleteMeterialDto;
import cn.ilanhai.kem.domain.material.dto.QueryMaterialInfoDto;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.StringVerifyUtil;

public class MaterialManager {
	private static Class<?> currentclass = MaterialDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	/**
	 * 添加/更新分类管理
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static MaterialEntity saveMaterialType(RequestContext requestContext, MaterialEntity materialEntity)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (materialEntity != null) {
				dao = getDao(requestContext);
				int val = dao.save(materialEntity);
				BLContextUtil.valiSaveDomain(val, "分类管理");
				return materialEntity;
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return materialEntity;
	}

	/**
	 * 添加/更新分类管理
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static MaterialEntity queryMaterialType(RequestContext requestContext, String string)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (string != null) {
				dao = getDao(requestContext);
				IdEntity<String> IdEntity = new IdEntity<>();
				IdEntity.setId(string);
				return (MaterialEntity) dao.query(IdEntity, false);
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return null;
	}

	/**
	 * 添加分类管理
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void saveMaterialImg(RequestContext requestContext, MaterialImgEntity materialImgEntity)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (materialImgEntity != null) {
				dao = getDao(requestContext);
				int val = dao.save(materialImgEntity);
				BLContextUtil.valiSaveDomain(val, "图片管理保存");
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static String createMaterialMain(RequestContext requestContext)
			throws BlAppException, SessionContainerException {
		String materialId = KeyFactory.newKey(KeyFactory.KEY_SVG);
		Dao dao;
		try {
			dao = getDao(requestContext);
			String userid = BLContextUtil.getSessionUserId(requestContext);

			MaterialMainEntity mainEntity = new MaterialMainEntity();
			mainEntity.setCreatetime(new Date());
			mainEntity.setEnable(EnableState.unenable.getValue());
			mainEntity.setMaterialId(materialId);
			mainEntity.setUserId(userid);
			mainEntity.setUpdatetime(null);
			int val = dao.save(mainEntity);
			BLContextUtil.valiSaveDomain(val, "SVG素材创建");
			// 保存类型为2 SVG
			saveMaterialInfo(requestContext, MaterialInfoType.materialtype, "2", materialId);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return materialId;
	}

	public static void saveMaterialMain_state(RequestContext requestContext, EnableState state, String id)
			throws BlAppException, SessionContainerException {
		Dao dao;
		try {
			MaterialMainEntity mainEntity = new MaterialMainEntity();
			mainEntity.setEnable(state.getValue());
			mainEntity.setMaterialId(id);
			mainEntity.setUpdatetime(new Date());
			dao = getDao(requestContext);
			int val = dao.save(mainEntity);
			BLContextUtil.valiSaveDomain(val, "SVG素材保存");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 根据id获取素材
	 * 
	 * @param requestContext
	 * @param id
	 * @return
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public static MaterialMainEntity getMaterialMain(RequestContext requestContext, String id)
			throws BlAppException, SessionContainerException {
		Dao dao;
		try {
			MaterialMainEntity mainEntity = new MaterialMainEntity();
			mainEntity.setMaterialId(id);
			dao = getDao(requestContext);
			return (MaterialMainEntity) dao.query(mainEntity, false);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 保存素材信息
	 * 
	 * @param requestContext
	 * @param infoType
	 * @param info
	 * @param materialId
	 * @throws BlAppException
	 */
	public static void saveMaterialInfo(RequestContext requestContext, MaterialInfoType infoType, String info,
			String materialId) throws BlAppException {
		Dao dao;
		if (Str.isNullOrEmpty(materialId)) {
			return;
		}
		try {
			if (info != null) {
				dao = getDao(requestContext);
				QueryMaterialInfoDto queryMaterialInfoDto = new QueryMaterialInfoDto();
				queryMaterialInfoDto.setInfoKey(infoType.getValue());
				queryMaterialInfoDto.setMaterialId(materialId);
				MaterialInfoEntity infoEntity = new MaterialInfoEntity();
				infoEntity.setInfoKey(infoType.getValue());
				infoEntity.setInfoValue(info);
				infoEntity.setMaterialId(materialId);
				infoEntity.setEnable(EnableState.enable.getValue());
				int val = dao.save(infoEntity);
				BLContextUtil.valiSaveDomain(val, "素材信息");
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 保存素材信息 关键词
	 * 
	 * @param requestContext
	 * @param infoType
	 * @param info
	 * @param materialId
	 * @throws BlAppException
	 */
	public static void saveMaterialInfo_keyword(RequestContext requestContext, String info, String materialId)
			throws BlAppException {
		Dao dao;
		List<String> arr = null;
		try {
			if (info != null) {
				dao = getDao(requestContext);
				String srts[] = info.split(",");
				MaterialInfoEntity infoEntity = getMaterialInfoById(requestContext, MaterialInfoType.materialkeyword,
						materialId);
				if (infoEntity != null) {
					String keywords[] = infoEntity.getInfoValue().split(",");
					arr = new ArrayList<String>(Arrays.asList(keywords));
					for (String string : srts) {
						if (!BLContextUtil.useList(arr, string)) {
							arr.add(string);
						}
					}
					infoEntity.setInfoValue(StringVerifyUtil.arrayToString(arr));
				} else {
					infoEntity = new MaterialInfoEntity();
					infoEntity.setInfoKey(MaterialInfoType.materialkeyword.getValue());
					infoEntity.setInfoValue(info);
					infoEntity.setMaterialId(materialId);
					infoEntity.setEnable(EnableState.enable.getValue());
				}
				infoEntity.setInfoValue(infoEntity.getInfoValue().replaceAll(" ", ""));
				int val = dao.save(infoEntity);
				BLContextUtil.valiSaveDomain(val, "素材信息_keyword");

			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 获取素材信息
	 * 
	 * @param requestContext
	 * @param infoType
	 * @param materialId
	 * @return
	 * @throws BlAppException
	 */
	public static MaterialInfoEntity getMaterialInfoById(RequestContext requestContext, MaterialInfoType infoType,
			String materialId) throws BlAppException {
		Dao dao;
		try {
			BLContextUtil.valiParaItemStrNullOrEmpty(materialId, "素材编号错误", false);
			dao = getDao(requestContext);
			QueryMaterialInfoDto queryMaterialInfoDto = new QueryMaterialInfoDto();
			queryMaterialInfoDto.setInfoKey(infoType.getValue());
			queryMaterialInfoDto.setMaterialId(materialId);
			return (MaterialInfoEntity) dao.query(queryMaterialInfoDto, false);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 获取素材信息
	 * 
	 * @param requestContext
	 * @param infoType
	 * @param materialId
	 * @return
	 * @throws BlAppException
	 */
	public static Iterator<Entity> getMaterialInfosById(RequestContext requestContext, MaterialInfoType infoType,
			String materialId) throws BlAppException {
		Dao dao;
		try {
			BLContextUtil.valiParaItemStrNullOrEmpty(materialId, "素材编号错误", false);
			dao = getDao(requestContext);
			QueryMaterialInfoDto queryMaterialInfoDto = new QueryMaterialInfoDto();
			queryMaterialInfoDto.setInfoKey(infoType.getValue());
			queryMaterialInfoDto.setMaterialId(materialId);
			return dao.query(queryMaterialInfoDto);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 添加分类管理
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void deleteMaterialImg(RequestContext requestContext, String id)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (!Str.isNullOrEmpty(id)) {
				dao = getDao(requestContext);
				IdEntity<String> idEntity = new IdEntity<String>();
				idEntity.setId(id);
				int val = dao.delete(idEntity);
				BLContextUtil.valiSaveDomain(val, "图片管理删除");
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private static Dao getDao(RequestContext requestContext) throws DaoAppException, BlAppException {
		CodeTable ct;
		Dao dao = daoFactory.getDao(requestContext, currentclass);
		if (dao == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "分类管理");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}

	public static String removalDuplicateKey(String key) {

		String[] keys = key.split(",");
		List<String> newKeys = new ArrayList<String>();
		for (String string : keys) {
			if (!BLContextUtil.useList(newKeys, string)) {
				newKeys.add(string);
			}
		}
		String newKey = StringVerifyUtil.arrayToString(newKeys);
		return newKey.replaceAll(" ", "");
	}

	/**
	 * 添加/更新分类管理
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static int deleteMaterial(RequestContext requestContext, DeleteMeterialDto entity)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (entity != null) {
				dao = getDao(requestContext);
				int val = dao.delete(entity);
				BLContextUtil.valiSaveDomain(val, "删除素材");
				return val;
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return -1;
	}

}
