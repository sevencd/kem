package cn.ilanhai.kem.domain.member.dto;


import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteUserResourceDto extends AbstractEntity {
	private static final long serialVersionUID = -5599410608993138506L;

	private String userId;
	/**
	 * 服务id
	 */
	private Integer serviceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
}
