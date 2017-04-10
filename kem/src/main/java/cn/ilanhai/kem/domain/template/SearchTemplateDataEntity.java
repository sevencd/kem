package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTemplateDataEntity extends AbstractEntity {
	private static final long serialVersionUID = 7544812850043911393L;
	private String templateId;
	private String templateName;
	private String templateImg;
	private Date createtime;
	private String publishedURL;
	private Integer templateState;
	private Integer templateType;
	private Integer publishState;
	private String user;
	private String summary;
	private Integer isCollection;

	private String verifyName;
	private Date verifytime;
	private Date shelftime;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getPublishState() {
		return publishState;
	}

	public void setPublishState(Integer publishState) {
		this.publishState = publishState;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public Date getVerifytime() {
		return verifytime;
	}

	public void setVerifytime(Date verifytime) {
		this.verifytime = verifytime;
	}

	public Date getShelftime() {
		return shelftime;
	}

	public void setShelftime(Date shelftime) {
		this.shelftime = shelftime;
	}
}
