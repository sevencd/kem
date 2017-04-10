package cn.ilanhai.kem.bl.contacts;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.contacts.ContactsDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.contacts.ContactInformation;
import cn.ilanhai.kem.domain.contacts.ContactsInfoEntity;
import cn.ilanhai.kem.domain.contacts.ContactsMainEntity;
import cn.ilanhai.kem.domain.contacts.SearchContactsById;
import cn.ilanhai.kem.domain.contacts.SynchronizeContractsRequest;
import cn.ilanhai.kem.domain.contacts.SynchronizeContractsResponse;
import cn.ilanhai.kem.domain.contacts.dto.ContactsKeyValue;
import cn.ilanhai.kem.domain.contacts.dto.DeleteContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryContactGroupsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupPhoneNumberDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupPhoneNumberResponseDto;
import cn.ilanhai.kem.domain.contacts.dto.SaveContactsDto;
import cn.ilanhai.kem.domain.contacts.group.AddGroupContactsRequest;
import cn.ilanhai.kem.domain.contacts.group.ContactsGroupEntity;
import cn.ilanhai.kem.domain.contacts.group.ContactsInfo;
import cn.ilanhai.kem.domain.contacts.group.CreateGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.DeleteGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.QueryContactsGroupIsExits;
import cn.ilanhai.kem.domain.contacts.group.QueryGroupContactsByContact;
import cn.ilanhai.kem.domain.contacts.group.SearchGroupContactsRequest;
import cn.ilanhai.kem.domain.customer.CustomerEntity;
import cn.ilanhai.kem.domain.email.EmailContractEntity;
import cn.ilanhai.kem.domain.email.EmailGroupEntity;
import cn.ilanhai.kem.domain.enums.ContactInfoType;
import cn.ilanhai.kem.domain.enums.ContactType;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateMemberRequest;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.MailMsg;
import cn.ilanhai.kem.util.StringVerifyUtil;

public class ContactManager {
	private static Logger logger = Logger.getLogger(ContactManager.class);

	/**
	 * 创建联系人
	 * 
	 * @param requestContext
	 * @param userId
	 * @return
	 * @throws BlAppException
	 */
	public static String createContact(RequestContext requestContext, String userId, Integer contactsType)
			throws BlAppException {
		Dao dao;
		String contactId = null;
		try {
			ContactsMainEntity mainEntity = new ContactsMainEntity();
			contactId = KeyFactory.newKey(KeyFactory.KEY_CONTACT);
			mainEntity.setContactsId(contactId);
			mainEntity.setCreatetime(new Date());
			mainEntity.setUpdatetime(new Date());
			mainEntity.setUserId(userId);
			mainEntity.setIsDelete(EnableState.enable.getValue());
			mainEntity.setContactsType(contactsType);
			dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
			int val = dao.save(mainEntity);
			BLContextUtil.valiSaveDomain(val, "创建联系人");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return contactId;
	}

	public static void createContactByRelationId(RequestContext context, String relationId, String name, String value,
			ContactType contactType) throws DaoAppException, BlAppException {
		// 根据relation id 获取 userid
		logger.info("获取relationId:" + relationId + "的用户id");
		ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, relationId);
		String userId = manuscriptEntity.getUserId();
		if (manuscriptEntity == null || Str.isNullOrEmpty(userId)) {
			return;
		}
		logger.info("用户id:" + userId);
		// 添加 手机联系人 手机联系人 type 为1
		String contactId = ContactManager.createContact(context, userId, contactType.getValue());
		// 保存手机联系人 姓名
		ContactManager.saveContactInfo(context, userId, contactId, ContactInfoType.name.getValue(), name);
		// 保存手机联系人手机号
		ContactManager.saveContactInfo(context, userId, contactId, ContactInfoType.context.getValue(), value);
	}

	/**
	 * 消息队列处理
	 * 
	 * @param dao
	 * @param userId
	 * @param name
	 * @param value
	 * @param contactType
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void createContactByDao(Dao dao, String userId, String name, String value, ContactType contactType)
			throws DaoAppException, BlAppException {
		// 根据relation id 获取 userid
		if (Str.isNullOrEmpty(userId)) {
			return;
		}
		logger.info("用户id:" + userId);
		// 添加 手机联系人 手机联系人 type 为1
		String contactId = ContactManager.createContact(dao, userId, contactType.getValue());
		// 保存手机联系人 姓名
		ContactManager.saveContactInfo(dao, userId, contactId, ContactInfoType.name.getValue(), name);
		// 保存手机联系人手机号
		ContactManager.saveContactInfo(dao, userId, contactId, ContactInfoType.context.getValue(), value);
	}

	/**
	 * 保存联系人基础信息
	 * 
	 * @param requestContext
	 * @param mainEntity
	 * @return
	 * @throws BlAppException
	 */
	public static String saveContactMain(RequestContext requestContext, ContactsMainEntity mainEntity)
			throws BlAppException {
		if (mainEntity == null) {
			return null;
		}
		try {
			int val = BLContextUtil.getDao(requestContext, ContactsDao.class).save(mainEntity);
			BLContextUtil.valiSaveDomain(val, "保存联系人");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return mainEntity.getContactsId();
	}

	/**
	 * 保存联系人信息
	 * 
	 * @param requestContext
	 * @param contactId
	 * @param key
	 * @param value
	 * @throws BlAppException
	 */
	public static void saveContactInfo(RequestContext requestContext, String userId, String contactId, Integer key,
			String value) throws BlAppException {
		if (key == null || Str.isNullOrEmpty(contactId)) {
			return;
		}
		logger.info("保存联系人[" + contactId + "]的key[" + key + "]为value[" + value + "]");
		try {
			ContactsInfoEntity infoEntity = new ContactsInfoEntity();
			infoEntity.setEnable(EnableState.enable.getValue());
			infoEntity.setInfoKey(key);
			infoEntity.setInfoValue(value);
			infoEntity.setContactsId(contactId);
			infoEntity.setUserId(userId);
			int val = BLContextUtil.getDao(requestContext, ContactsDao.class).save(infoEntity);
			BLContextUtil.valiSaveDomain(val, "保存联系人信息");
			logger.info("保存成功");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
	}

	/**
	 * 保存联系人信息
	 * 
	 * @param requestContext
	 * @param contactId
	 * @param key
	 * @param value
	 * @throws BlAppException
	 */
	public static void saveContactInfo(Dao dao, String userId, String contactId, Integer key, String value)
			throws BlAppException {
		if (key == null || Str.isNullOrEmpty(contactId)) {
			return;
		}
		logger.info("保存联系人[" + contactId + "]的key[" + key + "]为value[" + value + "]");
		try {
			ContactsInfoEntity infoEntity = new ContactsInfoEntity();
			infoEntity.setEnable(EnableState.enable.getValue());
			infoEntity.setInfoKey(key);
			infoEntity.setInfoValue(value);
			infoEntity.setContactsId(contactId);
			infoEntity.setUserId(userId);
			int val = dao.save(infoEntity);
			BLContextUtil.valiSaveDomain(val, "保存联系人信息");
			logger.info("保存成功");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
	}

	/**
	 * 创建联系人
	 * 
	 * @param requestContext
	 * @param userId
	 * @return
	 * @throws BlAppException
	 */
	public static String createContact(Dao dao, String userId, Integer contactsType) throws BlAppException {
		String contactId = null;
		try {
			ContactsMainEntity mainEntity = new ContactsMainEntity();
			contactId = KeyFactory.newKey(KeyFactory.KEY_CONTACT);
			mainEntity.setContactsId(contactId);
			mainEntity.setCreatetime(new Date());
			mainEntity.setUpdatetime(new Date());
			mainEntity.setUserId(userId);
			mainEntity.setIsDelete(EnableState.enable.getValue());
			mainEntity.setContactsType(contactsType);
			int val = dao.save(mainEntity);
			BLContextUtil.valiSaveDomain(val, "创建联系人");
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return contactId;
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
		QueryGroupContactsDto queryGroupContactsDto;
		try {
			if (groupIds != null && groupIds.size() > 0) {
				dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
				queryGroupContactsDto = new QueryGroupContactsDto();
				queryGroupContactsDto.setGroupIds(groupIds);
				queryGroupContactsDto.setUserId(userId);
				Iterator<Entity> contracts = dao.query(queryGroupContactsDto);
				List<EmailContractEntity> result = new ArrayList<EmailContractEntity>();
				while (contracts.hasNext()) {
					EmailContractEntity emailContractEntity = (EmailContractEntity) contracts.next();
					// 过滤异常联系人
					if (Str.isNullOrEmpty(emailContractEntity.getEmailAddr())
							|| Str.isNullOrEmpty(emailContractEntity.getId())) {
						continue;
					}
					// 联系人去重
					for (EmailContractEntity resultEmailContractEntity : result) {
						if (emailContractEntity.getId().equals(resultEmailContractEntity.getId())) {
							continue;
						}
					}
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
	public static List<String> getPhoneNumberByGroupId(RequestContext requestContext, List<String> groupIds,
			String userId) throws BlAppException {
		Dao dao;
		QueryGroupPhoneNumberDto queryGroupContactsDto;
		try {
			if (groupIds != null && groupIds.size() > 0) {
				dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
				queryGroupContactsDto = new QueryGroupPhoneNumberDto();
				queryGroupContactsDto.setGroupIds(groupIds);
				queryGroupContactsDto.setUserId(userId);
				Iterator<Entity> contracts = dao.query(queryGroupContactsDto);
				List<String> result = new ArrayList<String>();
				while (contracts.hasNext()) {
					QueryGroupPhoneNumberResponseDto emailContractEntity = (QueryGroupPhoneNumberResponseDto) contracts
							.next();
					// 过滤异常联系人
					if (!ExpressionMatchUtil.isPhoneNo(emailContractEntity.getPhone())) {
						continue;
					}
					result.add(emailContractEntity.getPhone());
					logger.info("电话号码:" + emailContractEntity.getPhone());
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
	 * 新增群组
	 * 
	 * @param context
	 * @param request
	 * @param userId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static String createContactGroup(RequestContext context, CreateGroupRequest request, String userId)
			throws DaoAppException, BlAppException {
		logger.info("创建新的群组");
		Dao dao = null;
		try {
			dao = BLContextUtil.getDao(context, ContactsDao.class);
			/**
			 * 群组判重，产品现在不要。。。 取消下面注释即可判重
			 */
			// QueryContactsGroupIsExits queryGroup = new
			// QueryContactsGroupIsExits();
			// queryGroup.setGroupName(request.getGroupName());
			// queryGroup.setUserId(BLContextUtil.getSessionUserId(context));
			logger.info("查询群组名字：" + request.getGroupName() + "是否存在");
			ContactsGroupEntity contactsGroupEntity = null;
			// contactsGroupEntity = (ContactsGroupEntity) dao.query(queryGroup,
			// false);
			// if (contactsGroupEntity != null) {
			// throw new DaoAppException(-1, "群组名已存在");
			// }
			contactsGroupEntity = new ContactsGroupEntity();
			Date date = new Date();
			contactsGroupEntity.setGroupId(KeyFactory.newKey(KeyFactory.KEY_CONTACTS_GROUP));
			contactsGroupEntity.setGroupName(request.getGroupName());
			contactsGroupEntity.setType(request.getType());
			contactsGroupEntity.setUserId(userId);
			contactsGroupEntity.setEnable(1);
			contactsGroupEntity.setCreateTime(date);
			contactsGroupEntity.setUpdateTime(date);
			logger.info("新建id=" + contactsGroupEntity.getGroupId() + "的群组");
			int val = dao.save(contactsGroupEntity);
			if (val <= 0) {
				throw new BlAppException(-1, "新建群组失败");
			}
			// 添加sendcloud
			// saveSendcloudAddress(contactsGroupEntity.getGroupId(),
			// groupAddress, contactsGroupEntity.getGroupName());

			return contactsGroupEntity.getGroupId();
		} catch (DaoAppException e) {
			throw new BlAppException(-1, e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 更新群组
	 * 
	 * @param context
	 * @param request
	 * @param userId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static String updateContactGroup(RequestContext context, CreateGroupRequest request, String userId,
			String groupAddress) throws DaoAppException, BlAppException {
		logger.info("更新群组");
		Dao dao = null;
		try {
			dao = BLContextUtil.getDao(context, ContactsDao.class);

			ContactsGroupEntity queryGroupid = new ContactsGroupEntity();
			queryGroupid.setGroupId(request.getGroupId());
			queryGroupid.setUserId(BLContextUtil.getSessionUserId(context));
			logger.info("查询群组id：" + request.getGroupId() + "是否存在");
			ContactsGroupEntity contactsGroupEntity = null;
			contactsGroupEntity = (ContactsGroupEntity) dao.query(queryGroupid, false);
			if (contactsGroupEntity == null) {
				throw new DaoAppException(-1, "群组不存在");
			}
			contactsGroupEntity = new ContactsGroupEntity();
			Date date = new Date();
			contactsGroupEntity.setGroupId(request.getGroupId() + groupAddress);
			contactsGroupEntity.setGroupName(request.getGroupName());
			contactsGroupEntity.setUserId(userId);
			contactsGroupEntity.setEnable(1);
			contactsGroupEntity.setUpdateTime(date);
			logger.info("更新id=" + contactsGroupEntity.getGroupId() + "的群组");
			int val = dao.save(contactsGroupEntity);
			if (val <= 0) {
				throw new BlAppException(-1, "更新群组失败");
			}
			// 更新sendcloud
			MailMsg<MailInfo> msg = null;
			UpdateAddressRequest updateAddressRequest = null;
			updateAddressRequest = new UpdateAddressRequest();
			updateAddressRequest.setAddress(contactsGroupEntity.getGroupId());
			updateAddressRequest.setName(contactsGroupEntity.getGroupName());
			msg = new MailMsg<MailInfo>();
			msg.setMsgContent(updateAddressRequest);
			QueueManager.getInstance().put(msg);
			logger.info("更新sendcloud成功");
			return contactsGroupEntity.getGroupId();
		} catch (DaoAppException e) {
			throw new BlAppException(-1, e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 封装 邮件群组联系人
	 * 
	 * @return
	 * @throws BlAppException
	 */
	public static List<EmailGroupEntity> bulidEmailGroupEntityByGroupId(RequestContext requestContext,
			List<String> groupIds, String userId) throws BlAppException {
		Dao dao;
		QueryContactGroupsDto queryContactGroupsDto;
		try {
			if (groupIds != null && groupIds.size() > 0) {
				dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
				queryContactGroupsDto = new QueryContactGroupsDto();
				queryContactGroupsDto.setGroupIds(groupIds);
				queryContactGroupsDto.setUserId(userId);
				Iterator<Entity> contractGroup = dao.query(queryContactGroupsDto);
				List<EmailGroupEntity> result = new ArrayList<EmailGroupEntity>();
				logger.info("群组实体:" + contractGroup);
				while (contractGroup.hasNext()) {
					EmailGroupEntity emailGroupEntity = (EmailGroupEntity) contractGroup.next();
					// 过滤异常群组
					if (Str.isNullOrEmpty(emailGroupEntity.getGroupId())
							|| Str.isNullOrEmpty(emailGroupEntity.getGroupName())) {
						continue;
					}
					// 群组去重
					for (EmailGroupEntity resultEmailContractEntity : result) {
						if (emailGroupEntity.getGroupId().equals(resultEmailContractEntity.getGroupId())) {
							continue;
						}
					}
					result.add(emailGroupEntity);
				}

				return result;
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
		return null;
	}

	// 去重
	public static List<String> removalDuplicateKey(List<String> keys) {
		List<String> newKeys = new ArrayList<String>();
		for (String string : keys) {
			if (!BLContextUtil.useList(newKeys, string)) {
				newKeys.add(string);
			}
		}

		return newKeys;
	}

	/**
	 * 保存联系人到sendcloud
	 * 
	 * @param address
	 * @param list
	 * @param name
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static void saveContactsSendCloud(RequestContext requestContext, String groupId, String address,
			List<Entity> list) throws BlAppException, DaoAppException {
		logger.info("添加成员到：" + address);
		Dao dao = null;
		List<String> name = new ArrayList<String>();
		List<String> member = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			ContactsInfo contactsInfo = (ContactsInfo) list.get(i);
			logger.info("添加到sendcloud的联系人为：" + contactsInfo);
			name.add(contactsInfo.getName());
			member.add(contactsInfo.getValue());
		}
		dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
		ContactsGroupEntity contactsGroupEntity = searchGroupInfoById(dao, groupId);
		deleteSendcloudAddress(groupId, address);
		saveSendcloudAddress(groupId, address, contactsGroupEntity.getGroupName());
		saveSendcloudMember(groupId, address, member, name);
	}

	// 处理字符串
	public static String[] handleString(String key) {
		String[] keys = key.split(",");
		return keys;
	}

	public static void deletegroupfromsendcloud(String groupId, String groupAddress) throws BlAppException {
		deleteSendcloudAddress(groupId, groupAddress);
	}

	public static void deleteContact(RequestContext requestContext, String id, String userId)
			throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(userId) || Str.isNullOrEmpty(id)) {
			return;
		}
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		DeleteContactsDto deleteContactsDto = new DeleteContactsDto();
		deleteContactsDto.setIds(ids);
		deleteContactsDto.setUserId(userId);
		Dao dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
		dao.delete(deleteContactsDto);
	}

	public static void deleteGroupContacts(RequestContext requestContext, String address, DeleteContactsDto request)
			throws DaoAppException, BlAppException {
		logger.info("进入同步删除群组联系人方法");
		Dao dao = null;
		dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
		QueryGroupContactsByContact contacts = new QueryGroupContactsByContact();
		List<String> contactsList = new ArrayList<String>();
		for (int i = 0; i < request.getIds().size(); i++) {
			String value = request.getIds().get(i);
			// 查询联系人对应的群组
			List<Entity> groups = searchGroupInfo(dao, value);
			// 查询联系人信息
			ContactsInfo contactsInfo = searchContactsInfo(dao, value);
			logger.info("联系人信息为：" + contactsInfo);
			if (groups != null && groups.size() > 0) {
				for (int j = 0; j < groups.size(); j++) {
					ContactsGroupEntity group = (ContactsGroupEntity) groups.get(j);
					List<String> list = new ArrayList<String>();
					list.add(contactsInfo.getValue());
					deleteSendcloudMember(group.getGroupId(), address, list);
				}
				contactsList.add(value);
			}
		}
		contacts.setList(contactsList);
		if (contacts.getList().size() > 0) {
			dao.delete(contacts);
		}
	}

	public static void updateMember(RequestContext requestContext, SaveContactsDto request, String address)
			throws DaoAppException, BlAppException {
		logger.info("修改联系人在sendcloud中的");
		Dao dao = null;
		List<String> member = new ArrayList<String>();
		List<String> newMember = new ArrayList<String>();
		List<String> name = new ArrayList<String>();
		dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
		ContactsInfo contactsInfo = searchContactsInfo(dao, request.getContractId());
		List<Entity> groups = searchGroupInfo(dao, request.getContractId());
		// 数据准备完成
		if (groups != null && groups.size() > 0) {
			member.add(contactsInfo.getValue());
			for (int i = 0; i < request.getInfo().size(); i++) {
				ContactsKeyValue contactsKeyValue = (ContactsKeyValue) request.getInfo().get(i);
				if (contactsKeyValue.getKey() == 2) {
					newMember.add(contactsKeyValue.getContent());
				} else if (contactsKeyValue.getKey() == 3) {
					name.add(contactsKeyValue.getContent());
				}
			}
			for (int i = 0; i < groups.size(); i++) {
				ContactsGroupEntity group = (ContactsGroupEntity) groups.get(i);
				logger.info("同步更新：" + group + "中的联系人");
				updateSendcloudMember(group.getGroupId(), address, member, newMember, name);
			}
		}

	}

	private static ContactsGroupEntity searchGroupInfoById(Dao dao, String groupId) throws DaoAppException {
		logger.info("查询群组：" + groupId);
		// 查询群组信息
		IdEntity<String> group = new IdEntity<String>();
		group.setId(groupId);
		ContactsGroupEntity contactsGroupEntity = (ContactsGroupEntity) dao.query(group, false);
		logger.info("群组信息为：" + contactsGroupEntity);
		return contactsGroupEntity;
	}

	/**
	 * 查询联系人对应的群组
	 * 
	 * @param dao
	 * @param groupId
	 * @return
	 * @throws DaoAppException
	 */
	private static List<Entity> searchGroupInfo(Dao dao, String contactId) throws DaoAppException {
		logger.info("查询联系人" + contactId + "所在群组");
		IdEntity<String> contactsId = new IdEntity<String>();
		contactsId.setId(contactId);
		Iterator<Entity> group = dao.query(contactsId);
		List<Entity> list = new ArrayList<Entity>();
		while (group.hasNext()) {
			ContactsGroupEntity contactsGroupEntity = (ContactsGroupEntity) group.next();
			list.add(contactsGroupEntity);
			logger.info("联系人所在群组：" + contactsGroupEntity);
		}
		return list;
	}

	/**
	 * 查询联系人信息
	 * 
	 * @param dao
	 * @param contactsId
	 * @return
	 * @throws DaoAppException
	 */
	private static ContactsInfo searchContactsInfo(Dao dao, String contactsId) throws DaoAppException {
		logger.info("查询联系人：" + contactsId);
		// 查询联系人信息
		SearchContactsById searchContactsById = new SearchContactsById();
		searchContactsById.setContactsId(contactsId);
		ContactsInfo contactsInfo = (ContactsInfo) dao.query(searchContactsById, false);
		logger.info("原联系人信息为：" + contactsInfo);

		return contactsInfo;
	}

	/**
	 * 新增sendcloud地址
	 * 
	 * @param groupId
	 * @param groupAddress
	 * @param groupName
	 * @throws BlAppException
	 */
	private static void saveSendcloudAddress(String groupId, String groupAddress, String groupName)
			throws BlAppException {
		logger.info("添加sendcloud的群组为：" + groupId + groupAddress);
		MailMsg<MailInfo> msg = null;
		AddAddressRequest addAddressRequest = null;
		addAddressRequest = new AddAddressRequest();
		addAddressRequest.setAddress(groupId + groupAddress);
		addAddressRequest.setName(groupName);
		msg = new MailMsg<MailInfo>();
		msg.setMsgContent(addAddressRequest);
		QueueManager.getInstance().put(msg);
		logger.info("添加sendcloud成功");
	}

	/**
	 * 添加sendcloud地址成员
	 * 
	 * @param groupId
	 * @param groupAddress
	 * @param member
	 * @param name
	 * @throws BlAppException
	 */
	private static void saveSendcloudMember(String groupId, String groupAddress, List<String> member, List<String> name)
			throws BlAppException {
		logger.info("更新sendcloud的群组为：" + groupId + groupAddress);
		MailMsg<MailInfo> msg = null;
		AddMemberRequest addMemberRequest = null;
		addMemberRequest = new AddMemberRequest();
		addMemberRequest.setAddress(groupId + groupAddress);
		addMemberRequest.setMember(member);
		addMemberRequest.setName(name);
		msg = new MailMsg<MailInfo>();
		msg.setMsgContent(addMemberRequest);
		QueueManager.getInstance().put(msg);
		logger.info("更新sendcloud成功");
	}

	/**
	 * 删除sendcloud地址
	 * 
	 * @param groupId
	 * @param groupAddress
	 * @throws BlAppException
	 */
	private static void deleteSendcloudAddress(String groupId, String groupAddress) throws BlAppException {
		logger.info("同步删除sendcloud中群组号为：" + groupId + groupAddress);
		MailMsg<MailInfo> msg = null;
		DeleteAddressRequest deleteAddressRequest = null;
		deleteAddressRequest = new DeleteAddressRequest();
		deleteAddressRequest.setAddress(groupId + groupAddress);
		msg = new MailMsg<MailInfo>();
		msg.setMsgContent(deleteAddressRequest);
		QueueManager.getInstance().put(msg);
		logger.info("删除sendcloud成功");
	}

	/**
	 * 删除sendcloud地址成员
	 * 
	 * @param groupId
	 * @param groupAddress
	 * @param list
	 * @throws BlAppException
	 */
	private static void deleteSendcloudMember(String groupId, String groupAddress, List<String> list)
			throws BlAppException {
		logger.info("同步删除sendcloud中群组号为：" + groupId + groupAddress + "的成员：" + FastJson.bean2Json(list));
		MailMsg<MailInfo> msg = null;
		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();
		deleteMemberRequest.setAddress(groupId + groupAddress);
		deleteMemberRequest.setMember(list);
		msg = new MailMsg<MailInfo>();
		msg.setMsgContent(deleteMemberRequest);
		QueueManager.getInstance().put(msg);
		logger.info("删除sendcloud中群组为：" + groupId + groupAddress + "联系人为：" + FastJson.bean2Json(list) + ",成功");
	}

	/**
	 * 更新sendcloud地址成员
	 * 
	 * @param groupId
	 * @param groupAddress
	 * @param member
	 * @param newMember
	 * @param name
	 * @throws BlAppException
	 */
	private static void updateSendcloudMember(String groupId, String groupAddress, List<String> member,
			List<String> newMember, List<String> name) throws BlAppException {
		logger.info("更新sendcloud中群组号为：" + groupId + groupAddress + "的成员");
		MailMsg<MailInfo> msg = null;
		UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest();
		updateMemberRequest.setAddress(groupId + groupAddress);
		updateMemberRequest.setMember(member);
		logger.info("原联系人：" + FastJson.bean2Json(member));
		updateMemberRequest.setNewMember(newMember);
		logger.info("新联系人：" + FastJson.bean2Json(newMember));
		updateMemberRequest.setName(name);
		logger.info("联系人名：" + FastJson.bean2Json(name));
		msg = new MailMsg<MailInfo>();
		msg.setMsgContent(updateMemberRequest);
		QueueManager.getInstance().put(msg);
		logger.info("更新sendcloud中群组号为：" + groupId + groupAddress + "的成员成功");
	}

	public static SynchronizeContractsResponse synchronizeContracts(RequestContext requestContext,
			SynchronizeContractsRequest request) throws BlAppException, DaoAppException {
		Dao dao;
		int count = 0;
		SynchronizeContractsResponse synchronizeContractsResponse = new SynchronizeContractsResponse();
		logger.info("进入同步联系人方法");
		try {
			dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
			Iterator<Entity> contacts = dao.query(request);
			IdEntity<Integer> removalCount = (IdEntity<Integer>) dao.query(request, false);
			logger.info("需要同步的联系人为" + contacts.hasNext());
			// 分类处理
			while (contacts.hasNext()) {
				ContactInformation contactInformation = (ContactInformation) contacts.next();
				if (!Str.isNullOrEmpty(contactInformation.getValueNumber())) {
					count++;
					String contactId = null;
					contactId = createContact(requestContext, request.getUserId(), request.getType());
					saveContactInfo(requestContext, request.getUserId(), contactId, 2,
							contactInformation.getValueNumber());
					saveContactInfo(requestContext, request.getUserId(), contactId, 3, contactInformation.getName());
				}
			}
			logger.info("联系人同步数量为：" + count);
			synchronizeContractsResponse.setSynCount(count);
			synchronizeContractsResponse.setRemovalCount(removalCount.getId());
			return synchronizeContractsResponse;
		} catch (DaoAppException e) {
			throw new DaoAppException(-1, e.getMessage());
		}
	}

	// 保存联系人进群组
	public static boolean saveCustomerInGroup(RequestContext requestContext, String groupId, List<String> list)
			throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		AddGroupContactsRequest addGroupContactsRequest = null;
		try {
			//屏蔽前端传入为空 报空引用异常BUG by 黄毅
			if (list == null) {
				list = new ArrayList<String>();
			}
			dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
			addGroupContactsRequest = new AddGroupContactsRequest();
			addGroupContactsRequest.setGroupId(groupId);
			addGroupContactsRequest.setList(list);
			int val = dao.save(addGroupContactsRequest);
			if (val <= 0) {
				return false;
			}
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	/**
	 * 
	 * @param requestContext
	 *            上下文
	 * @param groupId
	 *            群组id
	 * @param list
	 *            联系人ids
	 * @param address
	 *            sendcloud后缀
	 * @throws BlAppException
	 */
	public static void synchronizeSendcloud(RequestContext requestContext, String groupId, List<String> list,
			String address) throws BlAppException {
		Dao dao = null;
		CodeTable ct;
		try {
			dao = BLContextUtil.getDao(requestContext, ContactsDao.class);

			List<Entity> Customers = CustomerManager.searchCustomerByids(requestContext, list);
			// 同步到sendcloud
			saveCustomersSendCloud(requestContext, groupId, address, Customers);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}

	}

	private static void saveCustomersSendCloud(RequestContext requestContext, String groupId, String address,
			List<Entity> list) throws BlAppException, DaoAppException {
		logger.info("添加成员到：" + groupId + address);
		Dao dao = null;
		List<String> name = new ArrayList<String>();
		List<String> member = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			CustomerEntity customerEntity = (CustomerEntity) list.get(i);
			logger.info("添加到sendcloud的客户为：" + customerEntity);
			name.add(customerEntity.getName());
			member.add(customerEntity.getEmail());
		}
		dao = BLContextUtil.getDao(requestContext, ContactsDao.class);
		ContactsGroupEntity contactsGroupEntity = searchGroupInfoById(dao, groupId);
		saveSendcloudAddress(groupId, address, contactsGroupEntity.getGroupName());
		saveSendcloudMember(groupId, address, member, name);
	}

}
