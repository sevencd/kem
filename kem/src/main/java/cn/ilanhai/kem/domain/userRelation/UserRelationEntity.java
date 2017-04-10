package cn.ilanhai.kem.domain.userRelation;

import java.sql.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;

/**
 * 用户关系配置
 * 
 * @author hy
 *
 */
public class UserRelationEntity extends AbstractEntity {
	private static final long serialVersionUID = 8125338106010105595L;

	public UserRelationEntity() {

	}

	/**
	 * 主键编号
	 */
	private int id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户主主账户id
	 */
	private String fatherUserId;
	/**
	 * 用户类型 0主账户 1子账户
	 */
	private int userType;

	/**
	 * 短信消耗策略 0主账户 1子账户
	 */
	private int userSms;
	/**
	 * 邮件消耗策略 0主账户 1子账户
	 */
	private int userEmail;
	/**
	 * b2b消耗策略 0主账户 1子账户
	 */
	private int userB2b;
	/**
	 * 客户消耗策略 0主账户 1子账户
	 */
	private int userCustomer;

	private int userWechat;

	private int userSubuser;
	private int userPublishTime;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 状态 0:启用1:停用
	 */
	private int state;

	private String userPhone;

	public String getResouseUserId(int serviceId) {
		switch (serviceId - 1) {
		case PayInfoServiceInfoEntity.EMAIL:
			return getId(userEmail);
		case PayInfoServiceInfoEntity.SMS:
			return getId(userSms);
		case PayInfoServiceInfoEntity.CUSTOMERCLUE:
			return getId(userCustomer);
		case PayInfoServiceInfoEntity.B2B:
			return getId(userB2b);
		case PayInfoServiceInfoEntity.SUBACCOUNT:
			return getId(userSubuser);
		case PayInfoServiceInfoEntity.PUBLISHNUM:
			return getId(userPublishTime);
		}
		return fatherUserId;
	}

	private String getId(int type) {
		switch (type) {
		case 0:
			return fatherUserId;
		case 1:
			return userId;

		}
		return fatherUserId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFatherUserId() {
		return fatherUserId;
	}

	public void setFatherUserId(String fatherUserId) {
		this.fatherUserId = fatherUserId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getUserSms() {
		return userSms;
	}

	public void setUserSms(int userSms) {
		this.userSms = userSms;
	}

	public int getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(int userEmail) {
		this.userEmail = userEmail;
	}

	public int getUserB2b() {
		return userB2b;
	}

	public void setUserB2b(int userB2b) {
		this.userB2b = userB2b;
	}

	public int getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(int userCustomer) {
		this.userCustomer = userCustomer;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getUserWechat() {
		return userWechat;
	}

	public void setUserWechat(int userWechat) {
		this.userWechat = userWechat;
	}

	public int getUserSubuser() {
		return userSubuser;
	}

	public void setUserSubuser(int userSubuser) {
		this.userSubuser = userSubuser;
	}

	public int getUserPublishTime() {
		return userPublishTime;
	}

	public void setUserPublishTime(int userPublishTime) {
		this.userPublishTime = userPublishTime;
	}
}
