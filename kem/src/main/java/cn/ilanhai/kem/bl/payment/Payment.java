package cn.ilanhai.kem.bl.payment;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 支付接口
 * 
 * @author he
 *
 */
public interface Payment {

	/**
	 * 支付
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String pay(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 支付宝异步通
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean alipaynodify(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 微信异步通
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean weixinpaynodify(RequestContext context) throws BlAppException,
			DaoAppException;

}
