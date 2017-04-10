package cn.ilanhai.kem.domain.customer;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CustomerMainEntity extends AbstractEntity {
	private static final long serialVersionUID = -6687504826747471003L;
	private String customerId;
	private String userId;
	private Date createtime;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
