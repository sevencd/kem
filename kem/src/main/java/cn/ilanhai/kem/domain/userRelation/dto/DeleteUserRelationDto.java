package cn.ilanhai.kem.domain.userRelation.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询用户关系配置
 * 
 * @author hy
 *
 */
public class DeleteUserRelationDto extends AbstractEntity {
	private static final long serialVersionUID = 8125338106010105595L;
	/**
	 * 用户ID
	 */
	private List<Integer> ids;
	private String userId;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
