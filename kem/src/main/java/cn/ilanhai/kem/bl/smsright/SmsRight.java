package cn.ilanhai.kem.bl.smsright;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface SmsRight {

	/**
	 * 加载
	 * 
	 * @param ctx
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity load(RequestContext ctx) throws BlAppException, DaoAppException;

	/**
	 * 发送
	 * 
	 * @param ctx
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity send(RequestContext ctx) throws BlAppException, DaoAppException;
	
	/**
	 * 查询邮件
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除邮件
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;
}
