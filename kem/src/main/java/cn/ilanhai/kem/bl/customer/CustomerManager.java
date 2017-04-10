package cn.ilanhai.kem.bl.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.emailright.EmailRightManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.customer.CustomerDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.MapDto;
import cn.ilanhai.kem.domain.contacts.dto.ImportContactDto;
import cn.ilanhai.kem.domain.contacts.group.CreateGroupRequest;
import cn.ilanhai.kem.domain.customer.CustomerEntity;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.customer.QueryCustomersByIds;
import cn.ilanhai.kem.domain.customer.QueryGroupCustomersDto;
import cn.ilanhai.kem.domain.customer.dto.DeleteCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.ImplCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerCountDto;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.SearchResultCustomerDto;
import cn.ilanhai.kem.domain.customer.tag.CustomerTagEntity;
import cn.ilanhai.kem.domain.customer.tag.QueryCustomerDto;
import cn.ilanhai.kem.domain.customer.tag.QueryCustomerTagDto;
import cn.ilanhai.kem.domain.email.EmailContractEntity;
import cn.ilanhai.kem.domain.email.SaveAllCustomerRequest;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.DrawPrizeRequestDto;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.keyfac.Key;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.util.CollectionTypeUtil;
import cn.ilanhai.kem.util.StringVerifyUtil;
import cn.ilanhai.kem.util.ThreadPoolUtil;

public class CustomerManager {
	private static Logger logger = Logger.getLogger(CustomerManager.class);
	private static Class<?> currentClass = CustomerDao.class;

	public static void saveCustomerMainEntity(RequestContext context, CustomerMainEntity mainEntity)
			throws DaoAppException, BlAppException {
		if (mainEntity == null || Str.isNullOrEmpty(mainEntity.getCustomerId())) {
			return;
		}
		Dao dao = BLContextUtil.getDao(context, currentClass);
		BLContextUtil.valiSaveDomain(dao.save(mainEntity), "新增客户[" + mainEntity.getCustomerId() + "]");
	}

	public static void saveCustomerInfoEntity(RequestContext context, CustomerInfoEntity infoEntity)
			throws DaoAppException, BlAppException {
		if (infoEntity == null || Str.isNullOrEmpty(infoEntity.getCustomerKey())) {
			return;
		}
		Dao dao = BLContextUtil.getDao(context, currentClass);
		BLContextUtil.valiSaveDomain(dao.save(infoEntity), "新增客户信息[" + infoEntity.getCustomerKey() + "]");
	}

	public static void deleteCustomer(RequestContext context, DeleteCustomerDto delete)
			throws DaoAppException, BlAppException {
		if (delete == null || delete.getCustomerIds() == null || delete.getCustomerIds().size() <= 0) {
			return;
		}
		logger.info("deletecustomers:" + delete);
		Dao dao = BLContextUtil.getDao(context, currentClass);
		dao.delete(delete);
	}

	public static Iterator<Entity> countCustomerByManuscript(RequestContext context, String userId)
			throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(userId))
			return null;
		SearchCustomerCountDto searchCustomerCountDto = new SearchCustomerCountDto();
		searchCustomerCountDto.setUserId(userId);
		Dao dao = BLContextUtil.getDao(context, currentClass);
		return dao.query(searchCustomerCountDto);
	}

	public static Entity search(RequestContext context, SearchCustomerDto search)
			throws DaoAppException, BlAppException {
		if (search == null || Str.isNullOrEmpty(search.getUserId())) {
			return null;
		}
		logger.info("searchcustomers:" + search);
		Dao dao = BLContextUtil.getDao(context, currentClass);
		Iterator<Entity> info = dao.query(search);
		SearchResultCustomerDto searchResultCustomerDto = new SearchResultCustomerDto();
		searchResultCustomerDto.setPageSize(search.getPageSize());
		searchResultCustomerDto.setStartCount(search.getStartCount());
		searchResultCustomerDto.setTotalCount(search.getCount());
		List<Entity> list = new ArrayList<Entity>();
		logger.info("查询客户的标签");
		while (info.hasNext()) {
			CustomerTagEntity customerTagEntity = null;
			CustomerEntity customerEntity = (CustomerEntity) info.next();
			customerTagEntity = queryTag(context, customerEntity.getCustomerId());
			if (customerTagEntity != null) {
				List<String> specilTag = BLContextUtil
						.transformationStringCustomerTag(customerTagEntity.getSpecialTag(), false);
				List<String> customerTag = BLContextUtil
						.transformationStringCustomerTag(customerTagEntity.getCustomerTag(), false);
				customerEntity.setSpecialTag(specilTag);
				customerEntity.setCustomerTag(customerTag);
			}
			// 通过行业编码查询行业
			String industryNum = customerEntity.getIndustry();
			if (!Str.isNullOrEmpty(industryNum)) {
				customerEntity.setIndustry(CollectionTypeUtil.translateTypeNum(industryNum));
			}
			list.add(customerEntity);

		}

		searchResultCustomerDto.setList(list.iterator());
		return searchResultCustomerDto;
	}

	public static void importCustomer(final RequestContext context, final List<MapDto> customer, final String userId,
			final String customerType) throws SessionContainerException, BlAppException, DaoAppException {
		if (customer == null) {
			return;
		}
		if (customer.size() == 0) {
			return;
		}
		ThreadPoolUtil.cachedThreadPool.execute(new Runnable() {
			public void run() {
				ImplCustomerDto implCustomerDto = new ImplCustomerDto();
				List<CustomerMainEntity> mains = new ArrayList<CustomerMainEntity>();
				List<CustomerInfoEntity> infos = new ArrayList<CustomerInfoEntity>();
				implCustomerDto.setMains(mains);
				implCustomerDto.setInfos(infos);
				for (MapDto mapDto : customer) {
					CustomerInfoEntity info = null;
					String key, value;
					String customerId = KeyFactory.newKey(KeyFactory.KEY_CUSTOMER);
					boolean flag = true;
					for (Object obj : mapDto.entrySet()) {
						info = new CustomerInfoEntity();
						Entry<String, String> entryObjct = (Entry<String, String>) obj;
						key = entryObjct.getKey();
						value = entryObjct.getValue();
						info.setCustomerId(customerId);
						info.setCustomerKey(key);
						info.setCustomerValue(value);

						if (!info.checkInfo()) {
							continue;
						}
						infos.add(info);
						flag = false;
					}
					// 如果有信息存则生成一条记录
					if (!flag) {
						CustomerMainEntity main = new CustomerMainEntity();
						main.setCustomerId(customerId);
						main.setUserId(userId);
						mains.add(main);
						CustomerInfoEntity manual = new CustomerInfoEntity();
						manual.setCustomerId(customerId);
						manual.setCustomerKey(CustomerInfoEntity.KEY_ORIGINATE);
						manual.setCustomerValue("manual");
						CustomerInfoEntity customerTypeInfo = new CustomerInfoEntity();
						customerTypeInfo.setCustomerId(customerId);
						customerTypeInfo.setCustomerKey(CustomerInfoEntity.KEY_TYPE);
						customerTypeInfo.setCustomerValue(customerType);
						infos.add(customerTypeInfo);
						infos.add(manual);
					}
				}
				try {
					BLContextUtil.getDao(context, CustomerDao.class).save(implCustomerDto);
				} catch (DaoAppException | BlAppException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static MapDto importCustomerLittle(RequestContext context, ImportContactDto request)
			throws SessionContainerException, BlAppException, DaoAppException {
		List<MapDto> customer = request.getCustomerDatas();
		if (customer == null) {
			return null;
		}
		int total = customer.size();
		if (total == 0) {
			return null;
		}
		int timeint = 6000;
		String userId = BLContextUtil.getSessionUserId(context);
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
		if (userRelationEntity != null) {
			userId = userRelationEntity.getFatherUserId();
		}
		MemberEntity memberEntity = MemberManager.getMemberInfo(context, userId);
		if (memberEntity == null || memberEntity.getPackageServiceId() == null
				|| memberEntity.getPackageServiceId().equals(0)) {
			throw new BlAppException(CodeTable.BL_CUSTOMER_IMPL_ERROR.getValue(),
					CodeTable.BL_CUSTOMER_IMPL_ERROR.getDesc());
		} else {
			String time = BLContextUtil.getValue(context, memberEntity.getPackageServiceId() + "package");
			if (!Str.isNullOrEmpty(time)) {
				timeint = Integer.parseInt(time);
			}
		}
		if (total > timeint) {
			throw new BlAppException(CodeTable.BL_CUSTOMER_TIMES_ERROR.getValue(),
					String.format(CodeTable.BL_CUSTOMER_TIMES_ERROR.getDesc(), timeint));
		}
		MapDto result = new MapDto();
		result.put("total", total);
		ImplCustomerDto implCustomerDto = new ImplCustomerDto();
		List<CustomerMainEntity> mains = new ArrayList<CustomerMainEntity>();
		List<CustomerInfoEntity> infos = new ArrayList<CustomerInfoEntity>();
		implCustomerDto.setMains(mains);
		implCustomerDto.setInfos(infos);
		String userid = null;
		String type = request.getCustomerType();
		userid = BLContextUtil.getSessionUserId(context);
		List<String> customerIds = KeyFactory.newKey(Key.KEY_CUSTOMER, total);
		for (int i = 0; i < total; i++) {
			MapDto mapDto = customer.get(i);
			CustomerInfoEntity info = new CustomerInfoEntity();
			String key, value;
			String customerId = customerIds.get(i);
			boolean flag = true;
			for (Object obj : mapDto.entrySet()) {
				info = new CustomerInfoEntity();
				Entry<String, String> entryObjct = (Entry<String, String>) obj;
				key = entryObjct.getKey();
				value = entryObjct.getValue();
				info.setCustomerId(customerId);
				info.setCustomerKey(key);
				info.setCustomerValue(value);
				if (!info.checkInfo()) {
					continue;
				}
				infos.add(info);
				flag = false;
			}
			// 如果有信息存则生成一条记录
			if (!flag) {
				CustomerMainEntity main = new CustomerMainEntity();
				main.setCustomerId(customerId);
				main.setUserId(userid);
				mains.add(main);
				CustomerInfoEntity manual = new CustomerInfoEntity();
				manual.setCustomerId(customerId);
				manual.setCustomerKey(CustomerInfoEntity.KEY_ORIGINATE);
				manual.setCustomerValue("manual");
				CustomerInfoEntity customerTypeInfo = new CustomerInfoEntity();
				customerTypeInfo.setCustomerId(customerId);
				customerTypeInfo.setCustomerKey(CustomerInfoEntity.KEY_TYPE);
				customerTypeInfo.setCustomerValue(type);
				infos.add(customerTypeInfo);
				infos.add(manual);
			}
		}
		BLContextUtil.getDao(context, CustomerDao.class).save(implCustomerDto);
		return result;
	}

	/**
	 * 
	 * @param context
	 * @param customerId
	 *            客户id
	 * @param specailTag
	 *            客户从专题带过来的标签
	 * @param customerTag
	 *            客户自己定义标签
	 * @return
	 * @throws BlAppException
	 */
	public static boolean saveTag(RequestContext context, String customerId, List<String> specialTag,
			List<String> customerTag) throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		String specialTagStr = null;
		String customerTagStr = null;
		logger.info("保存或更新客户：" + customerId + "的标签");
		try {

			specialTagStr = StringVerifyUtil.arrayToString(specialTag).replaceAll(" ", "");
			customerTagStr = StringVerifyUtil.arrayToString(customerTag).replaceAll(" ", "");
			logger.info("验证标签的合法性");
			BLContextUtil.transformationStringCustomerTag(specialTagStr, true);
			BLContextUtil.transformationStringCustomerTag(customerTagStr, true);

			CustomerTagEntity CustomerTagEntity = new CustomerTagEntity();
			Date date = new Date();
			CustomerTagEntity.setCustomerId(customerId);
			CustomerTagEntity.setSpecialTag(specialTagStr);
			CustomerTagEntity.setCustomerTag(customerTagStr);
			CustomerTagEntity.setCreateTime(date);
			CustomerTagEntity.setUpdateTime(date);
			dao = BLContextUtil.getDao(context, currentClass);
			logger.info("保存客户标签" + CustomerTagEntity);
			int val = dao.save(CustomerTagEntity);
			if (val > 0) {
				return true;
			} else {
				return false;
			}
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

	public static boolean updateTag(RequestContext context, String customerId, List<String> specialTag,
			List<String> customerTag) throws BlAppException {
		return saveTag(context, customerId, specialTag, customerTag);
	}

	public static CustomerTagEntity queryTag(RequestContext context, String customerId) throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		QueryCustomerTagDto request = null;
		logger.info("查询客户id：" + customerId + "的标签");
		try {
			request = new QueryCustomerTagDto();
			request.setCustomerId(customerId);
			dao = BLContextUtil.getDao(context, currentClass);
			logger.info("查询客户标签" + request);
			return (CustomerTagEntity) dao.query(request, false);
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	public static CustomerEntity loadCustomerInfo(RequestContext context, String customerId) throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		CustomerEntity customerEntity = null;
		CustomerTagEntity customerTagEntity = null;
		try {
			QueryCustomerDto request = new QueryCustomerDto();
			request.setCustomerId(customerId);
			request.setUserId(BLContextUtil.getSessionUserId(context));
			dao = BLContextUtil.getDao(context, currentClass);
			customerEntity = (CustomerEntity) dao.query(request, false);
			customerTagEntity = queryTag(context, customerId);
			if (customerTagEntity != null) {
				List<String> specilTag = transformationString(customerTagEntity.getSpecialTag(), true);
				List<String> customerTag = transformationString(customerTagEntity.getCustomerTag(), true);
				customerEntity.setSpecialTag(specilTag);
				customerEntity.setCustomerTag(customerTag);
			}
			return customerEntity;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * String 转换为list
	 * 
	 * @param datas
	 * @return
	 * @throws BlAppException
	 */
	private static List<String> transformationString(String str, boolean isCheck) throws BlAppException {
		String desc;
		if (str == null || str.length() <= 0) {
			return null;
		}
		if (isCheck) {
			desc = "标签";
		} else {
			desc = "关键词";
		}
		List<String> result = new ArrayList<String>();
		String srts[] = str.split(",");
		for (String string : srts) {
			string = string.trim();
			result.add(string);
		}
		return result;
	}

	public static Iterator<Entity> getOriginateInfo(RequestContext context) throws BlAppException, DaoAppException {
		Dao dao = null;
		CodeTable ct;
		try {
			dao = BLContextUtil.getDao(context, currentClass);
			IdEntity<Integer> number = new IdEntity<Integer>();
			number.setId(1);
			return dao.query(number);
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

	/**
	 * 创建客户
	 * 
	 * @param context
	 * @param request
	 * @param entity
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static String saveCustomer(RequestContext context, Map<String, String> infos, String userId,
			List<String> tags, String customerId) throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(customerId)) {
			customerId = KeyFactory.newKey(KeyFactory.KEY_CUSTOMER);
			CustomerMainEntity main = new CustomerMainEntity();
			main.setCreatetime(new Date());
			main.setCustomerId(customerId);
			main.setUserId(userId);
			saveCustomerMainEntity(context, main);
		}
		CustomerInfoEntity info = new CustomerInfoEntity();
		info.setCustomerId(customerId);
		for (Entry<String, String> map : infos.entrySet()) {
			// 信息
			info.setCustomerKey(map.getKey());
			info.setCustomerValue(map.getValue());
			saveCustomerInfoEntity(context, info);
		}
		info.setCustomerKey(CustomerInfoEntity.KEY_TYPE);
		info.setCustomerValue("1");
		saveCustomerInfoEntity(context, info);
		saveTag(context, customerId, tags, null);
		return customerId;
	}

	public static Map<String, String> buildInfo(RequestContext context, DrawPrizeRequestDto request,
			ActivePluginEntity entity) throws DaoAppException, BlAppException {
		Map<String, String> infos = new HashMap<String, String>();
		infos.put(CustomerInfoEntity.KEY_ORIGINATE, entity.getActivePluginType().getValue() + "");
		infos.put(CustomerInfoEntity.KEY_NAME, request.getName());
		infos.put(CustomerInfoEntity.KEY_PHONE, request.getPhone());
		ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
				request.getRelationId(), ManuscriptParameterType.manuscriptName);
		String extensionName = manuscriptParameterEntity == null ? null : manuscriptParameterEntity.getParameter();
		infos.put(CustomerInfoEntity.KEY_EXTENSIONNAME, extensionName);
		infos.put(CustomerInfoEntity.KEY_EXTENSIONID, entity.getRelationId());
		infos.put(CustomerInfoEntity.KEY_TYPE, "1");
		return infos;
	}

	public static List<Entity> searchCustomerByids(RequestContext context, List<String> list) throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		QueryCustomersByIds queryCustomersByIds = null;
		try {
			dao = BLContextUtil.getDao(context, currentClass);
			queryCustomersByIds = new QueryCustomersByIds();
			queryCustomersByIds.setCustomerIds(list);
			List<Entity> customers = BLContextUtil.transformation(dao.query(queryCustomersByIds));
			return customers;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 根据群组id获取联系人
	 * 
	 * @param requestContext
	 * @param groupIds
	 * @return
	 * @throws BlAppException
	 */
	public static List<EmailContractEntity> getEmailContractByGroupId(RequestContext requestContext,
			List<String> groupIds, String userId) throws BlAppException {
		Dao dao;
		QueryGroupCustomersDto queryGroupCustomersDto;
		try {
			if (groupIds != null && groupIds.size() > 0) {
				dao = BLContextUtil.getDao(requestContext, currentClass);
				queryGroupCustomersDto = new QueryGroupCustomersDto();
				queryGroupCustomersDto.setGroupIds(groupIds);
				queryGroupCustomersDto.setUserId(userId);
				Iterator<Entity> customers = dao.query(queryGroupCustomersDto);
				List<EmailContractEntity> result = new ArrayList<EmailContractEntity>();
				while (customers.hasNext()) {
					CustomerEntity emailCustomerEntity = (CustomerEntity) customers.next();
					// 过滤异常联系人
					if (Str.isNullOrEmpty(emailCustomerEntity.getEmail())
							|| Str.isNullOrEmpty(emailCustomerEntity.getCustomerId())) {
						continue;
					}
					// 联系人去重
					for (EmailContractEntity resultEmailCustomerEntity : result) {
						if (emailCustomerEntity.getCustomerId().equals(resultEmailCustomerEntity.getId())) {
							continue;
						}
					}
					EmailContractEntity emailContractEntity = new EmailContractEntity();
					emailContractEntity.setId(emailCustomerEntity.getCustomerId());
					emailContractEntity.setEmailAddr(emailCustomerEntity.getEmail());
					emailContractEntity.setToName(emailCustomerEntity.getName());
					result.add(emailContractEntity);
				}
				return result;
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
		return null;
	}

	/**
	 * 根据群组id获取联系人
	 * 
	 * @param requestContext
	 * @param groupIds
	 * @return
	 * @throws BlAppException
	 */
	public static List<String> getPhoneNumberByGroupId(RequestContext requestContext, List<String> customerIds,
			String userId) throws BlAppException {
		try {
			if (customerIds != null && customerIds.size() > 0) {
				List<Entity> customers = searchCustomerByids(requestContext, customerIds);
				List<String> result = new ArrayList<String>();

				for (Entity entity : customers) {
					CustomerEntity customerEntity = (CustomerEntity) entity;
					if (!Str.isNullOrEmpty(customerEntity.getPhone())) {
						for (String phoneNo : result) {
							if (phoneNo.equals(customerEntity.getPhone())) {
								continue;
							}
						}
						result.add(customerEntity.getPhone());
					}
				}

				return result;
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			CodeTable ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
		return null;
	}

	/**
	 * 保存并同步所有客户
	 * 
	 * @param context
	 * @param request
	 * @throws BlAppException
	 */
	public static void synchronizeAllCustomer(RequestContext context, SaveAllCustomerRequest request)
			throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		SearchCustomerDto searchCustomerDto = null;
		String groupId = null;
		String userId = null;
		CreateGroupRequest createGroupRequest = null;
		try {
			dao = BLContextUtil.getDao(context, currentClass);
			userId = BLContextUtil.getSessionUserId(context);
			searchCustomerDto = new SearchCustomerDto();
			searchCustomerDto.setOriginate(request.getOriginate());
			searchCustomerDto.setPageSize(request.getPageSize());
			searchCustomerDto.setSearchStr(request.getSearchStr());
			searchCustomerDto.setSendType(2);
			searchCustomerDto.setType(request.getType());
			searchCustomerDto.setUserId(userId);
			Iterator<Entity> list = dao.query(searchCustomerDto);
			List<String> customerIds = new ArrayList<String>();
			logger.info("提取联系人id");
			while (list.hasNext()) {
				CustomerTagEntity entity = (CustomerTagEntity) list.next();
				customerIds.add(entity.getCustomerId());
			}

			// 创建发送邮件的组
			createGroupRequest = new CreateGroupRequest();
			createGroupRequest.setGroupName(request.getEmailId());
			// 1，手机联系人；2，邮箱联系人
			createGroupRequest.setType(2);
			groupId = ContactManager.createContactGroup(context, createGroupRequest, userId);
			logger.info("创建的群组为：" + groupId);

			// 保存联系人进群组
			if (groupId == null) {
				throw new BlAppException(-2, "保存联系人失败");
			}
			boolean status = ContactManager.saveCustomerInGroup(context, groupId, customerIds);
			if (!status) {
				throw new BlAppException(-1, "保存联系人失败");
			}
			// 同步sendcloud
			logger.info("同步省sendcloud");
			ContactManager.synchronizeSendcloud(context, groupId, customerIds,
					BLContextUtil.getValue(context, "groupAddress"));
			// 保存邮件和群组
			logger.info("保存邮件和群组");
			EmailRightManager.saveGroupCustomer(context, userId, groupId, request.getEmailId());
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
