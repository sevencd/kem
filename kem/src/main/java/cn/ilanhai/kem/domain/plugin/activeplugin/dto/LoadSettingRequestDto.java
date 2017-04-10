package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载活动设置请求传输对象
 * @author Nature
 *
 */
public class LoadSettingRequestDto extends AbstractEntity{

	private static final long serialVersionUID = -4632924630692514154L;

	//关联ID
	private String relationId;
	private Integer activePluginType;

	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public Integer getActivePluginType() {
		return activePluginType;
	}
	public void setActivePluginType(Integer activePluginType) {
		this.activePluginType = activePluginType;
	}
	
}
