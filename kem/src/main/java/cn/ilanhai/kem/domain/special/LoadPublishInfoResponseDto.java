package cn.ilanhai.kem.domain.special;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载专题发布设置响应Dto
 * 
 * @author hy
 *
 */
public class LoadPublishInfoResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -1916901499420432743L;
	/**
	 * 专题编号
	 */
	private String specialId;
	/**
	 * 封面
	 */
	private String coverImg;
	/**
	 * 发布名称
	 */
	private String publishName;
	/**
	 * 关键字
	 */
	private List<String> keywords;
	/**
	 * 标签
	 */
	private List<String> tagNames;
	/**
	 * 主色调
	 */
	private String mainColor;
	/**
	 * 简介
	 */
	private String summary;
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
	
	private boolean doneUnrights;
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

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
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

	public boolean isUnRights() {
		return isUnRights;
	}

	public void setUnRights(boolean isUnRights) {
		this.isUnRights = isUnRights;
	}

	public boolean isDoneUnrights() {
		return doneUnrights;
	}

	public void setDoneUnrights(boolean doneUnrights) {
		this.doneUnrights = doneUnrights;
	}
}
