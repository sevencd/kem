package cn.ilanhai.kem.domain.user.trafficuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryTrafficUserPluginEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259619017171331766L;

	private Integer trafficuserId;
	private String name;
	private String phoneNo;
	private String extensionId;
	public Integer getTrafficuserId() {
		return trafficuserId;
	}
	public void setTrafficuserId(Integer trafficuserId) {
		this.trafficuserId = trafficuserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
