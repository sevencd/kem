package cn.ilanhai.framework.common.exception;

/**
 * 业务逻辑异常
 * 
 * @author he
 *
 */
public class BlAppException extends AppException {

	public BlAppException() {
		super();

	}

	public BlAppException(String mssage) {
		super(mssage);

	}

	public BlAppException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public BlAppException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public BlAppException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public BlAppException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public BlAppException(int errorCode) {
		this(errorCode, "", null);

	}
}
