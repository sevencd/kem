package cn.ilanhai.framework.app.bl;

/**
 * 定义抽象业务逻辑代理工厂
 * 
 * @author he
 *
 */
public class DefaultBlProxyFactoryImpl extends AbstractBlProxyFactory {

	private static DefaultBlProxyFactoryImpl blProxyFactoryImpl = null;

	private DefaultBlProxyFactoryImpl() {
		super();
	}

	public final static BlProxyFactory getInstance() {
		if (blProxyFactoryImpl == null)
			blProxyFactoryImpl = new DefaultBlProxyFactoryImpl();
		return blProxyFactoryImpl;
	}
}
