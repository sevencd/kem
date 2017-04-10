package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class TemplateTagResponseDataEntity extends AbstractEntity {
	private static final long serialVersionUID = 2327497731069679357L;
	private List<Entity> list;

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}

	public TemplateTagResponseDataEntity(List<Entity> list) {
		this.list = list;
	}
}
