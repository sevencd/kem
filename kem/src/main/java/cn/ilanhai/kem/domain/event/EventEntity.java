package cn.ilanhai.kem.domain.event;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * @author he
 *
 */
public class EventEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String sessionId;
	private Date createTime;
	private String uri;
	private String userId;
	private String argsName;
	private String args;

	public EventEntity() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getArgsName() {
		return argsName;
	}

	public void setArgsName(String argsName) {
		this.argsName = argsName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
}
