package cn.ilanhai.kem.bl.rights.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.rights.Rights;
import cn.ilanhai.kem.bl.rights.UnRightsManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.domain.rights.UnRightsTimesEntity;
import cn.ilanhai.kem.domain.rights.dto.UnRightsTimesDto;

@Component("rights")
public class RightsImpl extends BaseBl implements Rights {

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchrightstimes(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		String userId = null;
		try {
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				userId = (String) context.getSession().getParameter(Constant.KEY_SESSION_USERID);
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				userId = context.getDomain(String.class);
			}
			this.valiParaItemStrNullOrEmpty(userId, "用户编号错误");
			UnRightsTimesDto unRightsTimesDto = new UnRightsTimesDto();
			UnRightsTimesEntity unRightsTimesEntity = UnRightsManger.searchTimes(context, userId);
			if (unRightsTimesEntity == null) {
				return unRightsTimesDto;
			}
			unRightsTimesDto.setTotailTimes(unRightsTimesEntity.getTotailTimes());
			unRightsTimesDto.setUnrightsTimes(unRightsTimesEntity.getUnrightsTimes());
			return unRightsTimesDto;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
