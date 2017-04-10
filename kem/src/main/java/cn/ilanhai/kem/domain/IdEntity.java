package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class IdEntity<T> extends AbstractEntity{

	private static final long serialVersionUID = 9219346247253730525L;
	
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
}
