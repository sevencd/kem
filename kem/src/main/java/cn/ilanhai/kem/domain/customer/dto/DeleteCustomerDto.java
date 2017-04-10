package cn.ilanhai.kem.domain.customer.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteCustomerDto extends AbstractEntity {
	private static final long serialVersionUID = 1405006279198511375L;
	private List<String> customerIds;
	private String userId;
	public List<String> getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
