package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ContextPageDto extends AbstractEntity {
	private static final long serialVersionUID = 3090305358998335478L;
	private String config;
	private String data;
	private String htmlData;

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHtmlData() {
		return htmlData;
	}

	public void setHtmlData(String htmlData) {
		this.htmlData = htmlData;
	}

}
