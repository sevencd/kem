package cn.ilanhai.kem.deploy;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.enums.ManuscriptType;

/**
 * 
 * 部署接口定义
 * 
 * @author he
 *
 */
public interface Deploy {

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
	boolean deploy(boolean mode, RequestContext context, ManuscriptType modelType) throws BlAppException, DaoAppException;

	/**
	 * 获取浏览URL (发布成功才有效)
	 * 
	 * @return
	 */
	String getBrowerUrl();
}
