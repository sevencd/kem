package cn.ilanhai.kem.domain.user.backuser;

import java.sql.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.util.MD5Util;

/**
 * 用户
 * 
 * @author hy
 *
 */
public class BackUserEntity extends AbstractEntity {
	private static final long serialVersionUID = 8125338106010105595L;
	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 密码
	 */
	private String login_pwd;
	/**
	 * 状态
	 */
	private UserStatus status;

	/**
	 * 修改密码
	 * 
	 * @param newPwd
	 */
	public void modifyLoginPwd(String newPwd) {
		this.login_pwd = encryption(newPwd);
	}

	/**
	 * 用户验证
	 * 
	 * @param loginPwd
	 * @return
	 */
	public boolean verifyUser(String loginPwd) {
		return this.login_pwd.equals(MD5Util.getEncryptedPwd(loginPwd));
	}

	/**
	 * 密码加密
	 * 
	 * @param pwd
	 *            密码
	 * @return 加密后的密码
	 */

	private String encryption(String pwd) {
		// TODO 加密
		return pwd;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getLogin_pwd() {
		return login_pwd;
	}

	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
}
