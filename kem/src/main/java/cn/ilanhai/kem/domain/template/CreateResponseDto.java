package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 新建模版返回数据
 * 
 * @author hy
 *
 */
public class CreateResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 3044496274582093561L;
	private String templateId;
	private Integer templateType;

	public CreateResponseDto() {

	}

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

}
