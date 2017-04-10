package cn.ilanhai.kem.payment;

import java.util.Map;

import cn.ilanhai.framework.common.exception.AppException;

/**
 * 支付抽象类型
 * 
 * @author he
 *
 */
public abstract class AbstractPayment {
	/**
	 * 支付
	 * 
	 * @param config
	 * @param info
	 * @return
	 */
	public abstract String pay(PaymentConfig config, PaymentInfo info)
			throws AppException;

	/**
	 * 通知处理
	 * 
	 * @param params
	 * @return 反回订单id
	 */
	public abstract String nodify(Map<String, String> params);
}
