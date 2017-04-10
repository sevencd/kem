package cn.ilanhai.framework.app.dao;

/**
 * 定义默认数据访问代理工厂
 * 
 * @author he
 *
 */
public class DefaultDaoProxyFactoryImpl extends AbstractDaoProxyFactory {

	private static DefaultDaoProxyFactoryImpl daoProxyFactoryImpl = null;

	private DefaultDaoProxyFactoryImpl() {
		super();
	}

	public final static DaoProxyFactory getInstance() {
		if (daoProxyFactoryImpl == null)
			daoProxyFactoryImpl = new DefaultDaoProxyFactoryImpl();
		return daoProxyFactoryImpl;
	}
}
