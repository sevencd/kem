package cn.ilanhai.kem.domain.notify;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通知实体
 * 
 * @author he
 *
 */
public class NotifyEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private Date addTime = null;
	private Date updateTime = null;
	private Boolean read = null;
	private Date readTime = null;
	private String content = null;
	private NotifyType notifyType = null;
	private String source = null;
	private String target = null;

	public NotifyEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
