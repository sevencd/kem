package cn.ilanhai.kem.bl.user.frontuser;

import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginResponseDto;

public interface Frontuser {
	/**
	 * 检测手机号是否已注册接口
	 * 
	 * @param context
	 *            全局上下文 可以获取入参
	 * @return true 为已注册， false 为未注册
	 * @throws BlAppException
	 */
	public Entity exist(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 注册接口
	 * 
	 * @param context
	 *            全局上下文 可以获取入参
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean regist(RequestContext context) throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 密码找回身份验证
	 * 
	 * @param token
	 *            session token
	 * @param context
	 *            全局上下文 可以获取入参
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean resetpwdcheckidentity(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 密码找回设置新密码
	 * 
	 * @param token
	 *            session token
	 * @param context
	 *            全局上下文 可以获取入参
	 * @throws BlAppException
	 * @throws DaoAppException
	 */

	public boolean resetpwdsetpwd(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException;

	/***
	 * 验证用户登录
	 * 
	 * @param context
	 * @return 返回当前登录用户的用户信息，返回null则登录失败
	 * @throws BlAppException
	 * @throws DaoAppException
	 */

	public UserLoginResponseDto userlogin(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 获取用户信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity currentuserinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 修改用户信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean modifycurrentuserinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 修改当前用户登录密码
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean modifycurrentuserloginpwd(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存子账户
	 * 
	 * @param context
	 * @return
	 * @throws SessionContainerException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public List<Entity> saveaccount(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 删除子账户
	 * 
	 * @param context
	 * @return
	 * @throws SessionContainerException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public int deleteaccounts(RequestContext context) throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 查询账户关系
	 * 
	 * @param context
	 * @return
	 * @throws SessionContainerException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity loadaccounts(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException;
	/**
	 * 我的营销
	 * 
	 * @param context 请求上下文
	 * @throws BlAppException
	 * @return
	 */
	Entity getMyMarketing(RequestContext context)throws BlAppException;
}
