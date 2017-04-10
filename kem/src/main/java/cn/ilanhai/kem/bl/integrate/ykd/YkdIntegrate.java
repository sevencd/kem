package cn.ilanhai.kem.bl.integrate.ykd;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface YkdIntegrate {
	/**
	 * 有客到登陆
	 * 
	 * @param context
	 * @return 登陆url
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String ykdlogin(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 有客到用户基本信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity userdetail(RequestContext context) throws BlAppException, DaoAppException;
}
