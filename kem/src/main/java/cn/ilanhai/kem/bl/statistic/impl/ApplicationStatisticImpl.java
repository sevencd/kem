package cn.ilanhai.kem.bl.statistic.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.statistic.ApplicationStatistic;
import cn.ilanhai.kem.bl.statistic.VIPUserStatistic;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.statistic.ApplicationStatisticDao;
import cn.ilanhai.kem.dao.statistic.VIPUserStatisticDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.statistic.dto.APPUserResponseDto;
import cn.ilanhai.kem.domain.statistic.dto.EmailUserRequestDto;
import cn.ilanhai.kem.domain.statistic.dto.SMSUserRequestDto;
import cn.ilanhai.kem.domain.statistic.dto.StatisticRequestDto;

@Component("applicationstatistic")
public class ApplicationStatisticImpl extends BaseBl implements ApplicationStatistic {
	private static String desc = "应用统计";

	@Override
	public List<Entity> applicationStatisticAmount(RequestContext context) throws BlAppException, DaoAppException {

		CodeTable ct;
		try {
			// 获取入参
			if (!context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, ApplicationStatisticDao.class);
			valiDaoIsNull(dao, desc);
			// 返回结果
			List<Entity> result = dao.queryList(null);
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	@Override
	public Entity emailApplicationStatisticDetail(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取入参
			StatisticRequestDto request = context.getDomain(EmailUserRequestDto.class);
			valiPara(request);
			if (!context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, ApplicationStatisticDao.class);
			valiDaoIsNull(dao, desc);
			//查询结果
			List<Entity> list = dao.queryList(request);
			CountDto count = (CountDto) dao.query(request, false);
			// 返回结果
			APPUserResponseDto result = new APPUserResponseDto();
			result.setDetail(list);
			result.setTotalCount(count.getCount());
			result.setStartCount(request.getStartCount());
			result.setPageSize(request.getPageSize());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	public Entity smsApplicationStatisticDetail(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取入参
			StatisticRequestDto request = context.getDomain(SMSUserRequestDto.class);
			valiPara(request);
			if (!context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, ApplicationStatisticDao.class);
			valiDaoIsNull(dao, desc);
			//查询结果
			List<Entity> list = dao.queryList(request);
			CountDto count = (CountDto) dao.query(request, false);
			// 返回结果
			APPUserResponseDto result = new APPUserResponseDto();
			result.setDetail(list);
			result.setTotalCount(count.getCount());
			result.setStartCount(request.getStartCount());
			result.setPageSize(request.getPageSize());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
