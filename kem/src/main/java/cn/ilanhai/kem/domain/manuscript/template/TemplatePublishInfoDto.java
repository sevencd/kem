package cn.ilanhai.kem.domain.manuscript.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模板发布设置实体
 * 
 * @author dgj
 *
 */
public class TemplatePublishInfoDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5831484802349814437L;
	/**
	 * not null
	 */
	private String templateId;
	/**
	 * 不可空，封面图片路径
	 */
	private String coverImg;
	/**
	 * 不可空，模板发布名称，不超过20字
	 */
	private String publishName;
//	/**
//	 * 不可空，关键词之间以逗号隔开，最多五个关键词，每个词不超过5个字
//	 */
//	private List<String> keywords;
//	/**
//	 * 主色调
//	 */
//	private String mainColor;

	/**
	 * 概述，不超过150个字
	 */
	private String summary;
	/**
	 * 以逗号隔开，每个标签做多不得超过五个字，不超过五个标签
	 */
	private List<String> tagNames;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	// public List<String> getKeywords() {
	// return keywords;
	// }
	//
	// public void setKeywords(List<String> keywords) {
	// this.keywords = keywords;
	// }
	//
	// public String getMainColor() {
	// return mainColor;
	// }
	//
	// public void setMainColor(String mainColor) {
	// this.mainColor = mainColor;
	// }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

}
