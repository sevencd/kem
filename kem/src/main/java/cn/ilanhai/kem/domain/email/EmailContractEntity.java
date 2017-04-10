package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 收件人实体
 * @author dgj
 *
 */
public class EmailContractEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819134273843866362L;

//	private Integer emailContractId;
//	private String emailId;
	private String toName;
	private String emailAddr;
	private String id;

//	public Integer getEmailContractId() {
//		return emailContractId;
//	}
//
//	public void setEmailContractId(Integer emailContractId) {
//		this.emailContractId = emailContractId;
//	}
//
//	public String getEmailId() {
//		return emailId;
//	}
//
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}

	
	public String getToName() {
		return toName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

}
