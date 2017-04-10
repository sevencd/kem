package cn.ilanhai.kem.bl.statistic.impl;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.statistic.VIPUserStatistic;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.statistic.VIPUserStatisticDao;
import cn.ilanhai.kem.domain.statistic.dto.StatisticRequestDto;
@Component("vipstatistic")
public class VIPUserStatisticImpl extends BaseBl implements VIPUserStatistic {
	private static String desc = "会员统计";

	@Override
	public Entity vipUserStatisticAmount(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取入参
			StatisticRequestDto request = context.getDomain(StatisticRequestDto.class);
			valiPara(request);
			if (!context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			} 

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,VIPUserStatisticDao.class);
			valiDaoIsNull(dao, desc);
			// 返回结果
			Entity result = dao.query(request,false);
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
