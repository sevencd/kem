package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTemplateEntity extends AbstractEntity {
	private static final long serialVersionUID = 3830987303729815700L;
	private String tagName;
	private String templateName;
	private Boolean isCurrentUser;
	private Integer startCount;
	private Integer pageSize;
	private Integer userId;
	private boolean isBackUser;

	public boolean isBackUser() {
		return isBackUser;
	}

	public void setBackUser(boolean isBackUser) {
		this.isBackUser = isBackUser;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Boolean getIsCurrentUser() {
		return isCurrentUser;
	}

	public void setIsCurrentUser(Boolean isCurrentUser) {
		this.isCurrentUser = isCurrentUser;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
