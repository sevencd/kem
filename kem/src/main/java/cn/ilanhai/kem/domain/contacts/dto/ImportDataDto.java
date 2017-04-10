package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ImportDataDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5652351303917181548L;
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
