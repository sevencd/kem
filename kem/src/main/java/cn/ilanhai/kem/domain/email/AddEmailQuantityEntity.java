package cn.ilanhai.kem.domain.email;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class AddEmailQuantityEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6202408076782203905L;

	private String userId;
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
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
