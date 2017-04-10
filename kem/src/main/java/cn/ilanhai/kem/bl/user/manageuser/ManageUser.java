package cn.ilanhai.kem.bl.user.manageuser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface ManageUser {
	/**
	 * 后台用户修改会员状态
	 * 
	 * @param context 全局上下文 可以获取入参
	 */

	boolean setstate(RequestContext context) throws BlAppException, DaoAppException;
	/*
	 * 后台用户修改试用用户状态
	 * 
	 * @param context 全局上下文 可以获取入参
	 */

	void updateTrialState(RequestContext context) throws BlAppException;
	/**
	 * 后台用户加载用户信息
	 * 
	 * @param context 全局上下文 可以获取入参
	 */
	Entity loaduserinfo(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 获取用户详情
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Map<String,Object> getuserdetail(RequestContext context) throws BlAppException, DaoAppException;

}
