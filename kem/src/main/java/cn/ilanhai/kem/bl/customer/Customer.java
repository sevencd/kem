package cn.ilanhai.kem.bl.customer;

import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Customer {
	/**
	 * 查询联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String save(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 搜索群组
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	List<Entity> originateinfo(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 加载客户信息
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity load(RequestContext context) throws BlAppException, DaoAppException;
}
