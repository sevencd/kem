package cn.ilanhai.framework.app.dao;

import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.app.*;

/**
 * 定义数据访问代理工厂
 * 
 * @author he
 *
 */
public interface DaoProxyFactory {

	/**
	 * 获取数据访问bean
	 * 
	 * @param url
	 * @return
	 */
	Dao getDao(RequestContext requestContext) throws DaoAppException;

	/**
	 * 获取数据访问bean
	 * 
	 * @param requestContext
	 * @param beanName
	 * @return
	 * @throws DaoAppException
	 */
	Dao getDao(RequestContext requestContext, String beanName)
			throws DaoAppException;
	
	Dao getDao(RequestContext requestContext, Class<?> c) 
			throws DaoAppException;
}
