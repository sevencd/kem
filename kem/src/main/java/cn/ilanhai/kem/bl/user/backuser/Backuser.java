package cn.ilanhai.kem.bl.user.backuser;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Backuser {
	/**
	 * 验证用户登录
	 * 
	 * @param context
	 * @return 返回当前登录用户的用户信息，返回null则登录失败
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity userlogin(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 获取当前用户信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity currentuserinfo(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 添加用户
	 * 
	 * @param context 请求上下文
	 * @throws BlAppException
	 * @time 2017-03-20 19:00
	 * @return 
	 * @throws DaoAppException 
 	 */
	int saveUser(RequestContext context) throws BlAppException;
}
