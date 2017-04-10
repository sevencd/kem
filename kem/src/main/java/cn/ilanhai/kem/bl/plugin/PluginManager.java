package cn.ilanhai.kem.bl.plugin;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.plugin.PluginDao;
import cn.ilanhai.kem.dao.plugin.activeplugin.ActivePluginDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.plugin.PluginEntity;
import cn.ilanhai.kem.domain.plugin.QueryPlugin;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.QueryActivePluginByRelationId;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.special.ConfigActiveEntity;

public class PluginManager {
	private static Class<?> currentclass = PluginDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();
	private static Logger logger = Logger.getLogger(PluginManager.class);

	/**
	 * 抽奖时间验证 如果如果为true 则早于活动时间 如果为false 则晚于活动时间 如果为null 则为活动时间
	 * 
	 * @param requestContext
	 * @param entity
	 * @return
	 * @throws BlAppException
	 */
	public static Integer CheckDrawPrizeTime(RequestContext requestContext, ActivePluginEntity entity)
			throws BlAppException {
		ConfigActiveEntity configActiveEntity = SpecialManager.getConfigActiveEntity(requestContext, entity);
		if (configActiveEntity == null || configActiveEntity.getStartTime() == null
				|| configActiveEntity.getEndTime() == null) {
			return 3;
		}
		if (new Date().before(configActiveEntity.getStartTime())) {
			return 1;
		} else if (new Date().after(configActiveEntity.getEndTime())) {
			return 2;
		}
		return 3;
	}

	public static void creatPlugin(RequestContext requestContext, String modelId, ManuscriptType relationType,
			PluginType pluginType, ActivePluginType activePluginType) throws SessionContainerException, BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			if (PluginType.FORMPLUGIN.equals(pluginType)) {
				saveFormPlugin(requestContext, modelId, relationType, pluginType, dao);
			} else if (PluginType.ACTIVEPLUGIN.equals(pluginType)) {
				if (activePluginType != null) {
					saveActivePlugin(requestContext, modelId, relationType, pluginType, activePluginType, dao);
				} else {
					disableAllPlugin(requestContext, modelId);
				}
			} else {
				disableAllPlugin(requestContext, modelId);
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private static void saveActivePlugin(RequestContext requestContext, String modelId, ManuscriptType relationType,
			PluginType pluginType, ActivePluginType activePluginType, Dao dao)
			throws DaoAppException, SessionContainerException, BlAppException {
		ActivePluginEntity pluginEntity = queryActivePlugin(requestContext, modelId, activePluginType);
		ActivePluginEntity activePluginEntity = new ActivePluginEntity();
		if (pluginEntity != null && pluginEntity.getPluginType() != null) {
			activePluginEntity.setPluginId(pluginEntity.getPluginId());
		} else {
			disableAllPlugin(requestContext, modelId);
		}
		activePluginEntity.setRelationType(relationType);
		activePluginEntity.setRelationId(modelId);
		activePluginEntity.setCreatetime(new Date());
		activePluginEntity.setPluginType(pluginType);
		activePluginEntity.setActivePluginType(activePluginType);
		activePluginEntity.setUsed(true);
		activePluginEntity.setUserId(BLContextUtil.getSessionUserId(requestContext, false));
		int val = dao.save(activePluginEntity);
		BLContextUtil.valiSaveDomain(val, "活动插件");
	}

	private static void saveFormPlugin(RequestContext requestContext, String modelId, ManuscriptType relationType,
			PluginType pluginType, Dao dao) throws BlAppException, DaoAppException, SessionContainerException {
		PluginEntity pluginEntity;
		pluginEntity = queryPlugin(requestContext, modelId, pluginType);
		disableAllPlugin(requestContext, modelId);
		if (pluginEntity == null) {
			pluginEntity = new PluginEntity();
		}
		pluginEntity.setUsed(true);
		pluginEntity.setCreatetime(new Date());
		pluginEntity.setUserId(BLContextUtil.getSessionUserId(requestContext));
		pluginEntity.setRelationType(relationType);
		pluginEntity.setRelationId(modelId);
		pluginEntity.setPluginType(pluginType);
		int val = dao.save(pluginEntity);
		BLContextUtil.valiSaveDomain(val, "表单插件");
	}

	public static void disableAllPlugin(RequestContext requestContext, String modelId)
			throws DaoAppException, BlAppException {
		IdEntity<String> relationId = null;
		relationId = new IdEntity<String>();
		relationId.setId(modelId);
		getDao(requestContext).save(relationId);
	}

	public static void copyPlugin(RequestContext requestContext, String resouceId, String targetId,
			ManuscriptType resouceRelationType, ManuscriptType targetRelationType)
			throws SessionContainerException, BlAppException {
		Dao dao, activeDao;
		logger.info("复制插件开始 源:[" + resouceId + "],目标:[" + targetId + "]");
		try {
			dao = getDao(requestContext);
			activeDao = daoFactory.getDao(requestContext, ActivePluginDao.class);
			BLContextUtil.valiDaoIsNull(activeDao, "活动插件");
			QueryPlugin queryPlugin = new QueryPlugin();
			PluginEntity pluginEntity;
			queryPlugin.setRelationId(resouceId);
			queryPlugin.setRelationType(resouceRelationType);
			QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
			queryDto.setRelationId(resouceId);
			ActivePluginEntity entity = (ActivePluginEntity) activeDao.query(queryDto, false);
			if (entity != null) {
				logger.info("复制活动插件");
				ActivePluginEntity activePluginEntity = new ActivePluginEntity();
				activePluginEntity.setRelationId(targetId);
				activePluginEntity.setRelationType(targetRelationType);
				activePluginEntity.setActivePluginType(entity.getActivePluginType());
				pluginEntity = (PluginEntity) dao.query(activePluginEntity, false);
				if (pluginEntity == null) {
					disableAllPlugin(requestContext, targetId);
					entity.setPluginId(null);
				} else {
					entity.setPluginId(pluginEntity.getPluginId());
				}
				entity.setRelationId(targetId);
				entity.setRelationType(targetRelationType);
				entity.setCreatetime(new Date());
				entity.setUsed(true);
				entity.setUserId(BLContextUtil.getSessionUserId(requestContext));
				int val = activeDao.save(entity);
				ManuscriptParameterEntity statrtactivetime = ManuscriptManager.getManuscriptParameterById(
						requestContext, resouceId, ManuscriptParameterType.statrtactivetime);
				ManuscriptParameterEntity endactivetime = ManuscriptManager.getManuscriptParameterById(requestContext,
						resouceId, ManuscriptParameterType.endactivetime);
				if (statrtactivetime != null && endactivetime != null) {
					logger.info("活动时间设置复制 statrtactivetime:[" + statrtactivetime.getParameter() + "],endactivetime:["
							+ endactivetime.getParameter() + "]");
					ManuscriptManager.saveManscriptParameter(requestContext, targetId, statrtactivetime.getParameter(),
							ManuscriptParameterType.statrtactivetime);
					ManuscriptManager.saveManscriptParameter(requestContext, targetId, endactivetime.getParameter(),
							ManuscriptParameterType.endactivetime);
				}
				BLContextUtil.valiSaveDomain(val, "活动插件复制");
			} else {
				queryPlugin.setPluginType(PluginType.FORMPLUGIN);
				queryPlugin.setIsUsed(true);
				pluginEntity = (PluginEntity) dao.query(queryPlugin, false);
				if (pluginEntity != null) {
					logger.info("复制表单插件");
					PluginEntity pluginEntity2 = queryPlugin(requestContext, targetId, PluginType.FORMPLUGIN);
					if (pluginEntity2 == null) {
						disableAllPlugin(requestContext, targetId);
						pluginEntity.setPluginId(null);
					} else {
						pluginEntity.setPluginId(pluginEntity2.getPluginId());
					}
					pluginEntity.setUserId(BLContextUtil.getSessionUserId(requestContext));
					pluginEntity.setCreatetime(new Date());
					pluginEntity.setRelationId(targetId);
					pluginEntity.setRelationType(targetRelationType);
					pluginEntity.setUsed(true);
					int val = dao.save(pluginEntity);
					BLContextUtil.valiSaveDomain(val, "表单插件复制");
				}
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 查询插件
	 * 
	 * @param requestContext
	 * @param relationId
	 * @param relationType
	 * @param pluginType
	 *            插件类型
	 * @return
	 * @throws BlAppException
	 */
	public static PluginEntity queryPlugin(RequestContext requestContext, String relationId, PluginType pluginType)
			throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			QueryPlugin queryPlugin = new QueryPlugin();
			queryPlugin.setRelationId(relationId);
			queryPlugin.setPluginType(pluginType);
			return (PluginEntity) dao.query(queryPlugin, false);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static ActivePluginEntity queryActivePlugin(RequestContext requestContext, String relationId,
			ActivePluginType activePluginType) throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			ActivePluginEntity activePluginEntity = new ActivePluginEntity();
			activePluginEntity.setRelationId(relationId);
			activePluginEntity.setActivePluginType(activePluginType);
			return (ActivePluginEntity) dao.query(activePluginEntity, false);
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 
	 * @param queryActivePluginByRelationId
	 * @return
	 * @throws BlAppException
	 */
	public static ActivePluginEntity queryActivePluginByRelationId(RequestContext requestContext, String relationId)
			throws BlAppException {
		Dao dao;
		CodeTable ct;
		try {
			dao = daoFactory.getDao(requestContext, ActivePluginDao.class);
			if (dao == null) {
				ct = CodeTable.BL_COMMON_GET_DAO;
				String tmp = ct.getDesc();
				tmp = String.format(tmp, "活动插件");
				throw new BlAppException(ct.getValue(), tmp);
			}
			QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
			queryDto.setRelationId(relationId);
			ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
			return entity;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 查询客户信息
	 * 
	 * @param requestContext
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static Iterator<Entity> queryUserInfo(RequestContext requestContext) throws DaoAppException, BlAppException {
		return null;
	}

	private static Dao getDao(RequestContext requestContext) throws DaoAppException, BlAppException {
		CodeTable ct;
		Dao dao = daoFactory.getDao(requestContext, currentclass);
		if (dao == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "插件");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}
	
	/**
	 * 通过ID获取插件实体
	 * @param requestContext
	 * @param relationId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static ActivePluginEntity getActivePlugin(RequestContext requestContext,String relationId) throws DaoAppException, BlAppException {
		Dao dao = BLContextUtil.getDao(requestContext,ActivePluginDao.class );
		BLContextUtil.valiDaoIsNull(dao, "活动插件");

		QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
		queryDto.setRelationId(relationId);
		ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
		return entity;
	}
}
