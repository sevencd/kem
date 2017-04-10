package cn.ilanhai.kem.domain.special;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除专题
 * 
 * @author hy
 *
 */
public class DeleteSpecialDto extends AbstractEntity {
	private static final long serialVersionUID = 7196867609570283821L;
	private List<String> specialIds;

	public List<String> getSpecialIds() {
		return specialIds;
	}

	public void setSpecialIds(List<String> specialIds) {
		this.specialIds = specialIds;
	}

}
