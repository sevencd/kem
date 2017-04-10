package cn.ilanhai.kem.bl.crawler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.cache.CacheFactory;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.crawler.CrawlerSearch;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.crawler.CrawlerSearchDao;
import cn.ilanhai.kem.dao.statistic.CustomerStatisticDao;
import cn.ilanhai.kem.domain.crawler.CrawlerCustomerDto;
import cn.ilanhai.kem.domain.crawler.CrawlerCustomerInfoEntity;
import cn.ilanhai.kem.domain.crawler.CrawlerCustomerRequestDto;
import cn.ilanhai.kem.domain.crawler.CrawlerSearchDto;
import cn.ilanhai.kem.domain.crawler.CrawlerSearchRequestDto;
import cn.ilanhai.kem.domain.crawler.CrawlerSearchResponseDto;
import cn.ilanhai.kem.domain.crawler.CrawlerSolrRequestDto;
import cn.ilanhai.kem.domain.customer.CustomerGroupingEnum;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.enums.OriginationType;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.CollectionTypeUtil;
import cn.ilanhai.kem.util.HttpHelper;

/**
 * 
 * 爬虫搜索请求业务逻辑
 * 
 * @TypeName CrawlerSearchImpl
 * @time 2017-03-06 10:00
 * @author csz
 */
@Component("CrawlerSearch")
public class CrawlerSearchImpl extends BaseBl implements CrawlerSearch {
	private Logger logger = Logger.getLogger(CrawlerSearchImpl.class);
	/**
	 * 八爪鱼采集数据使用的redis db库序号
	 */
	private final static int CRAWLER_INDEX = 3;
	/**
	 * solr接口url
	 */
	final String solrApiUrl = "solrApiUrl";
	/**
	 * Implementation of CrawlerSearch interface
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public CrawlerSearchResponseDto listCustomer(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		// 验证前台用户是否已登录
		this.checkFrontUserLogined(context);
		// 得到请求dto
		CrawlerSearchRequestDto request = context.getDomain(CrawlerSearchRequestDto.class);
		// 校验开始页面和页面大小
		this.checkLimit(request.getStartCount(), request.getPageSize());
		String userId = this.getSessionUserId(context);
		String url = BLContextUtil.getValue(context, solrApiUrl) + "acquisition" + "?exceptConsumer=" + userId
				+ "&startCount=" + request.getStartCount() + "&pageSize=" + request.getPageSize() + "&dataFrom="
				+ request.getDataFrom();
		logger.info("solr获取数据url是                " + url);
		try {
			// 请求solr的url
			String encodeUrl = urlEncode(url, request);
			logger.info("solr获取数据编码后的url是                " + encodeUrl);
			// 发送solr请求，返回json格式数据
			String responseJson = HttpHelper.sendGet(encodeUrl, "utf-8");
			if (Str.isNullOrEmpty(responseJson)) {
				throw new BlAppException(CodeTable.BL_SOLRAPI_REQUEST_ERROR.getValue(),
						CodeTable.BL_SOLRAPI_REQUEST_ERROR.getDesc());
			}
			logger.info("从solr接口返回的数据是                " + responseJson);

			// json格式数据反序列化为java对象
			CrawlerSearchResponseDto responseDto = FastJson.json2Bean(responseJson, CrawlerSearchResponseDto.class);
			responseDto.setDataFrom(request.getDataFrom());
			responseDto.setStartCount(request.getStartCount());
			responseDto.setPageSize(request.getPageSize());
			responseDto.setTotalCount(responseDto.getTotalCount());
			try {
				//获取redis cache对象
				Cache c=CacheUtil.getInstance().getCache(CRAWLER_INDEX);
				// 将数据存储在redis,key是userId
				c.set(userId, responseDto, -1);
			} catch (CacheContainerException e) {
				throw new BlAppException(-1, "缓存操作异常");
			}
			
			// 返回前端显示的数据，处理手机号，QQ号，email，行业num
			List<CrawlerSearchDto> crawlerSearchDtoList = responseDto.getDetail();
			for (CrawlerSearchDto dto : crawlerSearchDtoList) {
				if (!Str.isNullOrEmpty(dto.getPhoneNo())) {
					dto.setPhoneNo(dto.getPhoneNo().substring(0, dto.getPhoneNo().length() - 4) + "xxxx");
				}
				if (!Str.isNullOrEmpty(dto.getQq())) {
					dto.setQq(dto.getQq().substring(0, dto.getQq().length() - 4) + "xxxx");
				}
				if (!Str.isNullOrEmpty(dto.getEmail())) {
					String subStr = dto.getEmail().substring(dto.getEmail().indexOf("@") - 4,
							dto.getEmail().indexOf("@"));
					dto.setEmail(dto.getEmail().replace(subStr, "xxxx"));
				}
				if (!Str.isNullOrEmpty(dto.getIndustryTwo())) {
					dto.setIndustry(CollectionTypeUtil.translateTypeNum(dto.getIndustryTwo()));
				}
			}
			return responseDto;
		} catch (UnsupportedEncodingException e) {
			throw new BlAppException(-1, "汉字转码异常");
		} 
	}

	/*
	 * url编码
	 */
	private String urlEncode(String url, CrawlerSearchRequestDto request) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(url);
		if (!Str.isNullOrEmpty(request.getCity())) {
			sb.append("&city=" + URLEncoder.encode(request.getCity(), "utf-8"));
		}
		if (!Str.isNullOrEmpty(request.getProvince())) {
			sb.append("&province=" + URLEncoder.encode(request.getProvince(), "utf-8"));
		}
		if (!Str.isNullOrEmpty(request.getTypeNumOne())) {
			sb.append("&typeIdOne=" + request.getTypeNumOne());
		}
		if (!Str.isNullOrEmpty(request.getTypeNumTwo())) {
			sb.append("&typeIdTwo=" + request.getTypeNumTwo());
		}
		if (!Str.isNullOrEmpty(request.getUrl())) {
			sb.append("&url=" + request.getUrl());
		}
		if (!Str.isNullOrEmpty(request.getHometownProvince())) {
			sb.append("&hometownProvince=" + URLEncoder.encode(request.getHometownProvince(), "utf-8"));
		}
		if (!Str.isNullOrEmpty(request.getHometownCity())) {
			sb.append("&hometownCity=" + URLEncoder.encode(request.getHometownCity(), "utf-8"));
		}
		if (!Str.isNullOrEmpty(request.getKeyWord())) {
			sb.append("&keyWord=" + URLEncoder.encode(request.getKeyWord(), "utf-8"));
		}
		return sb.toString();
	}

	/*
	 * 从list包含的对象里找出等于ids的所有对象
	 * 
	 * @param list 原来的list
	 * 
	 * @param ids 需匹配的所有id
	 * 
	 * @return List<CrawlerSearchDto>
	 */
	private List<CrawlerSearchDto> getRecordById(List<CrawlerSearchDto> list, List<String> ids) {
		List<CrawlerSearchDto> customerList = new ArrayList<CrawlerSearchDto>();
		for (CrawlerSearchDto dto : list) {
			for (String id : ids) {
				if (dto.getId().equals(id)) {
					customerList.add(dto);
				}
			}
		}
		return customerList;
	}

	/*
	 * @see
	 * cn.ilanhai.kem.bl.crawler.CrawlerSearch#saveCustomer(cn.ilanhai.framework
	 * .app.RequestContext)
	 */
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public void saveCustomer(RequestContext context)
			throws BlAppException, SessionContainerException, CacheContainerException {
		// 得到请求dto
		CrawlerCustomerRequestDto request = context.getDomain(CrawlerCustomerRequestDto.class);
		// 验证前台用户是否已登录
		this.checkFrontUserLogined(context);
		// 当前userId
		String currentUserId = this.getSessionUserId(context);
		// 保存到数据库的Id
		String toDbUserId = null;
		// 验证请求ids是否为空
		valiParaItemListNull(request.getIds(), "客户");
		logger.info("记录id" + request.getIds());
		// 获取数据库操作dao
		Dao customerStatisticdao = null;
		try {
			customerStatisticdao = this.daoProxyFactory.getDao(context, CustomerStatisticDao.class);
		} catch (DaoAppException e1) {
			throw new BlAppException(CodeTable.BL_COMMON_GET_DAO.getValue(),
					String.format(CodeTable.BL_COMMON_GET_DAO.getDesc(), "添加到客户管理"));
		}
		request.setUserId(currentUserId);
		try {
			UserRelationEntity accountEntity = (UserRelationEntity) customerStatisticdao.query(request, false);
			// 如果是子账号，更改统计为主账号
			if (accountEntity.getUserType() == UserRelationType.SUBUSER.getValue()) {
				toDbUserId = accountEntity.getFatherUserId();
			}
		} catch (DaoAppException e) {
			throw new BlAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
		// 新建添加到客户管理请求入参
		CrawlerCustomerDto crawlerCustomerDto = new CrawlerCustomerDto();
		// 客户基本信息list
		List<CustomerMainEntity> mainList = new ArrayList<CustomerMainEntity>();
		// 客户详细信息list
		List<CrawlerCustomerInfoEntity> infoList = new ArrayList<CrawlerCustomerInfoEntity>();
		//声明变量
		CrawlerSearchResponseDto crawlerSearchResponseDto;
		List<CrawlerSearchDto> customerList;
		try {
			//获取redis cache对象
			Cache c=CacheUtil.getInstance().getCache(CRAWLER_INDEX);
			// 从redis获取保存的数据
			crawlerSearchResponseDto = c.get(currentUserId, CrawlerSearchResponseDto.class);
			// redis里面保存的客户详细信息
			List<CrawlerSearchDto> crawlerSearchDtoList = crawlerSearchResponseDto.getDetail();
			// 从redis里面选取用户指定的客户
			customerList = getRecordById(crawlerSearchDtoList, request.getIds());
			
		} catch (CacheContainerException e) {
			throw new BlAppException(-1, "缓存操作异常");
		}
		// 客户基本信息实体
		CustomerMainEntity main = null;
		// 客户详细信息实体
		CrawlerCustomerInfoEntity info = null;
		// 数据来源
		String dataFrom = crawlerSearchResponseDto.getDataFrom();
		// 组装dao的请求入参CrawlerCustomerDto
		for (CrawlerSearchDto dto : customerList) {
			main = new CustomerMainEntity();
			String customerId = KeyFactory.newKey(KeyFactory.KEY_CUSTOMER);
			main.setUserId(toDbUserId);
			main.setCustomerId(customerId);
			main.setCreatetime(new Date());
			mainList.add(main);
			info = new CrawlerCustomerInfoEntity();
			info.setCustomerId(customerId);
			info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_ORIGINATE);
			info.setCustomerValue(dataFrom);
			infoList.add(info);
			info = new CrawlerCustomerInfoEntity();
			info.setCustomerId(customerId);
			info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_TYPE);
			info.setCustomerValue(CustomerGroupingEnum.POTENTIAL.getValue().toString());
			infoList.add(info);
			// 根据数据来源，将采集到的数据放入客户管理
			transferObjectToKey(customerId, dto, infoList, dataFrom);
		}
		crawlerCustomerDto.setMainList(mainList);
		crawlerCustomerDto.setInfoList(infoList);

		try {
			// 获取数据库操作dao
			Dao dao = null;
			try {
				dao = this.daoProxyFactory.getDao(context, CrawlerSearchDao.class);
			} catch (DaoAppException e1) {
				e1.printStackTrace();
				throw new BlAppException(CodeTable.BL_COMMON_GET_DAO.getValue(),
						String.format(CodeTable.BL_COMMON_GET_DAO.getDesc(), "添加到客户管理"));
			}
			// 调用dao的保存方法
			int i = dao.save(crawlerCustomerDto);
			// 校验
			valiSaveDomain(i, "添加到客户管理");
			// 消耗当前账号的客户线索资源
			MemberManager.useUserServiceResources(context, currentUserId,
					PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.CUSTOMERCLUE), i);

			CrawlerSolrRequestDto solrDto = new CrawlerSolrRequestDto();
			solrDto.setConsumerId(currentUserId);
			solrDto.setDaqIdList(request.getIds());
			Map<String, String> map = new HashMap<String, String>();
			map.put("consumerId", currentUserId);
			map.put("daqIdList", FastJson.bean2Json(request.getIds()));

			String postStr = FastJson.bean2Json(solrDto);
			logger.info("请求solr" + postStr);
			String responsePostStr;
			try {
				responsePostStr = HttpHelper.sendPut(BLContextUtil.getValue(context, solrApiUrl) + "daq/consumer",
						postStr, "utf-8");
				logger.info("请求solr返回数据" + responsePostStr);
			} catch (Exception e) {
				throw new BlAppException(CodeTable.BL_SOLRAPI_REQUEST_ERROR.getValue(),
						CodeTable.BL_SOLRAPI_REQUEST_ERROR.getDesc(), e);
			}
		} catch (DaoAppException e1) {
			try {
				KeyFactory.inspects();
			} catch (DaoAppException e) {
				throw new BlAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
			}
		}
	}

	/*
	 * 把CrawlerSearchDto转换为表需要的数据结构
	 * 
	 * @param infoList customer_info需要的数据结构
	 * 
	 * @param dataFrom 根据数据来源的不同将用户在前端看到的数据保存到infoList
	 */
	private void transferObjectToKey(String customerId, CrawlerSearchDto dto, List<CrawlerCustomerInfoEntity> infoList,
			String dataFrom) {
		CrawlerCustomerInfoEntity info;
		if (!Str.isNullOrEmpty(dto.getUserName())) {
			info = new CrawlerCustomerInfoEntity();
			info.setCustomerId(customerId);
			info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_NAME);
			info.setCustomerValue(dto.getUserName());
			infoList.add(info);
		}
		if (!Str.isNullOrEmpty(dto.getIndustryTwo())) {
			info = new CrawlerCustomerInfoEntity();
			info.setCustomerId(customerId);
			info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_INDUSTRY);
			info.setCustomerValue(dto.getIndustryTwo());
			infoList.add(info);
		}
		if (!Str.isNullOrEmpty(dto.getCompany())) {
			info = new CrawlerCustomerInfoEntity();
			info.setCustomerId(customerId);
			info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_COMPANY);
			info.setCustomerValue(dto.getCompany());
			infoList.add(info);
		}
		if (OriginationType.PHONENO.getValue().equals(dataFrom)) {
			if (!Str.isNullOrEmpty(dto.getPhoneNo())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_PHONE);
				info.setCustomerValue(dto.getPhoneNo());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getJob())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_JOB);
				info.setCustomerValue(dto.getJob());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getAddress())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_ADDRESS);
				info.setCustomerValue(dto.getAddress());
				infoList.add(info);
			}
		} else if (OriginationType.QQNUMBER.getValue().equals(dataFrom)) {
			if (!Str.isNullOrEmpty(dto.getQq())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_QQ);
				info.setCustomerValue(dto.getQq());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getSex())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_SEX);
				info.setCustomerValue(dto.getSex());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getAge())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_AGE);
				info.setCustomerValue(dto.getAge());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getSignature())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_QQAUTOGRAPH);
				info.setCustomerValue(dto.getSignature());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getProvince())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_PROVINCE);
				info.setCustomerValue(dto.getProvince());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getHometownProvince())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_HOWNTOWN_PROVINCE);
				info.setCustomerValue(dto.getHometownProvince());
				infoList.add(info);
			}
		} else if (OriginationType.EMAIL.getValue().equals(dataFrom)) {
			if (!Str.isNullOrEmpty(dto.getEmail())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_EMAIL);
				info.setCustomerValue(dto.getEmail());
				infoList.add(info);
			}
			if (!Str.isNullOrEmpty(dto.getAddress())) {
				info = new CrawlerCustomerInfoEntity();
				info.setCustomerId(customerId);
				info.setCustomerKey(CrawlerCustomerInfoEntity.KEY_ADDRESS);
				info.setCustomerValue(dto.getAddress());
				infoList.add(info);
			}
		}

	}
}
