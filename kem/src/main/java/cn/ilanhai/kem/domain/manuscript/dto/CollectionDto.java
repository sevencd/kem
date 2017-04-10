package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CollectionDto extends AbstractEntity {
	private static final long serialVersionUID = -2670189838047335690L;
	/**
	 * 稿件ID，不可空
	 */
	private String manuscriptId;
	/**
	 * 是否收藏 0：不收藏，1：收藏 不填为不收藏
	 */
	private Integer isCollection;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}
}
