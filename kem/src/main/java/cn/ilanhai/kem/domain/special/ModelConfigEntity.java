package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模块和配置的连接实体
 * 
 * @author hy
 *
 */
public class ModelConfigEntity extends AbstractEntity {

	private static final long serialVersionUID = 3162660801871712614L;
	/**
	 * id
	 */
	private Integer modelConfigId;
	/**
	 * 模块id
	 */
	private String modelId;
	/**
	 * 模块类型
	 */
	private Integer modelType;

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}
}
