package cn.ilanhai.kem.domain.member.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteUserResourcesDto extends AbstractEntity {
	private static final long serialVersionUID = -5599410608993138506L;

	private List<String> userIds;
	/**
	 * 服务id
	 */
	private List<Integer> serviceIds;

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<Integer> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<Integer> serviceIds) {
		this.serviceIds = serviceIds;
	}
}
