package cn.ilanhai.framework.common.exception;

/**
 * 定义容器异常
 * 
 * @author he
 *
 */
public class ContainerException extends Exception {

	protected Throwable parentException = null;
	protected int errorCode = 0xffffffff;
	protected String errorDesc = "";

	public ContainerException() {
		super();

	}

	public ContainerException(String mssage) {
		super(mssage);

	}

	public ContainerException(Throwable parentException) {
		super(parentException);
		this.parentException = parentException;

	}

	public ContainerException(String mssage, Throwable parentException) {
		super(mssage, parentException);

	}

	public ContainerException(int errorCode, String errorDesc,
			Throwable parentException) {
		super(errorDesc, parentException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public ContainerException(int errorCode, String errorDesc) {
		this(errorCode, errorDesc, null);
	}

	public ContainerException(int errorCode) {
		this(errorCode, "", null);

	}

	public Throwable getParentException() {
		return parentException;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

}
