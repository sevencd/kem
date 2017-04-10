package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryExtensionByUserResponse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494040032259457748L;
	
	private String extensionId;
	private Integer extensionType;
	
	public String getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
	public Integer getExtensionType() {
		return extensionType;
	}
	public void setExtensionType(Integer extensionType) {
		this.extensionType = extensionType;
	}
	
}
