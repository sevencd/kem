package cn.ilanhai.framework.app.test.domain;

import org.w3c.dom.views.AbstractView;

import cn.ilanhai.framework.app.domain.*;

public class UEntity extends AbstractEntity {

	private int id = 0;
	private String name = "";

	public int getId() {
		return id;
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return String.format("id:%s name:%s\r\n", this.id, this.name);
	}

}
