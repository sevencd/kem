package cn.ilanhai.kem.deploy;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.domain.enums.ManuscriptType;

@Component("deploy")
public class DeployImpl extends BaseBl implements Deploy {
	private DeployStrategy deployStrategy = null;

	// @Override
	// public boolean browse(RequestContext context) throws BlAppException,
	// DaoAppException {
	// CodeTable ct;
	// try {
	// return this.deploy(false, context);
	// } catch (BlAppException e) {
	// throw e;
	// } catch (Exception e) {
	// ct = CodeTable.BL_UNHANDLED_EXCEPTION;
	// throw new BlAppException(ct.getValue(), ct.getDesc(), e);
	// }
	// }
	//
	// @Override
	// public boolean publish(RequestContext context) throws BlAppException,
	// DaoAppException {
	// CodeTable ct;
	// try {
	// return this.deploy(true, context);
	// } catch (BlAppException e) {
	// throw e;
	// } catch (Exception e) {
	// ct = CodeTable.BL_UNHANDLED_EXCEPTION;
	// throw new BlAppException(ct.getValue(), ct.getDesc(), e);
	// }
	// }

	/**
	 * 部署
	 * 
	 * 
	 * @param mode
	 *            false 浏览 true 发布
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean deploy(boolean mode, RequestContext context, ManuscriptType modelType)
			throws BlAppException, DaoAppException {
		switch (modelType) {
		case SPECIAL:
			deployStrategy = new SpecialDeployStrategy();
			break;
		case TEMPLATE:
			deployStrategy = new TemplateDeployStrategy();
			break;
		case EXCELLENTCASE:
			deployStrategy = new CaseDeployStrategy();
			break;
		default:
			deployStrategy = new DefDeployStrategy();
			break;
		}
		if (deployStrategy == null)
			throw new BlAppException(-1, "编号错误");
		return deployStrategy.deploy(mode, context);
	}

	public String getBrowerUrl() {
		if (this.deployStrategy == null)
			return null;
		return deployStrategy.getBrowerUrl();
	}
}
