package cn.ilanhai.kem.domain.smsright;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SmsRightEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2861479185137072621L;
	
	private Integer smsId;
	private Date createTime;
	private Date updateTime;
	private Integer quantity;
	private String userId;
	private Integer total;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSmsId() {
		return smsId;
	}
	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
