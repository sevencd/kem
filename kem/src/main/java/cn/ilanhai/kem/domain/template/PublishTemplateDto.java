package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TemplateState;

/**
 * @author he
 *
 */
public class PublishTemplateDto extends AbstractEntity {
	private String userId;
	private Date createtime;
	private Integer templateState;
	private String templateContent;

	public PublishTemplateDto() {

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

	public Integer getTemplateState() {
		return templateState;
	}

	public void setTemplateState(Integer templateState) {
		this.templateState = templateState;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

}
