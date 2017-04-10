package cn.ilanhai.kem.domain.security;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 定义资源结构
 * 
 * @author he
 *
 */
public class ResourceEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String uri;
	private Date addTime;
	private Date updateTime;
	private Boolean enable;
	private String remark;
	private String name;
	private Boolean delete;
	private ResourceType type;
	private String appId;

	public ResourceEntity() {
		this.type = ResourceType.API;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
