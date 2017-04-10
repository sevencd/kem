package cn.ilanhai.framework.app.domain;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 定义抽象领域对象
 * 
 * @author he
 *
 */
public class AbstractNodeEntity implements Entity {

	private ArrayList<Entity> child = null;

	public AbstractNodeEntity() {
		this.child = new ArrayList<Entity>();
	}

	public Iterator<Entity> getNodes() {

		return child.iterator();
	}

	public boolean add(Entity e) {
		return this.child.add(e);
	}

	public boolean remove(Entity e) {
		return this.child.remove(e);
	}

	public Entity getNode(int index) {
		if (index < 0 || index >= this.child.size() - 1)
			return null;
		return child.get(index);
	}
}
