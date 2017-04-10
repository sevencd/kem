package cn.ilanhai.framework.common.exception;

/**
 * 应用异常
 * 
 * @author he
 *
 */
public class AppException extends ContainerException {
	public AppException() {
		super();

	}

	public AppException(String mssage) {
		super(mssage);

	}

	public AppException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public AppException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public AppException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public AppException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public AppException(int errorCode) {
		this(errorCode, "", null);

	}
}
