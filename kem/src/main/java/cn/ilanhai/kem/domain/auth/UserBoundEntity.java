package cn.ilanhai.kem.domain.auth;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 用户绑定实体
 * 
 * @author he
 *
 */
public class UserBoundEntity extends AbstractEntity {
	private int id;
	private String userId;
	private Date addTime;
	private Date updateTime;
	private String authData;
	private String type;
	private String tag;
	private String at;
	private Date atUpdateTime;
	private Integer atExpiredTime;
	private String atData;
	private Integer atExpiryDate;

	public UserBoundEntity() {

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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAuthData() {
		return authData;
	}

	public void setAuthData(String authData) {
		this.authData = authData;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public Date getAtUpdateTime() {
		return atUpdateTime;
	}

	public void setAtUpdateTime(Date atUpdateTime) {
		this.atUpdateTime = atUpdateTime;
	}

	public Integer getAtExpiredTime() {
		return atExpiredTime;
	}

	public void setAtExpiredTime(Integer atExpiredTime) {
		this.atExpiredTime = atExpiredTime;
	}

	public String getAtData() {
		return atData;
	}

	public void setAtData(String atData) {
		this.atData = atData;
	}

	public Integer getAtExpiryDate() {
		return atExpiryDate;
	}

	public void setAtExpiryDate(Integer atExpiryDate) {
		this.atExpiryDate = atExpiryDate;
	}
	
}
