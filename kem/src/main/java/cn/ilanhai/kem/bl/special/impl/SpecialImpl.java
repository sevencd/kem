package cn.ilanhai.kem.bl.special.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.rights.UnRightsManger;
import cn.ilanhai.kem.bl.special.Special;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.bl.template.TemplateManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.deploy.Deploy;
import cn.ilanhai.kem.deploy.DeployImpl;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PublishState;
import cn.ilanhai.kem.domain.enums.SpecialState;
import cn.ilanhai.kem.domain.enums.TemplateState;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.rights.UnRightsTimesEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;
import cn.ilanhai.kem.domain.special.CopySpecialDto;
import cn.ilanhai.kem.domain.special.CreateSpecialRequestDto;
import cn.ilanhai.kem.domain.special.CreateSpecialResponseDto;
import cn.ilanhai.kem.domain.special.DeleteSpecialDto;
import cn.ilanhai.kem.domain.special.LoadPublishInfoResponseDto;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;
import cn.ilanhai.kem.domain.special.SaveSpecialDto;
import cn.ilanhai.kem.domain.special.SaveSpecialNameDto;
import cn.ilanhai.kem.domain.special.SavepublishinfoDto;
import cn.ilanhai.kem.domain.special.SearchSpecialRequestDto;
import cn.ilanhai.kem.domain.special.SearchSpecialRequestEntity;
import cn.ilanhai.kem.domain.special.SearchSpecialResponseDto;
import cn.ilanhai.kem.domain.special.SpecialContextDto;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.domain.special.SpecialTagResponseDto;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.TimeUtil;

@Component("special")
public class SpecialImpl extends BaseBl implements Special {
	private static Logger logger = Logger.getLogger(SpecialImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity create(RequestContext context) throws BlAppException, DaoAppException {
		SpecialEntity specialEntity = null;

		CodeTable ct;
		Dao dao = null;
		CreateSpecialRequestDto createSpecialDto = null;
		String id = null;
		CreateSpecialResponseDto createResponseDto = null;
		String specialName = "";
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			createSpecialDto = context.getDomain(CreateSpecialRequestDto.class);
			// 获取模板数据
			TemplateEntity templateEntity = TemplateManager.getTemplateEntity(createSpecialDto.getTemplateId(),
					context);
			// 获取用户ID
			id = this.getSessionUserId(context);
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			// 使用模板创建流程
			if (templateEntity != null) {
				if (!templateEntity.getTemplateState().equals(TemplateState.ADDED)) {
					throw new BlAppException(-1, "模板已经下架");
				}
				if (Str.isNullOrEmpty(createSpecialDto.getSpecialName())) {
					specialName = templateEntity.getVerifyName();
				} else {
					specialName = createSpecialDto.getSpecialName();
				}
				// 校验模版名称合法性
				this.valiParaItemNumGreaterThan(20, specialName.length(), "专题名称");
				// 构造专题数据
				specialEntity = new SpecialEntity();
				specialEntity.setSpecialName(specialName);
				specialEntity.setTemplateId(createSpecialDto.getTemplateId());
				specialEntity.setContext(templateEntity.getTemplateContent());
				specialEntity.setCoverImg(templateEntity.getCoverImg());
				specialEntity.setCreatetime(new java.util.Date());
				specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());
				specialEntity.setSpecialType(templateEntity.getTemplateType());
				specialEntity.setUserId(id);
				specialEntity.setSpecialId(KeyFactory.newKey(KeyFactory.KEY_SPECIAL));
				specialEntity.setPublishState(PublishState.CANNOTPUBLISH.getValue());
				// specialEntity.setSummary(templateEntity.getSummary());
				// 复制插件
				PluginManager.copyPlugin(context, templateEntity.getTemplateId(), specialEntity.getSpecialId(),
						ManuscriptType.TEMPLATE, ManuscriptType.SPECIAL);

				// 保存使用记录
				TemplateManager.saveTemplateUseLog(context, templateEntity.getTemplateId());
			}
			// 使用 案例创建流程
			else {
				// 获取案例
				ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context,
						createSpecialDto.getTemplateId());
				if (manuscriptEntity != null) {
					if (Str.isNullOrEmpty(createSpecialDto.getSpecialName())) {
						specialName = manuscriptEntity.getManuscriptSetting().getManuscriptName();
					} else {
						specialName = createSpecialDto.getSpecialName();
					}
					// 校验模版名称合法性
					this.valiParaItemNumGreaterThan(20, specialName.length(), "专题名称");
					// 构造专题数据
					specialEntity = new SpecialEntity();
					specialEntity.setSpecialName(specialName);
					specialEntity.setManuscriptId(createSpecialDto.getTemplateId());
					specialEntity.setContext(manuscriptEntity.getManuscriptContent().getContent());
					specialEntity.setCoverImg(manuscriptEntity.getManuscriptSetting().getManuscriptImg());
					specialEntity.setCreatetime(new java.util.Date());
					specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());
					specialEntity.setSpecialType(manuscriptEntity.getTerminalType());
					specialEntity.setUserId(id);
					specialEntity.setSpecialId(KeyFactory.newKey(KeyFactory.KEY_SPECIAL));
					specialEntity.setPublishState(PublishState.CANNOTPUBLISH.getValue());
					// specialEntity.setSummary(templateEntity.getSummary());
					// 复制插件
					PluginManager.copyPlugin(context, createSpecialDto.getTemplateId(), specialEntity.getSpecialId(),
							ManuscriptType.EXCELLENTCASE, ManuscriptType.SPECIAL);
					// 保存使用记录
					ManuscriptManager.saveUseLog(context, createSpecialDto.getTemplateId());
					// 保存使用记录
					TemplateManager.saveTemplateUseLog(context, createSpecialDto.getTemplateId());
				} else {
					throw new BlAppException(-1, "被使用稿件编号错误");
				}
			}
			// // 创建连接实体
			ModelConfigEntity modelConfig = new ModelConfigEntity();
			modelConfig.setModelId(specialEntity.getSpecialId());
			modelConfig.setModelType(ManuscriptType.SPECIAL.getValue());
			dao.save(modelConfig);
			// 保存连接实体参数
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					modelConfig.getModelConfigId() + "", ManuscriptParameterType.modelConfig);
			// 保存稿件实体
			ManuscriptManager.createManuscript(context, specialEntity);

			// 封装返回数据
			createResponseDto = new CreateSpecialResponseDto();
			createResponseDto.setSpeciaId(specialEntity.getSpecialId());
			createResponseDto.setSpecialType(specialEntity.getSpecialType());
			return createResponseDto;
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
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		Iterator<Entity> specialEntitys = null;
		CodeTable ct;
		Dao dao = null;
		SearchSpecialRequestDto searchSpecialDto = null;
		String id = null;
		SearchSpecialResponseDto searchResponseDto = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			searchSpecialDto = context.getDomain(SearchSpecialRequestDto.class);
			valiParaNotNull(searchSpecialDto.getStartCount(), "开始条数");
			valiParaNotNull(searchSpecialDto.getPageSize(), "查询条数");
			// 获取用户ID
			id = this.getSessionUserId(context);
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");

			// 封装查询条件
			SearchSpecialRequestEntity searchSpecialRequest = new SearchSpecialRequestEntity();
			searchSpecialRequest.setPageSize(searchSpecialDto.getPageSize());
			searchSpecialRequest.setSpecialName(searchSpecialDto.getSpecialName());
			searchSpecialRequest.setSpecialState(searchSpecialDto.getSpecialState());
			searchSpecialRequest.setSpecialType(searchSpecialDto.getSpecialType());
			searchSpecialRequest.setStartCount(searchSpecialDto.getStartCount());
			searchSpecialRequest.setTagName(searchSpecialDto.getTagName());
			searchSpecialRequest.setUserId(id);
			searchSpecialRequest.setTimeEnd(searchSpecialDto.getTimeEnd());
			searchSpecialRequest.setTimeStart(searchSpecialDto.getTimeStart());
			specialEntitys = dao.query(searchSpecialRequest);
			this.valiParaNotNull(specialEntitys, "专题查询结果");
			CountDto count = (CountDto) dao.query(searchSpecialRequest, false);
			this.valiParaNotNull(count, "专题查询结果总条数");

			// 封装返回数据
			searchResponseDto = new SearchSpecialResponseDto();
			searchResponseDto.setList(specialEntitys);
			searchResponseDto.setTotalCount(count.getCount());
			searchResponseDto.setPageSize(searchSpecialRequest.getPageSize());
			searchResponseDto.setStartCount(searchSpecialRequest.getStartCount());
			return searchResponseDto;
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
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		SpecialEntity specialEntity = null;
		CodeTable ct;
		Dao dao = null;
		String specialId = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			specialId = context.getDomain(String.class);
			this.valiParaItemStrNullOrEmpty(specialId, "专题id");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");

			specialEntity = SpecialManager.getSpecialEntityById(context, specialId);
			if (specialEntity == null) {
				throw new BlAppException(-1, "专题编号错误");
			}
			this.checkCurrentUser(context, specialEntity.getUserId());
			// 封装返回数据
			SpecialContextDto specialContextDto = new SpecialContextDto();
			specialContextDto.setSpecialId(specialId);
			specialContextDto.setData(FastJson.json2Bean(specialEntity.getContext(), ContextDataDto.class));
			return specialContextDto;
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
	public String copy(RequestContext context) throws BlAppException, DaoAppException {
		SpecialEntity specialEntity = null;
		CodeTable ct;
		Dao dao = null;
		String id = null;
		CopySpecialDto copySpecialDto = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			copySpecialDto = context.getDomain(CopySpecialDto.class);
			this.valiPara(copySpecialDto);
			this.valiParaItemStrNullOrEmpty(copySpecialDto.getSpecialId(), "专题编号");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");
			id = this.getSessionUserId(context);
			// 封装查询条件
			specialEntity = SpecialManager.getSpecialEntityById(context, copySpecialDto.getSpecialId());
			if (specialEntity == null) {
				throw new BlAppException(-1, "专题编号错误");
			}
			this.checkCurrentUser(context, specialEntity.getUserId());
			// 新建专题实体
			SpecialEntity specialEntityCopy = new SpecialEntity();
			specialEntityCopy.setContext(specialEntity.getContext());
			specialEntityCopy.setCoverImg(specialEntity.getCoverImg());
			if (!Str.isNullOrEmpty(copySpecialDto.getSpecialName())) {
				this.valiName(copySpecialDto.getSpecialName(), "专题名称", 20);
				specialEntityCopy.setSpecialName(copySpecialDto.getSpecialName());
				specialEntityCopy.setPublishName(copySpecialDto.getSpecialName());
			} else {
				specialEntityCopy.setSpecialName(specialEntity.getSpecialName());
				specialEntityCopy.setPublishName(specialEntity.getPublishName());
			}
			specialEntityCopy.setSpecialType(specialEntity.getSpecialType());
			specialEntityCopy.setSummary(specialEntity.getSummary());
			specialEntityCopy.setTemplateId(specialEntity.getTemplateId());
			specialEntityCopy.setUserId(id);
			specialEntityCopy.setCreatetime(new Date());
			specialEntityCopy.setSpecialState(SpecialState.HASSAVE.getValue());
			specialEntityCopy.setPublishState(PublishState.CANNOTPUBLISH.getValue());
			specialEntityCopy.setSpecialId(KeyFactory.newKey(KeyFactory.KEY_SPECIAL));

			// 创建连接实体
			ModelConfigEntity modelConfig = new ModelConfigEntity();
			modelConfig.setModelId(specialEntityCopy.getSpecialId());
			modelConfig.setModelType(ManuscriptType.SPECIAL.getValue());
			dao.save(modelConfig);

			Integer modelConfigId = modelConfig.getModelConfigId();
			// specialEntityCopy.setModelConfigId(modelConfigId);

			// 创建专题
			ManuscriptManager.createManuscript(context, specialEntityCopy);
			// 保存连接实体参数
			ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(), modelConfigId + "",
					ManuscriptParameterType.modelConfig);

			List<String> tags = new ArrayList<String>();
			for (ConfigTagEntity tag : specialEntity.getConfigTags()) {
				tags.add(tag.getTag());
			}
			// 保存标签
			ManuscriptManager.saveManscriptParameters(context, specialEntityCopy.getSpecialId(), tags,
					ManuscriptParameterType.tag);

			List<String> keywords = new ArrayList<String>();
			for (ConfigKeywordEntity keyword : specialEntity.getConfigKeywords()) {
				keywords.add(keyword.getKeyword());
			}
			// 保存关键字
			ManuscriptManager.saveManscriptParameters(context, specialEntityCopy.getSpecialId(), keywords,
					ManuscriptParameterType.keyword);

			if (specialEntity.getConfig() != null) {
				// 保存主色调
				ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(),
						specialEntity.getConfig().getMainColor(), ManuscriptParameterType.manuscriptMainColor);
				// 保存 推广执行时间 参数
				ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(),
						TimeUtil.format(specialEntity.getConfig().getStartTime()), ManuscriptParameterType.statrttime);

				ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(),
						TimeUtil.format(specialEntity.getConfig().getEndTime()), ManuscriptParameterType.endtime);

			}
			if (specialEntity.getConfigActive() != null) {
				// 保存 推广执行时间 参数
				ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(),
						TimeUtil.format(specialEntity.getConfigActive().getStartTime()),
						ManuscriptParameterType.statrtactivetime);

				ManuscriptManager.saveManscriptParameter(context, specialEntityCopy.getSpecialId(),
						TimeUtil.format(specialEntity.getConfigActive().getEndTime()),
						ManuscriptParameterType.endactivetime);
			}
			// 复制插件
			PluginManager.copyPlugin(context, specialEntity.getSpecialId(), specialEntityCopy.getSpecialId(),
					ManuscriptType.SPECIAL, ManuscriptType.SPECIAL);
			// 保存模版使用记录
			TemplateManager.saveTemplateUseLog(context, specialEntity.getTemplateId());
			return specialEntityCopy.getSpecialId();
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
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		DeleteSpecialDto specialIds = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			specialIds = context.getDomain(DeleteSpecialDto.class);
			valiParaNotNull(specialIds, "专题id");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");
			for (String specialId : specialIds.getSpecialIds()) {
				SpecialEntity specialEntity = SpecialManager.getSpecialEntityById(context, specialId);
				this.valiDomainIsNull(specialEntity, "specialEntity");
				this.checkCurrentUser(context, specialEntity.getUserId());
				ManuscriptManager.delete(context, specialId);
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String publish(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Deploy deploy = null;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 中途岛发布
			deploy = new DeployImpl();
			if (!deploy.deploy(true, context, ManuscriptType.SPECIAL))
				throw new BlAppException(-1, "预览失败");
			// 扣除发布次数
			MemberManager.useUserServiceResources(context, getSessionUserId(context),
					PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.PUBLISHNUM), 1);
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
	public Entity loadtags(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "模板");
			IdEntity<String> userid = new IdEntity<String>();
			// 设置userId的值
			this.checkFrontUserLogined(context);
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			userid.setId(this.getSessionUserId(context));
			return new SpecialTagResponseDto(dao.query(userid));
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
	public Entity save(RequestContext context) throws BlAppException, DaoAppException {
		SaveSpecialDto dto = null;
		SpecialEntity specialEntity = null;
		CreateSpecialResponseDto createResponseDto = null;
		CodeTable ct;
		try {

			// 获取请求数据
			dto = context.getDomain(SaveSpecialDto.class);
			this.valiPara(dto);
			specialEntity = SpecialManager.saveSpecial(context, dto);
			// 封装返回数据
			createResponseDto = new CreateSpecialResponseDto();
			createResponseDto.setSpeciaId(specialEntity.getSpecialId());
			createResponseDto.setSpecialType(specialEntity.getSpecialType());
			return createResponseDto;

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
	public Entity savespecialname(RequestContext context) throws BlAppException, DaoAppException {
		SaveSpecialNameDto dto = null;
		SpecialEntity specialEntity = null;
		CreateSpecialResponseDto createResponseDto = null;
		CodeTable ct;
		try {
			// 检查是否登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 获取请求数据
			dto = context.getDomain(SaveSpecialNameDto.class);
			this.valiPara(dto);
			this.valiName(dto.getName(), "专题名称", 20);
			String specialId = dto.getSpecialId();
			// 如果没有专题编号 则创建专题 并保存内容
			if (Str.isNullOrEmpty(dto.getSpecialId())) {
				this.valiParaItemObjectNull(dto.getTerminalType(), "模板类型");
				this.valiParaItemNumBetween(1, 2, dto.getTerminalType(), "终端类型");
				specialId = KeyFactory.newKey(KeyFactory.KEY_SPECIAL);
				// 构造专题数据
				specialEntity = new SpecialEntity();
				specialEntity.setCreatetime(new java.util.Date());
				specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());
				specialEntity.setSpecialType(dto.getTerminalType());
				specialEntity.setUserId(this.getSessionUserId(context));
				specialEntity.setSpecialId(specialId);
				specialEntity.setPublishState(PublishState.CANNOTPUBLISH.getValue());
				// 创建专题
				ManuscriptManager.createManuscript(context, specialEntity);

			}
			// 保存专题名称
			ManuscriptManager.saveManscriptParameter(context, specialId, dto.getName(),
					ManuscriptParameterType.manuscriptName);

			// 封装返回数据
			createResponseDto = new CreateSpecialResponseDto();
			createResponseDto.setSpeciaId(dto.getSpecialId());
			return createResponseDto;

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
	@InterfaceDocAnnotation(methodVersion = "1.0.2")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void savepublishinfo(RequestContext context) throws BlAppException, DaoAppException {
		SavepublishinfoDto savepublishinfoDto = null;
		SpecialEntity specialEntity = null;
		CodeTable ct;
		Dao dao = null;
		try {
			// 检查是否登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 获取请求数据
			savepublishinfoDto = context.getDomain(SavepublishinfoDto.class);
			this.valiPara(savepublishinfoDto);
			String specialId = savepublishinfoDto.getSpecialId();
			this.valiParaItemStrNullOrEmpty(specialId, "专题编号");
			this.valiName(savepublishinfoDto.getPublishName(), "专题名称", 20);
			this.valiSummary(savepublishinfoDto.getSummary(), "作品描述", 150, false);
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(specialId);
			// 查询专题数据
			specialEntity = (SpecialEntity) dao.query(searchSpecialRequest, false);
			this.valiDomainIsNull(specialEntity, "专题");
			this.checkCurrentUser(context, specialEntity.getUserId());
			logger.info("是否去版权:" + savepublishinfoDto.getIsUnRights());
			if (savepublishinfoDto.getIsUnRights()) {
				// 如果为首次去版权 才会扣除去版权次数
				if (Str.isNullOrEmpty(specialEntity.getExtensionId())
						|| UnRightsManger.queryUnRightsLog(context, specialEntity.getExtensionId()) == null) {
					// 验证去版权次数
					UnRightsTimesEntity unRightsTimesEntity = UnRightsManger.searchTimes(context,
							specialEntity.getUserId());
					if (unRightsTimesEntity == null || unRightsTimesEntity.getUnrightsTimes() <= 0) {
						ct = CodeTable.BL_UNRIGHTS_TIMES_ERROR;
						throw new BlAppException(ct.getValue(), ct.getDesc());
					}
					logger.info("去版权剩余次数:" + unRightsTimesEntity.getUnrightsTimes());
				}
				// 保存去版权设置
				ManuscriptManager.saveManscriptParameter(context, specialId,
						ManuscriptParameterType.unrights.toString(), ManuscriptParameterType.unrights);
			} else {
				// 保存去版权设置
				ManuscriptManager.saveManscriptParameter(context, specialId, "disable",
						ManuscriptParameterType.unrights);
			}
			// 保存标签
			ManuscriptManager.saveManscriptParameters(context, specialId,
					this.transformationTags(savepublishinfoDto.getTagNames()), ManuscriptParameterType.tag);
			// // 保存关键字
			// ManuscriptManager.saveManscriptParameters(context,
			// specialEntity.getSpecialId(),
			// this.transformationString(savepublishinfoDto.getKeyword(),
			// false), ManuscriptParameterType.keyword);

			// 保存 推广执行时间 参数
			ManuscriptManager.saveManscriptParameter(context, specialId,
					TimeUtil.format(savepublishinfoDto.getPublishStart()), ManuscriptParameterType.statrttime);

			ManuscriptManager.saveManscriptParameter(context, specialId,
					TimeUtil.format(savepublishinfoDto.getPublishEnd()), ManuscriptParameterType.endtime);
			// 可发布状态
			ManuscriptManager.saveManscriptParameter(context, specialId, PublishState.CANPUBLISH.getValue() + "",
					ManuscriptParameterType.publishstate);
			// 发布时间
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(), TimeUtil.format(new Date()),
					ManuscriptParameterType.publishtime);

			// 发布名称
			ManuscriptManager.saveManscriptParameter(context, specialId, savepublishinfoDto.getPublishName(),
					ManuscriptParameterType.publishname);
			if (!Str.isNullOrEmpty(savepublishinfoDto.getCoverImg())) {
				// 保存 封面
				ManuscriptManager.saveManscriptParameter(context, specialId, savepublishinfoDto.getCoverImg(),
						ManuscriptParameterType.manuscriptImg);
			}
			// // 保存 主色调
			// ManuscriptManager.saveManscriptParameter(context,
			// specialEntity.getSpecialId(),
			// savepublishinfoDto.getMainColor(),
			// ManuscriptParameterType.manuscriptMainColor);

			// 保存名称
			ManuscriptManager.saveManscriptParameter(context, specialId, savepublishinfoDto.getPublishName(),
					ManuscriptParameterType.manuscriptName);

			// 保存简介
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadpublishinfo(RequestContext context) throws BlAppException, DaoAppException {
		SpecialEntity specialEntity = null;
		LoadPublishInfoResponseDto loadPublishInfoResponseDto = null;
		CodeTable ct;
		Dao dao = null;
		String specialId = null;
		try {
			// // 检查是否登录
			// this.checkFrontUserLogined(context);
			// // 验证普通用户
			// this.checkFrontUserType(context, UserType.GENERAL_USER);
			// 获取请求数据
			specialId = context.getDomain(String.class);
			this.valiParaItemStrNullOrEmpty(specialId, "专题id");
			// 获取数据连接
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "专题");
			// 封装查询条件
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(specialId);
			specialEntity = (SpecialEntity) dao.query(searchSpecialRequest, false);
			this.valiParaNotNull(specialEntity, "专题加载结果");
			this.checkCurrentUser(context, specialEntity.getUserId());

			loadPublishInfoResponseDto = new LoadPublishInfoResponseDto();
			loadPublishInfoResponseDto.setSpecialId(specialEntity.getSpecialId());
			loadPublishInfoResponseDto.setCoverImg(specialEntity.getCoverImg());
			loadPublishInfoResponseDto.setSummary(specialEntity.getSummary());
			loadPublishInfoResponseDto.setPublishName(specialEntity.getPublishName());
			if (specialEntity.getConfig() != null) {
				loadPublishInfoResponseDto.setMainColor(specialEntity.getConfig().getMainColor());
				loadPublishInfoResponseDto.setPublishEnd(specialEntity.getConfig().getEndTime());
				loadPublishInfoResponseDto.setPublishStart(specialEntity.getConfig().getStartTime());
			}
			if (specialEntity.getConfigActive() != null) {
				loadPublishInfoResponseDto.setActiveEnd(specialEntity.getConfigActive().getEndTime());
				loadPublishInfoResponseDto.setActiveStart(specialEntity.getConfigActive().getStartTime());
			}
			ActivePluginEntity pluginEntity = PluginManager.queryActivePluginByRelationId(context,
					specialEntity.getSpecialId());
			if (pluginEntity == null || pluginEntity.getActivePluginType() == null) {
				loadPublishInfoResponseDto.setActiveType("-1");
			} else {
				loadPublishInfoResponseDto.setActiveType(pluginEntity.getActivePluginType().getValue() + "");
			}

			List<String> keywords = new ArrayList<String>();
			for (ConfigKeywordEntity configKeyword : specialEntity.getConfigKeywords()) {
				if (Str.isNullOrEmpty(configKeyword.getKeyword())) {
					continue;
				}
				keywords.add(configKeyword.getKeyword());
			}
			loadPublishInfoResponseDto.setKeywords(keywords);

			List<String> tagNames = new ArrayList<String>();
			for (ConfigTagEntity configTag : specialEntity.getConfigTags()) {
				if (Str.isNullOrEmpty(configTag.getTag())) {
					continue;
				}
				tagNames.add(configTag.getTag());
			}
			loadPublishInfoResponseDto.setTagNames(tagNames);
			// 加载是否已发布过去版权
			isDoneUnRights(context, specialEntity, loadPublishInfoResponseDto);
			// 加载是否去版权
			isUnRights(context, loadPublishInfoResponseDto, specialId);
			return loadPublishInfoResponseDto;
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
	 * 是否去版权
	 * 
	 * @param context
	 * @param loadPublishInfoResponseDto
	 * @param specialId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private void isUnRights(RequestContext context, LoadPublishInfoResponseDto loadPublishInfoResponseDto,
			String specialId) throws DaoAppException, BlAppException {
		ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(context, specialId,
				ManuscriptParameterType.unrights);
		if (unrights != null) {
			loadPublishInfoResponseDto
					.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
		}
	}

	/**
	 * 该专题是否发布过去版权
	 * 
	 * @param context
	 * @param specialEntity
	 * @param loadPublishInfoResponseDto
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private void isDoneUnRights(RequestContext context, SpecialEntity specialEntity,
			LoadPublishInfoResponseDto loadPublishInfoResponseDto) throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(specialEntity.getExtensionId())) {
			loadPublishInfoResponseDto.setDoneUnrights(false);
		} else {
			if (UnRightsManger.queryUnRightsLog(context, specialEntity.getExtensionId()) == null) {
				loadPublishInfoResponseDto.setDoneUnrights(false);
			} else {
				loadPublishInfoResponseDto.setDoneUnrights(true);
			}
		}
	}

	@Override
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
			if (!deploy.deploy(false, context, ManuscriptType.SPECIAL))
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
}
