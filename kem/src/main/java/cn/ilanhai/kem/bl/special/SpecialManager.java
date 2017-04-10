package cn.ilanhai.kem.bl.special;

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
import cn.ilanhai.kem.dao.special.SpecialDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.SearchConfigDataDto;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.PublishState;
import cn.ilanhai.kem.domain.enums.SpecialState;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.extension.DisableExtensionRequestDto;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.special.ConfigActiveEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.SaveSpecialDto;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.KeyUtil;

public class SpecialManager {
	private static Class<?> currentclass = SpecialDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();
	private static Logger logger = Logger.getLogger(SpecialManager.class);

	/**
	 * 保存专题
	 * 
	 * @param context
	 * @param dto
	 * @return
	 * @throws BlAppException
	 * @throws SessionContainerException
	 * @throws DaoAppException
	 */
	public static SpecialEntity saveSpecial(RequestContext context, SaveSpecialDto dto)
			throws BlAppException, SessionContainerException, DaoAppException {
		SpecialEntity specialEntity;
		if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			// 验证普通用户
			BLContextUtil.checkFrontUserType(context, UserType.GENERAL_USER);

		} else if (context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {

		} else {
			CodeTable ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		Dao dao;
		String specialId = dto.getSpecialId();
		// 如果没有专题编号 则创建专题 并保存内容
		if (Str.isNullOrEmpty(dto.getSpecialId())) {
			logger.info("专题编号为空 则创建专题");
			specialId = KeyFactory.newKey(KeyFactory.KEY_SPECIAL);
			specialEntity = creatSpecialById(context, dto, specialId);
			// 保存专题内容

		} else if (ManuscriptType.DEF.equals(KeyUtil.getKey(specialId))) {
			logger.info("该专题编号为临时模板编号:" + specialId);
			String newSpecialId = KeyFactory.newKey(KeyFactory.KEY_SPECIAL);
			specialEntity = creatSpecialById(context, dto, specialId);
			ManuscriptType manuscriptType = ManuscriptType.getEnum(dto.getTerminalType());
			logger.info("复制插件");
			PluginManager.copyPlugin(context, specialId, newSpecialId, manuscriptType, manuscriptType);
			logger.info("复制插件成功");
			logger.info("为该专题生成实际编号:" + newSpecialId);

		} else {
			logger.info("专题编号不为空 查询模版 并保存");
			// 获取数据连接
			dao = BLContextUtil.getDao(context, currentclass);
			BLContextUtil.valiDaoIsNull(dao, "专题");
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(specialId);
			// 查询专题数据
			specialEntity = (SpecialEntity) dao.query(searchSpecialRequest, false);
			BLContextUtil.valiDomainIsNull(specialEntity, "专题");
			BLContextUtil.checkCurrentUser(context, specialEntity.getUserId());
			// 将前台请求数据转为json数据
			specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());

			// 更新专题状态
			ManuscriptManager.saveManuscript(context, specialEntity);
		}
		String specialContent = JSON.toJSONString(dto.getData());
		specialEntity.setContext(specialContent);
		// 保存专题内容
		logger.info("保存专题内容开始");
		ManuscriptManager.saveManscriptContent(context, specialId, specialContent);
		logger.info("保存专题内容结束");

		logger.info("保存专题插件");
		if (PluginType.ACTIVEPLUGIN.getValue().equals(dto.getPluginType())) {
			BLContextUtil.valiParaItemObjectNull(dto.getActiveType(), "当插件类型时活动类型不能为空");
		}
		if (BLContextUtil.checkCreatePlugin(dto.getPluginType())) {
			PluginManager.creatPlugin(context, specialId, ManuscriptType.SPECIAL,
					PluginType.getEnum(dto.getPluginType()), ActivePluginType.getEnum(dto.getActiveType()));
		} else {
			PluginManager.disableAllPlugin(context, specialId);
		}
		return specialEntity;
	}

	private static SpecialEntity creatSpecialById(RequestContext context, SaveSpecialDto dto, String specialId)
			throws BlAppException, SessionContainerException, DaoAppException {
		SpecialEntity specialEntity;
		BLContextUtil.valiParaItemObjectNull(dto.getTerminalType(), "终端类型");
		BLContextUtil.valiParaItemNumBetween(1, 2, dto.getTerminalType(), "终端类型");
		logger.info("模版编号:" + specialId);
		// 构造专题数据
		specialEntity = new SpecialEntity();
		specialEntity.setCreatetime(new java.util.Date());
		specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());
		specialEntity.setSpecialType(dto.getTerminalType());
		specialEntity.setUserId(BLContextUtil.getSessionUserId(context));
		specialEntity.setSpecialId(specialId);
		specialEntity.setPublishState(PublishState.CANNOTPUBLISH.getValue());
		// 创建专题
		logger.info("创建专题开始");
		ManuscriptManager.createManuscript(context, specialEntity);
		logger.info("创建专题结束");
		return specialEntity;
	}

	/**
	 * 获取专题实体
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static SpecialEntity getSpecialEntityById(RequestContext requestContext, String specialId)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			SpecialEntity specialEntity;
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(specialId);
			specialEntity = (SpecialEntity) dao.query(searchSpecialRequest, false);
			return specialEntity;
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 保存专题实体
	 * 
	 * @param requestContext
	 * @param specialId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static int saveSpecialEntity(RequestContext requestContext, SpecialEntity specialEntity)
			throws DaoAppException, BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			return dao.save(specialEntity);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static ConfigActiveEntity getConfigActiveEntity(RequestContext requestContext, ActivePluginEntity entity)
			throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			return (ConfigActiveEntity) dao.query(entity, false);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 专题无效
	 * 
	 * @param context
	 * @param specialId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void disable(RequestContext context, String specialId) throws DaoAppException, BlAppException {
		CodeTable ct;
		SpecialEntity specialEntity = null;
		try {
			if (Str.isNullOrEmpty(specialId)) {
				return;
			}

			// 查询专题数据
			specialEntity = getSpecialEntityById(context, specialId);
			if (specialEntity == null) {
				return;
			}
			specialEntity.setSpecialState(SpecialState.HASDISABLE.getValue());
			DisableExtensionRequestDto disableExtensionRequestDto = context.getDomain(DisableExtensionRequestDto.class);
			BLContextUtil.valiDomainIsNull(disableExtensionRequestDto, "推广禁用请求数据错误");
			BLContextUtil.valiParaItemStrNullOrEmpty(disableExtensionRequestDto.getExtensionId(), "推广编号");
			specialEntity.setExtensionId(disableExtensionRequestDto.getExtensionId());
			// 更新专题状态
			ManuscriptManager.saveManuscript(context, specialEntity);
			// 保存专题 的推广关联id
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					disableExtensionRequestDto.getExtensionId(), ManuscriptParameterType.extensiontarget);
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

	/**
	 * 查询发布设置
	 * 
	 * @param requestContext
	 * @return
	 * @throws BlAppException
	 */
	public static Entity searchPublicSeting(RequestContext requestContext) throws BlAppException {
		String entity;
		Dao dao;
		SearchConfigDataDto searchConfigDataDto = new SearchConfigDataDto();
		try {
			// 获取入参
			entity = requestContext.getDomain(String.class);
			String tmp = null;
			// 入参不可为空
			if (Str.isNullOrEmpty(entity)) {
				CodeTable ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
				tmp = ct.getDesc();
				tmp = String.format(tmp, "专题");
				throw new BlAppException(ct.getValue(), tmp);
			}
			// 获取dao
			dao = getDao(requestContext);
			// 查询对象
			SpecialEntity specialEntity;
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(entity);
			specialEntity = (SpecialEntity) dao.query(searchSpecialRequest, false);
			BLContextUtil.valiDomainIsNull(specialEntity, "专题");

			// 加载是否去版权
			ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(requestContext, entity,
					ManuscriptParameterType.unrights);
			// 组织返回值
			if (unrights != null) {
				// 设置是否去版权
				searchConfigDataDto
						.setUnRights(ManuscriptParameterType.unrights.toString().equals(unrights.getParameter()));
			}
			for (ConfigKeywordEntity entry : specialEntity.getConfigKeywords()) {
				searchConfigDataDto.addKeyWord(entry.getKeyword());
			}
			searchConfigDataDto.setName(specialEntity.getPublishName());
			searchConfigDataDto.setSummary(specialEntity.getSummary());
			logger.info("查询结果" + searchConfigDataDto);
			return searchConfigDataDto;
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
}
