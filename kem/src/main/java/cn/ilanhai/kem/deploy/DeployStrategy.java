package cn.ilanhai.kem.deploy;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.domain.template.TemplateEntity;

public abstract class DeployStrategy extends BaseBl {
	protected String browerUrl = null;

	/**
	 * 获取地址
	 * 
	 * @param context
	 * @return
	 */
	protected String getPreViewDeployUrl(RequestContext context) {
		return this.getValue(context, "preViewDeployUrl");
	}

	protected String getPublishDeployUrl(RequestContext context) {
		return this.getValue(context, "publishDeployUrl");
	}

	protected String getDeployPreViewUrl(RequestContext context) {

		return this.getValue(context, "deployPreViewUrl");
	}

	protected String getDeployPublishUrl(RequestContext context) {
		return this.getValue(context, "deployPublishUrl");
	}

	/**
	 * 部署
	 * 
	 * @param mode
	 *            false 浏览 true 发布
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public abstract boolean deploy(boolean mode, RequestContext context) throws BlAppException, DaoAppException;

	public String getBrowerUrl() {
		return this.browerUrl;
	}

	protected String getType(int type) {
		switch (type) {
		case 1:
			return "pc";
		case 2:
			return "wap";
		default:
			return null;
		}
	}

	protected boolean deploy(DeployDto deployDto, String type, String DEPLOY_URL)
			throws BlAppException, DaoAppException {
		Deployer deployer = null;
		String strUrl = null;
		String doc = null;
		String modelId = null;
		try {
			modelId = deployDto.getModeId();
			// 预览和发布的时候 替换掉内容中的占位符
			deployDto.analysisData();
			
			deployer = new Deployer(deployDto);
			if (!deployer.genFile())
				throw new BlAppException(-1, "生成文件错误");
			if (!deployer.genZip())
				throw new BlAppException(-1, "压缩zip错误");
			strUrl = String.format(DEPLOY_URL, modelId, type);
			doc = deployer.uploadFile(strUrl);
			if (doc == null || doc.length() <= 0)
				throw new BlAppException(-1, "上传文件错误");
			if (doc.toLowerCase().indexOf("success") < 0)
				throw new BlAppException(-1, "上传文件返回错误");
			return true;
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage());
		} finally {
			if (deployer != null)
				deployer.clear();
		}

	}

	protected void checkContextData(DeployDto deployDto, TemplateEntity templateEntity, ContextDataDto contextDataDto,
			String desc) throws BlAppException {
		String jsonStr;
		if (contextDataDto == null) {
			jsonStr = templateEntity.getTemplateContent();
			if (jsonStr == null || jsonStr.length() <= 0)
				throw new BlAppException(-1, desc + "未保存编辑数据");
			try {
				contextDataDto = FastJson.json2Bean(jsonStr, ContextDataDto.class);
			} catch (Exception e) {
				new BlAppException(-1, "context格式错误");
			}
			deployDto.setData(contextDataDto);
		}
		this.valiParaItemObjectNull(deployDto.getData(), "编辑数据格式错误");
		this.valiParaItemListNull(deployDto.getData().getPages(), "编辑页面数据格式错误");
	}

}
