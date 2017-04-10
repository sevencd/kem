package cn.ilanhai.kem.domain;

import java.util.List;
import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ContextDataDto extends AbstractEntity {
	private static final long serialVersionUID = 6828137400533339652L;

	private String config;
	private String data;
	private List<ContextPageDto> pages;

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

	public List<ContextPageDto> getPages() {
		return pages;
	}

	public void setPages(List<ContextPageDto> pages) {
		this.pages = pages;
	}

}
