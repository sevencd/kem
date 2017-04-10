package cn.ilanhai.kem.bl.tag;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
public interface SysTag {
	/**
	 * 查询标签
	 * 
	 * @param context
	 *            全局上下文
	 * @return 查询返回对象
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除标签
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 是否精选标签
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void setselection(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 添加标签
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void add(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 排序
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void order(RequestContext context) throws BlAppException, DaoAppException;
}
