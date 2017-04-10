package cn.ilanhai.kem.domain.security;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 定义角色
 * 
 * @author he
 *
 */
public class RoleEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date addTime;
	private Date updateTime;
	private String remark;
	private Boolean enable;
	private Boolean delete;
	public RoleEntity() {

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Boolean getDelete() {
		return delete;
	}
	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
	
}
