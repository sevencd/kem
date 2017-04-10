package cn.ilanhai.framework.common.exception;

public class CacheContainerException extends ContainerException {
	public CacheContainerException() {
		super();

	}

	public CacheContainerException(String mssage) {
		super(mssage);

	}

	public CacheContainerException(Throwable throwable) {
		super(throwable);

	}

	public CacheContainerException(String mssage,
			Throwable throwable) {
		super(mssage, throwable);

	}
}
