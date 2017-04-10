package cn.ilanhai.kem.bl.security;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface RoleAuthorization {

	boolean authorization(RequestContext context) throws BlAppException,
			DaoAppException;
}
