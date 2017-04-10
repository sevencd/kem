package cn.ilanhai.kem.bl.statistic;

import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
/**
 * 应用统计
 * @author csz
 */
public interface ApplicationStatistic {

	/**
	 * 应用统计总览
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	List<Entity> applicationStatisticAmount(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 邮件应用统计详情
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity emailApplicationStatisticDetail(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 短信应用统计详情
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity smsApplicationStatisticDetail(RequestContext context) throws BlAppException,
			DaoAppException;

	

}
