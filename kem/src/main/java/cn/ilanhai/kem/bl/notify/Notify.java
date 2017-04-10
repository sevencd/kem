package cn.ilanhai.kem.bl.notify;

import java.util.Iterator;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.PageResponse;

/**
 * 定义通知接口
 * 
 * @author he
 *
 */
public interface Notify {

	/**
	 * 获取用户通知统计
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity count(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 获取用户通知信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	PageResponse search(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 修改通知状态
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean status(RequestContext context) throws BlAppException,
			DaoAppException;
}
