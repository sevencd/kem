package cn.ilanhai.kem.deploy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.bindhost.BindHostManager;
import cn.ilanhai.kem.bl.extension.ExtensionManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.rights.UnRightsManger;
import cn.ilanhai.kem.bl.tag.SysTagManager;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.special.SpecialDao;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.PublishState;
import cn.ilanhai.kem.domain.enums.SpecialState;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.StringVerifyUtil;
import cn.ilanhai.kem.util.TimeUtil;

public class SpecialDeployStrategy extends DeployStrategy {
	private static Logger logger = Logger.getLogger(SpecialDeployStrategy.class);

	@Override
	public boolean deploy(boolean mode, RequestContext context) throws BlAppException, DaoAppException {
		DeployDto deployDto = null;
		SpecialEntity specialEntity = null;
		String type = null;
		Dao dao = null;
		IdEntity<String> id = null;
		ContextDataDto contextDataDto = null;
		String jsonStr = null;
		CodeTable ct;
		String deployUrl = null;
		try {
			logger.info("context:" + context.getArgs().toString());
			deployDto = context.getDomain(DeployDto.class);
			this.valiPara(deployDto);
			this.valiParaItemStrNullOrEmpty(deployDto.getModeId(), "编号");
			this.valiParaItemBooleanNull(deployDto.isEditor(), "是否来自编辑界面发布判断不能为空");
			logger.info("deployDto:" + deployDto);
			if (deployDto.isEditor()) {
				this.valiDomainIsNull(deployDto.getData(), "编辑界面的数据不能为空");
			}
			logger.info("专题id:" + deployDto.getModeId());
			dao = this.daoProxyFactory.getDao(context, SpecialDao.class);
			this.valiDaoIsNull(dao, "专题");
			id = new IdEntity<String>();
			id.setId(deployDto.getModeId());
			specialEntity = (SpecialEntity) dao.query(id, false);
			this.valiDomainIsNull(specialEntity, "专题");
			int val = -1;
			val = specialEntity.getSpecialState();
			if (!deployDto.isEditor() && !SpecialState.HASSAVE.getValue().equals(val)) {
				ct = CodeTable.BL_UNHANDLED_EXCEPTION;
				throw new BlAppException(ct.getValue(), "已禁用的专题不允许预览/发布");
			}
			if (!mode)
				return brower(context);
			logger.info("发布专题");
			if (deployDto.isEditor()) {
				if (PluginType.ACTIVEPLUGIN.getValue().equals(deployDto.getPluginType())) {
					this.valiParaItemObjectNull(deployDto.getActiveType(), "当插件类型时活动类型不能为空");
				}
				if (checkCreatePlugin(deployDto.getPluginType())) {
					PluginManager.creatPlugin(context, deployDto.getModeId(), ManuscriptType.SPECIAL,
							PluginType.getEnum(deployDto.getPluginType()),
							ActivePluginType.getEnum(deployDto.getActiveType()));
				} else {
					PluginManager.disableAllPlugin(context, deployDto.getModeId());
				}
			}
			
			// 校验是否为自己的模版
			if (!this.getSessionUserId(context).equals(specialEntity.getUserId())) {
				ct = CodeTable.BL_UNHANDLED_EXCEPTION;
				throw new BlAppException(ct.getValue(), "只能发布自己的专题");
			}
			// 校验是否包含名称
			ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
					specialEntity.getSpecialId(), ManuscriptParameterType.publishname);
			if (manuscriptParameterEntity == null || Str.isNullOrEmpty(manuscriptParameterEntity.getParameter())) {
				ct = CodeTable.BL_COMMON_PUBLISH_STATUS;
				throw new BlAppException(ct.getValue(), "您还没有保存名称！");
			}
			// 校验是否可发布
			// if
			// (PublishState.CANNOTPUBLISH.getValue().equals(specialEntity.getPublishState()))
			// {
			// ct = CodeTable.BL_COMMON_PUBLISH_STATUS;
			// throw new BlAppException(ct.getValue(),
			// "您还没有进行发布设置，请先设置发布信息再发布专题！");
			// }
			
			contextDataDto = deployDto.getData();
			if (contextDataDto == null) {
				jsonStr = specialEntity.getContext();
				if (jsonStr == null || jsonStr.length() <= 0)
					throw new BlAppException(-1, "专题未保存编辑数据");
				try {

					contextDataDto = FastJson.json2Bean(jsonStr, ContextDataDto.class);
				} catch (Exception e) {
					new BlAppException(-1, "context格式错误");
				}
				deployDto.setData(contextDataDto);
			}
			this.valiParaItemObjectNull(deployDto.getData(), "编辑数据格式错误");
			this.valiParaItemListNull(deployDto.getData().getPages(), "编辑页数据格式错误");
			specialEntity.setContext(FastJson.bean2Json(contextDataDto));

			specialEntity.setSpecialState(SpecialState.HASSAVE.getValue());

			// 1生成推广数据
			ExtensionEntity extensionEntity;
			String extensionId = specialEntity.getExtensionId();
			extensionEntity = ExtensionManager.getExtensionEntityById(context, extensionId);
			// 创建连接实体
			if (extensionEntity == null) {
				extensionEntity = new ExtensionEntity();
				extensionId = KeyFactory.newKey(KeyFactory.KEY_EXTENSION);
				extensionEntity.setCreatetime(new Date());
				extensionEntity.setExtensionId(extensionId);
				extensionEntity.setContext(FastJson.bean2Json(contextDataDto));
			}
			logger.info("推广id:" + specialEntity.getExtensionId());

			ModelConfigEntity modelConfig = new ModelConfigEntity();
			modelConfig.setModelId(extensionId);
			modelConfig.setModelType(ManuscriptType.EXTENSION.getValue());
			dao.save(modelConfig);
			extensionEntity.setModelConfigId(modelConfig.getModelConfigId());

			extensionEntity.setExtensionId(extensionId);
			extensionEntity.setContext(FastJson.bean2Json(contextDataDto));
			specialEntity.setExtensionId(extensionId);
			deployDto.setModeId(extensionEntity.getExtensionId());
			type = this.getType(specialEntity.getSpecialType());
			if (type == null)
				throw new BlAppException(-1, "类型错误");
			deployUrl = getPublishDeployUrl(context);

			// 去版权
			doUnRights(context, specialEntity, extensionId);

			// TODO 等待消息队列实现
			if (!this.deploy(deployDto, type, deployUrl))
				return false;

			BindHostManager bindHostManager = new BindHostManager(context);
			browerUrl = bindHostManager.hasHostwithuser(extensionId, extensionEntity.getExtensionType());
			logger.info("获取hostUrl:" + browerUrl);
			if (Str.isNullOrEmpty(browerUrl)) {
				this.browerUrl = String.format(this.getDeployPublishUrl(context), deployDto.getModeId(), type);
			}
			extensionEntity
					.setExtensionUrl(String.format(this.getDeployPublishUrl(context), deployDto.getModeId(), type));
			logger.info("browerUrl:" + browerUrl);

			logger.info("cacheIndex:" + Integer.parseInt(getValue(context, "cacheIndex")));
			// 获取缓存
			Cache cache = CacheUtil.getInstance().getCache(Integer.parseInt(getValue(context, "cacheIndex")));
			logger.info("构造缓存key值入参编号:" + deployDto.getModeId());
			logger.info("构造缓存key值入参终端类型:" + specialEntity.getSpecialType());
			// 发布缓存 永久保存
			cache.set(BLContextUtil.redisKeyForPublish(deployDto.getModeId()),
					BLContextUtil.redisValueForPublish(deployDto.getModeId(), specialEntity.getSpecialType(),
							getValue(context, "apihost"), browerUrl),
					-1);
			logger.info("发布缓存 永久保存:" + StringVerifyUtil.unHttpAndPost(browerUrl));
			// 重新保存专题
			ExtensionManager.saveExtension(context, extensionEntity, specialEntity);
			// 保存目标 参数
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					specialEntity.getExtensionId(), ManuscriptParameterType.extensiontarget);
			// 保存专题内容
			ManuscriptManager.saveManscriptContent(context, specialEntity.getSpecialId(), specialEntity.getContext());
			// 更新专题
			ManuscriptManager.saveManuscript(context, specialEntity);

			// 发布时间
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(), TimeUtil.format(new Date()),
					ManuscriptParameterType.publishtime);
			// 发布时间
			ManuscriptManager.saveManscriptParameter(context, extensionEntity.getExtensionId(),
					TimeUtil.format(new Date()), ManuscriptParameterType.publishtime);
			// val = dao.save(specialEntity);
			// valiSaveDomain(val, "专题");
			// 复制插件
			PluginManager.copyPlugin(context, specialEntity.getSpecialId(), extensionEntity.getExtensionId(),
					ManuscriptType.SPECIAL, ManuscriptType.EXTENSION);

			List<String> tagNames = new ArrayList<String>();
			for (ConfigTagEntity configTagEntity : specialEntity.getConfigTags()) {
				tagNames.add(configTagEntity.getTag());
			}
			SysTagManager.quoteNumAdd(context, tagNames);
			return true;
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw new BlAppException(-1, e.getMessage());
		} catch (Exception e) {
			KeyFactory.inspects();
			throw new BlAppException(-1, "未知处理异常");
		}
	}

	/**
	 * 做去版权设置
	 * 
	 * @param context
	 * @param specialEntity
	 * @param extensionId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private void doUnRights(RequestContext context, SpecialEntity specialEntity, String extensionId)
			throws DaoAppException, BlAppException {
		ManuscriptParameterEntity unrights = ManuscriptManager.getManuscriptParameterById(context,
				specialEntity.getSpecialId(), ManuscriptParameterType.unrights);
		logger.info("获取是否去版权设置:" + unrights);
		if (unrights != null && ManuscriptParameterType.unrights.toString().equals(unrights.getParameter())) {
			logger.info("去版权设置");
			ManuscriptManager.saveManscriptParameter(context, extensionId, ManuscriptParameterType.unrights.toString(),
					ManuscriptParameterType.unrights);
			UnRightsManger.useTimes(context, specialEntity.getUserId(), extensionId);
		} else {
			logger.info("不去版权设置");
			// 保存去版权设置
			ManuscriptManager.saveManscriptParameter(context, extensionId, "disable", ManuscriptParameterType.unrights);
		}
	}

	private boolean brower(RequestContext context) throws BlAppException, DaoAppException {
		DeployDto deployDto = null;
		SpecialEntity specialEntity = null;
		String type = null;
		Dao dao = null;
		IdEntity<String> id = null;
		String deployUrl = null;
		ContextDataDto contextDataDto = null;
		String jsonStr = null;
		try {
			deployDto = context.getDomain(DeployDto.class);
			this.valiPara(deployDto);
			this.valiParaItemStrNullOrEmpty(deployDto.getModeId(), "编号");
			dao = this.daoProxyFactory.getDao(context, SpecialDao.class);
			this.valiDaoIsNull(dao, "专题");
			id = new IdEntity<String>();
			id.setId(deployDto.getModeId());
			specialEntity = (SpecialEntity) dao.query(id, false);
			this.valiDomainIsNull(specialEntity, "专题");
			contextDataDto = deployDto.getData();
			if (contextDataDto == null) {
				jsonStr = specialEntity.getContext();
				if (jsonStr == null || jsonStr.length() <= 0)
					throw new BlAppException(-1, "数据错误");
				contextDataDto = FastJson.json2Bean(jsonStr, ContextDataDto.class);
				deployDto.setData(contextDataDto);
			}
			this.valiParaItemObjectNull(deployDto.getData(), "数据");
			this.valiParaItemListNull(deployDto.getData().getPages(), "页数据");
			type = this.getType(specialEntity.getSpecialType());
			if (type == null)
				throw new BlAppException(-1, "类型错误");
			deployUrl = getPreViewDeployUrl(context);
			if (!this.deploy(deployDto, type, deployUrl))
				return false;
			this.browerUrl = String.format(this.getDeployPreViewUrl(context), deployDto.getModeId(), type);

			// 获取缓存
			Cache cache = CacheUtil.getInstance().getCache(Integer.parseInt(getValue(context, "cacheIndex")));
			// 发布缓存 预览只保存一天
			cache.set(BLContextUtil.redisKeyForPublish(deployDto.getModeId()),
					BLContextUtil.redisValueForPublish(deployDto.getModeId(), specialEntity.getSpecialType(),
							getValue(context, "apihost"), browerUrl),
					24 * 60 * 60);
			return true;
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage());
		}
	}
}
