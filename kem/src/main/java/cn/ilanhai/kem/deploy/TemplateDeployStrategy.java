package cn.ilanhai.kem.deploy;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.dao.template.TemplateDao;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.util.StringVerifyUtil;

public class TemplateDeployStrategy extends DeployStrategy {

	@Override
	public boolean deploy(boolean mode, RequestContext context) throws BlAppException, DaoAppException {
		DeployDto deployDto = null;
		TemplateEntity templateEntity = null;
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

			this.valiParaItemBooleanNull(deployDto.isEditor(), "是否来自编辑界面发布判断不能为空");
			if (deployDto.isEditor()) {
				this.valiDomainIsNull(deployDto.getData(), "编辑界面的数据不能为空");
			}
			if (mode)
				throw new BlAppException(-1, "模板不支持发布");
			deployDto = context.getDomain(DeployDto.class);
			this.valiPara(deployDto);
			this.valiParaItemStrNullOrEmpty(deployDto.getModeId(), "编号");
			dao = this.daoProxyFactory.getDao(context, TemplateDao.class);
			this.valiDaoIsNull(dao, "模板");
			id = new IdEntity<String>();
			id.setId(deployDto.getModeId());
			templateEntity = (TemplateEntity) dao.query(id, false);
			this.valiDomainIsNull(templateEntity, "模板");
			contextDataDto = deployDto.getData();
			if (contextDataDto == null) {
				jsonStr = templateEntity.getTemplateContent();
				if (jsonStr == null || jsonStr.length() <= 0)
					throw new BlAppException(-1, "模板没有内容无法预览");
				try {
					contextDataDto = FastJson.json2Bean(jsonStr, ContextDataDto.class);
				} catch (Exception e) {
					new BlAppException(-1, "context格式错误");
				}
				deployDto.setData(contextDataDto);
			}
			this.valiParaItemObjectNull(deployDto.getData(), "编辑数据格式错误");
			this.valiParaItemListNull(deployDto.getData().getPages(), "编辑页面数据格式错误");
			type = this.getType(templateEntity.getTemplateType());
			if (type == null)
				throw new BlAppException(-1, "类型错误");
			deployUrl = getPreViewDeployUrl(context);
			if (!this.deploy(deployDto, type, deployUrl))
				return false;
			this.browerUrl = String.format(this.getDeployPreViewUrl(context), deployDto.getModeId(), type);

			// 获取缓存
			Cache cache = CacheUtil.getInstance().getCache(Integer.parseInt(getValue(context, "cacheIndex")));
			// 预览缓存 1天
			cache.set(BLContextUtil.redisKeyForPublish(deployDto.getModeId()),
					BLContextUtil.redisValueForPublish(deployDto.getModeId(), templateEntity.getTemplateType(),
							getValue(context, "apihost"), browerUrl), 24 * 60 * 60);

			return true;
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage());
		}
	}

}
