package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ShelfTemplateRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 3184863553420126732L;
	private String templateId;
	private String shelfReason;
	private Boolean isShelf;
	private String tags;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getShelfReason() {
		return shelfReason;
	}

	public void setShelfReason(String shelfReason) {
		this.shelfReason = shelfReason;
	}

	public Boolean getIsShelf() {
		return isShelf;
	}

	public void setIsShelf(Boolean isShelf) {
		this.isShelf = isShelf;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
