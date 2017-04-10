package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CollectionTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = -2670189838047335690L;
	/**
	 * 模版ID，不可空
	 */
	private String templateId;
	/**
	 * 是否收藏 0：不收藏，1：收藏 不填为不收藏
	 */
	private Integer isCollection;

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
