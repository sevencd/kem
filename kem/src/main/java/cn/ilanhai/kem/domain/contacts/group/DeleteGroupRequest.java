package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除群组请求
 * 
 * @author dgj
 *
 */
public class DeleteGroupRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1083552722673881491L;

	private String[] list;
	private String userId;

	public String[] getList() {
		return list;
	}

	public void setList(String[] list) {
		this.list = list;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
