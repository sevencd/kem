package cn.ilanhai.framework.app.args;

import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 定义参数接口
 * 
 * @author he
 *
 */
public interface Args {
	/**
	 * 将对象(Enity、Iteror<Entity)、josn、xml格式数据转换成指定类型对象
	 * 
	 * @param clazz
	 * @return
	 */
	<T> T getDomain(Class<T> clazz) throws BlAppException;
}
