package cn.ilanhai.framework.app.dao;

import org.springframework.aop.framework.ProxyFactory;

import cn.ilanhai.framework.common.*;
import cn.ilanhai.framework.common.aop.*;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.framework.app.*;

/**
 * 定义抽象数据访问代理工厂
 * 
 * @author he
 *
 */
public abstract class AbstractDaoProxyFactory extends AbstractProxyFactory
		implements DaoProxyFactory {

	protected AbstractDaoProxyFactory() {
		super();

	}

	public Dao getDao(RequestContext requestContext) throws DaoAppException {
		CT ct;
		String serviceName = null;
		try {
			if (requestContext == null) {
				ct = CT.APP_DAO_CONTEXT_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			serviceName = requestContext
					.getApplicationServiceNameFromLocation();
			if (Str.isNullOrEmpty(serviceName)) {
				ct = CT.APP_DAO_SER_NAME_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			serviceName = String.format("%sDao", serviceName);
			// this.init();
			// this.proxyFactory.addInterface(Dao.class);

			return this.getDao(requestContext, serviceName);
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.APP_DAO_UNH_EXCEPTION;
			throw new DaoAppException(ct.getVal(), ct.getDesc(), e);
		}
	}

	public Dao getDao(RequestContext requestContext, String beanName)
			throws DaoAppException {
		Object traget = null;
		CT ct;
		try {
			if (requestContext == null) {
				ct = CT.APP_DAO_CONTEXT_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			if (Str.isNullOrEmpty(beanName)) {
				ct = CT.APP_DAO_SER_NAME_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			traget = requestContext.getApplication().getApplicationContext()
					.getBean(beanName);
			if (traget instanceof Dao)
				((Dao) traget).setApplication(requestContext.getApplication());
			if (traget == null) {
				ct = CT.APP_DAO_OBJ_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			ProxyFactory pf = null;
			pf = new ProxyFactory();
			pf.addInterface(Dao.class);
			pf.setTarget(traget);
			return (Dao) pf.getProxy();
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.APP_DAO_UNH_EXCEPTION;
			throw new DaoAppException(ct.getVal(), ct.getDesc());
		}
	}

	public Dao getDao(RequestContext requestContext, Class<?> c)
			throws DaoAppException {
		Object traget = null;
		CT ct;
		try {
			if (requestContext == null) {
				ct = CT.APP_DAO_CONTEXT_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			if (c == null) {
				ct = CT.APP_DAO_SER_TYPE_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			traget = requestContext.getApplication().getApplicationContext()
					.getBean(c);
			if (traget instanceof Dao)
				((Dao) traget).setApplication(requestContext.getApplication());
			if (traget == null) {
				ct = CT.APP_DAO_OBJ_ERROR;
				throw new DaoAppException(ct.getVal(), ct.getDesc());
			}
			// this.init();
			// this.proxyFactory.addInterface(Dao.class);
			// this.proxyFactory.setTarget(traget);
			ProxyFactory pf = null;
			pf = new ProxyFactory();
			pf.addInterface(Dao.class);
			pf.setTarget(traget);
			return (Dao) pf.getProxy();
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.APP_DAO_UNH_EXCEPTION;
			throw new DaoAppException(ct.getVal(), ct.getDesc(), e);
		}
	}

}
