package cn.ilanhai.framework.app;

import java.net.URI;

/**
 * 定义以响应
 * 
 * @author he
 *
 */
public class ResponseImpl implements Response {

	protected String sessionId = null;
	protected Object data = null;

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public <T> T getData(Class<T> t) {
		if (this.data == null)
			return null;
		return null;
	}
	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
