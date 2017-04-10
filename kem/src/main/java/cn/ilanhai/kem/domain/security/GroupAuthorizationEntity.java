package cn.ilanhai.kem.domain.security;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 定交组与角色的关系
 * 
 * @author he
 *
 */
public class GroupAuthorizationEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date addTime;
	private Date updateTime;
	private Integer groupId;
	private Integer roleId;
	private boolean enable;

	public GroupAuthorizationEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
