package cn.ilanhai.kem.domain.contacts.group;

import java.util.List;
import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存群组联系人
 * @author dgj
 *
 */
public class AddGroupContactsRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4400917210852286083L;

	private String groupId;
	private List<String> list;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}
