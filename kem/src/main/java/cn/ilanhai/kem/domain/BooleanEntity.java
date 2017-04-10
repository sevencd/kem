package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * boolean型实体通用实体
 * @author Nature
 *
 */
public class BooleanEntity extends AbstractEntity{

	private static final long serialVersionUID = 5846797873391006871L;
	private boolean value;

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
}
