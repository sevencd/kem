package cn.ilanhai.framework.app.transaction;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public abstract class AbstractDataSourceTransactionManager extends
		DataSourceTransactionManager implements BeanFactoryAware {

	public AbstractDataSourceTransactionManager() {
		super();
		this.setDataSource(this.ds());
	}

	public AbstractDataSourceTransactionManager(DataSource dataSource) {
		super(dataSource);
	}

	public DataSource ds() {
		return null;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

	}

}
