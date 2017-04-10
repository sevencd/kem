package cn.ilanhai.kem.domain.customer.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchCustomerDataDto extends AbstractEntity {
	private static final long serialVersionUID = 1405006279198511375L;
	private String manuscriptId;
	private String userId;
	private String name;
	private int pv;
	private int uv;
	private int customersum;
	private String time;
	private String img;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

	public int getCustomersum() {
		return customersum;
	}

	public void setCustomersum(int customersum) {
		this.customersum = customersum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
