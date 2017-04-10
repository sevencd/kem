package cn.ilanhai.kem.bl.manuscript.impl;

import java.text.ParseException;
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
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.enums.ClientTypes;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.manuscript.Manuscript;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.notify.NotifyImpl;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.rights.UnRightsManger;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.bl.template.TemplateManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.manuscript.ManuscriptDao;
import cn.ilanhai.kem.dao.special.SpecialDao;
import cn.ilanhai.kem.deploy.Deploy;
import cn.ilanhai.kem.deploy.DeployImpl;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.MapDto;
import cn.ilanhai.kem.domain.SearchConfigDataDto;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptState;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PublishState;
import cn.ilanhai.kem.domain.enums.SpecialState;
import cn.ilanhai.kem.domain.enums.TemplateState;
import cn.ilanhai.kem.domain.enums.TerminalType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptCollectionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptContentEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.manuscript.dto.CollectionDto;
import cn.ilanhai.kem.domain.manuscript.dto.CopyManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.CreateManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.CreateManuscriptResponseDto;
import cn.ilanhai.kem.domain.manuscript.dto.CreateResponseDto;
import cn.ilanhai.kem.domain.manuscript.dto.DeleteManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SaveManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SaveManuscriptNameDto;
import cn.ilanhai.kem.domain.manuscript.dto.SavepublishinfoDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchBackManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchFrontManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptCollectionDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchManuscriptResultDto;
import cn.ilanhai.kem.domain.manuscript.dto.SearchTagsDto;
import cn.ilanhai.kem.domain.manuscript.special.SpecialPublishinfoDto;
import cn.ilanhai.kem.domain.manuscript.template.TemplatePublishInfoDto;
import cn.ilanhai.kem.domain.notify.NotifyType;
import cn.ilanhai.kem.domain.rights.UnRightsTimesEntity;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;
import cn.ilanhai.kem.domain.special.SaveSpecialDto;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.domain.template.SaveTemplateDto;
import cn.ilanhai.kem.domain.template.SearchusedtemplateResponseDto;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.KeyUtil;
import cn.ilanhai.kem.util.TimeUtil;

@Component("manuscript")
public class ManuscriptImpl extends BaseBl implements Manuscript {
	Logger logger = Logger.getLogger(ManuscriptImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		ManuscriptEntity manuscriptEntity = null;
		CodeTable ct;
		String manuscriptId = null;
		try {
			manuscriptId = context.getDomain(String.class);

			this.valiParaItemStrNullOrEmpty(manuscriptId, "稿件编号");
			manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
			this.valiDomainIsNull(manuscriptEntity, "稿件");
			SaveManuscriptDto result = new SaveManuscriptDto();
			result.setManuscriptId(manuscriptId);
			result.setData(
					FastJson.json2Bean(manuscriptEntity.getManuscriptContent().getContent(), ContextDataDto.class));
			return result;
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
	public Entity searchmanuscript(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {

			// 获取请求数据
			SearchManuscriptDto searchManuscriptDto = context.getDomain(SearchManuscriptDto.class);

			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				String userId = (String) context.getSession().getParameter(Constant.KEY_SESSION_USERID);
				searchManuscriptDto.setUserId(userId);
			}
			this.valiParaItemObjectNull(searchManuscriptDto.getTerminalType(), "终端类型");
			this.checkLimit(searchManuscriptDto.getStartCount(), searchManuscriptDto.getPageSize());
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context, ManuscriptDao.class);
			this.valiDaoIsNull(dao, "优秀案例");
			SearchManuscriptResultDto searchManuscriptResultDto = new SearchManuscriptResultDto();
			searchManuscriptResultDto.setList(dao.query(searchManuscriptDto));
			searchManuscriptResultDto.setTotalCount(((CountDto) dao.query(searchManuscriptDto, false)).getCount());
			searchManuscriptResultDto.setStartCount(searchManuscriptDto.getStartCount());
			searchManuscriptResultDto.setPageSize(searchManuscriptDto.getPageSize());
			return searchManuscriptResultDto;
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
	public Entity create(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			BLContextUtil.checkUserLogined(context);
			// 获取请求数据
			CreateManuscriptDto createManuscriptDto = context.getDomain(CreateManuscriptDto.class);
			logger.info("入参:" + createManuscriptDto);
			this.valiPara(createManuscriptDto);
			ManuscriptType manuscriptType = createManuscriptDto.getManuscriptType();
			this.valiParaNotNull(manuscriptType, "稿件类型[manuscriptType]错误 1:模板 2:专题 3:推广 4:优秀案例");
			MapDto options = createManuscriptDto.getOptions();
			this.valiParaNotNull(options, "稿件参数[options]错误,请参考稿件创建接口文档谢谢合作");
			switch (manuscriptType) {
			case EXCELLENTCASE:
				return createCase(context, options);
			case TEMPLATE:
				return createTemplate(context, manuscriptType, options);
			case SPECIAL:
				return createSpecial(context, manuscriptType, options);
			default:
				throw new BlAppException(-1, "系统暂不支持其他类型的创建谢谢合作,暂只支持1:模板 2:专题  4:优秀案例");
			}
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 创建模版 普通用户登录 manuscriptId 源稿件id manuscriptName 专题名称
	 * 
	 * @param context
	 * @param manuscriptType
	 * @param options
	 * @return
	 * @throws SessionContainerException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private Entity createSpecial(RequestContext context, ManuscriptType manuscriptType, MapDto options)
			throws SessionContainerException, BlAppException, DaoAppException {
		// 验证普通用户
		this.checkFrontUserType(context, UserType.GENERAL_USER);
		String relationId = (String) options.get("manuscriptId");
		String name = (String) options.get("manuscriptName");
		Integer integerTerminalType = (Integer) options.get("terminalType");
		String keyHead = "";
		String specialId = "";
		if (Str.isNullOrEmpty(relationId)) {
			TerminalType terminalType = TerminalType.getEnum(integerTerminalType);
			this.valiParaNotNull(terminalType, "稿件终端类型[terminalType]错误,请参考稿件创建接口文档谢谢合作");
			specialId = ManuscriptManager.createManuscript(context, getSessionUserId(context), manuscriptType,
					terminalType);
			this.valiName(name, "专题名称", 20);
		} else {
			keyHead = KeyFactory.getKeyHeadByKey(relationId);
			if (!KeyFactory.KEY_TEMPLATE.equals(keyHead) && !KeyFactory.KEY_MANUSCRIPT.equals(keyHead)) {
				throw new BlAppException(22, "目前只支持模板和优秀案例创建专题操作谢谢合作,请检查[manuscriptId]是否正确");
			}
			ManuscriptEntity templateManuscript = ManuscriptManager.getManuscriptById(context, relationId);
			BLContextUtil.valiDomainIsNull(templateManuscript, KeyUtil.getName(manuscriptType));

			if (!ManuscriptState.ADDED.getValue().equals(templateManuscript.getManuscriptState())) {
				throw new BlAppException(CodeTable.BL_TEMPLATE_STATE_ERROR.getValue(), "模板已经下架");
			}
			specialId = ManuscriptManager.createManuscript(context, getSessionUserId(context), manuscriptType,
					TerminalType.getEnum(templateManuscript.getTerminalType()));
			if (Str.isNullOrEmpty(name)) {
				name = templateManuscript.getManuscriptParameterByType(ManuscriptParameterType.verifyName);
			}
			integerTerminalType = templateManuscript.getTerminalType();
			// 添加内容
			ManuscriptManager.saveManscriptContent(context, specialId,
					templateManuscript.getManuscriptContent().getContent());
			// 保存 封面
			String manuscriptImg = templateManuscript
					.getManuscriptParameterByType(ManuscriptParameterType.manuscriptImg);
			ManuscriptManager.saveManscriptParameter(context, specialId, manuscriptImg,
					ManuscriptParameterType.manuscriptImg);

			ManuscriptParameterType resouse = null;
			if (KeyFactory.KEY_TEMPLATE.equals(keyHead)) {
				// 从模板创建
				resouse = ManuscriptParameterType.templatesource;
			} else if (KeyFactory.KEY_MANUSCRIPT.equals(keyHead)) {
				resouse = ManuscriptParameterType.casesource;
			}
			// 添加来源
			ManuscriptManager.saveManscriptParameter(context, specialId, relationId, resouse);

			// 复制插件
			PluginManager.copyPlugin(context, relationId, specialId, KeyUtil.getKey(relationId),
					ManuscriptType.SPECIAL);
			// 保存使用记录
			ManuscriptManager.saveUseLog(context, relationId);
			// 保存使用记录
			TemplateManager.saveTemplateUseLog(context, relationId);
		}
		// 保存名称
		ManuscriptManager.saveManscriptParameter(context, specialId, name, ManuscriptParameterType.manuscriptName);

		// 保存发布名称
		ManuscriptManager.saveManscriptParameter(context, specialId, name, ManuscriptParameterType.publishname);

		// ------ 后期可以删除 start
		// // 创建连接实体
		ModelConfigEntity modelConfig = new ModelConfigEntity();
		modelConfig.setModelId(specialId);
		modelConfig.setModelType(ManuscriptType.SPECIAL.getValue());
		this.daoProxyFactory.getDao(context, SpecialDao.class).save(modelConfig);
		// 保存连接实体参数
		ManuscriptManager.saveManscriptParameter(context, specialId, modelConfig.getModelConfigId() + "",
				ManuscriptParameterType.modelConfig);
		// ------ 后期可以删除 end

		CreateResponseDto createResponseDto = new CreateResponseDto();
		createResponseDto.setManuscriptId(specialId);
		createResponseDto.setTerminalType(integerTerminalType);
		return createResponseDto;
	}

	/**
	 * 创建模版 设计师登录 manuscriptName 模板名称 terminalType 终端类型
	 * 
	 * @param context
	 * @param manuscriptType
	 * @param options
	 * @return
	 * @throws SessionContainerException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private Entity createTemplate(RequestContext context, ManuscriptType manuscriptType, MapDto options)
			throws SessionContainerException, BlAppException, DaoAppException {
		// 添加 验证设计师
		this.checkFrontUserType(context, UserType.DESIGNERS);
		String name = (String) options.get("manuscriptName");
		this.valiName(name, "模板名称", 20);
		// TODO 加上类型验证
		Integer integerTerminalType = (Integer) options.get("terminalType");
		TerminalType terminalType = TerminalType.getEnum(integerTerminalType);
		this.valiParaNotNull(terminalType, "稿件终端类型[terminalType]错误,请参考稿件创建接口文档谢谢合作");
		String templateId = ManuscriptManager.createManuscript(context, getSessionUserId(context), manuscriptType,
				terminalType);
		// 保存名称
		ManuscriptManager.saveManscriptParameter(context, templateId, name, ManuscriptParameterType.manuscriptName);

		CreateResponseDto createResponseDto = new CreateResponseDto();
		createResponseDto.setManuscriptId(templateId);
		createResponseDto.setTerminalType(terminalType.getValue());
		return createResponseDto;
	}

	/**
	 * 创建优秀案例 后台用户登录
	 * 
	 * @param context
	 * @param options
	 *            isEnable:取消或创建游戏案例 manuscriptId:源稿件id 这里为推广id manuscriptName
	 *            目标案例名称 tags 目标 标签
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	private Entity createCase(RequestContext context, MapDto options)
			throws BlAppException, DaoAppException, SessionContainerException {
		// 没有查检登录
		this.checkBackUserLogined(context);
		Boolean isEnable = (Boolean) options.get("isEnable");
		this.valiParaNotNull(isEnable, "优秀案例状态[isEnable]错误 true:设为优秀案例 false:取消优秀案例");
		String relationId = (String) options.get("manuscriptId");
		String keyHead = KeyFactory.getKeyHeadByKey(relationId);
		if (!KeyFactory.KEY_EXTENSION.equals(keyHead)) {
			throw new BlAppException(-1, "目前只支持对推广进行优秀案例的操作谢谢合作,请检查[manuscriptId]是否正确谢谢合作");
		}
		CreateResponseDto createResponseDto = new CreateResponseDto();
		if (isEnable) {
			String name = (String) options.get("manuscriptName");
			this.valiName(name, "优秀案例名称", 20);
			String tags = (String) options.get("tags");
			createResponseDto.setManuscriptId(ManuscriptManager.createManuscriptFrom(context, relationId, tags, name));
		} else {
			createResponseDto.setManuscriptId(ManuscriptManager.disableManuscript(context, relationId));
		}
		return createResponseDto;
	}

	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public Entity loadtags(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {
			checkFrontUserLogined(context);
			SearchTagsDto searchTagsDto = context.getDomain(SearchTagsDto.class);
			this.valiPara(searchTagsDto);
			Integer type = searchTagsDto.getParameterType();
			if (type == null) {
				searchTagsDto.setParameterType(ManuscriptParameterType.tag.getValue());
			} else if (!type.equals(ManuscriptParameterType.tag.getValue())
					&& !type.equals(ManuscriptParameterType.systag.getValue())) {
				searchTagsDto.setParameterType(ManuscriptParameterType.tag.getValue());
			}
			searchTagsDto.setUserId(getSessionUserId(context));

			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context, ManuscriptDao.class);
			this.valiDaoIsNull(dao, "稿件");
			return dao.query(searchTagsDto, false);
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

	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String preview(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Deploy deploy = null;
		try {
			deploy = new DeployImpl();

			DeployDto deployDto = context.getDomain(DeployDto.class);
			this.valiPara(deployDto);
			String manuscriptId = deployDto.getModeId();
			ManuscriptType type = null;
			logger.info("稿件编号:" + manuscriptId);
			// 根据稿件编号区分稿件类型
			switch (KeyUtil.getKey(manuscriptId)) {
			case EXCELLENTCASE:
				type = ManuscriptType.EXCELLENTCASE;
				break;
			case SPECIAL:
				type = ManuscriptType.SPECIAL;
				break;
			case TEMPLATE:
				type = ManuscriptType.TEMPLATE;
				break;
			case EXTENSION:
				type = ManuscriptType.EXTENSION;
				break;
			default:
				type = ManuscriptType.DEF;
				break;
			}
			logger.info("当前稿件类型为:" + type);
			if (type == null)
				throw new BlAppException(-1, "编号错误");
			if (!deploy.deploy(false, context, type))
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

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchcollection(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			this.checkFrontUserLogined(context);
			dao = this.daoProxyFactory.getDao(context, ManuscriptDao.class);
			this.valiDaoIsNull(dao, "模板");
			SearchManuscriptCollectionDto searchusedtemplateDto = context
					.getDomain(SearchManuscriptCollectionDto.class);
			// 设置查询条件
			searchusedtemplateDto.setUserId(this.getSessionUserId(context));
			// 查询数据
			List<Entity> datas = transformation(dao.query(searchusedtemplateDto));
			CountDto count = (CountDto) dao.query(searchusedtemplateDto, false);
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
	public Entity save(RequestContext context) throws BlAppException, DaoAppException {
		CreateManuscriptResponseDto createResponseDto = null;
		CodeTable ct;
		try {
			SaveManuscriptDto saveManuscriptDto = context.getDomain(SaveManuscriptDto.class);
			this.valiPara(saveManuscriptDto);
			Session Session = context.getSession();
			UserType userTypeValue = Session.getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
			logger.info("获取用户类型:" + userTypeValue);
			ManuscriptParameterEntity manuscriptParameterEntity;
			switch (userTypeValue) {
			case DESIGNERS:
				logger.info("用户类型为设计师");
				TemplateEntity templateEntity = null;
				SaveTemplateDto saveTemplateDto = new SaveTemplateDto();
				saveTemplateDto.setActiveType(saveManuscriptDto.getActiveType());
				saveTemplateDto.setData(saveManuscriptDto.getData());
				saveTemplateDto.setPluginType(saveManuscriptDto.getPluginType());
				saveTemplateDto.setTemplateId(saveManuscriptDto.getManuscriptId());
				saveTemplateDto.setTerminalType(saveManuscriptDto.getTerminalType());
				logger.info("创建模版");
				templateEntity = TemplateManager.saveTemplate(context, saveTemplateDto);

				manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
						templateEntity.getTemplateId(), ManuscriptParameterType.manuscriptName);
				if (manuscriptParameterEntity != null) {
					logger.info("专题已保存名称:" + manuscriptParameterEntity.getParameter());
					// 保存专题发布名称
					ManuscriptManager.saveManscriptParameter(context, templateEntity.getTemplateId(),
							manuscriptParameterEntity.getParameter(), ManuscriptParameterType.publishname);
				}
				logger.info("创建模版结束:" + templateEntity);
				createResponseDto = new CreateManuscriptResponseDto();
				createResponseDto.setManuscriptId(templateEntity.getTemplateId());
				createResponseDto.setTerminalType(templateEntity.getTemplateType());
				return createResponseDto;
			case GENERAL_USER:
				logger.info("用户类型为普通用户");
				SpecialEntity specialEntity = null;
				SaveSpecialDto saveSpecialDto = new SaveSpecialDto();
				saveSpecialDto.setActiveType(saveManuscriptDto.getActiveType());
				saveSpecialDto.setData(saveManuscriptDto.getData());
				saveSpecialDto.setPluginType(saveManuscriptDto.getPluginType());
				saveSpecialDto.setSpecialId(saveManuscriptDto.getManuscriptId());
				saveSpecialDto.setTerminalType(saveManuscriptDto.getTerminalType());
				logger.info("创建专题");
				specialEntity = SpecialManager.saveSpecial(context, saveSpecialDto);

				manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
						specialEntity.getSpecialId(), ManuscriptParameterType.manuscriptName);
				if (manuscriptParameterEntity != null) {
					logger.info("专题已保存名称可直接发布:" + manuscriptParameterEntity.getParameter());
					// 保存专题发布名称
					ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
							manuscriptParameterEntity.getParameter(), ManuscriptParameterType.publishname);
					// 可发布状态
					ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
							PublishState.CANPUBLISH.getValue() + "", ManuscriptParameterType.publishstate);
				}
				logger.info("创建专题结束:" + specialEntity);
				createResponseDto = new CreateManuscriptResponseDto();
				createResponseDto.setManuscriptId(specialEntity.getSpecialId());
				createResponseDto.setTerminalType(specialEntity.getSpecialType());
				return createResponseDto;

			case BACK_USER:
				String manuscriptId = saveManuscriptDto.getManuscriptId();
				logger.info("获取稿件id:" + manuscriptId);
				ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
				logger.info("获取稿件:" + manuscriptEntity);
				this.valiDomainIsNull(manuscriptEntity, "稿件编号错误", false);
				this.valiDomainIsNull(saveManuscriptDto.getData(), "稿件内容错误", false);
				String manuscriptContent = JSON.toJSONString(saveManuscriptDto.getData());
				logger.info("保存内容开始");
				ManuscriptManager.saveManscriptContent(context, manuscriptId, manuscriptContent);
				logger.info("保存内容结束");
				createResponseDto = new CreateManuscriptResponseDto();
				createResponseDto.setManuscriptId(manuscriptId);
				createResponseDto.setTerminalType(manuscriptEntity.getTerminalType());
				return createResponseDto;
			default:
				return createResponseDto;
			}
			// 封装返回数据
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity savemanuscriptname(RequestContext context) throws BlAppException, DaoAppException {
		SaveManuscriptNameDto dto = null;
		CreateManuscriptResponseDto createResponseDto = null;
		CodeTable ct;
		try {
			// 检查是否登录
			this.checkFrontUserLogined(context);
			dto = context.getDomain(SaveManuscriptNameDto.class);
			this.valiPara(dto);
			this.valiName(dto.getName(), "稿件名称", 20);
			Session Session = context.getSession();
			UserType userTypeValue = Session.getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
			logger.info("获取用户类型:" + userTypeValue);
			switch (userTypeValue) {
			case DESIGNERS:
				logger.info("用户类型为设计师");
				TemplateEntity templateEntity = null;
				String templateId = dto.getManuscriptId();
				if (ManuscriptType.DEF.equals(KeyUtil.getKey(templateId))) {
					logger.info("模版编号为空 则创建模版");
					BLContextUtil.valiParaItemObjectNull(dto.getTerminalType(), "终端类型");
					BLContextUtil.valiParaItemNumBetween(1, 2, dto.getTerminalType(), "终端类型");
					templateId = KeyFactory.newKey(KeyFactory.KEY_TEMPLATE);
					logger.info("模版编号:" + templateId);
					templateEntity = new TemplateEntity();
					templateEntity.setTemplateId(templateId);
					templateEntity.setCoverImg("");
					templateEntity.setCreatetime(new java.sql.Date(System.currentTimeMillis()));
					templateEntity.setMainColor("");
					templateEntity.setSummary("");
					templateEntity.setTemplateContent("");
					templateEntity.setTemplateType(dto.getTerminalType());
					templateEntity.setTemplateState(TemplateState.UNSUBMITTED);
					templateEntity.setUserId(BLContextUtil.getSessionUserId(context));
					logger.info("创建模版开始");
					ManuscriptManager.createManuscript(context, templateEntity);
					logger.info("创建模版结束");
				} else {
					ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, templateId);
					if (ManuscriptState.VERIFY.getValue().equals(manuscriptEntity.getManuscriptState())
							|| ManuscriptState.ADDED.getValue().equals(manuscriptEntity.getManuscriptState())) {
						logger.info("该模版为审核中或已上架无法修改名称");
						throw new BlAppException(-1, "审核中/已上架的模板名称不可被修改");
					}
					this.valiDomainIsNull(manuscriptEntity, "模板编号错误", false);
					dto.setTerminalType(manuscriptEntity.getTerminalType());
				}
				// 保存专题名称
				ManuscriptManager.saveManscriptParameter(context, templateId, dto.getName(),
						ManuscriptParameterType.manuscriptName);

				logger.info("保存模版名称结束:" + templateId);
				createResponseDto = new CreateManuscriptResponseDto();
				createResponseDto.setManuscriptId(templateId);
				createResponseDto.setTerminalType(dto.getTerminalType());
				return createResponseDto;
			case GENERAL_USER:
				logger.info("用户类型为普通用户");
				SpecialEntity specialEntity = null;
				String specialId = dto.getManuscriptId();
				this.valiParaItemObjectNull(dto.getTerminalType(), "模板类型");
				this.valiParaItemNumBetween(1, 2, dto.getTerminalType(), "终端类型");
				if (ManuscriptType.DEF.equals(KeyUtil.getKey(specialId))) {
					logger.info("专题编号为空 则创建专题");
					specialId = KeyFactory.newKey(KeyFactory.KEY_SPECIAL);
					logger.info("专题编号:" + specialId);
					// 构造专题数据
					specialEntity = new SpecialEntity();
					specialEntity.setCreatetime(new java.util.Date());
					specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());
					specialEntity.setSpecialType(dto.getTerminalType());
					specialEntity.setUserId(this.getSessionUserId(context));
					specialEntity.setSpecialId(specialId);
					specialEntity.setPublishState(PublishState.CANNOTPUBLISH.getValue());
					// 创建专题
					logger.info("创建专题开始");
					ManuscriptManager.createManuscript(context, specialEntity);
					logger.info("创建专题结束");
				} else {
					ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, specialId);
					this.valiDomainIsNull(manuscriptEntity, "专题编号错误", false);
					dto.setTerminalType(manuscriptEntity.getTerminalType());
				}
				// 保存专题名称
				ManuscriptManager.saveManscriptParameter(context, specialId, dto.getName(),
						ManuscriptParameterType.manuscriptName);
				// 保存专题发布名称
				ManuscriptManager.saveManscriptParameter(context, specialId, dto.getName(),
						ManuscriptParameterType.publishname);
				// 可发布状态
				ManuscriptManager.saveManscriptParameter(context, specialId, PublishState.CANPUBLISH.getValue() + "",
						ManuscriptParameterType.publishstate);
				logger.info("保存专题名称结束:" + specialId);
				createResponseDto = new CreateManuscriptResponseDto();
				createResponseDto.setManuscriptId(specialId);
				createResponseDto.setTerminalType(dto.getTerminalType());
				return createResponseDto;
			default:
				return createResponseDto;
			}
			// 封装返回数据
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String loadname(RequestContext context) throws BlAppException, DaoAppException {
		String manuscriptId = context.getDomain(String.class);
		ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
				manuscriptId, ManuscriptParameterType.manuscriptName);
		if (manuscriptParameterEntity == null) {
			return null;
		}
		return manuscriptParameterEntity.getParameter();
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String loadpublishstate(RequestContext context) throws BlAppException, DaoAppException {
		String manuscriptId = context.getDomain(String.class);
		ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
				manuscriptId, ManuscriptParameterType.publishstate);
		if (manuscriptParameterEntity == null) {
			return null;
		}
		return manuscriptParameterEntity.getParameter();
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public MapDto loadstate(RequestContext context) throws BlAppException, DaoAppException {
		String manuscriptId = context.getDomain(String.class);
		ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
		if (manuscriptEntity == null) {
			return null;
		}
		MapDto mapDto = new MapDto();
		mapDto.put("manuscriptType", manuscriptEntity.getManuscriptType());
		mapDto.put("manuscriptState", manuscriptEntity.getManuscriptState());
		return mapDto;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void collection(RequestContext context) throws BlAppException, DaoAppException {
		CollectionDto collectionDto = null;
		CodeTable ct;
		String userid = null;
		Dao dao = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 获取参数
			collectionDto = context.getDomain(CollectionDto.class);
			// 验证入参
			valiParaNotNull(collectionDto, "收藏");
			logger.info("入参:" + collectionDto);
			// 获取稿件id
			String manuscriptId = collectionDto.getManuscriptId();
			// 验证稿件id
			this.valiParaItemStrNullOrEmpty(manuscriptId, KeyUtil.getName(manuscriptId) + "编号");
			dao = this.daoProxyFactory.getDao(context, ManuscriptDao.class);
			this.valiDaoIsNull(dao, "收藏");
			// 获取userid
			userid = this.getSessionUserId(context);
			logger.info("userid:" + userid);
			// 根据稿件id 获取稿件实体
			ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
			// 验证稿件实体
			BLContextUtil.valiDomainIsNull(manuscriptEntity, KeyUtil.getName(manuscriptId));

			ManuscriptCollectionEntity collectionEntity = new ManuscriptCollectionEntity();
			collectionEntity.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());
			logger.info("收藏稿件实体:" + manuscriptEntity);
			if (ManuscriptType.TEMPLATE.getValue().equals(manuscriptEntity.getManuscriptType())) {
				// 如果稿件为模版 验证是否为上架状态
				if (!ManuscriptState.ADDED.getValue().equals(manuscriptEntity.getManuscriptState())) {
					throw new BlAppException(-1, "只能收藏已上架模版");
				}
				collectionEntity.setManuscriptType(ManuscriptType.TEMPLATE.getValue());
			} else if (!ManuscriptType.EXCELLENTCASE.getValue().equals(manuscriptEntity.getManuscriptType())) {
				throw new BlAppException(-1, "该稿件暂不支持收藏");
			}

			// 收藏记录保存
			collectionEntity
					.setCollectionState(collectionDto.getIsCollection() == null ? 0 : collectionDto.getIsCollection());
			collectionEntity.setUserId(userid);
			collectionEntity.setManuscriptId(collectionDto.getManuscriptId());
			collectionEntity.setCreatetime(new Date());
			int val = dao.save(collectionEntity);
			// 校验保存结果
			this.valiSaveDomain(val, "收藏");
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

	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String copy(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		CopyManuscriptDto copyManuscriptDto = null;
		String id = null;
		try {
			// 验证是否前台用户登录
			this.checkFrontUserLogined(context);
			// 获取入参
			copyManuscriptDto = context.getDomain(CopyManuscriptDto.class);
			// 验证入参
			this.valiPara(copyManuscriptDto);
			logger.info("参数:" + copyManuscriptDto);
			String manuscriptId = copyManuscriptDto.getManuscriptId();
			String baseDesc = KeyUtil.getName(manuscriptId);
			// 验证稿件编号
			this.valiParaItemStrNullOrEmpty(manuscriptId, baseDesc + "编号");
			// 获取用户id
			id = this.getSessionUserId(context);
			logger.info("userid:" + id);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "复制");

			// 根据稿件id 获取稿件实体
			ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
			// 验证稿件实体
			BLContextUtil.valiDomainIsNull(manuscriptEntity, KeyUtil.getName(manuscriptId));
			logger.info("被复制的稿件实体:" + manuscriptEntity);
			// 验证 拷贝他人模版时 是否已下架
			if (ManuscriptType.TEMPLATE.getValue().equals(manuscriptEntity.getManuscriptType())
					&& !this.getSessionUserId(context).equals(manuscriptEntity.getUserId())
					&& !ManuscriptState.ADDED.getValue().equals(manuscriptEntity.getManuscriptState())) {
				throw new BlAppException(-1, "模板已经下架");
			}

			if (ManuscriptType.SPECIAL.getValue().equals(manuscriptEntity.getManuscriptType())) {
				this.checkCurrentUser(context, manuscriptEntity.getUserId());
				if (SpecialState.HASDISABLE.getValue().equals(manuscriptEntity.getManuscriptState())) {
					throw new BlAppException(-1, "该专题已禁用");
				}
			}

			// 创建新的稿件id
			String newManscriptId = ManuscriptManager.createManuscript(context, id,
					ManuscriptType.getEnum(manuscriptEntity.getManuscriptType()),
					TerminalType.getEnum(manuscriptEntity.getTerminalType()));
			logger.info("新稿件id:" + newManscriptId);
			// 保存 封面
			ManuscriptManager.saveManscriptParameter(context, newManscriptId,
					manuscriptEntity.getManuscriptParameterByType(ManuscriptParameterType.manuscriptImg),
					ManuscriptParameterType.manuscriptImg);
			logger.info("保存 封面");
			// 获取名称
			String manuscriptName = copyManuscriptDto.getManuscriptName();
			// 获取上架后的名称
			ManuscriptParameterEntity verifyName = null;
			if (ManuscriptType.TEMPLATE.getValue().equals(manuscriptEntity.getManuscriptType())) {
				verifyName = ManuscriptManager.getManuscriptParameterById(context, manuscriptId,
						ManuscriptParameterType.verifyName);
			} else if (ManuscriptType.SPECIAL.getValue().equals(manuscriptEntity.getManuscriptType())) {
				verifyName = ManuscriptManager.getManuscriptParameterById(context, manuscriptId,
						ManuscriptParameterType.publishname);
			}

			logger.info("verifyName:" + verifyName);
			// 保存稿件名称
			if (!Str.isNullOrEmpty(manuscriptName)) {
				this.valiName(manuscriptName, baseDesc + "名称", 20, false);
				ManuscriptManager.saveManscriptParameter(context, newManscriptId, manuscriptName,
						ManuscriptParameterType.manuscriptName);
				if (ManuscriptType.SPECIAL.getValue().equals(manuscriptEntity.getManuscriptType())) {
					ManuscriptManager.saveManscriptParameter(context, newManscriptId, manuscriptName,
							ManuscriptParameterType.publishname);
				}
			} else if (verifyName != null && !Str.isNullOrEmpty(verifyName.getParameter())) {
				this.valiName(verifyName.getParameter(), baseDesc + "名称", 20, false);
				ManuscriptManager.saveManscriptParameter(context, newManscriptId, verifyName.getParameter(),
						ManuscriptParameterType.manuscriptName);
				if (ManuscriptType.SPECIAL.getValue().equals(manuscriptEntity.getManuscriptType())) {
					ManuscriptManager.saveManscriptParameter(context, newManscriptId, verifyName.getParameter(),
							ManuscriptParameterType.publishname);
				}
			}
			logger.info("保存稿件名称");
			ManuscriptContentEntity content = manuscriptEntity.getManuscriptContent();
			// 保存稿件内容
			ManuscriptManager.saveManscriptContent(context, newManscriptId,
					content == null ? "" : content.getContent());
			logger.info("保存稿件内容");
			// 添加插件复制
			PluginManager.copyPlugin(context, manuscriptId, newManscriptId,
					ManuscriptType.getEnum(manuscriptEntity.getManuscriptType()),
					ManuscriptType.getEnum(manuscriptEntity.getManuscriptType()));
			logger.info("插件复制");
			// 添加使用记录
			TemplateManager.saveTemplateUseLog(context, manuscriptId);
			logger.info("添加使用记录");
			return newManscriptId;
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		DeleteManuscriptDto dto = null;
		CodeTable ct;
		String msg = null;
		NotifyImpl notifyImpl = null;
		String currentUserId = null;
		try {
			BLContextUtil.checkUserLogined(context);
			dto = context.getDomain(DeleteManuscriptDto.class);
			this.valiParaNotNull(dto, "删除编号");
			logger.info("入参:" + dto);
			this.valiParaItemListNull(dto.getManuscriptIds(), "删除编号");
			currentUserId = this.getSessionUserId(context);
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				logger.info("后台删除");
				for (String manuscriId : dto.getManuscriptIds()) {
					ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriId);
					if (manuscriptEntity == null)
						continue;
					if (ManuscriptType.TEMPLATE.getValue().equals(manuscriptEntity.getManuscriptType())) {
						msg = "您设计中的模板%s已被后台管理员删除";
						msg = String.format(msg,
								manuscriptEntity.getManuscriptParameterByType(ManuscriptParameterType.manuscriptName));
						notifyImpl = new NotifyImpl();
						if (!notifyImpl.create(context, currentUserId, manuscriptEntity.getUserId(), msg,
								NotifyType.Sys))
							throw new BlAppException(-1, "创建通知错误");
					}
					ManuscriptManager.delete(context, manuscriId);
				}
				return;
			}
			logger.info("用户删除");
			String noManuscript = "";
			String notUser = "";
			String ok = "";
			for (String manuscriId : dto.getManuscriptIds()) {
				ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriId);
				if (manuscriptEntity == null) {
					noManuscript += manuscriId + ",";
					continue;
				}
				if (!this.getSessionUserId(context).equals(manuscriptEntity.getUserId())) {
					notUser += manuscriId + ",";
					continue;
				}
				ManuscriptManager.delete(context, manuscriId);
				ok += manuscriId + ",";
			}
			logger.info("本次无效删除:" + noManuscript);
			logger.info("本次无权限删除:" + notUser);
			logger.info("本次有效删除:" + ok);

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
	public void savepublishinfo(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		SavepublishinfoDto savepublishinfoDto = null;
		try {
			this.checkFrontUserLogined(context);
			savepublishinfoDto = context.getDomain(SavepublishinfoDto.class);
			this.valiPara(savepublishinfoDto);

			String manuscriptId = savepublishinfoDto.getManuscriptId();
			this.valiParaItemStrNullOrEmpty(manuscriptId, "稿件编号");
			ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, manuscriptId);
			BLContextUtil.valiDomainIsNull(manuscriptEntity, KeyUtil.getName(manuscriptId));
			this.valiName(savepublishinfoDto.getPublishName(), "模板名称", 20);
			this.valiSummary(savepublishinfoDto.getSummary(), "作品描述", 150, false);
			ManuscriptType type = ManuscriptType.getEnum(manuscriptEntity.getManuscriptType());
			ManuscriptState state = ManuscriptState.getEnum(manuscriptEntity.getManuscriptState());
			if (!ManuscriptType.TEMPLATE.equals(type) && !ManuscriptType.SPECIAL.equals(type)) {
				throw new BlAppException(-1, "系统暂不支持其他类型的发布保存谢谢合作,暂只支持1:模板 2:专题");
			}
			// 验证用户
			this.checkCurrentUser(context, manuscriptEntity.getUserId());

			if (ManuscriptType.TEMPLATE.equals(type)) {
				this.valiParaItemStrNullOrEmpty(savepublishinfoDto.getCoverImg(), "请为您的作品上传封面哦", false);

				if (ManuscriptState.ADDED.equals(state) || ManuscriptState.VERIFY.equals(state)) {
					ct = CodeTable.BL_TEMPLATE_SAVE_STATUS;
					throw new BlAppException(ct.getValue(), "审核中和已上架模版不允许该操作");
				}
			} else if (ManuscriptType.SPECIAL.equals(type)) {
				logger.info("是否去版权:" + savepublishinfoDto.getIsUnRights());
				if (savepublishinfoDto.getIsUnRights()) {
					// 如果为首次去版权 才会扣除去版权次数
					String extensionId = manuscriptEntity
							.getManuscriptParameterByType(ManuscriptParameterType.extensiontarget);
					if (Str.isNullOrEmpty(extensionId)
							|| UnRightsManger.queryUnRightsLog(context, extensionId) == null) {
						// 验证去版权次数
						UnRightsTimesEntity unRightsTimesEntity = UnRightsManger.searchTimes(context,
								manuscriptEntity.getUserId());
						if (unRightsTimesEntity == null || unRightsTimesEntity.getUnrightsTimes() <= 0) {
							ct = CodeTable.BL_UNRIGHTS_TIMES_ERROR;
							throw new BlAppException(ct.getValue(), ct.getDesc());
						}
						logger.info("去版权剩余次数:" + unRightsTimesEntity.getUnrightsTimes());
					}
					// 保存去版权设置
					ManuscriptManager.saveManscriptParameter(context, manuscriptId,
							ManuscriptParameterType.unrights.toString(), ManuscriptParameterType.unrights);
				} else {
					// 保存去版权设置
					ManuscriptManager.saveManscriptParameter(context, manuscriptId, "disable",
							ManuscriptParameterType.unrights);
				}
				// 推广执行时间
				ManuscriptManager.saveManscriptParameter(context, manuscriptId,
						TimeUtil.format(savepublishinfoDto.getPublishStart()), ManuscriptParameterType.statrttime);

				ManuscriptManager.saveManscriptParameter(context, manuscriptId,
						TimeUtil.format(savepublishinfoDto.getPublishEnd()), ManuscriptParameterType.endtime);
			}

			// 保存发布设置参数
			ManuscriptManager.saveManscriptParameter(context, manuscriptId, PublishState.CANPUBLISH.getValue() + "",
					ManuscriptParameterType.publishstate);
			// 保存发布名称
			ManuscriptManager.saveManscriptParameter(context, manuscriptId, savepublishinfoDto.getPublishName(),
					ManuscriptParameterType.publishname);
			// 保存标签
			ManuscriptManager.saveManscriptParameters(context, manuscriptId,
					this.transformationTags(savepublishinfoDto.getTagNames()), ManuscriptParameterType.tag);
			// 保存 封面
			ManuscriptManager.saveManscriptParameter(context, manuscriptId, savepublishinfoDto.getCoverImg(),
					ManuscriptParameterType.manuscriptImg);

			// 保存名称
			ManuscriptManager.saveManscriptParameter(context, manuscriptId, savepublishinfoDto.getPublishName(),
					ManuscriptParameterType.manuscriptName);
			// 保存简介
			ManuscriptManager.saveManscriptParameter(context, manuscriptId, savepublishinfoDto.getSummary(),
					ManuscriptParameterType.manuscriptSummary);
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
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchpublishsetting(RequestContext context) throws BlAppException, DaoAppException {
		String entity;
		entity = context.getDomain(String.class);
		logger.info("查询发布设置的稿件id:" + entity);
		this.valiParaItemStrNullOrEmpty(entity, "查询发布设置编号");
		if (!ClientTypes.Topic.toString().equals(context.getQueryString("ClientType"))) {
			CodeTable ct = CodeTable.BL_SESSION_WRONGCLIENT;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}

		if (entity.startsWith(KeyFactory.KEY_EXTENSION)) {
			SearchConfigDataDto searchConfigDataDto = buildResult(context, entity, ManuscriptParameterType.tag);
			// 加载是否去版权
			ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(context, entity,
					ManuscriptParameterType.unrights);
			if (unrights != null) {
				searchConfigDataDto
						.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
			}
			ManuscriptParameterEntity urlEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
					ManuscriptParameterType.extensionurl);
			if (urlEntity != null) {
				searchConfigDataDto.setUrl(urlEntity.getParameter());
			}
			// 推广查询
			logger.info("推广查询");
			return searchConfigDataDto;
		} else if (entity.startsWith(KeyFactory.KEY_SPECIAL)) {
			// 专题查询
			logger.info("专题查询");
			SearchConfigDataDto searchConfigDataDto = buildResult(context, entity, ManuscriptParameterType.tag);
			// 加载是否去版权
			ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(context, entity,
					ManuscriptParameterType.unrights);
			if (unrights != null) {
				searchConfigDataDto
						.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
			}
			return searchConfigDataDto;
		} else if (entity.startsWith(KeyFactory.KEY_TEMPLATE)) {
			// 模板查询
			logger.info("模板查询");
			SearchConfigDataDto searchConfigDataDto = buildResult(context, entity, ManuscriptParameterType.systag);
			searchConfigDataDto.setUnRights(true);
			return searchConfigDataDto;
		} else if (entity.startsWith(KeyFactory.KEY_MANUSCRIPT)) {
			// 优秀案例
			logger.info("查询优秀案例");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			ManuscriptParameterEntity nameEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
					ManuscriptParameterType.manuscriptName);
			searchConfigDataDto.setName(nameEntity != null ? nameEntity.getParameter() : null);
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		} else if (entity.startsWith(KeyFactory.KEY_DET)) {
			// 优秀案例
			logger.info("查询临时稿件");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			ManuscriptParameterEntity nameEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
					ManuscriptParameterType.manuscriptName);
			searchConfigDataDto.setName(nameEntity != null ? nameEntity.getParameter() : null);
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		}
		return null;
	}

	/**
	 * 查询发布设置
	 */
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				logger.info("后台用户查询稿件");
				SearchBackManuscriptDto request = context.getDomain(SearchBackManuscriptDto.class);
				this.valiPara(request);
				this.checkLimit(request.getStartCount(), request.getPageSize());
				return ManuscriptManager.search(context, request);
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				logger.info("前台用户查询稿件");
				SearchFrontManuscriptDto request = context.getDomain(SearchFrontManuscriptDto.class);
				this.valiPara(request);
				this.checkLimit(request.getStartCount(), request.getPageSize());
				return ManuscriptManager.search(context, request);
			} else {
				logger.info("匿名用户查询稿件");
				SearchFrontManuscriptDto request = context.getDomain(SearchFrontManuscriptDto.class);
				this.valiPara(request);
				this.checkLimit(request.getStartCount(), request.getPageSize());
				request.setManuscriptType(ManuscriptType.EXCELLENTCASE.getValue());
				return ManuscriptManager.search(context, request);
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

	private SearchConfigDataDto buildResult(RequestContext context, String entity, ManuscriptParameterType tag)
			throws DaoAppException, BlAppException {
		SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
		ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, entity);
		List<ManuscriptParameterEntity> manuscriptParameterEntitys = manuscriptEntity.getManuscriptParameters();
		for (ManuscriptParameterEntity entry : manuscriptParameterEntitys) {
			if (tag.getValue().equals(entry.getParameterType())) {
				searchConfigDataDto.addKeyWord(entry.getParameter());
			}
		}
		ManuscriptParameterEntity nameEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptName);
		searchConfigDataDto.setName(nameEntity != null ? nameEntity.getParameter() : null);
		ManuscriptParameterEntity summaryEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptSummary);
		searchConfigDataDto.setSummary(summaryEntity != null ? summaryEntity.getParameter() : null);
		return searchConfigDataDto;
	}

	@Override
	public Entity loadpublishinfo(RequestContext context) throws BlAppException, DaoAppException, ParseException {
		String entity;
		entity = context.getDomain(String.class);
		logger.info("加载发布设置的稿件id:" + entity);
		this.valiParaItemStrNullOrEmpty(entity, "加载发布设置编号");

		if (entity.startsWith(KeyFactory.KEY_SPECIAL)) {
			logger.info("查询专题的发布设置");
			SpecialPublishinfoDto specialPublishinfoDto = null;
			specialPublishinfoDto = buildSpecialPublishResult(context, entity);

			return specialPublishinfoDto;
		} else if (entity.startsWith(KeyFactory.KEY_TEMPLATE)) {
			logger.info("查询模板的发布设置");
			TemplatePublishInfoDto templatePublishInfoDto = null;
			templatePublishInfoDto = buildTemplatePublishResult(context, entity);

			return templatePublishInfoDto;
		}

		return null;
	}

	private SpecialPublishinfoDto buildSpecialPublishResult(RequestContext context, String entity)
			throws DaoAppException, BlAppException, ParseException {
		SpecialPublishinfoDto specialPublishinfoDto = new SpecialPublishinfoDto();
		specialPublishinfoDto.setSpecialId(entity);
		// 获取封面图片
		ManuscriptParameterEntity imgEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptImg);
		specialPublishinfoDto.setCoverImg(imgEntity != null ? imgEntity.getParameter() : null);
		// 获取发布名称
		ManuscriptParameterEntity publishNameEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptName);
		specialPublishinfoDto.setPublishName(publishNameEntity != null ? publishNameEntity.getParameter() : null);
		// 获取专题标签
		specialPublishinfoDto.setTagNames(ManuscriptManager.getTag(context, entity, ManuscriptParameterType.tag));
		// 获取详情
		ManuscriptParameterEntity summaryEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptSummary);
		specialPublishinfoDto.setSummary(summaryEntity != null ? summaryEntity.getParameter() : null);
		// 获取活动类型
		ManuscriptParameterEntity activeEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.activeType);
		specialPublishinfoDto.setActiveType(activeEntity != null ? activeEntity.getParameter() : null);
		// // 获取活动开始时间
		// ManuscriptParameterEntity activeStartEntity =
		// ManuscriptManager.getManuscriptParameterById(context, entity,
		// ManuscriptParameterType.statrtactivetime);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// if (activeStartEntity.getParameter() != null) {
		// Date date = sdf.parse(activeStartEntity.getParameter());
		// specialPublishinfoDto.setActiveEnd(date);
		// }
		// // 获取活动结束时间
		// ManuscriptParameterEntity endactiveTimeEntity =
		// ManuscriptManager.getManuscriptParameterById(context, entity,
		// ManuscriptParameterType.endactivetime);
		// if (endactiveTimeEntity.getParameter() != null) {
		// Date date = sdf.parse(endactiveTimeEntity.getParameter());
		// specialPublishinfoDto.setActiveEnd(date);
		// }
		// 加载是否已发布过去版权
		specialPublishinfoDto.setDoneUnrights(
				UnRightsManger.queryUnRightsLog(context, specialPublishinfoDto.getSpecialId()) != null ? true : false);
		ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(context,
				specialPublishinfoDto.getSpecialId(), ManuscriptParameterType.unrights);
		// 加载是否去版权
		if (unrights != null) {
			specialPublishinfoDto
					.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
		} else {
			specialPublishinfoDto.setUnRights(false);
		}

		return specialPublishinfoDto;
	}

	private TemplatePublishInfoDto buildTemplatePublishResult(RequestContext context, String entity)
			throws DaoAppException, BlAppException {
		TemplatePublishInfoDto templatePublishInfoDto = new TemplatePublishInfoDto();
		templatePublishInfoDto.setTemplateId(entity);

		ManuscriptParameterEntity imgEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptImg);
		templatePublishInfoDto.setCoverImg(imgEntity != null ? imgEntity.getParameter() : null);

		// 获取发布名称
		ManuscriptParameterEntity publishNameEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptName);
		templatePublishInfoDto.setPublishName(publishNameEntity != null ? publishNameEntity.getParameter() : null);
		// 获取专题标签
		templatePublishInfoDto.setTagNames(ManuscriptManager.getTag(context, entity, ManuscriptParameterType.tag));
		// 获取详情
		ManuscriptParameterEntity summaryEntity = ManuscriptManager.getManuscriptParameterById(context, entity,
				ManuscriptParameterType.manuscriptSummary);
		templatePublishInfoDto.setSummary(summaryEntity != null ? summaryEntity.getParameter() : null);
		return templatePublishInfoDto;
	}

}
