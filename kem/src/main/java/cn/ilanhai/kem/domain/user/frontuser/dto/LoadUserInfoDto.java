package cn.ilanhai.kem.domain.user.frontuser.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class LoadUserInfoDto extends AbstractEntity {
	private static final long serialVersionUID = -3149572019354020155L;
	private List<Entity> lists;
	private Integer subUserCount;
	public List<Entity> getLists() {
		return lists;
	}
	public void setLists(List<Entity> lists) {
		this.lists = lists;
	}
	public Integer getSubUserCount() {
		return subUserCount;
	}
	public void setSubUserCount(Integer subUserCount) {
		this.subUserCount = subUserCount;
	}
}
