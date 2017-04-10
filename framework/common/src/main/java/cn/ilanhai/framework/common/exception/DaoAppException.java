package cn.ilanhai.framework.common.exception;

/**
 * 数据访问异常
 * 
 * @author he
 *
 */
public class DaoAppException extends AppException {
	public DaoAppException() {
		super();

	}

	public DaoAppException(String mssage) {
		super(mssage);

	}

	public DaoAppException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public DaoAppException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public DaoAppException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public DaoAppException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public DaoAppException(int errorCode) {
		this(errorCode, "", null);

	}
}
