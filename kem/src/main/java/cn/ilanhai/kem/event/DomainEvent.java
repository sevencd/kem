package cn.ilanhai.kem.event;

import java.util.Date;
import java.util.UUID;

import cn.ilanhai.framework.uitl.FastJson;

/**
 * 基础领域事件类 实现了领域事件接口的基本内容 可以被继承 同时提供了相关便利的操作
 * 
 * @author Nature
 *
 */
public class DomainEvent {
	private String id;//时间ID，唯一全局唯一的事件ID，不可为空
	private String sessionId;//sessionId,如果涉及用户会话，则将会话ID记录在此
	private Date createTime;//该事件创建时间
	private String uri;//事件uri
	private String userId;//如果涉及用户，则记录用户ID在此
	private String argsName;//事件名称,不可为空，事件名称可用来区分事件的分类,约定上为事件参数的类型全称
	private String args;//事件内容，约定上，此处为一个对象序列化后的json字符串

	public DomainEvent() {
		this.id = UUID.randomUUID().toString().toLowerCase();
		this.createTime = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setArgsName(String argsName) {
		this.argsName = argsName;
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

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public void setArgs(DomainEventArgs args) {
		if (args == null)
			return;
		this.argsName = args.getClass().getName();
		this.args = FastJson.bean2Json(args);
	}
}
