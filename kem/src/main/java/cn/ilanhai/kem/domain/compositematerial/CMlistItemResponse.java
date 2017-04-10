package cn.ilanhai.kem.domain.compositematerial;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CMlistItemResponse extends AbstractEntity {
	private String id;
	private String iconUrl;
	private String type;
	private Integer clientType;
	public CMlistItemResponse(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	
	
}
