package cn.ilanhai.framework.app.bl;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.common.exception.*;

/**
 * 定义业务逻辑代理工厂接口
 * 
 * @author he
 *
 */
public interface BlProxyFactory {

	/**
	 * 获取业务逻辑bean
	 * 
	 * @param requestContext
	 * @return
	 * @throws BlAppException
	 */
	<T> T getBl(RequestContext requestContext) throws BlAppException;
}
