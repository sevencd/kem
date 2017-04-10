package cn.ilanhai.kem.bl.statistic;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface ManuscriptStatistic {

	/**
	 * 数据总览数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity total(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 数据传播数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity propagate(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 停留时间数据(单位秒)
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity residencetime(RequestContext context) throws BlAppException,
			DaoAppException;

	boolean addresidencetime(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 传播层级数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity sharelevel(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 地域分布数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity areadistribution(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 地域浏览量排名数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity arearanking(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 渠道分析数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity channelanalysis(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 收集数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean collectmanuscriptdata(RequestContext context)
			throws BlAppException, DaoAppException;

	/**
	 * 分享数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean sharedatasubmit(RequestContext context) throws BlAppException,
			DaoAppException;

}
