package cn.ilanhai.framework.app.bl;

import org.springframework.aop.framework.ProxyFactory;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.common.*;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.common.aop.*;
import cn.ilanhai.framework.uitl.*;

/**
 * 定义抽象业务逻辑代理工厂
 * 
 * @author he
 *
 */
public abstract class AbstractBlProxyFactory extends AbstractProxyFactory
		implements BlProxyFactory {

	protected AbstractBlProxyFactory() {
		super();
	}

	public <T> T getBl(RequestContext requestContext) throws BlAppException {
		String serviceName = "";
		Class<?>[] targetIntf = null;
		Object target = null;
		CT ct;
		try {

			if (requestContext == null) {
				ct = CT.APP_BL_CONTEXT_ERROR;
				throw new BlAppException(ct.getVal(), ct.getDesc());
			}
			serviceName = requestContext
					.getApplicationServiceNameFromLocation();
			if (Str.isNullOrEmpty(serviceName)) {
				ct = CT.APP_BL_SER_NAME_ERROR;
				throw new BlAppException(ct.getVal(), ct.getDesc());
			}
			target = requestContext.getApplication().getApplicationContext()
					.getBean(serviceName);
			if (target == null) {
				ct = CT.APP_BL_OBJ_ERROR;
				throw new BlAppException(ct.getVal(), ct.getDesc());
			}
			if (target instanceof Bl)
				((Bl) target).setApplication(requestContext.getApplication());
			targetIntf = target.getClass().getInterfaces();
			if (targetIntf == null || targetIntf.length <= 0) {
				ct = CT.APP_BL_OBJ_NOT_IMPL_BL_INTFACE;
				throw new BlAppException(ct.getVal(), ct.getDesc());
			}
			// this.init();
			// this.proxyFactory.addInterface(targetIntf[0]);
			// this.proxyFactory.setTarget(target);
			// target = this.proxyFactory.getProxy();
			ProxyFactory pf = null;
			pf = new ProxyFactory();
			pf.addInterface(targetIntf[0]);
			pf.setTarget(target);
			target = pf.getProxy();
			return (T) target;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.APP_BL_UNH_EXCEPTION;
			throw new BlAppException(ct.getVal(), ct.getDesc(), e);
		}
	}

}
