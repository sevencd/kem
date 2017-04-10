package cn.ilanhai.kem.domain.plugin.formplugin;

import cn.ilanhai.kem.domain.Page;

/**
 * @author he
 *
 */
public class SearchFormPluginData extends Page {
	private Integer pluginId;
	private String word;// 可空，收索关键 模糊匹配 分别在名称 电话 邮件中搜索

	public SearchFormPluginData() {

	}

	public Integer getPluginId() {
		return pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}


	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
