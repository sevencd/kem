package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryDrawprizeDataDto extends AbstractEntity {
	private static final long serialVersionUID = 2604706158267357403L;
	private Integer pluginId;
	private String phoneNo;
	private boolean isWinning;
	public Integer getPluginId() {
		return pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public boolean isWinning() {
		return isWinning;
	}

	public void setWinning(boolean isWinning) {
		this.isWinning = isWinning;
	}
}
