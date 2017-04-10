package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * id实体
 * 
 * @author he
 *
 */
public class IdDto extends AbstractEntity {

	private static final long serialVersionUID = 3377542153607693436L;
	
	private int id;

	public IdDto() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
