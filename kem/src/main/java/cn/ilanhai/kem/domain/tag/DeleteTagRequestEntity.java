package cn.ilanhai.kem.domain.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除标签请求
 * 
 * @author hy
 *
 */
public class DeleteTagRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = -2119208095918112134L;
	/**
	 * 标签id
	 */
	private Integer tagId;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}
