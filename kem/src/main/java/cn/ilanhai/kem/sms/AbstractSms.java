package cn.ilanhai.kem.sms;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.kem.sms.protocol.Result;
import cn.ilanhai.kem.sms.protocol.SmsInfo;

/**
 * 短信
 * 
 * @author he
 *
 */
public abstract class AbstractSms {

	/**f
	 * 发送短信
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result sendTo(SmsConfig config, SmsInfo info)
			throws AppException;
}
