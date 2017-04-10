package cn.ilanhai.kem.domain.smsright;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class AddSmsQuantityEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341069290996210743L;

	private String UserId;
	private Integer quantity;
	private Integer total;
	private Date createTime;
	private Date updateTime;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
