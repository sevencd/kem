package cn.ilanhai.kem.bl.contacts.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.contacts.Contact;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.contacts.ContactsDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.MapDto;
import cn.ilanhai.kem.domain.contacts.SynchronizeContractsRequest;
import cn.ilanhai.kem.domain.contacts.dto.ContactsKeyValue;
import cn.ilanhai.kem.domain.contacts.dto.DeleteContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.ImportContactDto;
import cn.ilanhai.kem.domain.contacts.dto.ImportDataDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryContactsResponseDto;
import cn.ilanhai.kem.domain.contacts.dto.SaveContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.SearchContactsDto;
import cn.ilanhai.kem.domain.contacts.group.AddGroupContactsRequest;
import cn.ilanhai.kem.domain.contacts.group.ContactsGroupEntity;
import cn.ilanhai.kem.domain.contacts.group.ContactsGroupResponse;
import cn.ilanhai.kem.domain.contacts.group.CreateGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.DeleteGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.SearchContactGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.SearchGroupContactsRequest;
import cn.ilanhai.kem.domain.contacts.group.SearchGroupContactsRespose;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("contact")
public class ContactImpl extends BaseBl implements Contact {
	private Logger logger = Logger.getLogger(ContactImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		SearchContactsDto request;
		CodeTable ct;
		try {
			logger.info("进入查询");
			this.checkFrontUserLogined(context);
			request = context.getDomain(SearchContactsDto.class);
			valiPara(request);
			this.valiParaNotNull(request.getType(), "查询类型错误", false);
			this.valiParaItemNumBetween(1, 2, request.getType(), "查询类型错误", false);
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数量不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "查询数量不能为空");
			Dao dao = BLContextUtil.getDao(context, ContactsDao.class);
			QueryContactsDto query = new QueryContactsDto();
			query.setKeyword(request.getKeyword());
			query.setPageSize(request.getPageSize());
			query.setStartCount(request.getStartCount());
			query.setType(request.getType());
			query.setUserId(getSessionUserId(context));
			QueryContactsResponseDto queryContactsResponseDto = new QueryContactsResponseDto();
			queryContactsResponseDto.setList(dao.query(query));
			queryContactsResponseDto.setPageSize(request.getPageSize());
			queryContactsResponseDto.setStartCount(request.getStartCount());
			queryContactsResponseDto.setTotalCount(((CountDto) dao.query(query, false)).getCount());
			return queryContactsResponseDto;
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		DeleteContactsDto request;
		CodeTable ct;
		try {
			logger.info("进入删除");
			this.checkFrontUserLogined(context);
			request = context.getDomain(DeleteContactsDto.class);
			valiPara(request);
			request.setUserId(getSessionUserId(context));
			logger.info("删除:" + request);
			Dao dao = BLContextUtil.getDao(context, ContactsDao.class);
			ContactManager.deleteGroupContacts(context, this.getValue(context, "groupAddress"), request);
			dao.delete(request);
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

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String save(RequestContext context) throws BlAppException, DaoAppException {
		SaveContactsDto request;
		String contractId = null;
		CodeTable ct;
		boolean isadd = false;
		try {
			logger.info("进入保存联系人");
			// 验证登录
			this.checkFrontUserLogined(context);
			request = context.getDomain(SaveContactsDto.class);
			valiPara(request);
			// 验证参数信息
			this.valiParaNotNull(request.getContractType(), "联系人类型错误", false);
			this.valiParaItemNumBetween(1, 2, request.getContractType(), "联系人类型错误", false);
			contractId = request.getContractId();
			String userid = this.getSessionUserId(context);
			// 验证是否是新增
			if (Str.isNullOrEmpty(contractId)) {
				contractId = ContactManager.createContact(context, userid, request.getContractType());
				logger.info("新增联系人:" + contractId);
				isadd = true;
			}

			if (request.getInfo() != null) {
				for (ContactsKeyValue keyvalue : request.getInfo()) {
					if (new Integer(3).equals(keyvalue.getKey())) {
						// 验证名称是否正确
						if (!ExpressionMatchUtil.isName(keyvalue.getContent())) {
							ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH;
							String tmp = null;
							tmp = ct.getDesc();
							tmp = String.format(tmp, "姓名", "20");
							throw new BlAppException(ct.getValue(), "请输入不超过20个字且不包含非法字符的姓名");
						}
					} else if (new Integer(2).equals(keyvalue.getKey())) {
						if (new Integer(2).equals(request.getContractType())) {
							// 验证邮箱否正确
							if (!ExpressionMatchUtil.isEmailAddress(keyvalue.getContent())) {
								throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(),
										"联系人邮件地址错误");
							}
						} else {
							// 验证电话是否正确
							if (!ExpressionMatchUtil.isPhoneNo(keyvalue.getContent())) {
								throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(),
										"联系人电话号码错误");
							}
						}
					}
					//如果是邮件联系人，进入sendcloud同步方法
					if (!isadd && request.getContractType() == 2) {
						ContactManager.updateMember(context, request, this.getValue(context, "groupAddress"));
					}
					// 保存内容
					ContactManager.saveContactInfo(context, userid, contractId, keyvalue.getKey(),
							keyvalue.getContent());
				}
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return contractId;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String savegroup(RequestContext context) throws BlAppException, DaoAppException {

		CodeTable ct;
		CreateGroupRequest request = null;
		String userId = null;
		try {
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			request = context.getDomain(CreateGroupRequest.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getGroupName(), "群组名字不能为空");
			if (!ExpressionMatchUtil.isName(request.getGroupName())) {
				ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH;
				String tmp = null;
				tmp = ct.getDesc();
				tmp = String.format(tmp, "群组名字", "20");
				throw new BlAppException(ct.getValue(), "请输入不超过20个字且没有特殊字符的群组名字");
			}
			BLContextUtil.valiParaItemIntegerNull(request.getType(), "群组类型不能为空");
			userId = BLContextUtil.getSessionUserId(context);
			logger.info("当前用户id=" + userId);
			if (Str.isNullOrEmpty(request.getGroupId())) {
				return ContactManager.createContactGroup(context, request, userId);
			} else {
				return ContactManager.updateContactGroup(context, request, userId,
						this.getValue(context, "groupAddress"));
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	public Entity searchgroup(RequestContext context) throws BlAppException, DaoAppException {

		CodeTable ct;
		Dao dao = null;
		SearchContactGroupRequest request = null;
		try {
			request = context.getDomain(SearchContactGroupRequest.class);
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "分页数不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getType(), "群组类型不能为空");
			request.setUserId(BLContextUtil.getSessionUserId(context));
			logger.info("参数验证完成");
			dao = BLContextUtil.getDao(context, ContactsDao.class);
			ContactsGroupResponse contactsGroupResponse = new ContactsGroupResponse();
			contactsGroupResponse.setList(transformation(dao.query(request)));
			contactsGroupResponse.setPageSize(request.getPageSize());
			contactsGroupResponse.setStartCount(request.getStartCount());
			IdEntity<Integer> count = (IdEntity<Integer>) dao.query(request, false);
			contactsGroupResponse.setTotalCount(count.getId());

			return contactsGroupResponse;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), e.getMessage(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String savegroupcontact(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		AddGroupContactsRequest request = null;
		try {
			request = context.getDomain(AddGroupContactsRequest.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getGroupId(), "群组id不能为空");
			dao = BLContextUtil.getDao(context, ContactsDao.class);
			ContactsGroupEntity contactsGroupEntity = new ContactsGroupEntity();
			contactsGroupEntity.setUserId(BLContextUtil.getSessionUserId(context));
			contactsGroupEntity.setGroupId(request.getGroupId());
			ContactsGroupEntity group = (ContactsGroupEntity) dao.query(contactsGroupEntity, false);
			if (group == null) {
				throw new BlAppException(-1, "该群组不存在");
			}
			request.setList(ContactManager.removalDuplicateKey(request.getList()));
			int val = dao.save(request);
			if (val <= 0) {
				throw new BlAppException(-1, "保存群组联系人失败");
			}

			SearchGroupContactsRequest searchGroupContactsRequest = new SearchGroupContactsRequest();
			searchGroupContactsRequest.setGroupId(request.getGroupId());
			searchGroupContactsRequest.setGroupIds(ContactManager.handleString(request.getGroupId()));
			searchGroupContactsRequest.setStartCount(0);
			searchGroupContactsRequest.setPageSize(1000);
			List<Entity> list = transformation(dao.query(searchGroupContactsRequest));
			ContactManager.saveContactsSendCloud(context, request.getGroupId(), this.getValue(context, "groupAddress"), list);
			return request.getGroupId();
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public MapDto importcontact(RequestContext context) throws BlAppException, DaoAppException {
		ImportContactDto request = null;
		String contractId = null;
		try {
			logger.info("进入导入联系人");
			// 验证登录
			this.checkFrontUserLogined(context);
			request = context.getDomain(ImportContactDto.class);
			valiPara(request);

			if ("customer".equals(request.getImportType())) {
				logger.info("进入导入客户信息");
				return CustomerManager.importCustomerLittle(context, request);
			}
			// 验证参数信息
			this.valiParaNotNull(request.getContactType(), "联系人类型错误", false);
			this.valiParaItemNumBetween(1, 2, request.getContactType(), "联系人类型错误", false);
			this.valiParaNotNull(request.getDatas(), "联系人格式错误", false);
			String userid = this.getSessionUserId(context);

			List<ImportDataDto> datas = request.getDatas();
			for (ImportDataDto importDataDto : datas) {
				contractId = ContactManager.createContact(context, userid, request.getContactType());
				try {
					logger.info("新增联系人:" + contractId);
					if (new Integer(1).equals(request.getContactType())) {
						// 验证电话是否正确
						if (!ExpressionMatchUtil.isPhoneNo(importDataDto.getValue())) {
							throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "联系人电话号码错误");
						}
					} else {
						// 验证邮箱否正确
						if (!ExpressionMatchUtil.isEmailAddress(importDataDto.getValue())) {
							throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "联系人邮件地址错误");
						}
					}
					// 保存内容
					ContactManager.saveContactInfo(context, userid, contractId, 2, importDataDto.getValue());
					// 保存名称
					ContactManager.saveContactInfo(context, userid, contractId, 3, importDataDto.getName());
				} catch (Exception e) {
					ContactManager.deleteContact(context, contractId, userid);
					continue;
				}
				logger.info("新增成功:" + contractId);
			}
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
		}
		return null;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	public Entity searchgroupcontact(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		SearchGroupContactsRequest request = null;
		try {
			request = context.getDomain(SearchGroupContactsRequest.class);

			BLContextUtil.valiParaItemStrNullOrEmpty(request.getGroupId(), "群组id不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "分页数不能为空");

			logger.info("参数验证成功");
			dao = BLContextUtil.getDao(context, ContactsDao.class);
			request.setGroupIds(ContactManager.handleString(request.getGroupId()));
			logger.info("开始装配请求");
			SearchGroupContactsRespose searchGroupContactsRespose = new SearchGroupContactsRespose();
			searchGroupContactsRespose.setPageSize(request.getPageSize());
			searchGroupContactsRespose.setStartCount(request.getStartCount());
			searchGroupContactsRespose.setList(transformation(dao.query(request)));
			IdEntity<Integer> count = (IdEntity<Integer>) dao.query(request, false);
			searchGroupContactsRespose.setTotalCount(count.getId());
			logger.info("查询完成返回结果");
			return searchGroupContactsRespose;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean deletegroup(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		DeleteGroupRequest request = null;
		try {
			request = context.getDomain(DeleteGroupRequest.class);
			if (request.getList().length <= 0) {
				throw new BlAppException(-1, "群组id不能为空");
			}
			request.setUserId(BLContextUtil.getSessionUserId(context));
			dao = BLContextUtil.getDao(context, ContactsDao.class);
			int val = dao.save(request);
			if (val <= 0) {
				throw new BlAppException(-1, "群组删除失败");
			}
			for (int i = 0; i < request.getList().length; i++) {
				ContactManager.deletegroupfromsendcloud(request.getList()[i], this.getValue(context, "groupAddress"));
			}
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.3.0.0")
	public Entity synchronization(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		SynchronizeContractsRequest request = null;
		try {
			request = context.getDomain(SynchronizeContractsRequest.class);
			BLContextUtil.valiParaItemIntegerNull(request.getType(), "联系人类型");
			BLContextUtil.valiParaItemNumBetween(1, 2, request.getType(), "联系人类型");
			request.setUserId(BLContextUtil.getSessionUserId(context));
			return ContactManager.synchronizeContracts(context, request);

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
