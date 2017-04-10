package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class TagResponseDataEntity extends AbstractEntity {
	private static final long serialVersionUID = 2327497731069679357L;
	private String tagName;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
