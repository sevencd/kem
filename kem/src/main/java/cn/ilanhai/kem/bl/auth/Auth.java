package cn.ilanhai.kem.bl.auth;

import java.util.Iterator;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Auth {
	/**
	 * 获取第三主登陆平台
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Iterator<Entity> platform(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 第三方登陆认证
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity auth(RequestContext context) throws BlAppException, DaoAppException;
}
