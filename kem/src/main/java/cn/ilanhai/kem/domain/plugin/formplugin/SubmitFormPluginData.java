package cn.ilanhai.kem.domain.plugin.formplugin;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SubmitFormPluginData extends AbstractEntity {
	private String name;// 名称 不可为空 最大20个字符
	private String phone;// 电话 不可为空 符合手机格式
	private String email;// 邮件 不可为空 符合邮件格式
	private String relationId;// 关联编号 不可为空

	public SubmitFormPluginData() {

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

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	

}
