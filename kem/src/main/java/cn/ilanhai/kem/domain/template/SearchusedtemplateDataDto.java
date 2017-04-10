package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchusedtemplateDataDto extends AbstractEntity {
	private static final long serialVersionUID = -4590737413877559069L;
	/**
	 * 模版ID
	 */
	private String templateId;
	/**
	 * 模版类型
	 */
	private Integer templateType;
	/**
	 * 模版名称
	 */
	private String templateName;
	/**
	 * 模版封面
	 */
	private String templateImg;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 发布路径
	 */
	private String publishedURL;
	/**
	 * 模版状态
	 */
	private Integer templateState;
	/**
	 * 发布状态
	 */
	private Integer publishState;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateImg() {
		return templateImg;
	}

	public void setTemplateImg(String templateImg) {
		this.templateImg = templateImg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPublishedURL() {
		return publishedURL;
	}

	public void setPublishedURL(String publishedURL) {
		this.publishedURL = publishedURL;
	}

	public Integer getTemplateState() {
		return templateState;
	}

	public void setTemplateState(Integer templateState) {
		this.templateState = templateState;
	}

	public Integer getPublishState() {
		return publishState;
	}

	public void setPublishState(Integer publishState) {
		this.publishState = publishState;
	}
}
