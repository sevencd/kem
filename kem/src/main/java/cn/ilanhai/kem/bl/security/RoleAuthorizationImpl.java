package cn.ilanhai.kem.bl.security;

import java.net.URI;
import java.util.BitSet;
import java.util.Iterator;

import org.apache.activemq.state.SessionState;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.CT;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.security.RoleAuthorizationDao;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.security.Authority;
import cn.ilanhai.kem.domain.security.QueryRoleAuthorizationParamerter;
import cn.ilanhai.kem.domain.security.RoleAuthorizationEntity;

@Component("roleauthorization")
public class RoleAuthorizationImpl extends BaseBl implements RoleAuthorization {

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean authorization(RequestContext ctx) throws BlAppException,
			DaoAppException {
		return true;
//		Dao dao = null;
//		QueryRoleAuthorizationParamerter paramerter = null;
//		CT ct = CT.APP_SER_SECURITY;
//		Iterator<Entity> iterator = null;
//		RoleAuthorizationEntity roleAuthorizationEntity = null;
//		URI location = null;
//		Session session = null;
//		UserType userType = null;
//		String path = null;
//		boolean flag = false;
//		Integer authorizationCode = null;
//		try {
//			location = ctx.getLocation();
//			if (location == null)
//				throw new BlAppException(ct.getVal(), "服务定位地址错误");
//			path = location.getRawPath();
//			if (path == null || path.length() <= 0)
//				throw new BlAppException(ct.getVal(), "服务定位格式错误");
//			session = ctx.getSession();
//			if (session.getSessionState().getSessionStateType()
//					.equals(SessionStateType.ANONYMOUS_SESSION_STATE))
//				session.setParameter(Constant.KEY_SESSION_USERTYPE,
//						UserType.ANONYMOUS_USER);
//			userType = session.getParameter(Constant.KEY_SESSION_USERTYPE,
//					UserType.class);
//			if (userType == null)
//				throw new BlAppException(ct.getVal(), "用户类型错误");
//			dao = this.daoProxyFactory.getDao(ctx, RoleAuthorizationDao.class);
//			if (dao == null)
//				throw new BlAppException(ct.getVal(), "角色权限数据访问对象错误");
//			paramerter = new QueryRoleAuthorizationParamerter();
//			paramerter.setGroupId(userType.getValue());
//			paramerter.setUri(path);
//			iterator = dao.query(paramerter);
//			if (iterator == null || !iterator.hasNext())
//				throw new BlAppException(ct.getVal(), "您没有权限访问该资源");
//			while (iterator.hasNext()) {
//				roleAuthorizationEntity = (RoleAuthorizationEntity) iterator
//						.next();
//				if (roleAuthorizationEntity == null)
//					continue;
//				authorizationCode = roleAuthorizationEntity.getAuthorityCode();
//				if (authorizationCode == null)
//					continue;
//
//				if ((authorizationCode & 0xffffffff) == Authority.API.getVal())
//					flag = true;
//			}
//		if (!flag)
//				throw new BlAppException(ct.getVal(), "您没有权限访问该资源");
//			return true;
//		} catch (BlAppException e) {
//			throw e;
//		} catch (DaoAppException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new BlAppException(ct.getVal(), e.getMessage());
//		}
	}
}
