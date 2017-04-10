package cn.ilanhai.framework.common.exception;

/**
 * 服务调用异常
 * 
 * @author he
 *
 */
public class ServiceAppException extends AppException {
	public ServiceAppException() {
		super();

	}

	public ServiceAppException(String mssage) {
		super(mssage);

	}

	public ServiceAppException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public ServiceAppException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public ServiceAppException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public ServiceAppException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public ServiceAppException(int errorCode) {
		this(errorCode, "", null);

	}
}
