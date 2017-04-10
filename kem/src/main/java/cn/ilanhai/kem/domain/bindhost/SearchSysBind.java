package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchSysBind extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1353768057743666212L;

	private String key;//查询sys_config

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param string the key to set
	 */
	public void setKey(String string) {
		this.key = string;
	}


}
