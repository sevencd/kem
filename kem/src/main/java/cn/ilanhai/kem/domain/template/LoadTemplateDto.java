package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TemplateState;

public class LoadTemplateDto extends AbstractEntity {
	private String userId;
	private Date createtime;
	private TemplateState templateState;
	private String templateContent;
	public LoadTemplateDto(){
		
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	

}
