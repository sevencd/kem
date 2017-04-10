package cn.ilanhai.kem.domain.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 精选标签请求
 * 
 * @author hy
 *
 */
public class SetSelectionTagRequestEntity extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624330522886918518L;
	/**
	 * 标签id
	 */
	private Integer tagId;
	private Boolean isSelection;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Boolean getIsSelection() {
		return isSelection;
	}

	public void setIsSelection(Boolean isSelection) {
		this.isSelection = isSelection;
	}

}
