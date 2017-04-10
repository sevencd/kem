package cn.ilanhai.kem.bl.integrate.ykd.impl;


import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.integrate.ykd.YkdIntegrate;
import cn.ilanhai.kem.bl.integrate.ykd.YkdIntegrateManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.YkdUserEntity;

@Component("ykdintegrate")
public class YkdIntegrateImpl extends BaseBl implements YkdIntegrate {

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public String ykdlogin(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct = null;
		try {
			BLContextUtil.checkFrontUserLogined(context);
			Dao dao = this.daoProxyFactory.getDao(context, FrontuserDao.class);
			IdEntity<String> idDto = new IdEntity<String>();
			idDto.setId(BLContextUtil.getSessionUserId(context));
			FrontUserEntity frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
			this.valiDomainIsNull(frontUserEntity, "前端用户");
			YkdIntegrateManager ykdIntegrateManager = new YkdIntegrateManager();
			ykdIntegrateManager.init(context);
			return ykdIntegrateManager.ykdLogin(frontUserEntity);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public Entity userdetail(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct = null;
		try {
			String id = context.getDomain(String.class);
			if (Str.isNullOrEmpty(id)) {
				throw new BlAppException(-1, "参数错误");
			}
			YkdIntegrateManager ykdIntegrateManager = new YkdIntegrateManager();
			ykdIntegrateManager.init(context);
			String result = ykdIntegrateManager.getUserNameByYkdUser(id);
			if (Str.isNullOrEmpty(result)) {
				throw new BlAppException(-2, "无效用户");
			}
			YkdUserEntity ykdUserEntity = new YkdUserEntity();
			ykdUserEntity.setUserName(result);
			return ykdUserEntity;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
}
