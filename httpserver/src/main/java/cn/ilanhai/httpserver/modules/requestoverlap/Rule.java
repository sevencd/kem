package cn.ilanhai.httpserver.modules.requestoverlap;

import cn.ilanhai.framework.uitl.Str;

/**
 * 规则
 * 
 * @author he
 *
 */
public class Rule {
	/**
	 * 匹配模式 true 精确 false 包含
	 */
	private boolean impreciseMatching;
	/**
	 * url
	 */
	private String url;

	public Rule() {

	}

	/**
	 * 是否匹配
	 * 
	 * @param ignoreCase
	 * @param url
	 * @return
	 */
	public boolean isMatching(boolean ignoreCase, String url) {
		if (Str.isNullOrEmpty(url))
			return false;
		if (Str.isNullOrEmpty(this.url))
			return false;
		// 忽略大小写
		if (ignoreCase) {
			if (this.impreciseMatching)// 精确匹配
				return url.equalsIgnoreCase(this.url);
			// 包含配置
			url = url.toUpperCase();
			return url.indexOf(this.url.toUpperCase()) > -1;
		}
		// 不忽略大小写
		if (this.impreciseMatching)// 精确匹配
			return url.equals(this.url);
		// 包含配置
		return url.indexOf(this.url) > -1;
	}

	public boolean isImpreciseMatching() {
		return impreciseMatching;
	}

	public void setImpreciseMatching(boolean impreciseMatching) {
		this.impreciseMatching = impreciseMatching;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
