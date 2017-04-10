package cn.ilanhai.framework.common.exception;

public class JMSAppException extends AppException {
	public JMSAppException() {
		super();

	}

	public JMSAppException(String mssage) {
		super(mssage);

	}

	public JMSAppException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public JMSAppException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public JMSAppException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public JMSAppException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public JMSAppException(int errorCode) {
		this(errorCode, "", null);

	}

}
