package cn.ilanhai.framework.common.exception;

/**
 * 容器配置异常
 * 
 * @author he
 *
 */
public class ConfigurationContainerException extends ContainerException {
	public ConfigurationContainerException() {
		super();

	}

	public ConfigurationContainerException(String mssage) {
		super(mssage);

	}

	public ConfigurationContainerException(Throwable throwable) {
		super(throwable);

	}

	public ConfigurationContainerException(String mssage, Throwable throwable) {
		super(mssage, throwable);

	}
}
