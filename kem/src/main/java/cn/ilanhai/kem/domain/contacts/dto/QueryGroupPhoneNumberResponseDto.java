package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryGroupPhoneNumberResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -3036826718637113312L;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhones(String phone) {
		this.phone = phone;
	}
}
