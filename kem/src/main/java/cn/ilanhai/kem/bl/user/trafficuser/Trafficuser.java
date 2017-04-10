package cn.ilanhai.kem.bl.user.trafficuser;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Trafficuser {
	/**
	 * 加载客户信息
	 * 
	 * @param context
	 *            全局上下文 可以获取入参
	 * @return true 为已注册， false 为未注册
	 * @throws BlAppException
	 */
	public Entity load(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存客户信息
	 * 
	 * @param context
	 *            全局上下文 可以获取入参
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void save(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 保存客户信息
	 * 
	 * @param context
	 *            全局上下文 可以获取入参
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void saveuserinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除客户信息
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询客户信息
	 */
	public Entity search(RequestContext context) throws BlAppException, DaoAppException;
}
