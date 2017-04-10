package cn.ilanhai.kem.bl.statistic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.args.JSONArgs;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.statistic.CustomerStatistic;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.customer.CustomerDao;
import cn.ilanhai.kem.dao.statistic.CustomerStatisticDao;
import cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerCountDto;
import cn.ilanhai.kem.domain.enums.RelationType;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.dto.CustomerBoardResponseDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticRequestDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryTotalManuscriptStatisticDto;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;

@Component("CustomerStatistic")
public class CustomerStatisticImpl extends BaseBl implements CustomerStatistic {

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Entity getCustomerStatisticByDays(RequestContext context) throws BlAppException, SessionContainerException {
		// 验证前台用户是否已登录
		this.checkFrontUserLogined(context);
		/*
		 * //限制参数个数为1个 JSONArgs json=(JSONArgs)context.getArgs();
		 * if(json.getJsonString().split(",").length>1){ throw new
		 * BlAppException(CodeTable.BL_COMMON_PARAMETER_ONLY_ONE.getValue(),
		 * CodeTable.BL_COMMON_PARAMETER_ONLY_ONE.getDesc()); } // 获取天数 Integer
		 * days = context.getDomain(Integer.class);
		 */
		CustomerStatisticRequestDto requestDto = new CustomerStatisticRequestDto();
		this.valiPara(requestDto);
		// this.valiParaItemIntegerNull(days, "天数");
		// 获取useId
		String userId = this.getSessionUserId(context);
		requestDto.setUserId(userId);// 当前账号查询
		// requestDto.setDays(days);
		// 返回数据
		try {
			Dao dao = this.daoProxyFactory.getDao(context, CustomerStatisticDao.class);
			// 获取当前账号的客户统计数据
			Entity entity = dao.query(requestDto, false);
			//CustomerBoardResponseDto currentAccountData = (CustomerBoardResponseDto) entity;
			// 如果是主账号，获取子账号的客户统计数据
			/*if (currentAccountData.getRelationType() == UserRelationType.MAINUSER.getValue()) {
				List<Entity> sonResources = new ArrayList<Entity>(16);
				List<UserRelationEntity> sonAccounts = UserRelationManger.getSubAccountUser(context,UserStatus.ENABLE.getValue());
				for (UserRelationEntity son : sonAccounts) {
					requestDto.setUserId(son.getUserId());
					requestDto.setRelationType(UserRelationType.SUBUSER.getValue());// 子账号查询
					requestDto.setPhoneNo(son.getUserPhone());
					Entity sonStastic = dao.query(requestDto, false);
					sonResources.add(sonStastic);
				}
				currentAccountData.setList(sonResources);
			}*/
			return entity;
		} catch (DaoAppException e) {
			throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Iterator<Entity> extensionlistinfo(RequestContext context) throws BlAppException, SessionContainerException {
		try {
			checkFrontUserLogined(context);
			return CustomerManager.countCustomerByManuscript(context, getSessionUserId(context));
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			CodeTable ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	/**
	 * @param visitUrl
	 *            推广id
	 * @param statisticsType
	 * @return
	 * @throws DaoAppException
	 */
	private long getTotal(RequestContext context, String visitUrl,
			ManuscriptDataStatisticsEntity.StatisticsType statisticsType) throws DaoAppException {
		if (Str.isNullOrEmpty(visitUrl))
			return 0;
		if (statisticsType == null)
			return 0;
		Dao dao = this.daoProxyFactory.getDao(context, ManuscriptDataStatisticsDao.class);
		if (dao == null)
			return 0;
		QueryTotalManuscriptStatisticDto totalManuscriptStatisticDto = null;
		totalManuscriptStatisticDto = new QueryTotalManuscriptStatisticDto();
		totalManuscriptStatisticDto.setStatisticsType(statisticsType);
		totalManuscriptStatisticDto.setVisitUrl(visitUrl);
		dao.query((Entity) totalManuscriptStatisticDto);
		return totalManuscriptStatisticDto.getTotalQuantity();
	}
}
