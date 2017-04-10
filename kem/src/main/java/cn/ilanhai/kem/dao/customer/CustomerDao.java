package cn.ilanhai.kem.dao.customer;

import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.customer.CustomerGroupingEnum;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.customer.QueryCustomersByIds;
import cn.ilanhai.kem.domain.customer.QueryGroupCustomersDto;
import cn.ilanhai.kem.domain.customer.dto.DeleteCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.ImplCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerCountDto;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerDto;
import cn.ilanhai.kem.domain.customer.tag.CustomerTagEntity;
import cn.ilanhai.kem.domain.customer.tag.QueryCustomerDto;
import cn.ilanhai.kem.domain.customer.tag.QueryCustomerTagDto;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.statistic.dto.CustomerBoardResponseDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticEntity;
import cn.ilanhai.kem.domain.statistic.dto.CustomerStatisticRequestDto;

/**
 * 客户dao
 * 
 * @author hy
 *
 */
@Component("customerDao")
public class CustomerDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	/**
	 * 从数据库查出的初步客户统计数据
	 */
	// List<CustomerStatisticEntity> list;

	public CustomerDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof CustomerMainEntity) {
			return saveCustomerMainEntity((CustomerMainEntity) entity);
		}
		if (entity instanceof CustomerInfoEntity) {
			return saveCustomerInfoEntity((CustomerInfoEntity) entity);
		} else if (entity instanceof CustomerTagEntity) {
			return saveCustomerTag((CustomerTagEntity) entity);
		} else if (entity instanceof ImplCustomerDto) {
			return saveCustomerList((ImplCustomerDto) entity);
		}
		return super.save(entity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null) {
			return null;
		}
		if (entity instanceof SearchCustomerDto) {
			SearchCustomerDto searchCustomerDto = (SearchCustomerDto) entity;
			searchCustomerDto.setCount(searchCustomerCount(searchCustomerDto));
			return searchCustomer(searchCustomerDto).iterator();
		} else if (entity instanceof IdEntity) {
			return searchOrigination((IdEntity<Integer>) entity).iterator();
		} else if (entity instanceof QueryCustomersByIds) {
			// 通过ids查询多个联系人
			return queryCustomers((QueryCustomersByIds) entity).iterator();
		} else if (entity instanceof QueryGroupCustomersDto) {
			// 通过群组id和userId查询客户信息
			return queryCustomers((QueryGroupCustomersDto) entity).iterator();
		} else if (entity instanceof SearchCustomerCountDto) {
			return queryCustomers((SearchCustomerCountDto) entity).iterator();
		}
		return super.query(entity);
	}

	private List<Entity> searchCustomer(SearchCustomerDto entity) {
		return sqlSession.selectList(baseNamespace + "customer.queryCustomer", entity);
	}

	private Integer searchCustomerCount(SearchCustomerDto entity) {
		return sqlSession.selectOne(baseNamespace + "customer.queryCustomerCount", entity);
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
			if (requestDto.getRelationType() != null
					&& requestDto.getRelationType() == UserRelationType.SUBUSER.getValue()) {
				try {
					return this.getSubAccount(requestDto, list);
				} catch (Exception e) {
					throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
				}
			} else {
				try {
					return this.getCurrentAccount(list, requestDto);
				} catch (Exception e) {
					throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
				}
			}
		} else if (entity instanceof QueryCustomerTagDto) {
			return query((QueryCustomerTagDto) entity);
		} else if (entity instanceof QueryCustomerDto) {
			return loadCustomerInfo((QueryCustomerDto) entity);
		}
		return super.query(entity, flag);
	}

	/**
	 * 获取子账号一天的数据
	 * 
	 * @param requestDto
	 * @return
	 */
	private Entity getSubAccount(CustomerStatisticRequestDto requestDto, List<CustomerStatisticEntity> list) {
		CustomerStatisticDto dto = this.getCustomerStasticByDays(list, requestDto, 1, true);
		dto.setPhoneNo(requestDto.getPhoneNo());
		return dto;
	}

	/**
	 * 获取当前账号的数据
	 * 
	 * @param requestDto
	 * @return
	 */
	private Entity getCurrentAccount(List<CustomerStatisticEntity> list, CustomerStatisticRequestDto requestDto) {
		// 得到当前账号的账号类型（主账号还是子账号)
		CustomerBoardResponseDto currentAccount = sqlSession
				.selectOne(this.baseNamespace + "customer.selectAccountType", requestDto);
		// 查询1天7天30天的数据
		CustomerStatisticDto day1 = this.getCustomerStasticByDays(list, requestDto, 1, true);
		CustomerStatisticDto day7 = this.getCustomerStasticByDays(list, requestDto, 7, true);
		CustomerStatisticDto day30 = this.getCustomerStasticByDays(list, requestDto, 30, true);
		// 设置当前账号1天的数据
		CustomerStatisticDto dto = this.getCustomerStasticByDays(list, requestDto, 1, false);
		currentAccount.setNewComeNum(dto.getNewComeNum());
		currentAccount.setIntentNum(dto.getIntentNum());
		currentAccount.setDealNum(dto.getDealNum());
		// 设置1天，7天，30天的数据（汇率计算用）
		currentAccount.setCustomerForDay1(day1);
		currentAccount.setCustomerForDay7(day7);
		currentAccount.setCustomerForDay30(day30);
		return currentAccount;
	}

	/**
	 * 得到按客户类别的初步统计数据 客户类别有潜在客户，有意向客户，成交客户
	 * 
	 * @param requestDto
	 * @return
	 */
	private List<CustomerStatisticEntity> getCustomerStasticEntity(CustomerStatisticRequestDto requestDto) {
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
	private CustomerStatisticDto getCustomerStasticByDays(List<CustomerStatisticEntity> list,
			CustomerStatisticRequestDto requestDto, int days, boolean flag) {
		CustomerStatisticDto dto = new CustomerStatisticDto();
		/*
		 * threadLocal.set(list); list=threadLocal.get();
		 */
		Long newComeNum = 0L;
		Long intentNum = 0L;
		Long dealNum = 0L;
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
	@Deprecated
	private CustomerStatisticDto getCurrentDayCustomer(CustomerStatisticRequestDto requestDto) {
		CustomerStatisticDto dto = new CustomerStatisticDto();
		// 潜在客户
		requestDto.setType(CustomerGroupingEnum.POTENTIAL.getValue().toString());
		requestDto.setDays(1);
		Long newComeNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		// 有意向客户
		requestDto.setType(CustomerGroupingEnum.INTERESTED.getValue().toString());
		requestDto.setDays(1);
		Long intentNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		// 成交客户
		requestDto.setType(CustomerGroupingEnum.TURNOVER.getValue().toString());
		requestDto.setDays(1);
		Long dealNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		dto.setDealNum(dealNum);
		dto.setIntentNum(intentNum);
		dto.setNewComeNum(newComeNum);
		return dto;
	}

	@Deprecated
	private CustomerStatisticDto getCustomerStasticDtoByDays(CustomerStatisticRequestDto requestDto, int days) {
		CustomerStatisticDto dto = new CustomerStatisticDto();
		// 潜在客户
		requestDto.setType(CustomerGroupingEnum.POTENTIAL.getValue().toString());
		requestDto.setDays(days);
		Long newComeNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		// 有意向客户
		requestDto.setType(CustomerGroupingEnum.INTERESTED.getValue().toString());
		requestDto.setDays(days);
		Long intentNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		// 成交客户
		requestDto.setType(CustomerGroupingEnum.TURNOVER.getValue().toString());
		requestDto.setDays(days);
		Long dealNum = sqlSession.selectOne(baseNamespace + "customer.selectForCount", requestDto);
		dto.setDealNum(dealNum);
		dto.setIntentNum(intentNum);
		dto.setNewComeNum(dealNum + intentNum + newComeNum);

		return dto;
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		if (entity instanceof DeleteCustomerDto) {
			return sqlSession.update(baseNamespace + "customer.deleteCustomer", (DeleteCustomerDto) entity);
		}
		return super.delete(entity);
	}

	private int saveCustomerMainEntity(CustomerMainEntity customerMainEntity) {
		if (customerMainEntity == null) {
			return -1;
		}
		return sqlSession.insert(baseNamespace + "customer.insertMain", customerMainEntity);
	}

	private int saveCustomerInfoEntity(CustomerInfoEntity customerInfoEntity) {
		if (customerInfoEntity == null) {
			return -1;
		}
		CustomerInfoEntity Info = sqlSession.selectOne(baseNamespace + "customer.selectCustomerInfo",
				customerInfoEntity);
		if (Info != null) {
			return sqlSession.update(baseNamespace + "customer.updateInfo", customerInfoEntity);
		}
		return sqlSession.insert(baseNamespace + "customer.insertInfo", customerInfoEntity);
	}

	private int saveCustomerList(ImplCustomerDto implCustomerDto) {
		if (implCustomerDto == null) {
			return -1;
		}
		List<CustomerMainEntity> mains = implCustomerDto.getMains();
		List<CustomerInfoEntity> infos = implCustomerDto.getInfos();
		if (mains == null || infos == null) {
			return -1;
		}
		sqlSession.insert(baseNamespace + "customer.insertMainList", mains);
		return sqlSession.insert(baseNamespace + "customer.insertInfoList", infos);
	}

	/**
	 * 保存客户标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveCustomerTag(CustomerTagEntity entity) throws DaoAppException {
		if (entity == null) {
			return 0;
		}
		if (!isExist(entity)) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}

	// 新增客户标签信息
	private int insert(CustomerTagEntity entity) {
		logger.info("新增客户标签");
		return sqlSession.insert(baseNamespace + "customer.insertcustomertag", entity);
	}

	// 更新客户标签信息
	private int update(CustomerTagEntity entity) {
		logger.info("更新客户标签");
		return sqlSession.update(baseNamespace + "customer.updatecustomertag", entity);
	}

	// 客户标签信息是否存在
	private boolean isExist(CustomerTagEntity entity) {
		QueryCustomerTagDto queryCustomerTagDto = new QueryCustomerTagDto();
		queryCustomerTagDto.setCustomerId(entity.getCustomerId());
		CustomerTagEntity customerTagEntity = (CustomerTagEntity) query(queryCustomerTagDto);
		if (customerTagEntity != null) {
			return true;
		} else {
			return false;
		}

	}

	private Entity query(QueryCustomerTagDto entity) {
		return sqlSession.selectOne(baseNamespace + "customer.searchcustomertag", entity);
	}

	// 加载客户信息
	private Entity loadCustomerInfo(QueryCustomerDto entity) {

		return sqlSession.selectOne(baseNamespace + "customer.loadcustomerinfo", entity);
	}

	// 查询客户来源
	private List<Entity> searchOrigination(IdEntity<Integer> entity) {

		return sqlSession.selectList(baseNamespace + "customer.searchorigination", entity);
	}

	private List<Entity> queryCustomers(QueryCustomersByIds entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList(baseNamespace + "customer.querycustomersbyids", entity);
	}

	private List<Entity> queryCustomers(SearchCustomerCountDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList(baseNamespace + "customer.querycustomersCountbyids", entity);
	}

	private List<Entity> queryCustomers(QueryGroupCustomersDto entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList(baseNamespace + "customer.querycustomersbygroupids", entity);
	}

}
