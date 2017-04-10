package cn.ilanhai.kem.bl.template;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.template.TemplateDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.SearchConfigDataDto;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.TemplateState;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.template.LoadpublishinfoDto;
import cn.ilanhai.kem.domain.template.LoadpublishinfoResponseDto;
import cn.ilanhai.kem.domain.template.SaveTemplateDto;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.domain.template.TemplateUseLogEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.KeyUtil;

public class TemplateManager {
	private static Class<?> currentclass = TemplateDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();
	private static Logger logger = Logger.getLogger(TemplateManager.class);

	/**
	 * 保存模版使用记录
	 * 
	 * @param context
	 * @param templateId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void saveTemplateUseLog(RequestContext context, String templateId)
			throws SessionContainerException, DaoAppException, BlAppException {
		int val = -1;
		// 模版使用记录
		TemplateUseLogEntity logEntity = new TemplateUseLogEntity();
		logEntity.setTemplateId(templateId);
		logEntity.setUserId(context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class));
		logEntity.setUserType(context.getSession().getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class));
		logEntity.setCreateTime(new java.util.Date());
		val = getDao(context).save(logEntity);
		if (val < 0) {
			CodeTable ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "模版");
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 获取模版数据
	 * 
	 * @param templateId
	 * @param requestContext
	 * @return
	 * @throws BlAppException
	 */
	public static TemplateEntity getTemplateEntity(String templateId, RequestContext requestContext)
			throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			IdEntity<String> queryTemplate = new IdEntity<String>();
			queryTemplate.setId(templateId);
			return (TemplateEntity) dao.query(queryTemplate, false);
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
			tmp = String.format(tmp, "专题");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}

	public static Entity searchPublicSeting(RequestContext requestContext) throws BlAppException {
		String entity;
		Dao dao;
		try {
			entity = requestContext.getDomain(String.class);
			String tmp = null;
			if (Str.isNullOrEmpty(entity)) {
				CodeTable ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
				tmp = ct.getDesc();
				tmp = String.format(tmp, "模版");
				throw new BlAppException(ct.getValue(), tmp);
			}
			dao = getDao(requestContext);
			LoadpublishinfoDto loadpublishinfoDto = new LoadpublishinfoDto();
			loadpublishinfoDto.setTemplateId(entity);
			LoadpublishinfoResponseDto loadpublishinfoResponseDto = (LoadpublishinfoResponseDto) dao
					.query(loadpublishinfoDto, false);
			BLContextUtil.valiDomainIsNull(loadpublishinfoResponseDto, "模版");
			SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
			for (String entry : loadpublishinfoResponseDto.getKeywords()) {
				searchConfigDataDto.addKeyWord(entry);
			}
			searchConfigDataDto.setName(loadpublishinfoResponseDto.getPublishName());
			searchConfigDataDto.setSummary(loadpublishinfoResponseDto.getSummary());
			searchConfigDataDto.setUnRights(true);
			return searchConfigDataDto;
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static TemplateEntity saveTemplate(RequestContext context, SaveTemplateDto dto)
			throws BlAppException, SessionContainerException, DaoAppException {
		TemplateEntity templateEntity;
		Dao dao;
		IdEntity<String> idDto;
		String templateId = dto.getTemplateId();
		if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			// 验证普通用户
			BLContextUtil.checkFrontUserType(context, UserType.DESIGNERS);

		} else if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {

		} else {
			CodeTable ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}

		if (Str.isNullOrEmpty(templateId)) {
			logger.info("模版编号为空 则创建模版");
			templateId = KeyFactory.newKey(KeyFactory.KEY_TEMPLATE);
			templateEntity = creatTemplateById(context, dto, templateId);
		} else if (ManuscriptType.DEF.equals(KeyUtil.getKey(templateId))) {
			logger.info("该模版编号为临时模板编号:" + templateId);
			String newTemplateId = KeyFactory.newKey(KeyFactory.KEY_TEMPLATE);
			templateEntity = creatTemplateById(context, dto, newTemplateId);
			ManuscriptType manuscriptType = ManuscriptType.getEnum(dto.getTerminalType());
			logger.info("复制插件");
			PluginManager.copyPlugin(context, templateId, newTemplateId, manuscriptType, manuscriptType);
			logger.info("复制插件成功");
			logger.info("为该模版生成实际编号:" + templateId);

		} else {
			logger.info("模版编号不为空 查询模版 并保存");
			dao = BLContextUtil.getDao(context, currentclass);
			BLContextUtil.valiDaoIsNull(dao, "模板");
			idDto = new IdEntity<String>();
			idDto.setId(templateId);
			templateEntity = (TemplateEntity) dao.query(idDto, false);
			BLContextUtil.valiDomainIsNull(templateEntity, "模板");
			BLContextUtil.checkCurrentUser(context, templateEntity.getUserId());

			// 校验模版状态
			checkTemplateState(templateEntity);

		}

		String templateContent = JSON.toJSONString(dto.getData());
		templateEntity.setTemplateContent(templateContent);
		// 修改 模板状态
		templateEntity.setTemplateState(TemplateState.UNSUBMITTED);
		logger.info("保存模版内容开始");
		ManuscriptManager.saveManuscript(context, templateEntity);
		// 保存模版内容
		ManuscriptManager.saveManscriptContent(context, templateId, templateContent);
		logger.info("保存模版内容结束");

		logger.info("保存模版插件");
		if (PluginType.ACTIVEPLUGIN.getValue().equals(dto.getPluginType())) {
			BLContextUtil.valiParaItemObjectNull(dto.getActiveType(), "当插件类型时活动类型不能为空");
		}
		if (BLContextUtil.checkCreatePlugin(dto.getPluginType())) {
			PluginManager.creatPlugin(context, templateId, ManuscriptType.TEMPLATE,
					PluginType.getEnum(dto.getPluginType()), ActivePluginType.getEnum(dto.getActiveType()));
		} else {
			PluginManager.disableAllPlugin(context, templateId);
		}

		return templateEntity;
	}

	private static TemplateEntity creatTemplateById(RequestContext context, SaveTemplateDto dto, String templateId)
			throws BlAppException, SessionContainerException, DaoAppException {
		TemplateEntity templateEntity;
		BLContextUtil.valiParaItemObjectNull(dto.getTerminalType(), "终端类型");
		BLContextUtil.valiParaItemNumBetween(1, 2, dto.getTerminalType(), "终端类型");
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
		return templateEntity;
	}

	private static void checkTemplateState(TemplateEntity templateEntity) throws BlAppException {
		CodeTable ct;
		if (templateEntity.getTemplateState().equals(TemplateState.VERIFY)
				|| templateEntity.getTemplateState().equals(TemplateState.ADDED)) {
			ct = CodeTable.BL_TEMPLATE_SAVE_STATUS;
			throw new BlAppException(ct.getValue(), "审核中和已上架模版不允许该操作");
		}
	}
}
