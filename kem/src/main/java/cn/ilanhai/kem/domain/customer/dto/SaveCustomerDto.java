package cn.ilanhai.kem.domain.customer.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.MapDto;

public class SaveCustomerDto extends AbstractEntity {
	private static final long serialVersionUID = -4272160629466807135L;
	private String customerId;
	private MapDto info;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public MapDto getInfo() {
		return info;
	}

	public void setInfo(MapDto info) {
		this.info = info;
	}
}
