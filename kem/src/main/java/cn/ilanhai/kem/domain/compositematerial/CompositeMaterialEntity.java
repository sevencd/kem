package cn.ilanhai.kem.domain.compositematerial;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CompositeMaterialEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private Date addTime;
	private Date updateTime;
	private String iconUrl;
	private CMState state;
	private CMCategory category;
	private String data;
	private String userId;
	private String type;
	private Integer clientType;
	public CompositeMaterialEntity() {
		state = CMState.New;
		category = CMCategory.Sys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public CMState getState() {
		return state;
	}

	public void setState(CMState state) {
		this.state = state;
	}

	public CMCategory getCategory() {
		return category;
	}

	public void setCategory(CMCategory category) {
		this.category = category;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	

}
