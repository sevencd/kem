package cn.ilanhai.kem.domain.email;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 邮件right实体
 * @author dgj
 *
 */
public class EmailRightEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8897339878068598873L;
	
	private Integer emailId;
	private Date createTime;
	private Date updateTime;
	private Integer remainTimes;
	private String userId;
	private Integer total;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getEmailId() {
		return emailId;
	}
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
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
	public Integer getRemainTimes() {
		return remainTimes;
	}
	public void setRemainTimes(Integer remainTimes) {
		this.remainTimes = remainTimes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
