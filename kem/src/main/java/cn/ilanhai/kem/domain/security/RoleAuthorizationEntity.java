package cn.ilanhai.kem.domain.security;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 定义角色授权
 * 
 * @author he
 *
 */
public class RoleAuthorizationEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date addTime;
	private Date updateTime;
	private Integer roleId;
	private Integer resourceId;
	private Integer authorityCode;

	public RoleAuthorizationEntity() {

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(Integer authorityCode) {
		this.authorityCode = authorityCode;
	}
	

}
