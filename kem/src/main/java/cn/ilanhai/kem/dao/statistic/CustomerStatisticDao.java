package cn.ilanhai.kem.dao.statistic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.crawler.CrawlerCustomerRequestDto;
import cn.ilanhai.kem.domain.customer.CustomerGroupingEnum;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.statistic.dto.CustomerBoardResponseDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticEntity;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticRequestDto;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;

/**
 * 客户统计dao
 * 
 * @author csz
 * @date 2017-04-06
 */
@Component("CustomerStatisticDao")
public class CustomerStatisticDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();
	/**
	 * 从数据库查出的初步客户统计数据
	 */
	private static ThreadLocal<List<CustomerStatisticEntity>> threadLocal = new ThreadLocal<List<CustomerStatisticEntity>>();

	public CustomerStatisticDao() throws DaoAppException {
		super();
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		if (entity instanceof CustomerStatisticRequestDto) {
			CustomerStatisticRequestDto requestDto = (CustomerStatisticRequestDto) entity;
			/**
			 * 从数据库查出的初步客户统计数据
			 */
			List<CustomerStatisticEntity> list = this.getCustomerStasticEntity(requestDto);
			threadLocal.set(list);
			try {
				return this.getCurrentAccount(requestDto);
			} catch (Exception e) {
				throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
			}
		}
		if (entity instanceof CrawlerCustomerRequestDto) {
			try {
				return this.getAccountInfo(entity);
			} catch (Exception e) {
				throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
			}
		}
		return super.query(entity, flag);
	}

	/**
	 * 得到当前账号的账号类型（主账号还是子账号)
	 * 
	 * @param entity
	 * @return
	 */
	private UserRelationEntity getAccountInfo(Entity entity) {
		return sqlSession.selectOne(this.baseNamespace + "customer.selectAccountType", entity);
	}

	/**
	 * 获取子账号一天的数据
	 * 
	 * @param requestDto
	 * @return
	 */
	/*
	 * private Entity getSubAccount(CustomerStatisticRequestDto requestDto) {
	 * CustomerStatisticDto dto = this.getCustomerStasticByDays(requestDto, 1,
	 * true); dto.setPhoneNo(requestDto.getPhoneNo()); return dto; }
	 */

	/**
	 * 获取当前账号的数据
	 * 
	 * @param requestDto
	 * @return
	 */
	private Entity getCurrentAccount(CustomerStatisticRequestDto requestDto) {
		CustomerBoardResponseDto currentAccountResponseDto = new CustomerBoardResponseDto();
		// 查询1天7天30天的数据
		CustomerStatisticDto day1 = this.getCustomerStasticByDays(requestDto, 1, true);
		CustomerStatisticDto day7 = this.getCustomerStasticByDays(requestDto, 7, true);
		CustomerStatisticDto day30 = this.getCustomerStasticByDays(requestDto, 30, true);
		// 设置当前账号1天的数据
		CustomerStatisticDto dto = this.getCustomerStasticByDays(requestDto, 1, false);
		currentAccountResponseDto.setNewComeNum(dto.getNewComeNum());
		currentAccountResponseDto.setIntentNum(dto.getIntentNum());
		currentAccountResponseDto.setDealNum(dto.getDealNum());
		// 设置1天，7天，30天的数据（汇率计算用）
		currentAccountResponseDto.setCustomerForDay1(day1);
		currentAccountResponseDto.setCustomerForDay7(day7);
		currentAccountResponseDto.setCustomerForDay30(day30);
		return currentAccountResponseDto;
	}

	/**
	 * 得到按客户类别的初步统计数据 客户类别有潜在客户，有意向客户，成交客户
	 * 
	 * @param requestDto
	 * @return
	 */
	private List<CustomerStatisticEntity> getCustomerStasticEntity(CustomerStatisticRequestDto requestDto) {
		// 得到当前账号的账号类型（主账号还是子账号)
		UserRelationEntity accountEntity = this.getAccountInfo(requestDto);
		// 如果是子账号，更改统计为主账号
		if (accountEntity.getUserType() == UserRelationType.SUBUSER.getValue()) {
			requestDto.setUserId(accountEntity.getFatherUserId());
		}
		return sqlSession.selectList(baseNamespace + "customer.selectForCount", requestDto);
	}

	/**
	 * 得到按天的客户统计数据
	 * 
	 * @param requestDto
	 * @param days
	 * @param flag
	 *            档flag为ture时，潜在客户包含成交客户，为false时，不包含
	 * @return
	 */
	private CustomerStatisticDto getCustomerStasticByDays(CustomerStatisticRequestDto requestDto, int days,
			boolean flag) {
		CustomerStatisticDto dto = new CustomerStatisticDto();
		Long newComeNum = 0L;
		Long intentNum = 0L;
		Long dealNum = 0L;
		List<CustomerStatisticEntity> list = threadLocal.get();
		logger.info("thread[" + Thread.currentThread().getName() + "] --> sn[" + list.size() + "]");
		for (CustomerStatisticEntity entity : list) {
			// 潜在客户
			if (CustomerGroupingEnum.POTENTIAL.getValue() == entity.getType() && days >= entity.getDays()) {
				newComeNum += entity.getCount();
			}
			// 有意向客户
			if (CustomerGroupingEnum.INTERESTED.getValue() == entity.getType() && days >= entity.getDays()) {
				intentNum += entity.getCount();
			}
			// 成交客户
			if (CustomerGroupingEnum.TURNOVER.getValue() == entity.getType() && days >= entity.getDays()) {
				dealNum += entity.getCount();
			}
		}
		// flag为ture时，潜在客户包含成交客户，为false时，不包含
		if (flag) {
			dto.setDealNum(dealNum);
			dto.setIntentNum(intentNum);
			dto.setNewComeNum(dealNum + intentNum + newComeNum);
		} else {
			dto.setDealNum(dealNum);
			dto.setIntentNum(intentNum);
			dto.setNewComeNum(newComeNum);
		}
		return dto;
	}

	/**
	 * 获取当天数据
	 * 
	 * @param requestDto
	 * @return
	 */
	/*
	 * @Deprecated private CustomerStatisticDto
	 * getCurrentDayCustomer(CustomerStatisticRequestDto requestDto) {
	 * CustomerStatisticDto dto = new CustomerStatisticDto(); // 潜在客户
	 * requestDto.setType(CustomerGroupingEnum.POTENTIAL.getValue().toString());
	 * requestDto.setDays(1); Long newComeNum =
	 * sqlSession.selectOne(baseNamespace + "customer.selectForCount",
	 * requestDto); // 有意向客户
	 * requestDto.setType(CustomerGroupingEnum.INTERESTED.getValue().toString())
	 * ; requestDto.setDays(1); Long intentNum =
	 * sqlSession.selectOne(baseNamespace + "customer.selectForCount",
	 * requestDto); // 成交客户
	 * requestDto.setType(CustomerGroupingEnum.TURNOVER.getValue().toString());
	 * requestDto.setDays(1); Long dealNum = sqlSession.selectOne(baseNamespace
	 * + "customer.selectForCount", requestDto); dto.setDealNum(dealNum);
	 * dto.setIntentNum(intentNum); dto.setNewComeNum(newComeNum); return dto; }
	 */
	/*
	 * @Deprecated private CustomerStatisticDto
	 * getCustomerStasticDtoByDays(CustomerStatisticRequestDto requestDto, int
	 * days) { CustomerStatisticDto dto = new CustomerStatisticDto(); // 潜在客户
	 * requestDto.setType(CustomerGroupingEnum.POTENTIAL.getValue().toString());
	 * requestDto.setDays(days); Long newComeNum =
	 * sqlSession.selectOne(baseNamespace + "customer.selectForCount",
	 * requestDto); // 有意向客户
	 * requestDto.setType(CustomerGroupingEnum.INTERESTED.getValue().toString())
	 * ; requestDto.setDays(days); Long intentNum =
	 * sqlSession.selectOne(baseNamespace + "customer.selectForCount",
	 * requestDto); // 成交客户
	 * requestDto.setType(CustomerGroupingEnum.TURNOVER.getValue().toString());
	 * requestDto.setDays(days); Long dealNum =
	 * sqlSession.selectOne(baseNamespace + "customer.selectForCount",
	 * requestDto); dto.setDealNum(dealNum); dto.setIntentNum(intentNum);
	 * dto.setNewComeNum(dealNum + intentNum + newComeNum);
	 * 
	 * return dto; }
	 */
}
