package cn.ilanhai.framework.common.exception;

public class ParameterException extends ContainerException {
	private static final long serialVersionUID = 3473293754439549361L;

	public ParameterException() {
		super();

	}

	public ParameterException(String mssage) {
		super(mssage);

	}

	public ParameterException(Throwable throwable) {
		super(throwable);

	}

	public ParameterException(String mssage, Throwable throwable) {
		super(mssage, throwable);

	}

}
