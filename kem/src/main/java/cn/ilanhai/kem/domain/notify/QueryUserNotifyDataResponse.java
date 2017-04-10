package cn.ilanhai.kem.domain.notify;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryUserNotifyDataResponse extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private Date addTime = null;
	private Date updateTime = null;
	private Boolean read = null;
	private Date readTime = null;
	private String content = null;
	private Integer notifyType = null;

	public QueryUserNotifyDataResponse() {

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

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

}
