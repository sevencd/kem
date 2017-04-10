package cn.ilanhai.kem.domain.special;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SavepublishinfoDto extends AbstractEntity {
	private static final long serialVersionUID = 6202832861605113473L;
	/**
	 * not null
	 */
	private String specialId;
	/**
	 * 不可空，封面图片路径
	 */
	private String coverImg;
	/**
	 * 不可空，模板发布名称，不超过20字
	 */
	private String publishName;
	/**
	 * 不可空，关键词之间以逗号隔开，最多五个关键词，每个词不超过5个字
	 */
	private String keyword;
	/**
	 * 主色调
	 */
	private String mainColor;

	/**
	 * 概述，不超过150个字
	 */
	private String summary;
	/**
	 * 以逗号隔开，每个标签做多不得超过五个字，不超过五个标签
	 */
	private String tagNames;
	/**
	 * 推广执行时间开始
	 */
	private Date publishStart;
	/**
	 * 推广执行时间结束
	 */
	private Date publishEnd;
	/**
	 * 活动类型
	 */
	private String activeType;
	/**
	 * 活动时间开始
	 */
	private Date activeStart;
	/**
	 * 活动时间结束
	 */
	private Date activeEnd;
	
	private boolean isUnRights;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public Date getPublishStart() {
		return publishStart;
	}

	public void setPublishStart(Date publishStart) {
		this.publishStart = publishStart;
	}

	public Date getPublishEnd() {
		return publishEnd;
	}

	public void setPublishEnd(Date publishEnd) {
		this.publishEnd = publishEnd;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public Date getActiveStart() {
		return activeStart;
	}

	public void setActiveStart(Date activeStart) {
		this.activeStart = activeStart;
	}

	public Date getActiveEnd() {
		return activeEnd;
	}

	public void setActiveEnd(Date activeEnd) {
		this.activeEnd = activeEnd;
	}

	public boolean getIsUnRights() {
		return isUnRights;
	}

	public void setIsUnRights(boolean isUnRights) {
		this.isUnRights = isUnRights;
	}
}
