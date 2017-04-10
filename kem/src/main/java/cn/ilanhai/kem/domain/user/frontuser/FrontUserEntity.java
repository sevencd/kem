package cn.ilanhai.kem.domain.user.frontuser;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.util.MD5Util;

/**
 * 用户
 * 
 * @author hy
 *
 */
public class FrontUserEntity extends AbstractEntity {
	private static final long serialVersionUID = -6274621638426467194L;
	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 用户类型
	 */
	private UserType userType;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 电话号码
	 */
	private String phoneNo;
	/**
	 * 邮件
	 */
	private String email;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 密码
	 */
	private String loginPwd;

	/**
	 * 新密码
	 */
	private String newPwd;
	/**
	 * 状态
	 */
	private UserStatus status;

	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 图形验证码
	 */
	private String imgCode;

	private String userImg;
	private String companyCode;
	private boolean isExistPhoneNo;

	private List<FrontUserInfoEntity> infos;

	public List<FrontUserInfoEntity> getInfos() {
		return infos;
	}

	public void setInfos(List<FrontUserInfoEntity> infos) {
		this.infos = infos;
	}

	/**
	 * 修改密码
	 * 
	 * @param newPwd
	 */
	public void modifyLoginPwd(String newPwd) {
		this.loginPwd = MD5Util.getEncryptedPwd(newPwd);
	}

	/**
	 * 用户验证
	 * 
	 * @param loginPwd
	 * @return
	 */
	public boolean verifyUser(String loginPwd) {
		return MD5Util.getEncryptedPwd(loginPwd).equals(this.loginPwd);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getUserName() {
		if (Str.isNullOrEmpty(userName)) {
			if (Str.isNullOrEmpty(phoneNo)) {
				return "";
			}
			return phoneNo;
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public boolean isExistPhoneNo() {
		return isExistPhoneNo;
	}

	public void setExistPhoneNo(boolean isExistPhoneNo) {
		this.isExistPhoneNo = isExistPhoneNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
