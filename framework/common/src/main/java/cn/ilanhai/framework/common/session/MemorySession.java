package cn.ilanhai.framework.common.session;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.common.exception.SessionContainerException;

public class MemorySession extends AbstractSession {

	protected Map<String, Object> parameters;

	public MemorySession(SessionManager sessionManager) {
		super(sessionManager);
		this.parameters = new HashMap<String, Object>();
	}



	public Object getParameter(String key) throws SessionContainerException {
		if (key == null || key.length() <= 0)
			return null;
		if (!this.parameters.containsKey(key))
			return null;
		return this.parameters.get(key);
	}

	public <T> T getParameter(String key, Class<T> t)
			throws SessionContainerException {
		Object object = null;
		object = this.getParameter(key);
		if (object == null)
			return null;
		if (object.getClass() != t)
			return null;
		return (T) object;
	}

	public Object setParameter(String key, Object value)
			throws SessionContainerException {
		if (key == null || key.length() <= 0)
			return null;
		if (this.parameters.containsKey(key)) {

			this.parameters.remove(key);
		}
		return this.parameters.put(key, value);
	}

	public void abandon() {
		try {
			this.parameters.clear();
			if (this.sessionManager != null)
				this.sessionManager.unRegister(this);
		} catch (Exception e) {

		}
	}

	public boolean checkTimeOut() {
		return false;
	}

	public boolean verify(BaseSessionState destState) {

		return false;
	}

	@Override
	public BaseSessionState getSessionState() {

		return null;
	}

	@Override
	public void setSessionState(BaseSessionState baseSessionState) {

	}

	@Override
	public String getClientType() {

		return null;
	}

	@Override
	public void removeParameter(String key) throws SessionContainerException {

		if (key == null || key.length() <= 0)
			return;
		if (!this.parameters.containsKey(key))
			return;
		this.parameters.remove(key);

	}

}
