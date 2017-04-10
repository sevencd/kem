package cn.ilanhai.kem.bl.extension;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Extension {
	/**
	 * 查询推广
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 专题删除
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 禁用
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void disable(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查看
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String lookfor(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询发布设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchPublishSetting(RequestContext context) throws BlAppException, DaoAppException;

	Entity details(RequestContext context) throws BlAppException, DaoAppException;

}
