package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 标签实体
 * 
 * @author hy
 *
 */
public class ConfigTagEntity extends AbstractEntity {

	private static final long serialVersionUID = -3265462403176886224L;
	/**
	 * 标签id
	 */
	private Integer tagId;
	/**
	 * 模块连接id
	 */
	private Integer modelConfigId;
	/**
	 * 标签
	 */
	private String tag;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
