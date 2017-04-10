package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.sql.Date;

public class Member {
	public Member() {

	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtUpdated() {
		return gmtUpdated;
	}

	public void setGmtUpdated(Date gmtUpdated) {
		this.gmtUpdated = gmtUpdated;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getAme() {
		return ame;
	}

	public void setAme(String ame) {
		this.ame = ame;
	}

	public String getVars() {
		return vars;
	}

	public void setVars(String vars) {
		this.vars = vars;
	}

	/**
	 * 成员创建时间
	 */
	private Date gmtCreated;
	/**
	 * 成员修改时间
	 */
	private Date gmtUpdated;
	/**
	 * 所属地址列表
	 */
	private String address;
	/**
	 * 成员邮件地址
	 */
	private String member;
	/**
	 * 成员姓名
	 */
	private String ame;
	/**
	 * 变量
	 */
	private String vars;
}
