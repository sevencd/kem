package cn.ilanhai.kem.bl.customer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.customer.Customer;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.domain.MapDto;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.customer.dto.DeleteCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.SaveCustomerDto;
import cn.ilanhai.kem.domain.customer.dto.SearchCustomerDto;
import cn.ilanhai.kem.domain.customer.tag.QueryCustomerDto;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("customer")
public class CustomerImpl extends BaseBl implements Customer {
	Logger logger = Logger.getLogger(CustomerImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		String userId = null;
		try {
			BLContextUtil.checkFrontUserLogined(context);
			// 获取请求数据
			SearchCustomerDto searchCustomerDto = context.getDomain(SearchCustomerDto.class);
			logger.info("入参:" + searchCustomerDto);
			this.valiPara(searchCustomerDto);
			userId = getSessionUserId(context);
			// 获取userid的父账号
			UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
			if (userRelationEntity != null) {
				userId = userRelationEntity.getFatherUserId();
			}
			// 查询全部时为空
			// BLContextUtil.valiParaItemIntegerNull(searchCustomerDto.getType(),
			// "客户分组不能为空");
			BLContextUtil.valiParaItemIntegerNull(searchCustomerDto.getSendType(), "查询类型不能为空");
			if (searchCustomerDto.getSendType() == 0) {
				this.checkLimit(searchCustomerDto.getStartCount(), searchCustomerDto.getPageSize());
			}
			searchCustomerDto.setUserId(userId);
			return CustomerManager.search(context, searchCustomerDto);
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
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		String userId = null;
		try {
			BLContextUtil.checkFrontUserLogined(context);
			userId = getSessionUserId(context);
			// 获取userid的父账号
			UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
			if (userRelationEntity != null) {
				userId = userRelationEntity.getFatherUserId();
			}
			// 获取请求数据
			DeleteCustomerDto deleteCustomerDto = context.getDomain(DeleteCustomerDto.class);
			logger.info("入参:" + deleteCustomerDto);
			this.valiPara(deleteCustomerDto);
			deleteCustomerDto.setUserId(userId);
			CustomerManager.deleteCustomer(context, deleteCustomerDto);
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
	@InterfaceDocAnnotation(methodVersion = "1.3.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String save(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			BLContextUtil.checkFrontUserLogined(context);
			// 获取请求数据
			SaveCustomerDto saveCustomerDto = context.getDomain(SaveCustomerDto.class);
			logger.info("入参:" + saveCustomerDto);
			this.valiPara(saveCustomerDto);
			String customerId = saveCustomerDto.getCustomerId();
			String userId = getSessionUserId(context);
			// 获取userid的父账号
			UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, userId);
			if (userRelationEntity != null) {
				userId = userRelationEntity.getFatherUserId();
			}
			CustomerInfoEntity info = new CustomerInfoEntity();
			String key;
			Object value;
			List<String> specialTag = new ArrayList<String>();
			List<String> customerTag = new ArrayList<String>();
			if (Str.isNullOrEmpty(customerId)) {
				customerId = KeyFactory.newKey(KeyFactory.KEY_CUSTOMER);
				CustomerMainEntity main = new CustomerMainEntity();
				main.setCreatetime(new Date());
				main.setCustomerId(customerId);
				main.setUserId(userId);
				CustomerManager.saveCustomerMainEntity(context, main);
				info.setCustomerId(customerId);
				info.setCustomerKey("originate");
				info.setCustomerValue("manual");
				CustomerManager.saveCustomerInfoEntity(context, info);
			}
			info.setCustomerId(customerId);
			MapDto mapInfo = saveCustomerDto.getInfo();
			this.valiPara(mapInfo);
			for (Object obj : mapInfo.entrySet()) {
				// Entry<String, String> entryObjct = (Entry<String, String>)
				// obj;
				Entry<String, Object> entryObjct = (Entry<String, Object>) obj;
				key = entryObjct.getKey();
				value = entryObjct.getValue();
				if (value instanceof String) {
					info.setCustomerKey(key);
					info.setCustomerValue((String) value);
					info.exceptionInfo();
					CustomerManager.saveCustomerInfoEntity(context, info);
				} else if (value instanceof Integer) {
					info.setCustomerKey(key);
					info.setCustomerValue(value + "");
					info.exceptionInfo();
					CustomerManager.saveCustomerInfoEntity(context, info);
				} else if (value instanceof JSONArray) {
					if (key.equals("specialTag")) {
						for (Object string : ((JSONArray) value).toArray()) {
							if (string instanceof String) {
								specialTag.add((String) string);
							}
						}
					} else if (key.equals("customerTag")) {
						for (Object string : ((JSONArray) value).toArray()) {
							if (string instanceof String) {
								customerTag.add((String) string);
							}
						}
					}
				}

			}
			CustomerManager.saveTag(context, customerId, specialTag, customerTag);
			return customerId;
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public List<Entity> originateinfo(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			Iterator<Entity> list = CustomerManager.getOriginateInfo(context);
			return transformation(list);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		String customerId = null;
		try {
			customerId = context.getDomain(String.class);
			logger.info("加载客户:" + customerId + "的信息");
			BLContextUtil.valiParaItemStrNullOrEmpty(customerId, "客户id不能为空");
			return CustomerManager.loadCustomerInfo(context, customerId);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
