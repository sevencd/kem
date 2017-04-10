package cn.ilanhai.framework.common.exception;

public class SessionContainerException extends ContainerException {
	public SessionContainerException() {
		super();

	}

	public SessionContainerException(String mssage) {
		super(mssage);

	}

	public SessionContainerException(Throwable throwable) {
		super(throwable);

	}

	public SessionContainerException(String mssage, Throwable throwable) {
		super(mssage, throwable);

	}

	public SessionContainerException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public SessionContainerException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public SessionContainerException(int errorCode) {
		this(errorCode, "", null);

	}
}
