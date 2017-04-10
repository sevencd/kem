package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ManuscriptContentEntity extends AbstractEntity {
	private static final long serialVersionUID = -8861819576716707720L;
	private Integer contentId;
	private String manuscriptId;
	private String content;
	private Date createTime;
	private Integer enableState;

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getEnableState() {
		return enableState;
	}

	public void setEnableState(Integer enableState) {
		this.enableState = enableState;
	}
}
