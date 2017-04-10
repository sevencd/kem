package cn.ilanhai.framework.app.bl;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.dao.*;
import cn.ilanhai.framework.common.mq.MQManager;

/**
 * 定义抽象业务逻辑
 * 
 * @author he
 *
 */
public abstract class AbstractBl implements Bl {
	private Application application = null;
	protected DaoProxyFactory daoProxyFactory = null;

	protected AbstractBl() {
		this.daoProxyFactory = DefaultDaoProxyFactoryImpl.getInstance();
		// org.springframework.transaction.
	}

	public final void setApplication(Application application) {
		if (this.application != null)
			return;
		this.application = application;
	}

	public final Application getApplication() {
		return this.application;
	}

	protected MQManager getMQManager() {
		// return MQManager.getInstance();
		return null;
	}
}
