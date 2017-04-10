package cn.ilanhai.kem.domain.plugin.formplugin;

import cn.ilanhai.kem.domain.Page;

public class SearchFormPluginRequestData extends Page {

	private String word;// 可空，收索关键 模糊匹配 分别在名称 电话 邮件中搜索
	private String relationId;// 关联编号 大于0
	private String orderMode;

	public SearchFormPluginRequestData() {

	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}



	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

}
