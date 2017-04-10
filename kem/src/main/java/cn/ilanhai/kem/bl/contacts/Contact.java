package cn.ilanhai.kem.bl.contacts;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.MapDto;

public interface Contact {
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
	 * 保存群组
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String savegroup(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 搜索群组
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchgroup(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存群组联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String savegroupcontact(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 导入联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	MapDto importcontact(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询群组联系人
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchgroupcontact(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除群组
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean deletegroup(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 同步客户联系人
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity synchronization(RequestContext context) throws BlAppException, DaoAppException;
}
