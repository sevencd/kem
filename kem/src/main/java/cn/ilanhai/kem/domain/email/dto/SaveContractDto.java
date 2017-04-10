package cn.ilanhai.kem.domain.email.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.kem.domain.email.EmailContractEntity;
import cn.ilanhai.kem.domain.email.EmailGroupEntity;

/**
 * 保存群组联系人入参
 * 
 * @author dgj
 *
 */
public class SaveContractDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826835099829118942L;

	private String emailId;
	private List<String> customerIds;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<String> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}

}
