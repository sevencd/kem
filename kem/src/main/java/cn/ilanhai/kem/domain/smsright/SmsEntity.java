package cn.ilanhai.kem.domain.smsright;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 短信实体
 * 
 * @author he
 *
 */
public class SmsEntity extends AbstractEntity {
	public SmsEntity() {

	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * key生成id
	 */
	private String smsId;
	/**
	 * 是否删除，1删除，0没有
	 */
	private boolean delete;
	/**
	 * 开通时间
	 */
	private Date createTime;
	/**
	 * 
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 用户编号
	 */
	private String userId;

}
