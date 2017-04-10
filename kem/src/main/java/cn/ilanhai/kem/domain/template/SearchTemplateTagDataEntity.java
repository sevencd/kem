package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTemplateTagDataEntity extends AbstractEntity {
	private static final long serialVersionUID = 2327497731069679357L;
	private String userId;
	private Integer tag_type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTag_type() {
		return tag_type;
	}

	public void setTag_type(Integer tag_type) {
		this.tag_type = tag_type;
	}
	
}
