package cn.ilanhai.kem.bl.sms;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;

/**
 * 短信验证码使用该接口
 * @author Nature
 *
 */
public interface SmsValicode {
	
	/**
	 * 验证长度
	 */
	int VAILCODE_COUNT = 6;
	/**
	 * 验证过期时间（单位：分钟）
	 */
	int VAILCODE_EXPIRED = 3;
	
	/**
	 * 生成验证码数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException 
	 */
	Entity generate(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 校验验证码
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException 
	 */
	boolean vali(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

}
