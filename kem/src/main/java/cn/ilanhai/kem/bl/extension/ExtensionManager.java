package cn.ilanhai.kem.bl.extension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.extension.ExtensionDao;
import cn.ilanhai.kem.dao.special.SpecialDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.SearchConfigDataDto;
import cn.ilanhai.kem.domain.enums.ExtensionState;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.special.AuditLogEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.util.TimeUtil;

public class ExtensionManager {
	private static Logger logger = Logger.getLogger(ExtensionManager.class);
	private static Class<?> currentclass = ExtensionDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	/**
	 * 获取推广实体
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static ExtensionEntity getExtensionEntityById(RequestContext requestContext, String extensionId)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			if (Str.isNullOrEmpty(extensionId)) {
				return null;
			}
			dao = getDao(requestContext);
			ExtensionEntity extensionEntity;
			IdEntity<String> searchExtensionRequest = new IdEntity<String>();
			searchExtensionRequest.setId(extensionId);
			extensionEntity = (ExtensionEntity) dao.query(searchExtensionRequest, false);
			return extensionEntity;
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 获取推广实体
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void deleteExtensionEntityById(RequestContext requestContext, String extensionId)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			IdEntity<String> searchExtensionRequest = new IdEntity<String>();
			searchExtensionRequest.setId(extensionId);
			int val = dao.delete(searchExtensionRequest);
			BLContextUtil.valiDeleteDomain(val, "推广保存前的删除");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static void saveAuditLogModel(RequestContext requestContext, AuditLogEntity auditLogEntity)
			throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			int val = dao.save(auditLogEntity);
			if (val < 0) {
				CodeTable ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
				String tmp = ct.getDesc();
				tmp = String.format(tmp, "推广");
				throw new BlAppException(ct.getValue(), tmp);
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static void saveConfigModel(RequestContext requestContext, ModelConfigEntity modelConfigEntity)
			throws BlAppException {
		Dao dao;
		try {
			dao = daoFactory.getDao(requestContext, SpecialDao.class);
			dao.delete(modelConfigEntity);
			int val = dao.save(modelConfigEntity);
			if (val < 0) {
				CodeTable ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
				String tmp = ct.getDesc();
				tmp = String.format(tmp, "推广刷新id");
				throw new BlAppException(ct.getValue(), tmp);
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
			tmp = String.format(tmp, "推广");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}

	public static Entity searchPublicSeting(RequestContext requestContext) throws BlAppException {
		String entity;
		Dao dao;
		try {
			entity = requestContext.getDomain(String.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(entity, "推广编号");
			dao = getDao(requestContext);
			ExtensionEntity extensionEntity;
			IdEntity<String> searchExtensionRequest = new IdEntity<String>();
			searchExtensionRequest.setId(entity);
			extensionEntity = (ExtensionEntity) dao.query(searchExtensionRequest, false);
			BLContextUtil.valiDomainIsNull(extensionEntity, "推广");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			for (ConfigKeywordEntity entry : extensionEntity.getConfigKeywords()) {
				searchConfigDataDto.addKeyWord(entry.getKeyword());
			}
			searchConfigDataDto.setName(extensionEntity.getExtensionName());
			searchConfigDataDto.setSummary(extensionEntity.getSummary());

			// 加载是否去版权
			ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(requestContext, entity,
					ManuscriptParameterType.unrights);
			if (unrights != null) {
				searchConfigDataDto
						.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
			}
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static void saveExtension(RequestContext requestContext, ExtensionEntity extensionEntity,
			SpecialEntity specialEntity) throws BlAppException {
		try {
			Integer modelConfigId;
			extensionEntity.setCreatetime(new Date());
			extensionEntity.setExtensionImg(specialEntity.getCoverImg());
			extensionEntity.setExtensionName(specialEntity.getSpecialName());
			extensionEntity.setExtensionState(ExtensionState.HASPUBLISH.getValue());
			extensionEntity.setExtensionType(specialEntity.getSpecialType());
			extensionEntity.setPublishTime(new Date());
			extensionEntity.setSpecialId(specialEntity.getSpecialId());
			extensionEntity.setSpecialName(specialEntity.getSpecialName());
			extensionEntity.setUserId(specialEntity.getUserId());
			extensionEntity.setSummary(specialEntity.getSummary());

			modelConfigId = extensionEntity.getModelConfigId();
			// 创建推广
			ManuscriptManager.createManuscript(requestContext, extensionEntity);

			// 保存推广链接
			ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
					extensionEntity.getExtensionUrl(), ManuscriptParameterType.extensionurl);

			// 保存连接实体参数
			ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
					modelConfigId + "", ManuscriptParameterType.modelConfig);

			// 保存标签
 			List<String> tagNames = new ArrayList<String>();
			for (ConfigTagEntity tag : specialEntity.getConfigTags()) {
				tagNames.add(tag.getTag());
				logger.info("tagNames:"+tag.getTag());
			}
			ManuscriptManager.saveManscriptParameters(requestContext, extensionEntity.getExtensionId(), tagNames,
					ManuscriptParameterType.tag);

			// 保存关键字
			List<String> keywords = new ArrayList<String>();
			for (ConfigKeywordEntity keyword : specialEntity.getConfigKeywords()) {
				keywords.add(keyword.getKeyword());
			}
			ManuscriptManager.saveManscriptParameters(requestContext, extensionEntity.getExtensionId(), keywords,
					ManuscriptParameterType.keyword);

			if (specialEntity.getConfig() != null) {
				// 获取设置在保存
				// 保存主色调
				ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
						specialEntity.getConfig().getMainColor(), ManuscriptParameterType.manuscriptMainColor);

				// 保存 推广执行时间 参数
				ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
						TimeUtil.format(specialEntity.getConfig().getStartTime()), ManuscriptParameterType.statrttime);

				ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
						TimeUtil.format(specialEntity.getConfig().getEndTime()), ManuscriptParameterType.endtime);

			}
			if (specialEntity.getConfigActive() != null) {
				// 保存活动设置
				ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
						TimeUtil.format(specialEntity.getConfigActive().getStartTime()),
						ManuscriptParameterType.statrtactivetime);

				ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
						TimeUtil.format(specialEntity.getConfigActive().getEndTime()),
						ManuscriptParameterType.endactivetime);
			}

		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static void saveExtension(RequestContext requestContext, ExtensionEntity extensionEntity)
			throws BlAppException {
		try {
			Dao extensionDao = getDao(requestContext);
			int val = extensionDao.save(extensionEntity);
			BLContextUtil.valiSaveDomain(val, "推广");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
}
