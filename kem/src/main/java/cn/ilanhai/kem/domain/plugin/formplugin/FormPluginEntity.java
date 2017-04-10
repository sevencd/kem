package cn.ilanhai.kem.domain.plugin.formplugin;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 表单插件实体
 * 
 * @author he
 *
 */
public class FormPluginEntity extends AbstractEntity {
	private Integer id;
	private Integer pluginId;
	private Date addTime;
	private Date updateTime;
	private String name;
	private String phone;
	private String email;

	public FormPluginEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPluginId() {
		return pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
