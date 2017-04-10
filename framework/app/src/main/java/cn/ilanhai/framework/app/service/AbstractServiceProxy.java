package cn.ilanhai.framework.app.service;

import java.lang.reflect.*;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.app.bl.*;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.common.aop.*;
import cn.ilanhai.framework.uitl.*;
import cn.ilanhai.framework.common.*;

/**
 * 定义抽象服务代理接口
 * 
 * @author he
 *
 */
public abstract class AbstractServiceProxy extends AbstractProxyFactory
		implements ServiceProxy {
	private BlProxyFactory blProxyFactory = null;

	protected AbstractServiceProxy() {
		super();
		this.blProxyFactory = DefaultBlProxyFactoryImpl.getInstance();
	}

	public Object invoke(RequestContext requestContext)
			throws ContainerException, AppException, Throwable {
		Object target = null;
		Object proxyObject = null;
		Method method = null;
		Class<?>[] targetIntf = null;
		String serviceName = null;
		String actionName = null;
		CT ct;
		try {
			// 进行权限校验
			this.authorization(requestContext);
			if (requestContext == null) {
				ct = CT.APP_SER_CONTEXT_ERROR;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			serviceName = requestContext
					.getApplicationServiceNameFromLocation();
			if (Str.isNullOrEmpty(serviceName)) {
				ct = CT.APP_SER_SER_NAME_ERROR;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			actionName = requestContext.getApplicationActionNameFromLocation();
			if (Str.isNullOrEmpty(actionName)) {
				ct = CT.APP_SER_ACTION_NAME_ERROR;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			target = this.blProxyFactory.getBl(requestContext);
			if (target == null) {
				ct = CT.APP_SER_BL_OBJ_ERROR;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			targetIntf = target.getClass().getInterfaces();
			if (targetIntf == null || targetIntf.length <= 0) {
				ct = CT.APP_SER_BL_OBJ_NOT_IMPL_BL_INTFACE;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			proxyObject = buildProxyObject(target, targetIntf);
			method = proxyObject.getClass().getDeclaredMethod(actionName,
					RequestContext.class);
			if (method == null) {
				ct = CT.APP_SER_BL_INTFACE_NOT_CALL_METHOD;
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			}
			return method.invoke(proxyObject, requestContext);

		} catch (ServiceAppException e) {
			throw e;
		} catch (InvocationTargetException e) {
			Throwable t;
			t = e.getTargetException();
			if (t instanceof AppException)
				throw (AppException) t;
			else if (t instanceof ContainerException)
				throw (ContainerException) t;
			else
				throw e;
		} catch (Throwable e) {
			throw e;
		}

	}

	public Object buildProxyObject(Object target, Class<?>[] targetIntf) {
		Object proxyObject;
		// this.init();
		// this.proxyFactory.addInterface(targetIntf[0]);
		// this.proxyFactory.setTarget(target);
		// proxyObject = this.proxyFactory.getProxy();
		ProxyFactory pf = null;
		pf = new ProxyFactory();
		pf.addInterface(targetIntf[0]);
		pf.setTarget(target);
		proxyObject = pf.getProxy();
		return proxyObject;
	}

	/**
	 * 进行权限校验
	 * 
	 * @param ctx
	 * @return
	 * @throws ContainerException
	 * @throws AppException
	 * @throws Throwable
	 */
	private Object authorization(RequestContext ctx) throws ContainerException,
			AppException, Throwable {
		// 访问指定的服务进行权限处理
		Object target = null;
		Object proxyObject = null;
		Method method = null;
		Class<?>[] targetIntf = null;
		// 指定的服务名
		String serviceName = "roleauthorization";
		// 指定的方法名
		String actionName = "authorization";
		ApplicationContext appCtx = null;
		CT ct = CT.APP_SER_CONTEXT_ERROR;
		try {
			if (ctx == null)
				throw new ServiceAppException(ct.getVal(), ct.getDesc());
			appCtx = ctx.getApplication().getApplicationContext();
			target = appCtx.getBean(serviceName);
			if (target == null)
				return false;
			targetIntf = target.getClass().getInterfaces();
			if (targetIntf == null || targetIntf.length <= 0)
				return false;
			proxyObject = buildProxyObject(target, targetIntf);
			method = proxyObject.getClass().getDeclaredMethod(actionName,
					RequestContext.class);
			if (method == null)
				return false;
			return method.invoke(proxyObject, ctx);

		} catch (ServiceAppException e) {
			throw e;
		} catch (InvocationTargetException e) {
			Throwable t;
			t = e.getTargetException();
			if (t instanceof AppException)
				throw (AppException) t;
			else if (t instanceof ContainerException)
				throw (ContainerException) t;
			else
				throw e;
		} catch (Throwable e) {
			throw e;
		}

	}
}
