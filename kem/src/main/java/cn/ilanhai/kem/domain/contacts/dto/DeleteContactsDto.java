package cn.ilanhai.kem.domain.contacts.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteContactsDto extends AbstractEntity {

	private static final long serialVersionUID = -611875836593634919L;

	private List<String> ids;
	private String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
