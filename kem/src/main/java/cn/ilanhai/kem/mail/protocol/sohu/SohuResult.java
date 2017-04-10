package cn.ilanhai.kem.mail.protocol.sohu;

import cn.ilanhai.kem.mail.protocol.Result;

public class SohuResult extends Result {

	public SohuResult() {

	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * API 请求是否成功
	 */
	private boolean result;
	/**
	 * API 返回码
	 */
	private int statusCode;
	/**
	 * API 返回码的中文解释
	 */
	private String message;
	// info: 数据信息
}
