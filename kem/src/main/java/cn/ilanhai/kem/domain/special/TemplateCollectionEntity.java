package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模版收藏实体
 * 
 * @author hy
 *
 */
public class TemplateCollectionEntity extends AbstractEntity {
	private static final long serialVersionUID = 7794897282387881303L;
	/**
	 * 收藏id
	 */
	private Integer collectionId;
	/**
	 * 模版id
	 */
	private String templateId;
	/**
	 * 用户
	 */
	private Integer userId;
	/**
	 * 收藏状态
	 */
	private Integer collectionState;

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCollectionState() {
		return collectionState;
	}

	public void setCollectionState(Integer collectionState) {
		this.collectionState = collectionState;
	}
}
