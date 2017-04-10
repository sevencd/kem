package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 关键字实体
 * 
 * @author hy
 *
 */
public class ConfigKeywordEntity extends AbstractEntity {

	private static final long serialVersionUID = -3265462403176886224L;
	/**
	 * 关键字id
	 */
	private Integer keywordId;
	/**
	 * 模块连接id
	 */
	private Integer modelConfigId;
	/**
	 * 关键字
	 */
	private String keyword;

	public Integer getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
