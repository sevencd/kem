package cn.ilanhai.kem.bl.compositematerial;

import java.util.Iterator;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.PageResponse;

/**
 * @author SYSTEM
 *
 */
public interface CompositeMaterial {

	/**
	 * 保存组合数材数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean save(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 以分页方式获取组合数材数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	PageResponse list(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 组合数材详情
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity info(RequestContext context) throws BlAppException, DaoAppException;
}
