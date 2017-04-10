package cn.ilanhai.framework.app.args;

import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 定义参数对象(Entity)格式
 * 
 * @author he
 *
 */
public class EntityArgs implements Args {
	private Entity entity = null;

	public <T> T getDomain(Class<T> clazz) throws BlAppException {
		try {
			return (T) entity;
		} catch (Exception e) {
			throw new BlAppException(-1, "参数据类型转换错误");
		}
	}

	/**
	 * 获取领域对象
	 * 
	 * @return
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * 设置领域对象
	 * 
	 * @param entity
	 */
	public Entity getEntity() {
		return entity;
	}

}
