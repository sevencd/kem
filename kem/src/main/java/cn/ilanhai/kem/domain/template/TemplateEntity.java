package cn.ilanhai.kem.domain.template;

import java.sql.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TemplateState;

/**
 * 模板实体
 * 
 * @author he
 *
 */
public class TemplateEntity extends AbstractEntity {
	private static final long serialVersionUID = -2929614244517154150L;
	
	
	private String templateId;
	private String userId;
	private String coverImg;
	private String templateName;
	private String mainColor;
	private String summary;
	private Date createtime;
	private TemplateState templateState;
	private String templateContent;
	private Integer publish_state;
	private String verifyName;
	private Date verifytime;
	private Date shelftime;
	private String bouncedReason;
	/**
	 * 模版类型
	 */
	private Integer templateType;

	public TemplateEntity() {

	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getCreatetime() {
		
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public TemplateState getTemplateState() {
		return templateState;
	}

	public void setTemplateState(TemplateState templateState) {
		this.templateState = templateState;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getPublish_state() {
		return publish_state;
	}

	public void setPublish_state(Integer publish_state) {
		this.publish_state = publish_state;
	}

	public Date getVerifytime() {
		return verifytime;
	}

	public void setVerifytime(Date date) {
		this.verifytime = date;
	}

	public Date getShelftime() {
		return shelftime;
	}

	public void setShelftime(Date shelftime) {
		this.shelftime = shelftime;
	}

	public String getBouncedReason() {
		return bouncedReason;
	}

	public void setBouncedReason(String bouncedReason) {
		this.bouncedReason = bouncedReason;
	}
}
