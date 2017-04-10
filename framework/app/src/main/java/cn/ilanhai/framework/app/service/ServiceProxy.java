package cn.ilanhai.framework.app.service;

import cn.ilanhai.framework.app.*;

import cn.ilanhai.framework.common.exception.*;

/**
 * 定义服务代理接口
 * 
 * @author he
 *
 */
public interface ServiceProxy {

	/**
	 * 调用业务逻辑
	 * 
	 * @param requestContext
	 * @return
	 * @throws ServiceAppException
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws Exception
	 */
	Object invoke(RequestContext requestContext) throws AppException, Throwable;
}
