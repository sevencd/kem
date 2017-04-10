package cn.ilanhai.kem.bl.extension.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.ClientTypes;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.bindhost.BindHostManager;
import cn.ilanhai.kem.bl.extension.Extension;
import cn.ilanhai.kem.bl.extension.ExtensionManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.notify.NotifyImpl;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.bl.template.TemplateManager;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.extension.ExtensionMybatisDao;
import cn.ilanhai.kem.dao.plugin.PluginDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.SearchConfigDataDto;
import cn.ilanhai.kem.domain.enums.ExtensionState;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.extension.DisableExtensionRequestDto;
import cn.ilanhai.kem.domain.extension.ExtensionEetailsDto;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.extension.ExtensionIdRequestDto;
import cn.ilanhai.kem.domain.extension.SearExtensionResponseDto;
import cn.ilanhai.kem.domain.extension.SearchExtensionRequestDto;
import cn.ilanhai.kem.domain.extension.SearchExtensionRequestEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.notify.NotifyType;
import cn.ilanhai.kem.domain.plugin.PluginEntity;
import cn.ilanhai.kem.domain.plugin.QueryPlugin;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.special.AuditLogEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("extension")
public class ExtensionImpl extends BaseBl implements Extension {
	Logger logger = Logger.getLogger(ExtensionImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity search(RequestContext context) throws BlAppException,
			DaoAppException {
		SearchExtensionRequestDto searchExtensionRequestDto = null;
		SearchExtensionRequestEntity searchExtensionRequestEntity = null;
		CodeTable ct;
		Dao dao = null;
		try {
			searchExtensionRequestEntity = new SearchExtensionRequestEntity();
			// 获取请求数据
			searchExtensionRequestDto = context
					.getDomain(SearchExtensionRequestDto.class);
			this.valiDomainIsNull(searchExtensionRequestDto, "推广");
			valiParaNotNull(searchExtensionRequestDto.getStartCount(), "开始条数");
			valiParaNotNull(searchExtensionRequestDto.getPageSize(), "查询条数");
			// 设置userId的值
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				if (searchExtensionRequestDto.getUser_id() != null
						&& searchExtensionRequestDto.getUser_id().length() > 0) {
					searchExtensionRequestEntity
							.setUserId(searchExtensionRequestDto.getUser_id());
				}
			} else if (context.getSession().getSessionState()
					.getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				searchExtensionRequestEntity.setUserId(this
						.getSessionUserId(context));
				this.checkFrontUserType(context, UserType.GENERAL_USER);
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			searchExtensionRequestEntity
					.setExtensionName(searchExtensionRequestDto
							.getExtensionName());
			searchExtensionRequestEntity
					.setExtensionState(searchExtensionRequestDto
							.getExtensionState());
			searchExtensionRequestEntity
					.setExtensionType(searchExtensionRequestDto
							.getExtensionType());
			searchExtensionRequestEntity
					.setStartCount(searchExtensionRequestDto.getStartCount());
			searchExtensionRequestEntity.setPageSize(searchExtensionRequestDto
					.getPageSize());
			searchExtensionRequestEntity.setTimeStart(searchExtensionRequestDto
					.getTimeStart());
			searchExtensionRequestEntity.setTimeEnd(searchExtensionRequestDto
					.getTimeEnd());
			searchExtensionRequestEntity.setOrderType(searchExtensionRequestDto
					.getOrder() + "");
			searchExtensionRequestEntity
					.setOrderType_new(searchExtensionRequestDto.getOrder() + "");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context,
					ExtensionMybatisDao.class);
			this.valiDaoIsNull(dao, "推广");
			SearExtensionResponseDto SearExtensionResponseDto = new SearExtensionResponseDto();
			SearExtensionResponseDto.setPageSize(searchExtensionRequestDto
					.getPageSize());
			SearExtensionResponseDto.setStartCount(searchExtensionRequestDto
					.getStartCount());
			SearExtensionResponseDto.setList(dao
					.query(searchExtensionRequestEntity));
			IdEntity<Integer> idEntity = (IdEntity<Integer>) dao.query(
					searchExtensionRequestEntity, false);
			SearExtensionResponseDto.setTotalCount(idEntity.getId());
			return SearExtensionResponseDto;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException,
			DaoAppException {
		ExtensionIdRequestDto extensionId = null;
		CodeTable ct;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 获取请求数据
			extensionId = context.getDomain(ExtensionIdRequestDto.class);
			this.valiDomainIsNull(extensionId, "推广");
			this.valiParaItemStrNullOrEmpty(extensionId.getExtensionId(),
					"推广编号");
			ExtensionEntity extensionEntity = ExtensionManager
					.getExtensionEntityById(context,
							extensionId.getExtensionId());
			this.valiDomainIsNull(extensionEntity, "推广");
			this.checkCurrentUser(context, extensionEntity.getUserId());
			ManuscriptManager.delete(context, extensionId.getExtensionId());
			// 去掉redis中推广对应域名
			BindHostManager manager = new BindHostManager(context);
			manager.disableExtensionHost(extensionId.getExtensionId());
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void disable(RequestContext context) throws BlAppException,
			DaoAppException {
		DisableExtensionRequestDto disableExtensionRequestDto = null;
		CodeTable ct;
		Dao dao = null;
		NotifyImpl notifyImpl = null;
		String content = null;
		String currUserId = "";
		try {
			this.checkBackUserLogined(context);
			currUserId = this.getSessionUserId(context);
			// 获取请求数据
			disableExtensionRequestDto = context
					.getDomain(DisableExtensionRequestDto.class);
			this.valiDomainIsNull(disableExtensionRequestDto, "推广禁用请求数据错误");
			this.valiParaItemStrNullOrEmpty(
					disableExtensionRequestDto.getExtensionId(), "推广编号");
			this.valiParaItemStrNullOrEmpty(
					disableExtensionRequestDto.getDisableReason(), "禁用原因不能为空");
			ExtensionEntity extensionEntity = ExtensionManager
					.getExtensionEntityById(context,
							disableExtensionRequestDto.getExtensionId());
			this.valiDomainIsNull(extensionEntity, "推广");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "推广");
			// 删除
			// ManuscriptManager.delete(context,
			// extensionEntity.getExtensionId());

			extensionEntity.setExtensionState(ExtensionState.HASDISABLE
					.getValue());
			extensionEntity.setDisableReason(disableExtensionRequestDto
					.getDisableReason());
			// 刷新推广状态
			ManuscriptManager.saveManuscript(context, extensionEntity);

			ManuscriptManager.saveManscriptParameter(context,
					extensionEntity.getExtensionId(),
					disableExtensionRequestDto.getDisableReason(),
					ManuscriptParameterType.bouncedReason);

			content = "很抱歉，您推广的专题有一些违规内容哦，请修改之后再重新发布。";
			notifyImpl = new NotifyImpl();
			if (!notifyImpl.create(context, currUserId,
					extensionEntity.getUserId(), content, NotifyType.Sys))
				throw new BlAppException(-1, "创建通知错误");
			// 推广保存禁用记录
			AuditLogEntity auditLogEntity = new AuditLogEntity();
			auditLogEntity.setCreatetime(new Date());
			auditLogEntity.setDisable_reason(disableExtensionRequestDto
					.getDisableReason());
			auditLogEntity.setModelConfigId(extensionEntity.getModelConfigId());
			ExtensionManager.saveAuditLogModel(context, auditLogEntity);

			// 专题保存禁用记录
			SpecialManager.disable(context, extensionEntity.getSpecialId());
			AuditLogEntity auditLogSpecialEntity = new AuditLogEntity();
			auditLogSpecialEntity.setCreatetime(new Date());
			auditLogSpecialEntity.setDisable_reason(disableExtensionRequestDto
					.getDisableReason());
			auditLogSpecialEntity.setModelConfigId(0);
			
			SpecialEntity specialeEntity = SpecialManager.getSpecialEntityById(
					context, extensionEntity.getSpecialId());
			if (specialeEntity != null)
				auditLogSpecialEntity.setModelConfigId(specialeEntity
						.getModelConfigId());
			ExtensionManager.saveAuditLogModel(context, auditLogSpecialEntity);
			// 获取缓存
			Cache cache = CacheUtil.getInstance().getCache(
					Integer.parseInt(getValue(context, "cacheIndex")));
			// 推广缓存存在时间 1秒 做到删除缓存
			cache.set(BLContextUtil.redisKeyForPublish(extensionEntity
					.getExtensionId()), BLContextUtil.redisValueForPublish(
					extensionEntity.getExtensionId(),
					extensionEntity.getExtensionType(),
					getValue(context, "apihost"),
					extensionEntity.getExtensionUrl()), 1);
			// 专题缓存存在时间 1秒 做到删除缓存
			cache.set(BLContextUtil.redisKeyForPublish(extensionEntity
					.getSpecialId()), BLContextUtil.redisValueForPublish(
					extensionEntity.getSpecialId(),
					extensionEntity.getExtensionType(),
					getValue(context, "apihost"),
					extensionEntity.getExtensionUrl()), 1);

			// 去掉redis中推广对应域名
			BindHostManager manager = new BindHostManager(context);
			manager.disableExtensionHost(disableExtensionRequestDto
					.getExtensionId());
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String lookfor(RequestContext context) throws BlAppException,
			DaoAppException {
		String extensionId = null;
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取请求数据
			
			
			extensionId = context.getDomain(String.class);
			this.valiParaItemStrNullOrEmpty(extensionId, "推广编号");
			ExtensionEntity extensionEntity = ExtensionManager
					.getExtensionEntityById(context, extensionId);
			this.valiDomainIsNull(extensionEntity, "推广");


//			extensionEntity.setExtensionState(ExtensionState.HASLOOKFOR.getValue());
//			// 更新状态
//			ManuscriptManager.saveManuscript(context, extensionEntity);
     
			return extensionEntity.getExtensionUrl();
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 查询发布设置
	 */
	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchPublishSetting(RequestContext context)
			throws BlAppException, DaoAppException {
		String entity;
		entity = context.getDomain(String.class);
		logger.info("查询发布设置的稿件id:" + entity);
		this.valiParaItemStrNullOrEmpty(entity, "查询发布设置编号");
		if (!ClientTypes.Topic.toString().equals(
				context.getQueryString("ClientType"))) {
			CodeTable ct = CodeTable.BL_SESSION_WRONGCLIENT;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}

		if (entity.startsWith(KeyFactory.KEY_EXTENSION)) {
			// 推广查询
			logger.info("推广查询");
			return ExtensionManager.searchPublicSeting(context);
		} else if (entity.startsWith(KeyFactory.KEY_SPECIAL)) {
			// 专题查询
			logger.info("专题查询");
			return SpecialManager.searchPublicSeting(context);
		} else if (entity.startsWith(KeyFactory.KEY_TEMPLATE)) {
			// 模板查询
			logger.info("模板查询");
			return TemplateManager.searchPublicSeting(context);
		} else if (entity.startsWith(KeyFactory.KEY_MANUSCRIPT)) {
			// 优秀案例
			logger.info("查询优秀案例");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			ManuscriptParameterEntity nameEntity = ManuscriptManager
					.getManuscriptParameterById(context, entity,
							ManuscriptParameterType.manuscriptName);
			searchConfigDataDto.setName(nameEntity != null ? nameEntity
					.getParameter() : null);
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		} else if (entity.startsWith(KeyFactory.KEY_DET)) {
			// 优秀案例
			logger.info("查询临时稿件");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			ManuscriptParameterEntity nameEntity = ManuscriptManager
					.getManuscriptParameterById(context, entity,
							ManuscriptParameterType.manuscriptName);
			searchConfigDataDto.setName(nameEntity != null ? nameEntity
					.getParameter() : null);
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		}
		return null;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity details(RequestContext context) throws BlAppException,
			DaoAppException {
		String extensionId = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 获取请求数据
			extensionId = context.getDomain(String.class);
			this.valiParaItemStrNullOrEmpty(extensionId, "推广编号");
			ExtensionEntity extensionEntity = ExtensionManager
					.getExtensionEntityById(context, extensionId);
			this.valiDomainIsNull(extensionEntity, "推广");

			if (!this.getSessionUserId(context).equals(
					extensionEntity.getUserId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			// 获取数据连接
			// Dao dao = this.daoProxyFactory.getDao(context);
			// this.valiDaoIsNull(dao, "推广");
			// int val = dao.save(extensionEntity);
			// this.valiSaveDomain(val, "推广");
			ExtensionEetailsDto extensionEetailsDto = new ExtensionEetailsDto();
			extensionEetailsDto.setCreatetime(extensionEntity.getCreatetime());
			extensionEetailsDto
					.setExtensionId(extensionEntity.getExtensionId());
			extensionEetailsDto.setExtensionImg(extensionEntity
					.getExtensionImg());
			extensionEetailsDto.setExtensionName(extensionEntity
					.getExtensionName());
			extensionEetailsDto.setExtensionState(extensionEntity
					.getExtensionState());
			extensionEetailsDto.setExtensionType(extensionEntity
					.getExtensionType());
			extensionEetailsDto.setExtensionUrl(extensionEntity
					.getExtensionUrl());
			extensionEetailsDto
					.setPublishTime(extensionEntity.getPublishTime());
			extensionEetailsDto.setSpecialId(extensionEntity.getSpecialId());
			extensionEetailsDto.setSummary(extensionEntity.getSummary());
			extensionEetailsDto
					.setSpecialName(extensionEntity.getSpecialName());
			extensionEetailsDto.setUser(extensionEntity.getUser());
			ActivePluginEntity ActivePluginEntity = PluginManager
					.queryActivePluginByRelationId(context, extensionId);

			QueryPlugin queryPlugin = new QueryPlugin();
			queryPlugin.setRelationId(extensionId);
			queryPlugin.setRelationType(ManuscriptType.EXTENSION);
			queryPlugin.setPluginType(PluginType.FORMPLUGIN);
			queryPlugin.setIsUsed(true);
			Dao pluginDao = this.daoProxyFactory.getDao(context,
					PluginDao.class);
			this.valiDaoIsNull(pluginDao, "用户表单插件信息");
			PluginEntity pluginEntity = (PluginEntity) pluginDao.query(
					queryPlugin, false);
			if (ActivePluginEntity != null
					&& ActivePluginEntity.getActivePluginType() != null) {
				extensionEetailsDto.setHasActive(2);
			} else if (pluginEntity != null) {
				extensionEetailsDto.setHasActive(1);
			} else {
				extensionEetailsDto.setHasActive(-1);
			}
			return extensionEetailsDto;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
