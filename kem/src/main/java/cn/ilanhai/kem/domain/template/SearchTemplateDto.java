package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = 3830987303729815700L;
	private String tagName;
	private String templateName;
	/**
	 * 是否是当前用户的，不可空，当前用户的所有模板，则查询所有状态的，不是当前用户的模板，则查询所有用户已上架的模板，true或者false
	 */
	private Boolean isCurrentUser;
	private Integer startCount;
	private Integer pageSize;
	private String userId;
	private String currentLoginUserId;
	private boolean isBackUser;
	private boolean isRand;
	/**
	 * 可空，为空则查询所有状态，1:未提交，2:审核中，3:已上架，4:已退回
	 */
	private Integer templateState;
	/**
	 * 可空，为空则查询所有模板类型，模板类型：1：PC端；2：移动端
	 */
	private Integer templateType;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会大于等于该值
	 */
	private Date timeStart;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会小于该值
	 */
	private Date timeEnd;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTemplateState() {
		return templateState;
	}

	public void setTemplateState(Integer templateState) {
		this.templateState = templateState;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getCurrentLoginUserId() {
		return currentLoginUserId;
	}

	public void setCurrentLoginUserId(String currentLoginUserId) {
		this.currentLoginUserId = currentLoginUserId;
	}

	/**
	 * @return the isRand
	 */
	public boolean isRand() {
		return isRand;
	}

	/**
	 * @param isRand the isRand to set
	 */
	public void setRand(boolean isRand) {
		this.isRand = isRand;
	}
}
