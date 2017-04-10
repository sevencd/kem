package cn.ilanhai.framework.common.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 定义抽象aop
 * 
 * @author he
 *
 */
public abstract class AbstractProxyFactory {

	protected ProxyFactory proxyFactory = null;

	protected AbstractProxyFactory() {

	}

	protected void init() {
		this.proxyFactory = new ProxyFactory();
	}

	/**
	 * 添加advice
	 * 
	 * @param advice
	 */
	public final void addAdvice(Advice advice) {
		if (this.proxyFactory == null)
			return;
		this.proxyFactory.addAdvice(advice);
	}

	/**
	 * 添加advice
	 * 
	 * @param pos
	 * @param advice
	 */
	public final void addAdvice(int pos, Advice advice) {
		if (this.proxyFactory == null)
			return;
		this.proxyFactory.addAdvice(pos, advice);

	}

	/**
	 * 移出advice
	 * 
	 * @param pos
	 * @param advice
	 */
	public final void removeAdvice(int pos, Advice advice) {
		if (this.proxyFactory == null)
			return;
		this.proxyFactory.removeAdvice(advice);

	}
}
