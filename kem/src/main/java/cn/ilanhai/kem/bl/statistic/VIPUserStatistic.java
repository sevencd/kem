package cn.ilanhai.kem.bl.statistic;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
/**
 * 会员统计
 * @author csz
 */
public interface VIPUserStatistic {

	/**
	 * 数据总览数据
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity vipUserStatisticAmount(RequestContext context) throws BlAppException, DaoAppException;

}
