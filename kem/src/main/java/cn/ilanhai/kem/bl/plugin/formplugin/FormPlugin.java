package cn.ilanhai.kem.bl.plugin.formplugin;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 表单插件接口定义
 * 
 * @author he
 *
 */
public interface FormPlugin {

	/**
	 * 提交表单数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean submit(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 查询表单数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException,
			DaoAppException;
}
