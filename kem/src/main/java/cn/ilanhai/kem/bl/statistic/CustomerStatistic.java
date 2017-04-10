package cn.ilanhai.kem.bl.statistic;

import java.util.Iterator;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
/**
 * 客户统计
 * @author csz
 * @time 2017-03-22
 */
public interface CustomerStatistic {

	/**
	 * 按天数统计客户数据
	 * 
	 * @param context 请求上下文
	 * @throws BlAppException
	 * @throws SessionContainerException 
	 */
	Entity getCustomerStatisticByDays(RequestContext context) throws BlAppException, SessionContainerException;
	
	/**
	 * 客户推广信息
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	Iterator<Entity> extensionlistinfo(RequestContext context) throws BlAppException, SessionContainerException;
}
