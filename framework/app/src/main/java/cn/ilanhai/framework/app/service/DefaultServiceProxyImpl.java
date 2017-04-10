package cn.ilanhai.framework.app.service;

/**
 * 定义默认服务代理
 * 
 * @author he
 *
 */
public class DefaultServiceProxyImpl extends AbstractServiceProxy {
	private static DefaultServiceProxyImpl defaultServiceProxyImpl = null;

	private DefaultServiceProxyImpl() {
		super();
	}

	public final static ServiceProxy getInstance() {
		if (defaultServiceProxyImpl == null)
			defaultServiceProxyImpl = new DefaultServiceProxyImpl();
		return defaultServiceProxyImpl;
	}
}
