package cn.ilanhai.kem.bl.template;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.notify.NotifyImpl;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.tag.SysTagManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.template.TemplateMabatisDao;
import cn.ilanhai.kem.deploy.Deploy;
import cn.ilanhai.kem.deploy.DeployImpl;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.TemplateState;
import cn.ilanhai.kem.domain.notify.NotifyType;
import cn.ilanhai.kem.domain.template.CreateTemplateDto;
import cn.ilanhai.kem.domain.template.DeleteTemplateDto;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.PublishState;
import cn.ilanhai.kem.domain.template.CollectionTemplateDto;
import cn.ilanhai.kem.domain.template.CopyOrPublishDto;
import cn.ilanhai.kem.domain.template.CopyTemplateDto;
import cn.ilanhai.kem.domain.template.CreateResponseDto;
import cn.ilanhai.kem.domain.template.PublishTemplateDto;
import cn.ilanhai.kem.domain.template.SaveTemplateDto;
import cn.ilanhai.kem.domain.template.LoadTemplateDto;
import cn.ilanhai.kem.domain.template.LoadpublishinfoDto;
import cn.ilanhai.kem.domain.template.LoadpublishinfoResponseDto;
import cn.ilanhai.kem.domain.template.SavepublishinfoDto;
import cn.ilanhai.kem.domain.template.SearchCollectionTemplateDto;
import cn.ilanhai.kem.domain.template.SearchCollectionTemplateEntity;
import cn.ilanhai.kem.domain.template.SearchTemplateDto;
import cn.ilanhai.kem.domain.template.SearchTemplateResponseEntity;
import cn.ilanhai.kem.domain.template.SearchTemplateTagDataEntity;
import cn.ilanhai.kem.domain.template.SearchusedtemplateDto;
import cn.ilanhai.kem.domain.template.SearchusedtemplateEntity;
import cn.ilanhai.kem.domain.template.SearchusedtemplateResponseDto;
import cn.ilanhai.kem.domain.template.ShelfTemplateRequestDto;
import cn.ilanhai.kem.domain.template.TemplateCollectionEntity;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.domain.template.TemplateTagResponseDataEntity;
import cn.ilanhai.kem.domain.template.VerifyTemplateRequestEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.StringVerifyUtil;
import cn.ilanhai.kem.util.TimeUtil;

/**
 * 模板实现
 * 
 * @author he
 *
 */
@Component("template")
public class TemplateImpl extends BaseBl implements Template {
	private static Logger logger = Logger.getLogger(TemplateImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		LoadTemplateDto dto = null;
		TemplateEntity templateEntity = null;
		CodeTable ct;
		Dao dao = null;
		String templateId = null;
		IdEntity<String> queryTemplate = null;
		try {
			templateId = context.getDomain(String.class);
			// this.valiParaItemNumLassThan(templateId, 0, "模板编号");
			this.valiParaItemStrNullOrEmpty(templateId, "模板编号");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			queryTemplate = new IdEntity<String>();
			queryTemplate.setId(templateId);
			// queryTemplate.setUserId(id);
			templateEntity = (TemplateEntity) dao.query(queryTemplate, false);
			this.valiDomainIsNull(templateEntity, "模板");
			dto = new LoadTemplateDto();
			dto.setCreatetime(templateEntity.getCreatetime());
			dto.setTemplateContent(templateEntity.getTemplateContent());
			dto.setTemplateState(templateEntity.getTemplateState());
			dto.setUserId(templateEntity.getUserId());
			SaveTemplateDto result = new SaveTemplateDto();
			result.setTemplateId(templateEntity.getTemplateId());
			result.setData(FastJson.json2Bean(templateEntity.getTemplateContent(), ContextDataDto.class));
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean save(RequestContext context) throws BlAppException, DaoAppException {
		SaveTemplateDto dto = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			dto = context.getDomain(SaveTemplateDto.class);
			this.valiPara(dto);
			TemplateManager.saveTemplate(context, dto);
			return true;
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity create(RequestContext context) throws BlAppException, DaoAppException {
		TemplateEntity templateEntity = null;
		CodeTable ct;
		Dao dao = null;
		CreateTemplateDto createTemplateDto = null;
		String id = null;
		CreateResponseDto createResponseDto = null;
		try {
			this.checkFrontUserLogined(context);
			// 添加 验证设计师
			this.checkFrontUserType(context, UserType.DESIGNERS);
			createTemplateDto = context.getDomain(CreateTemplateDto.class);
			logger.info("name:" + createTemplateDto.getTemplateName());
			this.valiParaItemStrNullOrEmpty(createTemplateDto.getTemplateName(), "模板名称");
			this.valiParaItemNumBetween(0, 20, createTemplateDto.getTemplateName().length(), "模板名称");
			this.valiParaItemObjectNull(createTemplateDto.getTemplateType(), "模板类型");
			this.valiParaItemNumBetween(1, 2, createTemplateDto.getTemplateType(), "模板类型");

			// 校验模版名称合法性
			StringVerifyUtil.templateNameVerify(createTemplateDto.getTemplateName());
			this.valiName(createTemplateDto.getTemplateName(), "模板名称", 20);

			id = this.getSessionUserId(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			/*
			 * pluginDao = this.daoProxyFactory.getDao(context,
			 * PluginDao.class); this.valiDaoIsNull(pluginDao, "用户插件信息");
			 */
			// 根据创建模版请求 dto 生成模版实体
			templateEntity = buildTemplateEntity(createTemplateDto, id);
			// 根据模板实体 生成稿件
			ManuscriptManager.createManuscript(context, templateEntity);

			createResponseDto = new CreateResponseDto();
			createResponseDto.setTemplateId(templateEntity.getTemplateId());
			createResponseDto.setTemplateType(templateEntity.getTemplateType());
			return createResponseDto;

		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 根据创建模版请求 dto 生成模版实体
	 * 
	 * @param createTemplateDto
	 * @param id
	 * @return
	 */
	private TemplateEntity buildTemplateEntity(CreateTemplateDto createTemplateDto, String id) {
		TemplateEntity templateEntity;
		templateEntity = new TemplateEntity();
		templateEntity.setTemplateId(KeyFactory.newKey(KeyFactory.KEY_TEMPLATE));
		templateEntity.setCoverImg("");
		templateEntity.setCreatetime(new java.sql.Date(System.currentTimeMillis()));
		templateEntity.setMainColor("");
		templateEntity.setSummary("");
		templateEntity.setTemplateContent("");
		templateEntity.setTemplateName(createTemplateDto.getTemplateName());
		templateEntity.setTemplateType(createTemplateDto.getTemplateType());
		templateEntity.setTemplateState(TemplateState.UNSUBMITTED);
		templateEntity.setUserId(id);
		return templateEntity;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String copy(RequestContext context) throws BlAppException, DaoAppException {
		TemplateEntity templateEntity = null;
		CodeTable ct;
		Dao dao = null;
		CopyTemplateDto copyTemplateDto = null;
		String id = null;
		IdEntity<String> idDto = null;
		try {

			this.checkFrontUserLogined(context);
			copyTemplateDto = context.getDomain(CopyTemplateDto.class);
			this.valiPara(copyTemplateDto);
			// this.valiParaItemNumLassThan(copyOrPublishDto.getTemplateId(), 0,
			// "模板编号");
			this.valiParaItemStrNullOrEmpty(copyTemplateDto.getTemplateId(), "模板编号");
			id = this.getSessionUserId(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");

			idDto = new IdEntity<String>();
			idDto.setId(copyTemplateDto.getTemplateId());
			templateEntity = (TemplateEntity) dao.query(idDto, false);
			this.valiDomainIsNull(templateEntity, "模板");
			if (!this.getSessionUserId(context).equals(templateEntity.getUserId())
					&& !templateEntity.getTemplateState().equals(TemplateState.ADDED)) {
				throw new BlAppException(-1, "模板已经下架");
			}
			this.valiDomainIsNull(templateEntity, "模板");
			LoadpublishinfoDto loadpublishinfoDto = new LoadpublishinfoDto();
			loadpublishinfoDto.setTemplateId(templateEntity.getTemplateId());
			LoadpublishinfoResponseDto dto = (LoadpublishinfoResponseDto) dao.query(loadpublishinfoDto, false);
			this.valiDomainIsNull(dto, "模板发布设置");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			templateEntity.setTemplateId(KeyFactory.newKey(KeyFactory.KEY_TEMPLATE));
			if (!Str.isNullOrEmpty(copyTemplateDto.getTemplateName())) {
				templateEntity.setTemplateName(copyTemplateDto.getTemplateName());
				this.valiParaItemNumBetween(0, 20, copyTemplateDto.getTemplateName().length(), "模板名称");
			} else if (!Str.isNullOrEmpty(templateEntity.getVerifyName())) {
				templateEntity.setTemplateName(templateEntity.getVerifyName());
			}
			templateEntity.setCreatetime(new java.sql.Date(System.currentTimeMillis()));
			templateEntity.setTemplateState(TemplateState.UNSUBMITTED);

			templateEntity.setUserId(id);
			templateEntity.setCoverImg(dto.getCoverImg());
			templateEntity.setSummary("");
			templateEntity.setMainColor("");
			// 保存模板
			ManuscriptManager.createManuscript(context, templateEntity);
			ManuscriptManager.saveManscriptContent(context, templateEntity.getTemplateId(),
					templateEntity.getTemplateContent());
			// 添加插件复制
			PluginManager.copyPlugin(context, copyTemplateDto.getTemplateId(), templateEntity.getTemplateId(),
					ManuscriptType.TEMPLATE, ManuscriptType.TEMPLATE);
			// 添加使用记录
			TemplateManager.saveTemplateUseLog(context, copyTemplateDto.getTemplateId());
			return templateEntity.getTemplateId();
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity publish(RequestContext context) throws BlAppException, DaoAppException {
		PublishTemplateDto dto = null;
		TemplateEntity templateEntity = null;
		CodeTable ct;
		Dao dao = null;
		IdEntity<String> idDto = null;
		CopyOrPublishDto copyOrPublishDto = null;
		try {
			this.checkFrontUserLogined(context);
			// 设计师校验
			this.checkFrontUserType(context, UserType.DESIGNERS);
			copyOrPublishDto = context.getDomain(CopyOrPublishDto.class);
			this.valiPara(copyOrPublishDto);
			this.valiParaItemBooleanNull(copyOrPublishDto.isEditor(), "是否来自编辑界面发布判断不能为空");
			if (copyOrPublishDto.isEditor()) {
				this.valiDomainIsNull(copyOrPublishDto.getData(), "编辑界面的数据不能为空");
				if (PluginType.ACTIVEPLUGIN.getValue().equals(copyOrPublishDto.getPluginType())) {
					this.valiParaItemObjectNull(copyOrPublishDto.getActiveType(), "当插件类型时活动类型不能为空");
				}
				if (checkCreatePlugin(copyOrPublishDto.getPluginType())) {
					PluginManager.creatPlugin(context, copyOrPublishDto.getTemplateId(), ManuscriptType.TEMPLATE,
							PluginType.getEnum(copyOrPublishDto.getPluginType()),
							ActivePluginType.getEnum(copyOrPublishDto.getActiveType()));
				} else {
					PluginManager.disableAllPlugin(context, copyOrPublishDto.getTemplateId());
				}
			}
			// this.valiParaItemNumLassThan(copyOrPublishDto.getTemplateId(), 0,
			// "模板编号");
			this.valiParaItemStrNullOrEmpty(copyOrPublishDto.getTemplateId(), "模板编号");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			idDto = new IdEntity<String>();
			idDto.setId(copyOrPublishDto.getTemplateId());
			templateEntity = (TemplateEntity) dao.query(idDto, false);
			this.valiDomainIsNull(templateEntity, "模板");
			// 校验是否为自己的模版
			if (!this.getSessionUserId(context).equals(templateEntity.getUserId())) {
				ct = CodeTable.BL_UNHANDLED_EXCEPTION;
				throw new BlAppException(ct.getValue(), "只能发布自己的模板");
			}

			// 校验是否可发布
			if (PublishState.CANNOTPUBLISH.getValue().equals(templateEntity.getPublish_state())) {
				ct = CodeTable.BL_COMMON_PUBLISH_STATUS;
				throw new BlAppException(ct.getValue(), "您还没有进行发布设置，请先设置发布信息再发布模板！");
			}
			checkTemplateState(templateEntity);
			templateEntity.setTemplateState(TemplateState.VERIFY);
			if (copyOrPublishDto.isEditor()) {
				String content = JSON.toJSONString(copyOrPublishDto.getData());
				templateEntity.setTemplateContent(content);
				// 保存更新内容
				ManuscriptManager.saveManscriptContent(context, copyOrPublishDto.getTemplateId(), content);
			}
			String jsonStr = templateEntity.getTemplateContent();
			if (jsonStr == null || jsonStr.length() <= 0)
				throw new BlAppException(-1, "模版未保存编辑数据");

			// 保存状态
			ManuscriptManager.saveManuscript(context, templateEntity);

			// 发布时间
			// templateEntity.setVerifytime(new
			// java.sql.Date(System.currentTimeMillis()));

			// 保存发布时间
			ManuscriptManager.saveManscriptParameter(context, copyOrPublishDto.getTemplateId(),
					TimeUtil.format(new java.sql.Date(System.currentTimeMillis())), ManuscriptParameterType.verifytime);

			// val = dao.save(templateEntity);
			// this.valiSaveDomain(val, "模板");
			dto = new PublishTemplateDto();
			dto.setCreatetime(new java.util.Date(templateEntity.getCreatetime().getTime()));
			dto.setTemplateContent(templateEntity.getTemplateContent());
			dto.setTemplateState(templateEntity.getTemplateState().getValue());
			dto.setUserId(templateEntity.getUserId());
			return dto;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("search");
		SearchTemplateResponseEntity response = null;
		CodeTable ct;
		Dao dao = null;
		boolean isbackmanager = false;
		SearchTemplateDto request = null;
		try {
			// 不查检登录
			request = context.getDomain(SearchTemplateDto.class);
			request.setRand(false);
			valiPara(request);
			valiParaItemBooleanNull(request.getIsCurrentUser(), "模版是否是当前用户");
			valiParaNotNull(request.getStartCount(), "开始条数");
			valiParaNotNull(request.getPageSize(), "查询条数");

			// 如果是后台用户已登录
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				// 设置搜索条件，后台用户为true
				request.setBackUser(true);
				// 此时，搜索条件不能带有是当前用户的请求
				if (request.getIsCurrentUser()) {
					ct = CodeTable.BL_UNHANDLED_EXCEPTION;
					throw new BlAppException(ct.getValue(), "后台用户无法查看当前用户模版");
				}
				// 如果用户ID为空，则模板类型不可以为空
				if (request.getUserId() == null) {
					valiParaNotNull(request.getTemplateState(), "模版类型");
				} else if (request.getTemplateState() == null || request.getTemplateState() == 0) {
					// 如果模板状态为null或者为0，只能查询已上架模版，将是否是管理员查询标志位设置为true
					isbackmanager = true;
				}
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				// 如果是前端用户已登录

				String userId = (String) context.getSession().getParameter(Constant.KEY_SESSION_USERID);
				// 获取当前用户
				if (request.getIsCurrentUser()) {
					request.setUserId(userId);
					// 验证是否为设计师
					checkFrontUserType(context, UserType.DESIGNERS);
				} else {
					// 只能查询已上架模版
					request.setTemplateState(TemplateState.ADDED.getValue());
				}
				request.setCurrentLoginUserId(userId);
			} else {
				request.setTemplateState(TemplateState.ADDED.getValue());
			}

			// 后台管理员登录查询逻辑
			if (isbackmanager) {
				dao = this.daoProxyFactory.getDao(context, TemplateMabatisDao.class);
				this.valiDaoIsNull(dao, "模板");
				List<Entity> datas = transformation(dao.query(request));
				IdEntity<Integer> idEntity = (IdEntity<Integer>) dao.query(request, false);
				response = new SearchTemplateResponseEntity();
				response.setList(datas);
				response.setPageSize(request.getPageSize());
				response.setStartCount(request.getStartCount());
				response.setTotalCount(idEntity.getId());
			}
			// 否则，为前台用户查询逻辑
			else {
				logger.info("原流程");
				dao = this.daoProxyFactory.getDao(context);
				this.valiDaoIsNull(dao, "模板");
				List<Entity> datas = transformation(dao.query(request));
				CountDto count = (CountDto) dao.query(request, false);
				response = new SearchTemplateResponseEntity();
				response.setList(datas);
				response.setPageSize(request.getPageSize());
				response.setStartCount(request.getStartCount());
				response.setTotalCount(count.getCount());

			}
			logger.info(JSON.toJSON(response).toString());
			return response;

		} catch (

		BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchbyrand(RequestContext context) throws BlAppException, DaoAppException {
		SearchTemplateResponseEntity response = null;
		CodeTable ct;
		Dao dao = null;
		SearchTemplateDto request = null;
		try {
			// 不查检登录
			request = context.getDomain(SearchTemplateDto.class);
			request.setRand(true);
			valiPara(request);
			valiParaNotNull(request.getPageSize(), "查询条数");

			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				// 如果是前端用户已登录
				String userId = (String) context.getSession().getParameter(Constant.KEY_SESSION_USERID);
				// 只能查询已上架模版
				request.setTemplateState(TemplateState.ADDED.getValue());
				request.setCurrentLoginUserId(userId);
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.ANONYMOUS_SESSION_STATE)) {
				request.setTemplateState(TemplateState.ADDED.getValue());
			} else {
				// 否则，报错，用户未登录
				throw new BlAppException(-1, "用户状态错误");
			}

			dao = this.daoProxyFactory.getDao(context, TemplateMabatisDao.class);
			this.valiDaoIsNull(dao, "模板");
			List<Entity> datas = transformation(dao.query(request));
			IdEntity<Integer> idEntity = (IdEntity<Integer>) dao.query(request, false);
			response = new SearchTemplateResponseEntity();
			response.setList(datas);
			response.setPageSize(request.getPageSize());
			response.setTotalCount(idEntity.getId());

			return response;

		} catch (

		BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void verify(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		VerifyTemplateRequestEntity request = null;
		IdEntity<String> idDto = null;
		NotifyImpl notifyImpl = null;
		String content = null;
		String currUserId = "";
		try {
			// 没有查检登录
			this.checkBackUserLogined(context);
			currUserId = this.getSessionUserId(context);
			request = context.getDomain(VerifyTemplateRequestEntity.class);
			valiPara(request);
			valiParaItemBooleanNull(request.getIsOK(), "是否通过");
			valiParaNotNull(request.getTemplateId(), "模版ID");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			idDto = new IdEntity<String>();
			idDto.setId(request.getTemplateId());
			TemplateEntity entity = (TemplateEntity) dao.query(idDto, true);

			if (!(TemplateState.VERIFY.equals(entity.getTemplateState())
					|| TemplateState.SHELF.equals(entity.getTemplateState()))) {
				throw new BlAppException(-1, "该模版非审核中或下架状态状态");
			}
			if (request.getIsOK()) {
				List<String> tags = transformationString(request.getTags(), true);
				entity.setTemplateState(TemplateState.ADDED);
				this.valiName(request.getVerifyName(), "模板上架名称", 20);

				// 保存模版状态 上架
				ManuscriptManager.saveManuscript(context, entity);

				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(),
						TimeUtil.format(new java.sql.Date(System.currentTimeMillis())),
						ManuscriptParameterType.shelftime);
				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(), request.getVerifyName(),
						ManuscriptParameterType.verifyName);
				// 保存模板参数
				ManuscriptManager.saveManscriptParameters(context, request.getTemplateId(), tags,
						ManuscriptParameterType.systag);

				content = "恭喜您！您的%s模板已被录入到模板中心。";
				content = String.format(content, entity.getTemplateName());
				notifyImpl = new NotifyImpl();
				if (!notifyImpl.create(context, currUserId, entity.getUserId(), content, NotifyType.Sys))
					throw new BlAppException(-1, "创建通知错误");
				SysTagManager.quoteNumAdd(context, tags);

			} else {
				this.valiSummary(request.getBouncedReason(), "模板退回原因", 150);
				entity.setTemplateState(TemplateState.BOUNCED);
				entity.setBouncedReason(request.getBouncedReason());

				ManuscriptManager.saveManuscript(context, entity);
				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(), request.getBouncedReason(),
						ManuscriptParameterType.bouncedReason);

				content = "很抱歉，您申请上架的%s模板未能通过审核，（%s），请修改后重新提交。";
				content = String.format(content, entity.getTemplateName(), request.getBouncedReason());
				notifyImpl = new NotifyImpl();
				if (!notifyImpl.create(context, currUserId, entity.getUserId(), content, NotifyType.Sys))
					throw new BlAppException(-1, "创建通知错误");
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void shelf(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		ShelfTemplateRequestDto request = null;
		IdEntity<String> idDto = null;
		NotifyImpl notifyImpl = null;
		String currUserId = "";
		String content = null;
		try {
			// 没有查检登录
			this.checkBackUserLogined(context);
			currUserId = this.getSessionUserId(context);
			request = context.getDomain(ShelfTemplateRequestDto.class);
			valiPara(request);
			valiParaNotNull(request.getTemplateId(), "模版ID");
			valiParaNotNull(request.getIsShelf(), "是否上架/下架参数不能为空");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			idDto = new IdEntity<String>();
			idDto.setId(request.getTemplateId());
			TemplateEntity entity = (TemplateEntity) dao.query(idDto, true);
			if (request.getIsShelf()) {
				if (!TemplateState.ADDED.equals(entity.getTemplateState())) {
					throw new BlAppException(-1, "该模版非上架状态");
				}
				this.valiSummary(request.getShelfReason(), "模板下架原因", 150);

				// 保存模版状态 下架
				entity.setTemplateState(TemplateState.SHELF);
				ManuscriptManager.saveManuscript(context, entity);

				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(),
						TimeUtil.format(new java.sql.Date(System.currentTimeMillis())),
						ManuscriptParameterType.shelftime);
				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(), request.getShelfReason(),
						ManuscriptParameterType.bouncedReason);

				notifyImpl = new NotifyImpl();
				content = "您的%s模板已从模板中心下架。";
				content = String.format(content, entity.getTemplateName());
				if (!notifyImpl.create(context, currUserId, entity.getUserId(), content, NotifyType.Sys))
					throw new BlAppException(-1, "创建通知错误");
			} else {
				if (!TemplateState.SHELF.equals(entity.getTemplateState())) {
					throw new BlAppException(-1, "该模版非下架状态");
				}

				List<String> tags = transformationString(request.getTags(), true);

				// 保存模版状态 下架
				entity.setTemplateState(TemplateState.ADDED);
				ManuscriptManager.saveManuscript(context, entity);

				// 保存模板参数
				ManuscriptManager.saveManscriptParameter(context, request.getTemplateId(),
						TimeUtil.format(new java.sql.Date(System.currentTimeMillis())),
						ManuscriptParameterType.verifytime);
				// 保存模板参数
				ManuscriptManager.saveManscriptParameters(context, request.getTemplateId(), tags,
						ManuscriptParameterType.systag);
				SysTagManager.quoteNumAdd(context, tags);

				content = "恭喜您！您的%s模板已被录入到模板中心。";
				content = String.format(content, entity.getTemplateName());
				notifyImpl = new NotifyImpl();
				if (!notifyImpl.create(context, currUserId, entity.getUserId(), content, NotifyType.Sys))
					throw new BlAppException(-1, "创建通知错误");
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadtags(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			Integer tagType = context.getDomain(Integer.class);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			SearchTemplateTagDataEntity searchTemplateTagDataEntity = new SearchTemplateTagDataEntity();

			this.checkFrontUserLogined(context);
			this.checkFrontUserType(context, UserType.DESIGNERS);
			searchTemplateTagDataEntity.setUserId(this.getSessionUserId(context));
			if (tagType == null) {
				tagType = 0;
			}
			searchTemplateTagDataEntity.setTag_type(tagType);
			// 设置userId的值
			// if (context.getSession().getSessionState().getSessionStateType()
			// .equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			// searchTemplateTagDataEntity.setUserId(0);
			// } else if
			// (context.getSession().getSessionState().getSessionStateType()
			// .equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			// } else {
			// ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			// throw new BlAppException(ct.getValue(), ct.getDesc());
			// }
			// 查询数据
			List<Entity> datas = transformation(dao.query(searchTemplateTagDataEntity));
			return new TemplateTagResponseDataEntity(datas);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void savepublishinfo(RequestContext context) throws BlAppException, DaoAppException {
		TemplateEntity templateEntity = null;
		CodeTable ct;
		Dao dao = null;
		IdEntity<String> idDto = null;
		SavepublishinfoDto savepublishinfoDto = null;
		try {
			this.checkFrontUserLogined(context);
			// 设计师校验
			this.checkFrontUserType(context, UserType.DESIGNERS);
			savepublishinfoDto = context.getDomain(SavepublishinfoDto.class);
			this.valiPara(savepublishinfoDto);
			this.valiParaItemStrNullOrEmpty(savepublishinfoDto.getTemplateId(), "模板编号");
			this.valiName(savepublishinfoDto.getPublishName(), "模板名称", 20);
			this.valiSummary(savepublishinfoDto.getSummary(), "作品描述", 150, false);
			this.valiParaItemStrNullOrEmpty(savepublishinfoDto.getCoverImg(), "请为您的作品上传封面哦", false);
			// this.valiParaItemStrNullOrEmpty(savepublishinfoDto.getMainColor(),
			// "请选择与稿件颜色相同相近的颜色哦", false);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			idDto = new IdEntity<String>();
			idDto.setId(savepublishinfoDto.getTemplateId());
			templateEntity = (TemplateEntity) dao.query(idDto, false);
			this.valiDomainIsNull(templateEntity, "模板");

			this.checkCurrentUser(context, templateEntity.getUserId());

			checkTemplateState(templateEntity);
			templateEntity.setPublish_state(PublishState.CANPUBLISH.getValue());

			// 保存发布设置参数
			ManuscriptManager.saveManscriptParameter(context, savepublishinfoDto.getTemplateId(),
					PublishState.CANPUBLISH.getValue() + "", ManuscriptParameterType.publishstate);
			// 保存发布名称
			ManuscriptManager.saveManscriptParameter(context, savepublishinfoDto.getTemplateId(),
					savepublishinfoDto.getPublishName(), ManuscriptParameterType.publishname);
			// 保存标签
			ManuscriptManager.saveManscriptParameters(context, savepublishinfoDto.getTemplateId(),
					this.transformationTags(savepublishinfoDto.getTagNames()), ManuscriptParameterType.tag);
			// 保存关键字
			// ManuscriptManager.saveManscriptParameters(context,
			// savepublishinfoDto.getTemplateId(),
			// this.transformationString(savepublishinfoDto.getKeyword(),
			// false), ManuscriptParameterType.keyword);

			// 保存 封面
			ManuscriptManager.saveManscriptParameter(context, savepublishinfoDto.getTemplateId(),
					savepublishinfoDto.getCoverImg(), ManuscriptParameterType.manuscriptImg);
			// // 保存 主色调
			// ManuscriptManager.saveManscriptParameter(context,
			// savepublishinfoDto.getTemplateId(),
			// savepublishinfoDto.getMainColor(),
			// ManuscriptParameterType.manuscriptMainColor);

			// 保存名称
			ManuscriptManager.saveManscriptParameter(context, savepublishinfoDto.getTemplateId(),
					savepublishinfoDto.getPublishName(), ManuscriptParameterType.manuscriptName);

			// 保存简介
			ManuscriptManager.saveManscriptParameter(context, savepublishinfoDto.getTemplateId(),
					savepublishinfoDto.getSummary(), ManuscriptParameterType.manuscriptSummary);
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

	private void checkTemplateState(TemplateEntity templateEntity) throws BlAppException {
		CodeTable ct;
		if (templateEntity.getTemplateState().equals(TemplateState.VERIFY)
				|| templateEntity.getTemplateState().equals(TemplateState.ADDED)) {
			ct = CodeTable.BL_TEMPLATE_SAVE_STATUS;
			throw new BlAppException(ct.getValue(), "审核中和已上架模版不允许该操作");
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchusedtemplate(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			this.checkFrontUserLogined(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			SearchusedtemplateDto searchusedtemplateDto = context.getDomain(SearchusedtemplateDto.class);
			SearchusedtemplateEntity searchusedtemplateEntity = new SearchusedtemplateEntity();
			// 设置查询条件
			searchusedtemplateEntity.setUserId(this.getSessionUserId(context));
			searchusedtemplateEntity.setPageSize(searchusedtemplateDto.getPageSize());
			searchusedtemplateEntity.setStartCount(searchusedtemplateDto.getStartCount());
			searchusedtemplateEntity.setTemplateType(searchusedtemplateDto.getTemplateType());
			// 查询数据
			List<Entity> datas = transformation(dao.query(searchusedtemplateEntity));
			CountDto count = (CountDto) dao.query(searchusedtemplateEntity, false);
			// 返回结果
			SearchusedtemplateResponseDto reslut = new SearchusedtemplateResponseDto(datas);
			reslut.setPageSize(searchusedtemplateDto.getPageSize());
			reslut.setStartCount(searchusedtemplateDto.getStartCount());
			reslut.setTotalCount(count.getCount());
			return reslut;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchCollection(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			this.checkFrontUserLogined(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			SearchCollectionTemplateDto searchusedtemplateDto = context.getDomain(SearchCollectionTemplateDto.class);
			SearchCollectionTemplateEntity searchusedtemplateEntity = new SearchCollectionTemplateEntity();
			// 设置查询条件
			searchusedtemplateEntity.setUserId(this.getSessionUserId(context));
			searchusedtemplateEntity.setPageSize(searchusedtemplateDto.getPageSize());
			searchusedtemplateEntity.setStartCount(searchusedtemplateDto.getStartCount());
			searchusedtemplateEntity.setTemplateType(searchusedtemplateDto.getTemplateType());
			searchusedtemplateEntity.setTemplateName(searchusedtemplateDto.getTemplateName());
			// 查询数据
			List<Entity> datas = transformation(dao.query(searchusedtemplateEntity));
			CountDto count = (CountDto) dao.query(searchusedtemplateEntity, false);
			// 返回结果
			SearchusedtemplateResponseDto reslut = new SearchusedtemplateResponseDto(datas);
			reslut.setPageSize(searchusedtemplateDto.getPageSize());
			reslut.setStartCount(searchusedtemplateDto.getStartCount());
			reslut.setTotalCount(count.getCount());
			return reslut;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadpublishinfo(RequestContext context) throws BlAppException, DaoAppException {
		LoadpublishinfoResponseDto dto = null;
		CodeTable ct;
		Dao dao = null;
		LoadpublishinfoDto loadpublishinfoDto = null;
		String templateId = null;
		try {
			// this.checkFrontUserLogined(context);
			// // 设计师校验
			// this.checkFrontUserType(context, UserType.DESIGNERS);
			templateId = context.getDomain(String.class);
			this.valiParaNotNull(templateId, "加载模版");
			loadpublishinfoDto = new LoadpublishinfoDto();
			loadpublishinfoDto.setTemplateId(templateId);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			dto = (LoadpublishinfoResponseDto) dao.query(loadpublishinfoDto, false);
			this.valiDomainIsNull(dto, "模版编号所属模版对象");
			return dto;
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		DeleteTemplateDto dto = null;
		CodeTable ct;
		Dao dao = null;
		IdEntity<String> id = null;
		String msg = null;
		NotifyImpl notifyImpl = null;
		String currentUserId = null;
		try {
			currentUserId = this.getSessionUserId(context);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			dto = context.getDomain(DeleteTemplateDto.class);
			this.valiParaNotNull(dto, "需要删除的模版");
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				for (String templateId : dto.getTemplateIds()) {
					TemplateEntity templateEntity = TemplateManager.getTemplateEntity(templateId, context);
					if (templateEntity == null)
						continue;
					msg = "您设计中的模板%s已被后台管理员删除";
					msg = String.format(msg, templateEntity.getTemplateName());
					notifyImpl = new NotifyImpl();
					if (!notifyImpl.create(context, currentUserId, templateEntity.getUserId(), msg, NotifyType.Sys))
						throw new BlAppException(-1, "创建通知错误");
					ManuscriptManager.delete(context, templateId);

				}
				return;
			}
			this.checkFrontUserLogined(context);
			// 设计师校验
			this.checkFrontUserType(context, UserType.DESIGNERS);
			id = new IdEntity<String>();
			for (String templateId : dto.getTemplateIds()) {
				id.setId(templateId);
				TemplateEntity templateEntity = TemplateManager.getTemplateEntity(templateId, context);
				if (templateEntity == null) {
					continue;
				}
				this.checkCurrentUser(context, templateEntity.getUserId());
				ManuscriptManager.delete(context, templateId);

			}

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
	public String preview(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Deploy deploy = null;
		try {
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			deploy = new DeployImpl();
			if (!deploy.deploy(false, context, ManuscriptType.TEMPLATE))
				throw new BlAppException(-1, "预览失败");
			return deploy.getBrowerUrl();
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void collection(RequestContext context) throws BlAppException, DaoAppException {
		CollectionTemplateDto collectionTemplateDto = null;
		CodeTable ct;
		String id = null;
		Dao dao = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			collectionTemplateDto = context.getDomain(CollectionTemplateDto.class);
			valiParaNotNull(collectionTemplateDto, "收藏模版");
			this.valiParaItemStrNullOrEmpty(collectionTemplateDto.getTemplateId(), "模版编号");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			id = this.getSessionUserId(context);
			IdEntity<String> templatIdEntity = new IdEntity<String>();
			templatIdEntity.setId(collectionTemplateDto.getTemplateId());
			TemplateEntity templateEntity = (TemplateEntity) dao.query(templatIdEntity, false);
			TemplateCollectionEntity templateCollectionEntity = new TemplateCollectionEntity();
			if (templateEntity != null) {
				if (!TemplateState.ADDED.equals(templateEntity.getTemplateState())) {
					throw new BlAppException(-1, "只能收藏已上架模版");
				}
				templateCollectionEntity.setManuscriptType(ManuscriptType.TEMPLATE.getValue());
			} else {
				if (ManuscriptManager.getManuscriptById(context, collectionTemplateDto.getTemplateId()) != null) {
					templateCollectionEntity.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());
				} else {
					ct = CodeTable.BL_COMMON_GET_DOAMIN;
					String tmp = ct.getDesc();
					tmp = String.format(tmp, "稿件编号错误");
					throw new BlAppException(ct.getValue(), tmp);
				}
			}
			templateCollectionEntity.setCollectionState(
					collectionTemplateDto.getIsCollection() == null ? 0 : collectionTemplateDto.getIsCollection());
			templateCollectionEntity.setUserId(id);
			templateCollectionEntity.setTemplateId(collectionTemplateDto.getTemplateId());
			templateCollectionEntity.setCreatetime(new Date());
			int val = dao.save(templateCollectionEntity);
			// 校验保存结果
			this.valiSaveDomain(val, "模版收藏");
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
