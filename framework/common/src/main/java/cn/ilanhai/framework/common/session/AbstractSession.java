package cn.ilanhai.framework.common.session;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractSession implements Session {

	protected Date deadline;
	protected String sessionId;
	protected transient SessionManager sessionManager;
	protected Date createTime;

	protected AbstractSession() {

	}

	protected AbstractSession(SessionManager sessionManager) {
		this.createTime = new Date();
		this.deadline = this.createTime;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.deadline);
		cal.add(Calendar.MINUTE, 30);
		this.deadline = cal.getTime();
		this.sessionId = String.format("kem:session:%s", UUID.randomUUID()
				.toString());
		this.sessionManager = sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public boolean isExpire() {
		Date now = new Date();
		if (now.after(this.deadline)) {
			return true;
		} else {
			return false;
		}
	}

	public Session fresh() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.deadline);
		cal.add(Calendar.MINUTE, 30);
		this.deadline = cal.getTime();
		return this;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}
}
