package cn.ilanhai.kem.transaction;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.SyncApplication;
import cn.ilanhai.framework.app.transaction.AbstractDataSourceTransactionManager;
import cn.ilanhai.kem.common.Constant;

@Component("transactionManager")
public class DataSourceTransactionManager extends
		AbstractDataSourceTransactionManager {

	public DataSourceTransactionManager() {
		super();
	}

	@Override
	public DataSource ds() {
		Application application = null;
		DataSource dataSource = null;
		application = SyncApplication.getApplication();
		dataSource = application.getDs().get(Constant.KEY_DATABASE_NAME_KEM);
		return dataSource;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	}

}
