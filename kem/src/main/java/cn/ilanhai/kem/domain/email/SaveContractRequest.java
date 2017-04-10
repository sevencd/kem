package cn.ilanhai.kem.domain.email;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 保存联系人入参
 * 
 * @author dgj
 *
 */
public class SaveContractRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826835099829118942L;

	private String emailId;
	private List<EmailContractEntity> list;
	private List<EmailGroupEntity> groupList;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<EmailContractEntity> getList() {
		return list;
	}

	public void setList(List<EmailContractEntity> list) {
		this.list = list;
	}

	public List<EmailGroupEntity> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<EmailGroupEntity> groupList) {
		this.groupList = groupList;
	}
}
