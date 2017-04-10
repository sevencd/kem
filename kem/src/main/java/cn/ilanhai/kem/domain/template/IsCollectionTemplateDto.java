package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除模版
 * 
 * @author hy
 *
 */
public class IsCollectionTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = -4489310394416790506L;
	private String templateId;
	private Integer isCollection;

	public IsCollectionTemplateDto() {

	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}
}
