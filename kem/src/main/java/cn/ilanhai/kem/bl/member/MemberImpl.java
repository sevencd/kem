package cn.ilanhai.kem.bl.member;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.manuscript.ManuscriptDao;
import cn.ilanhai.kem.dao.member.MemberDao;
import cn.ilanhai.kem.domain.member.QueryCondition.MemberStatusQCondition;
import cn.ilanhai.kem.domain.member.dto.MemberStatusDto;

@Component("member")
public class MemberImpl extends BaseBl implements Member {

	private Logger logger = Logger.getLogger(MemberImpl.class);

	/**
	 * 获取会员状态
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public MemberStatusDto status(RequestContext context) throws BlAppException, DaoAppException {
		// 获取用户ID
		String userId = context.getDomain(String.class);
		logger.info("获得入参userId：" + userId);
		// 用户ID不可为空
		BLContextUtil.valiParaNotNull(userId, "用户ID");
		// 获得dao
		Dao dao = this.daoProxyFactory.getDao(context, MemberDao.class);
		BLContextUtil.valiDaoIsNull(dao, "会员");
		// 获取用户状态并返回
		MemberStatusQCondition condition = new MemberStatusQCondition();
		condition.setUserId(userId);
		MemberStatusDto response = (MemberStatusDto) dao.query(condition, false);

		return response;
	}

	/**
	 * 升级服务价格
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public double upgradepackageservice(RequestContext context) throws BlAppException, DaoAppException {
		try {
			Integer packageId = context.getDomain(Integer.class);
			checkFrontUserLogined(context);
			MemberManager m = new MemberManager(context);
			return m.upgradePackageAmount(packageId, getSessionUserId(context));
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			CodeTable ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}
}
