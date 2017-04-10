package cn.ilanhai.kem.domain.contacts.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveContactsDto extends AbstractEntity {
	private static final long serialVersionUID = -6116827379315327281L;

	private String contractId;
	private Integer contractType;
	private List<ContactsKeyValue> info;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public List<ContactsKeyValue> getInfo() {
		return info;
	}

	public void setInfo(List<ContactsKeyValue> info) {
		this.info = info;
	}
}
