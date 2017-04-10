package cn.ilanhai.kem.bl.crawler;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
/**
 * 采集任务配置
 * @author csz
 * @time 2017-03-01 10:00
 */
public interface CrawlerRule {

	/**
	 * 添加任务
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void saveTask(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 删除任务
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void removeTask(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 修改任务
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void updateTask(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 查询任务
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @return Entity
	 */
	Entity listTask(RequestContext context) throws BlAppException;

}
