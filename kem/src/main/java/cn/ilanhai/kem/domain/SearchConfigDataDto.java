package cn.ilanhai.kem.domain;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchConfigDataDto extends AbstractEntity {
	private static final long serialVersionUID = 6828137400533339652L;

	private String name;
	private List<String> keywords;
	private String description;
	private boolean isUnRights;
	private String url;

	public void addKeyWord(String keyWord) {
		if (keywords == null) {
			keywords = new ArrayList<>();
		}
		keywords.add(keyWord);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keyWords) {
		this.keywords = keyWords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSummary(String summary) {
		this.description = summary;
	}

	public boolean isUnRights() {
		return isUnRights;
	}

	public void setUnRights(boolean isUnRights) {
		this.isUnRights = isUnRights;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
