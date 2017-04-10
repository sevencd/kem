package cn.ilanhai.kem.bl.emailright;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface EmailRight {

	/**
	 * 加载收件人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadcontrants(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean savecontrants(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载邮件数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity load(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存邮件数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity save(RequestContext context) throws BlAppException, DaoAppException;

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
	 * 发送邮件
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean send(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除邮件
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 数据分析接口
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws AppException 
	 */
	Entity analysisdata(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 保存所有联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean saveallcontrants(RequestContext context) throws BlAppException, DaoAppException;
}
