package cn.ilanhai.kem.domain.manuscript.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除稿件
 * 
 * @author hy
 *
 */
public class DeleteManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = -4489310394416790506L;
	private List<String> manuscriptIds;

	public DeleteManuscriptDto() {

	}

	public List<String> getManuscriptIds() {
		return manuscriptIds;
	}

	public void setManuscriptIds(List<String> manuscriptIds) {
		this.manuscriptIds = manuscriptIds;
	}
}
