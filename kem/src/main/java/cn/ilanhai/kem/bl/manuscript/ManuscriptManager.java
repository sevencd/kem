package cn.ilanhai.kem.bl.manuscript;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.extension.ExtensionManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.bl.tag.SysTagManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.manuscript.ManuscriptDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptState;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.TerminalType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptContentEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptContentDto;
import cn.ilanhai.kem.domain.manuscript.dto.GetManuscriptParameterDto;
import cn.ilanhai.kem.domain.manuscript.dto.SaveManuscriptParamsDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchBackManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchFrontManuscriptDto;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.domain.template.TemplateUseLogEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.KeyUtil;

public class ManuscriptManager {
	private static Class<?> currentclass = ManuscriptDao.class;

	/**
	 * 根据relationid 发布优秀案例
	 * 
	 * @param requestContext
	 * @param relationId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public static String createManuscriptFrom(RequestContext requestContext, String relationId, String tags,
			String name) throws DaoAppException, BlAppException, SessionContainerException {
		BLContextUtil.valiName(name, "优秀案例名称", 20);
		String keyHead = KeyFactory.getKeyHeadByKey(relationId);
		if (!KeyFactory.KEY_EXTENSION.equals(keyHead)) {
			throw new BlAppException(-1, "目前只支持对推广进行优秀案例的操作谢谢合作,请检查[manuscriptId]是否正确谢谢合作");
		}
		return buildManscriptByExtension(requestContext, relationId, tags, name);
	}

	public static String createManuscript(RequestContext requestContext, String userId, ManuscriptType type,
			TerminalType terminalType) throws DaoAppException, BlAppException {
		ManuscriptEntity manuscriptEntity = new ManuscriptEntity();
		String manuscriptId = KeyFactory.newKey(KeyUtil.getKey(type));
		BLContextUtil.valiParaItemStrNullOrEmpty("创建:" + manuscriptId, KeyUtil.getName(type) + "编号");
		manuscriptEntity.setCreateTime(new Date());
		manuscriptEntity.setEnableState(EnableState.enable.getValue());
		manuscriptEntity.setManuscriptId(manuscriptId);
		manuscriptEntity.setManuscriptType(type.getValue());
		manuscriptEntity.setTerminalType(terminalType.getValue());
		manuscriptEntity.setManuscriptState(ManuscriptState.UNSUBMITTED.getValue());
		manuscriptEntity.setUserId(userId);
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
		BLContextUtil.valiSaveDomain(val, "创建" + KeyUtil.getName(type));
		return manuscriptId;
	}

	/**
	 * 创建稿件 兼容模版 推广 专题
	 * 
	 * @param requestContext
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static String createManuscript(RequestContext requestContext, Entity entity)
			throws DaoAppException, BlAppException {
		ManuscriptEntity manuscriptEntity = new ManuscriptEntity();
		if (entity instanceof TemplateEntity) {
			TemplateEntity templateEntity = (TemplateEntity) entity;
			manuscriptEntity.setCreateTime(templateEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(templateEntity.getTemplateId());
			manuscriptEntity.setManuscriptType(ManuscriptType.TEMPLATE.getValue());
			manuscriptEntity.setTerminalType(templateEntity.getTemplateType());
			manuscriptEntity.setManuscriptState(templateEntity.getTemplateState().getValue());
			manuscriptEntity.setUserId(templateEntity.getUserId());

			// 保存 封面
			ManuscriptManager.saveManscriptParameter(requestContext, templateEntity.getTemplateId(),
					templateEntity.getCoverImg(), ManuscriptParameterType.manuscriptImg);
			// 保存名称
			ManuscriptManager.saveManscriptParameter(requestContext, templateEntity.getTemplateId(),
					templateEntity.getTemplateName(), ManuscriptParameterType.manuscriptName);

			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "创建模板");
		} else if (entity instanceof SpecialEntity) {
			SpecialEntity specialEntity = (SpecialEntity) entity;
			manuscriptEntity.setCreateTime(specialEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(specialEntity.getSpecialId());
			manuscriptEntity.setManuscriptType(ManuscriptType.SPECIAL.getValue());
			manuscriptEntity.setTerminalType(specialEntity.getSpecialType());
			manuscriptEntity.setManuscriptState(specialEntity.getSpecialState());
			manuscriptEntity.setUserId(specialEntity.getUserId());
			// 保存 封面
			ManuscriptManager.saveManscriptParameter(requestContext, specialEntity.getSpecialId(),
					specialEntity.getCoverImg(), ManuscriptParameterType.manuscriptImg);
			// 保存名称
			ManuscriptManager.saveManscriptParameter(requestContext, specialEntity.getSpecialId(),
					specialEntity.getSpecialName(), ManuscriptParameterType.manuscriptName);

			// 保存发布名称
			ManuscriptManager.saveManscriptParameter(requestContext, specialEntity.getSpecialId(),
					specialEntity.getSpecialName(), ManuscriptParameterType.publishname);

			if (!Str.isNullOrEmpty(specialEntity.getTemplateId())) {
				// 从模板创建
				saveManscriptParameter(requestContext, specialEntity.getSpecialId(), specialEntity.getTemplateId(),
						ManuscriptParameterType.templatesource);
			} else if (!Str.isNullOrEmpty(specialEntity.getManuscriptId())) {
				// 从优秀案例创建
				saveManscriptParameter(requestContext, specialEntity.getSpecialId(), specialEntity.getManuscriptId(),
						ManuscriptParameterType.casesource);
			}
			// 保存专题广内容
			saveManscriptContent(requestContext, specialEntity.getSpecialId(), specialEntity.getContext());

			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "创建专题");
		} else if (entity instanceof ExtensionEntity) {
			ExtensionEntity extensionEntity = (ExtensionEntity) entity;
			manuscriptEntity.setCreateTime(extensionEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(extensionEntity.getExtensionId());
			manuscriptEntity.setManuscriptType(ManuscriptType.EXTENSION.getValue());
			manuscriptEntity.setTerminalType(extensionEntity.getExtensionType());
			manuscriptEntity.setManuscriptState(extensionEntity.getExtensionState());
			manuscriptEntity.setUserId(extensionEntity.getUserId());

			// 保存 封面
			ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
					extensionEntity.getExtensionImg(), ManuscriptParameterType.manuscriptImg);
			// 保存名称
			ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
					extensionEntity.getExtensionName(), ManuscriptParameterType.manuscriptName);

			// 保存简介
			ManuscriptManager.saveManscriptParameter(requestContext, extensionEntity.getExtensionId(),
					extensionEntity.getSummary(), ManuscriptParameterType.manuscriptSummary);

			if (!Str.isNullOrEmpty(extensionEntity.getSpecialId())) {
				// 从专题创建
				saveManscriptParameter(requestContext, extensionEntity.getExtensionId(), extensionEntity.getSpecialId(),
						ManuscriptParameterType.specialsource);
			}
			// 保存推广内容
			saveManscriptContent(requestContext, extensionEntity.getExtensionId(), extensionEntity.getContext());

			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "创建推广");
		}
		return manuscriptEntity.getManuscriptId();
	}

	public static String saveManuscript(RequestContext requestContext, Entity entity)
			throws DaoAppException, BlAppException {
		ManuscriptEntity manuscriptEntity = new ManuscriptEntity();
		if (entity instanceof TemplateEntity) {
			TemplateEntity templateEntity = (TemplateEntity) entity;
			manuscriptEntity.setCreateTime(templateEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(templateEntity.getTemplateId());
			manuscriptEntity.setManuscriptType(ManuscriptType.TEMPLATE.getValue());
			manuscriptEntity.setTerminalType(templateEntity.getTemplateType());
			manuscriptEntity.setManuscriptState(templateEntity.getTemplateState().getValue());
			manuscriptEntity.setUserId(templateEntity.getUserId());
			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "保存模板");
		} else if (entity instanceof SpecialEntity) {
			SpecialEntity specialEntity = (SpecialEntity) entity;
			manuscriptEntity.setCreateTime(specialEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(specialEntity.getSpecialId());
			manuscriptEntity.setManuscriptType(ManuscriptType.SPECIAL.getValue());
			manuscriptEntity.setTerminalType(specialEntity.getSpecialType());
			manuscriptEntity.setManuscriptState(specialEntity.getSpecialState());
			manuscriptEntity.setUserId(specialEntity.getUserId());
			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "保存专题");
		} else if (entity instanceof ExtensionEntity) {
			ExtensionEntity extensionEntity = (ExtensionEntity) entity;
			manuscriptEntity.setCreateTime(extensionEntity.getCreatetime());
			manuscriptEntity.setEnableState(EnableState.enable.getValue());
			manuscriptEntity.setManuscriptId(extensionEntity.getExtensionId());
			manuscriptEntity.setManuscriptType(ManuscriptType.EXTENSION.getValue());
			manuscriptEntity.setTerminalType(extensionEntity.getExtensionType());
			manuscriptEntity.setManuscriptState(extensionEntity.getExtensionState());
			manuscriptEntity.setUserId(extensionEntity.getUserId());
			int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
			BLContextUtil.valiSaveDomain(val, "保存专题");
		}
		return manuscriptEntity.getManuscriptId();
	}

	/**
	 * 保存稿件内容
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 * @param content
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void saveManscriptContent(RequestContext requestContext, String manuscriptId, String content)
			throws DaoAppException, BlAppException {
		ManuscriptContentEntity manuscriptContentEntity = getManuscriptContentById(requestContext, manuscriptId);
		if (manuscriptContentEntity == null) {
			manuscriptContentEntity = new ManuscriptContentEntity();
			manuscriptContentEntity.setCreateTime(new Date());
		}
		manuscriptContentEntity.setEnableState(EnableState.enable.getValue());
		manuscriptContentEntity.setManuscriptId(manuscriptId);
		// 替换 content 中的占位符
		// manuscriptContentEntity.setContent(BLContextUtil.analysisMainId(content,
		// manuscriptId));
		manuscriptContentEntity.setContent(content);
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptContentEntity);
		BLContextUtil.valiSaveDomain(val, "保存模板内容");
	}

	/**
	 * 保存稿件设置
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 * @param content
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	// public static void saveManscriptSetting_disable(RequestContext
	// requestContext, String manuscriptId,
	// ManuscriptSettingEntity setting) throws DaoAppException, BlAppException {
	// ManuscriptSettingEntity manuscriptSettingEntity =
	// getManuscriptSettingById(requestContext, manuscriptId);
	// if (manuscriptSettingEntity == null) {
	// manuscriptSettingEntity = setting;
	// }
	// if (setting.getEnableState() != null) {
	// manuscriptSettingEntity.setEnableState(setting.getEnableState());
	// }
	// if (!Str.isNullOrEmpty(manuscriptId)) {
	// manuscriptSettingEntity.setManuscriptId(manuscriptId);
	// }
	// if (!Str.isNullOrEmpty(setting.getManuscriptImg())) {
	// manuscriptSettingEntity.setManuscriptImg(setting.getManuscriptImg());
	// }
	// if (!Str.isNullOrEmpty(setting.getManuscriptMainColor())) {
	// manuscriptSettingEntity.setManuscriptMainColor(setting.getManuscriptMainColor());
	// }
	//
	// if (!Str.isNullOrEmpty(setting.getManuscriptName())) {
	// manuscriptSettingEntity.setManuscriptName(setting.getManuscriptName());
	// }
	//
	// if (!Str.isNullOrEmpty(setting.getManuscriptSummary())) {
	// manuscriptSettingEntity.setManuscriptSummary(setting.getManuscriptSummary());
	// }
	// int val = getDao(requestContext,
	// currentclass).save(manuscriptSettingEntity);
	// valiSaveDomain(val, "保存模板内容");
	// }
	/**
	 * 保存 稿件参数
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 * @param parameter
	 * @param parameteType
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void saveManscriptParameter(RequestContext requestContext, String manuscriptId, String parameter,
			ManuscriptParameterType parameteType) throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(parameter)) {
			return;
		}
		if (parameteType == null) {
			return;
		}
		ManuscriptParameterEntity manuscriptParameterEntity = getManuscriptParameterById(requestContext, manuscriptId,
				parameteType);
		if (manuscriptParameterEntity == null) {
			manuscriptParameterEntity = new ManuscriptParameterEntity();
			manuscriptParameterEntity.setCreateTime(new Date());
			manuscriptParameterEntity.setEnableState(EnableState.enable.getValue());
			manuscriptParameterEntity.setManuscriptId(manuscriptId);
			manuscriptParameterEntity.setParameter(parameter);
			manuscriptParameterEntity.setParameterType(parameteType.getValue());
		} else {
			if (parameter.equals(manuscriptParameterEntity.getParameter())) {
				return;
			}
			manuscriptParameterEntity.setParameter(parameter);
		}
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptParameterEntity);
		BLContextUtil.valiSaveDomain(val, "保存参数");
	}

	public static void saveManscriptParameters(RequestContext requestContext, String manuscriptId, List<String> params,
			ManuscriptParameterType type) throws DaoAppException, BlAppException {
		if (params == null) {
			return;
		}
		SaveManuscriptParamsDto saveManuscriptTagsDto = new SaveManuscriptParamsDto();
		saveManuscriptTagsDto.setManuscriptId(manuscriptId);
		saveManuscriptTagsDto.setParams(params);
		saveManuscriptTagsDto.setParamType(type.getValue());
		int val = BLContextUtil.getDao(requestContext, currentclass).save(saveManuscriptTagsDto);
		BLContextUtil.valiSaveDomain(val, "保存标签");
	}

	private static void buildManscriptBySpecial(RequestContext requestContext, String relationId, String tags,
			String manuscriptId, Date createTime, ManuscriptEntity manuscriptEntity, String name)
			throws DaoAppException, BlAppException, SessionContainerException {
		SpecialEntity specialEntity = SpecialManager.getSpecialEntityById(requestContext, relationId);
		if (Str.isNullOrEmpty(relationId) || specialEntity == null) {
			CodeTable ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			throw new BlAppException(ct.getValue(), "专题编号错误");
		}

		if (!Str.isNullOrEmpty(specialEntity.getManuscriptId())) {
			ManuscriptEntity specialEntity_manuscriptEntity = getManuscriptById(requestContext,
					specialEntity.getManuscriptId());
			// 验证
			if (specialEntity_manuscriptEntity != null
					&& EnableState.enable.getValue().equals(specialEntity_manuscriptEntity.getEnableState())) {
				throw new BlAppException(-800, "该专题已发布优秀案例 请先取消发布");
			}
		}

		PluginManager.copyPlugin(requestContext, relationId, manuscriptId, ManuscriptType.SPECIAL,
				ManuscriptType.EXCELLENTCASE);

		manuscriptEntity.setTerminalType(specialEntity.getSpecialType());
		manuscriptEntity.setUserId(specialEntity.getUserId());
		manuscriptEntity.setCreateTime(new Timestamp(createTime.getTime()));

		manuscriptEntity.setManuscriptState(ManuscriptState.UNSUBMITTED.getValue());
		manuscriptEntity.setEnableState(EnableState.enable.getValue());
		manuscriptEntity.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());
		// 保存 封面
		ManuscriptManager.saveManscriptParameter(requestContext, manuscriptId, specialEntity.getCoverImg(),
				ManuscriptParameterType.manuscriptImg);
		// 保存 主色调
		ManuscriptManager.saveManscriptParameter(requestContext, manuscriptId, specialEntity.getConfig().getMainColor(),
				ManuscriptParameterType.manuscriptMainColor);

		// 保存名称
		ManuscriptManager.saveManscriptParameter(requestContext, manuscriptId, name,
				ManuscriptParameterType.manuscriptName);

		// 保存简介
		ManuscriptManager.saveManscriptParameter(requestContext, manuscriptId, specialEntity.getSummary(),
				ManuscriptParameterType.manuscriptSummary);

		// 添加内容
		ManuscriptContentEntity manuscriptContent = new ManuscriptContentEntity();
		manuscriptContent.setContent(specialEntity.getContext());
		manuscriptContent.setEnableState(EnableState.enable.getValue());
		manuscriptContent.setManuscriptId(manuscriptId);
		manuscriptContent.setCreateTime(createTime);
		manuscriptEntity.setManuscriptContent(manuscriptContent);

		// 添加参数
		List<ManuscriptParameterEntity> manuscriptParameters = new ArrayList<ManuscriptParameterEntity>();
		// 添加源数据参数
		manuscriptParameters.add(createManuscriptParameter(manuscriptId, EnableState.enable,
				ManuscriptParameterType.extensionsource, relationId));
		// 添加标签
		for (String tag : BLContextUtil.transformationString(tags, true)) {
			manuscriptParameters
					.add(createManuscriptParameter(manuscriptId, EnableState.enable, ManuscriptParameterType.tag, tag));
		}
		manuscriptEntity.setManuscriptParameters(manuscriptParameters);
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
		BLContextUtil.valiSaveDomain(val, "发布优秀案例");
		// 专题会写 案例id
		specialEntity.setManuscriptId(manuscriptId);
		SpecialManager.saveSpecialEntity(requestContext, specialEntity);
	}

	private static String buildManscriptByExtension(RequestContext requestContext, String relationId, String tags,
			String name) throws DaoAppException, BlAppException, SessionContainerException {
		ManuscriptEntity extensionManuscript = getManuscriptById(requestContext, relationId);
		BLContextUtil.valiDomainIsNull(extensionManuscript, "推广");
		String caseId = extensionManuscript.getManuscriptParameterByType(ManuscriptParameterType.casetarget);
		if (!Str.isNullOrEmpty(caseId)) {
			ManuscriptEntity caseEntity = getManuscriptById(requestContext, caseId);
			// 验证
			if (caseEntity != null && EnableState.enable.getValue().equals(caseEntity.getEnableState())) {
				throw new BlAppException(-800, "该专题已发布优秀案例 请先取消发布");
			}
		} else {
			caseId = KeyFactory.newKey(KeyFactory.KEY_MANUSCRIPT);
		}
		PluginManager.copyPlugin(requestContext, relationId, caseId, ManuscriptType.EXTENSION,
				ManuscriptType.EXCELLENTCASE);
		ManuscriptEntity manuscriptEntity = new ManuscriptEntity();
		manuscriptEntity.setManuscriptId(caseId);
		manuscriptEntity.setTerminalType(extensionManuscript.getTerminalType());
		manuscriptEntity.setUserId(extensionManuscript.getUserId());
		manuscriptEntity.setCreateTime(new Timestamp(new Date().getTime()));

		manuscriptEntity.setManuscriptState(ManuscriptState.UNSUBMITTED.getValue());
		manuscriptEntity.setEnableState(EnableState.enable.getValue());
		manuscriptEntity.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());

		// 保存 封面
		ManuscriptManager.saveManscriptParameter(requestContext, caseId,
				extensionManuscript.getManuscriptParameterByType(ManuscriptParameterType.manuscriptImg),
				ManuscriptParameterType.manuscriptImg);
		// 保存 主色调
		ManuscriptManager.saveManscriptParameter(requestContext, caseId,
				extensionManuscript.getManuscriptParameterByType(ManuscriptParameterType.manuscriptMainColor),
				ManuscriptParameterType.manuscriptMainColor);
		// 保存名称
		ManuscriptManager.saveManscriptParameter(requestContext, caseId, name, ManuscriptParameterType.manuscriptName);

		// 保存简介
		ManuscriptManager.saveManscriptParameter(requestContext, caseId,
				extensionManuscript.getManuscriptParameterByType(ManuscriptParameterType.manuscriptSummary),
				ManuscriptParameterType.manuscriptSummary);

		// 添加内容
		saveManscriptContent(requestContext, caseId, extensionManuscript.getManuscriptContent().getContent());

		// 添加参数
		List<ManuscriptParameterEntity> manuscriptParameters = new ArrayList<ManuscriptParameterEntity>();
		// 添加源数据参数
		manuscriptParameters.add(createManuscriptParameter(caseId, EnableState.enable,
				ManuscriptParameterType.extensionsource, relationId));
		// 添加标签
		List<String> tagNames = BLContextUtil.transformationString(tags, true);
		for (String tag : tagNames) {
			manuscriptParameters
					.add(createManuscriptParameter(caseId, EnableState.enable, ManuscriptParameterType.tag, tag));
		}
		manuscriptEntity.setManuscriptParameters(manuscriptParameters);
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
		BLContextUtil.valiSaveDomain(val, "发布优秀案例");
		// // 专题会写 案例id
		// extensionEntity.setManuscriptId(manuscriptId);
		// ExtensionManager.saveExtension(requestContext, extensionEntity);

		ManuscriptManager.saveManscriptParameter(requestContext, relationId, caseId,
				ManuscriptParameterType.casetarget);
		SysTagManager.quoteNumAdd(requestContext, tagNames);
		return caseId;
	}

	/**
	 * 根据relationid 取消发布优秀案例
	 * 
	 * @param requestContext
	 * @param relationId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public static String disableManuscript(RequestContext requestContext, String relationId)
			throws DaoAppException, BlAppException, SessionContainerException {
		ManuscriptEntity manuscriptEntity;
		String keyHead = KeyFactory.getKeyHeadByKey(relationId);
		String manuscriptId = null;
		switch (keyHead) {
		case KeyFactory.KEY_EXTENSION:
			ExtensionEntity extensionEntity = ExtensionManager.getExtensionEntityById(requestContext, relationId);
			manuscriptId = extensionEntity.getManuscriptId();
			if (Str.isNullOrEmpty(relationId) || extensionEntity == null) {
				CodeTable ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
				throw new BlAppException(ct.getValue(), "专题编号错误");
			}
			break;
		case KeyFactory.KEY_SPECIAL:
			SpecialEntity specialEntity = SpecialManager.getSpecialEntityById(requestContext, relationId);
			manuscriptId = specialEntity.getManuscriptId();
			if (Str.isNullOrEmpty(relationId) || specialEntity == null) {
				CodeTable ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
				throw new BlAppException(ct.getValue(), "专题编号错误");
			}
			break;

		case KeyFactory.KEY_TEMPLATE:
			return null;
		}
		if (!Str.isNullOrEmpty(manuscriptId)) {
			manuscriptEntity = getManuscriptById(requestContext, manuscriptId);
			// 验证 优秀案例状态
			if (manuscriptEntity != null && EnableState.unenable.getValue().equals(manuscriptEntity.getEnableState())) {
				throw new BlAppException(-801, "该专题已取消发布优秀案例 请先发布");
			}
		} else {
			throw new BlAppException(-802, "该专题未发布优秀案例 请先发布");
		}
		manuscriptEntity.setEnableState(EnableState.unenable.getValue());
		int val = BLContextUtil.getDao(requestContext, currentclass).save(manuscriptEntity);
		BLContextUtil.valiSaveDomain(val, "取消发布优秀案例");
		return manuscriptEntity.getManuscriptId();
	}

	/**
	 * 构建 稿件参数
	 * 
	 * @param manuscriptId
	 *            稿件编号
	 * @param enableState
	 *            参数状态
	 * @param manuscriptParameterType
	 *            参数类型
	 * @param param
	 *            参数内容
	 * @return
	 */
	public static ManuscriptParameterEntity createManuscriptParameter(String manuscriptId, EnableState enableState,
			ManuscriptParameterType manuscriptParameterType, String param) {
		ManuscriptParameterEntity manuscriptParameterEntity = new ManuscriptParameterEntity();
		manuscriptParameterEntity.setParameter(param);
		manuscriptParameterEntity.setEnableState(enableState.getValue());
		manuscriptParameterEntity.setManuscriptId(manuscriptId);
		manuscriptParameterEntity.setParameterType(manuscriptParameterType.getValue());
		manuscriptParameterEntity.setCreateTime(new Date());
		return manuscriptParameterEntity;
	}

	/**
	 * 通过稿件编号 获取 稿件实体
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static ManuscriptEntity getManuscriptById(RequestContext requestContext, String manuscriptId)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(requestContext, currentclass);
		IdEntity<String> manuscriptQueryId = new IdEntity<String>();
		manuscriptQueryId.setId(manuscriptId);
		return (ManuscriptEntity) dao.query(manuscriptQueryId, false);
	}

	/**
	 * 通过稿件编号校验 稿件实体
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void checkManuscriptById(RequestContext requestContext, String manuscriptId)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(requestContext, currentclass);
		GetManuscriptContentDto getManuscriptContentDto = new GetManuscriptContentDto();
		getManuscriptContentDto.setId(manuscriptId);
		ManuscriptEntity manuscriptEntity = (ManuscriptEntity) dao.query(getManuscriptContentDto, false);
		BLContextUtil.valiDomainIsNull(manuscriptEntity, KeyUtil.getName(manuscriptId));
		return;
	}

	/**
	 * 通过稿件编号 获取 稿件参数列表
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static ManuscriptParameterEntity getManuscriptParameterById(RequestContext requestContext,
			String manuscriptId, ManuscriptParameterType parameteType) throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(requestContext, currentclass);
		GetManuscriptParameterDto getManuscriptParameterDto = new GetManuscriptParameterDto();
		getManuscriptParameterDto.setId(manuscriptId);
		getManuscriptParameterDto.setParameterType(parameteType.getValue());
		return (ManuscriptParameterEntity) dao.query(getManuscriptParameterDto, false);
	}

	/**
	 * 通过稿件编号 获取 稿件内容实体
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static ManuscriptContentEntity getManuscriptContentById(RequestContext requestContext, String manuscriptId)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(requestContext, currentclass);
		GetManuscriptContentDto getManuscriptContentDto = new GetManuscriptContentDto();
		getManuscriptContentDto.setId(manuscriptId);
		return (ManuscriptContentEntity) dao.query(getManuscriptContentDto, false);
	}

	/**
	 * 通过稿件编号 获取 稿件内容实体
	 * 
	 * @param requestContext
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	// public static ManuscriptSettingEntity
	// getManuscriptSettingById_disable(RequestContext requestContext, String
	// manuscriptId)
	// throws DaoAppException, BlAppException {
	// Dao dao = getDao(requestContext, currentclass);
	// GetManuscriptSettingDto getManuscriptSettingDto = new
	// GetManuscriptSettingDto();
	// getManuscriptSettingDto.setId(manuscriptId);
	// return (ManuscriptSettingEntity) dao.query(getManuscriptSettingDto,
	// false);
	// }

	/**
	 * 保存稿件使用记录
	 * 
	 * @param context
	 * @param templateId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void saveUseLog(RequestContext context, String templateId)
			throws SessionContainerException, DaoAppException, BlAppException {
		int val = -1;
		// 模版使用记录
		TemplateUseLogEntity logEntity = new TemplateUseLogEntity();
		logEntity.setTemplateId(templateId);
		logEntity.setUserId(context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class));
		logEntity.setUserType(context.getSession().getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class));
		logEntity.setCreateTime(new java.util.Date());
		val = BLContextUtil.getDao(context, currentclass).save(logEntity);
		if (val < 0) {
			CodeTable ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "案例");
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void delete(RequestContext context, String manuscriptId) throws DaoAppException, BlAppException {
		IdEntity<String> id = new IdEntity<String>();
		id.setId(manuscriptId);
		int val = BLContextUtil.getDao(context, currentclass).delete(id);
		BLContextUtil.valiSaveDomain(val, "删除稿件");
	}

	public static Entity search(RequestContext context, SearchBackManuscriptDto searchDto)
			throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(context, currentclass);
		return dao.query(searchDto, false);
	}

	public static Entity search(RequestContext context, SearchFrontManuscriptDto searchDto)
			throws DaoAppException, BlAppException, SessionContainerException {
		Dao dao = BLContextUtil.getDao(context, currentclass);
		searchDto.setUserId(BLContextUtil.getSessionUserId(context,true));
		return dao.query(searchDto, false);
	}
	
	public static List<String> getTag(RequestContext context, String entity, ManuscriptParameterType tag) throws DaoAppException, BlAppException {
		List<String> list = new ArrayList<String>();
		ManuscriptEntity manuscriptEntity = getManuscriptById(context, entity);
		if (manuscriptEntity != null) {
			List<ManuscriptParameterEntity> manuscriptParameterEntitys = manuscriptEntity.getManuscriptParameters();
			for (ManuscriptParameterEntity entry : manuscriptParameterEntitys) {
				if (tag.getValue().equals(entry.getParameterType())) {
					list.add(entry.getParameter());
				}
			}
		}	
		return list;
	}
	
}
