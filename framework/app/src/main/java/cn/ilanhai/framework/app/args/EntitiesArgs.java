package cn.ilanhai.framework.app.args;

import java.util.*;

import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 定义参数对象(Iterator<Entity>)格式
 * 
 * @author he
 *
 */
public class EntitiesArgs implements Args {
	private Iterator<Entity> entity = null;

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
	public Iterator<Entity> getEntity() {
		return entity;
	}

	/**
	 * 设置领域对象
	 * 
	 * @param entity
	 */
	public void setEntity(Iterator<Entity> entity) {
		this.entity = entity;
	}

}
