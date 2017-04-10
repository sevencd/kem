package cn.ilanhai.kem.bl.bindhost;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface BindHost {

	
	/**
	 * 校验验证码
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 */
	boolean bind(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 加载后台支付配置信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loaduserhost(RequestContext context) throws BlAppException, DaoAppException;
}
