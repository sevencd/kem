package cn.ilanhai.framework.app.dao;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.domain.*;

import java.util.*;

import cn.ilanhai.framework.common.exception.*;

/**
 * 定义数据访问接口
 * 
 * @author he
 *
 */
public interface Dao {
	/**
	 * 保存或更新数据
	 * 
	 * @param enity
	 * @return 失败-1; 成功(反回 0或影响行数或自动增长id)
	 * @throws DaoAppException
	 */
	int save(Entity enity) throws DaoAppException;

	/**
	 * 保存或更新数据
	 * 
	 * @param enity
	 * @return 失败-1; 成功(反回 0或影响行数或自动增长id)
	 * @throws DaoAppException
	 */
	int[] save(Iterator<Entity> enity) throws DaoAppException;

	/**
	 * 删除数据
	 * 
	 * @param entity
	 * @return 失败-1; 成功(反回 0或影响行数)
	 * @throws DaoAppException
	 */
	int delete(Entity entity) throws DaoAppException;

	/**
	 * 删除数据
	 * 
	 * @param entity
	 * @return 失败-1; 成功(反回 0或影响行数)
	 * @throws DaoAppException
	 */
	int[] delete(Iterator<Entity> enity) throws DaoAppException;

	/**
	 * 获取数据
	 * 
	 * @param entity
	 * @param flag
	 *            没有用，只是为了参数重载，默认填false
	 * @return null或Entity
	 * @throws DaoAppException
	 */
	Entity query(Entity entity, boolean flag) throws DaoAppException;

	/**
	 * 获取数据
	 * 
	 * @param entity
	 * @return null或Iterator<Entity>
	 * @throws DaoAppException
	 */
	Iterator<Entity> query(Entity entity) throws DaoAppException;
	/**
	 * 获取数据
	 * 
	 * @param entity
	 * @return null或List<Entity>
	 * @throws DaoAppException
	 */
	List<Entity> queryList(Entity entity) throws DaoAppException;
	 void setApplication(Application application);

	Application getApplication();
}
