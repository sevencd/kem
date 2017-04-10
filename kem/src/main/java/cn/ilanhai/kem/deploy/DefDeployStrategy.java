package cn.ilanhai.kem.deploy;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.StringVerifyUtil;

/**
 * 未登录稿件预览
 * 
 * @author hy
 *
 */
public class DefDeployStrategy extends DeployStrategy {

	@Override
	public boolean deploy(boolean mode, RequestContext context) throws BlAppException, DaoAppException {
		DeployDto deployDto = null;
		String type = null;
		String deployUrl = null;
		try {
			if (mode)
				throw new BlAppException(-1, "不支持发布");
			deployDto = context.getDomain(DeployDto.class);
			this.valiPara(deployDto);
			if(Str.isNullOrEmpty(deployDto.getModeId())){
				// 设置默认编号
				deployDto.setModeId(KeyFactory.newKey(KeyFactory.KEY_DET));
			}
			// TODO 是否做保存
			this.valiParaItemObjectNull(deployDto.getData(), "编辑数据格式错误");
			this.valiParaItemListNull(deployDto.getData().getPages(), "编辑页面数据格式错误");
			this.valiParaItemObjectNull(deployDto.getTerminalType(), "终端类型");
			this.valiParaItemNumBetween(1, 2, deployDto.getTerminalType(), "终端类型");
			type = this.getType(deployDto.getTerminalType());
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
					BLContextUtil.redisValueForPublish(deployDto.getModeId(), deployDto.getTerminalType(),
							getValue(context, "apihost"), browerUrl),
					24 * 60 * 60);
			return true;
		} catch (Exception e) {
			KeyFactory.inspects();
			throw new BlAppException(-1, e.getMessage());
		}
	}

}
