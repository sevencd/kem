package cn.ilanhai.kem.domain.special;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SpecialTagResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -2805041330589388403L;
	private Iterator<Entity> list;

	public Iterator<Entity> getList() {
		return list;
	}

	public void setList(Iterator<Entity> list) {
		this.list = list;
	}

	public SpecialTagResponseDto(Iterator<Entity> list) {
		this.list = list;
	}
}
