package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模版收藏实体
 * 
 * @author hy
 *
 */
public class ManuscriptCollectionEntity extends AbstractEntity {
	private static final long serialVersionUID = 7794897282387881303L;
	/**
	 * 收藏id
	 */
	private Integer collectionId;
	/**
	 * 稿件版id
	 */
	private String manuscriptId;
	/**
	 * 用户
	 */
	private String userId;
	/**
	 * 收藏状态
	 */
	private Integer collectionState;
	/**
	 * 创建时间
	 */
	private Date createtime;

	private Integer manuscriptType;

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCollectionState() {
		return collectionState;
	}

	public void setCollectionState(Integer collectionState) {
		this.collectionState = collectionState;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getManuscriptType() {
		return manuscriptType;
	}

	public void setManuscriptType(Integer manuscriptType) {
		this.manuscriptType = manuscriptType;
	}
}
