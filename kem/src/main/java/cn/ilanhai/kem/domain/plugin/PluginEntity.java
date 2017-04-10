package cn.ilanhai.kem.domain.plugin;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;

public class PluginEntity extends AbstractEntity {

	private static final long serialVersionUID = -4997346627760600671L;

	// 插件编号
	protected Integer pluginId;
	// 插件类型，1：表单插件；2：活动插件
	protected PluginType pluginType;
	// 创建时间
	protected Date createtime;
	// 前端用户ID
	protected String userId;
	// 关联ID
	protected String relationId;
	// 关联ID类型，1 模板 2 专题 3 推广
	protected ManuscriptType relationType;
	// 是否已使用
	protected Boolean isUsed;

	public Integer getPluginId() {
		return pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}

	public PluginType getPluginType() {
		return pluginType;
	}

	public void setPluginType(PluginType pluginType) {
		this.pluginType = pluginType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public ManuscriptType getRelationType() {
		return relationType;
	}

	public void setRelationType(ManuscriptType relationType) {
		this.relationType = relationType;
	}

	public Boolean isUsed() {
		return isUsed;
	}

	public void setUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

}
