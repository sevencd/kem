package cn.ilanhai.kem.domain.customer;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 客户来源实体
 * 
 * @author dgj
 *
 */
public class OriginationEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5030184133221730852L;

	private Integer id;
	private String value;
	private String key;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
