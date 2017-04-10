package cn.ilanhai.kem.bl.paymentservice;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;

/**
 * 
 * 支付接口 支付相关功能由此接口提供 2016-11-16
 * 
 * @author Nature
 *
 */
public interface PaymentService {

	/**
	 * 下单接口
	 * 
	 * @param context
	 * @return
	 * @throws AppException
	 * @throws SessionContainerException
	 */
	Entity placeorder(RequestContext context) throws AppException, SessionContainerException;

	/**
	 * 保存支付配置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean savepayconfigure(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载支付配置
	 * 
	 * @param context
	 * @return
	 * @throws AppException
	 * @throws SessionContainerException
	 */
	Entity loadpayconfigure(RequestContext context) throws AppException, SessionContainerException;

	/**
	 * 保存支付配置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean savepayinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载后台支付配置信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadpayinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询用户订单信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchorder(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询支付状态
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	Integer searchpaystatus(RequestContext context) throws BlAppException, DaoAppException, SessionContainerException;

	/**
	 * 查询服务状态
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	Integer getrightstatus(RequestContext context) throws BlAppException, DaoAppException, SessionContainerException;

	/**
	 * 保存服务的价格区间
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws SessionContainerException
	 */
	boolean saveservicepricerange(RequestContext context) throws BlAppException, DaoAppException, SessionContainerException;

	/**
	 * 
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadpricerange(RequestContext context) throws BlAppException, DaoAppException;
}
