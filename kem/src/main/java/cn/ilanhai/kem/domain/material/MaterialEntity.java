package cn.ilanhai.kem.domain.material;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MaterialEntity extends AbstractEntity {
	private static final long serialVersionUID = 2597223490710324237L;

	// 类型id
	private String material_id;
	// 分类名称
	private String material_name;
	// 分类状态
	private Integer material_state;
	// 创建时间
	private Date createtime;
	// 终端类型
	private Integer terminal_type;

	private Integer count;
	
	private String userId;

	private String key;
	public String getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public Integer getMaterial_state() {
		return material_state;
	}

	public void setMaterial_state(Integer material_state) {
		this.material_state = material_state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(Integer terminal_type) {
		this.terminal_type = terminal_type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
