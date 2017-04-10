package cn.ilanhai.kem.domain.manuscript.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTagsResultDto extends AbstractEntity {
	private static final long serialVersionUID = 6250151820854346127L;
	private List<String> tags;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public SearchTagsResultDto(List<String> tags) {
		this.tags = tags;
	}
}
