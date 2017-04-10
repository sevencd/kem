package cn.ilanhai.kem.bl.plugin.formplugin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.user.trafficuser.TrafficuserManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.plugin.PluginDao;
import cn.ilanhai.kem.domain.Order;
import cn.ilanhai.kem.domain.PageResponse;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.TrafficuserType;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.plugin.PluginEntity;
import cn.ilanhai.kem.domain.plugin.QueryPlugin;
import cn.ilanhai.kem.domain.plugin.formplugin.FormPluginEntity;
import cn.ilanhai.kem.domain.plugin.formplugin.SearchFormPluginData;
import cn.ilanhai.kem.domain.plugin.formplugin.SearchFormPluginRequestData;
import cn.ilanhai.kem.domain.plugin.formplugin.SubmitFormPluginData;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;

/**
 * 表单插件实现
 * 
 * @author he
 *
 */
@Component("formplugin")
public class FormPluginImpl extends BaseBl implements FormPlugin {
	private final String EMAIL_REGULAR = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean submit(RequestContext context) throws BlAppException, DaoAppException {
		SubmitFormPluginData dto = null;
		FormPluginEntity formPluginEntity = null;
		CodeTable ct;
		Dao dao = null;
		Dao pluginDao = null;
		PluginEntity pluginEntity = null;
		QueryPlugin queryPlugin = null;
		int relationType = -1;
		try {
			dto = context.getDomain(SubmitFormPluginData.class);
			this.valiPara(dto);
			this.valiParaItemStrNullOrEmpty(dto.getRelationId(), "关联编号");
			relationType = this.getRelationType(dto.getRelationId());
			this.valiParaItemNumBetween(1, 3, relationType, "关联类型");
			this.valiParaItemStrNullOrEmpty(dto.getName(), "名称");
			this.valiParaItemStrLength(dto.getName(), 20, "名称");
			this.valiParaItemStrNullOrEmpty(dto.getPhone(), "电话号");
			if (!ExpressionMatchUtil.isPhoneNo(dto.getPhone())) {
				throw new BlAppException(CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_REGULAR.getValue(), "电话号");
			}
			this.valiParaItemStrNullOrEmpty(dto.getEmail(), "电子邮件");
			this.valiParaItemStrRegular(EMAIL_REGULAR, dto.getEmail(), "电子邮件");
			pluginDao = this.daoProxyFactory.getDao(context, PluginDao.class);
			this.valiDaoIsNull(pluginDao, "用户插件信息");

			queryPlugin = new QueryPlugin();
			queryPlugin.setRelationId(dto.getRelationId());
			queryPlugin.setRelationType(ManuscriptType.getEnum(relationType));
			queryPlugin.setPluginType(PluginType.FORMPLUGIN);
			queryPlugin.setIsUsed(true);
			pluginEntity = (PluginEntity) pluginDao.query(queryPlugin, false);
			this.valiFormPulginIsNull(pluginEntity);
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "表单数据");
			formPluginEntity = new FormPluginEntity();
			formPluginEntity.setAddTime(new Date());
			formPluginEntity.setEmail(dto.getEmail());
			formPluginEntity.setName(dto.getName());
			formPluginEntity.setPhone(dto.getPhone());
			formPluginEntity.setUpdateTime(new Date());
			formPluginEntity.setPluginId(pluginEntity.getPluginId());
			int val = -1;
			val = dao.save(formPluginEntity);
			if (val <= 0)
				this.valiSaveDomain(val, "表单数据");

			// 保存用户信息
			TrafficuserEntity trafficuserEntity = new TrafficuserEntity();
			trafficuserEntity.setExtensionId(dto.getRelationId());
			trafficuserEntity.setName(dto.getName());
			trafficuserEntity.setPhoneNo(dto.getPhone());
			trafficuserEntity.setEmail(dto.getEmail());
			trafficuserEntity.setCreatetime(new Date());
			trafficuserEntity.setTrafficuserType(TrafficuserType.FORMPLUGINUSER);
			TrafficuserManager.saveTrafficuser(context, trafficuserEntity);
			// 构建信息
			Map<String, String> infos = buildInfos(context, dto);
			// 创建客户
			CustomerManager.saveCustomer(context, infos, pluginEntity.getUserId(),
					ManuscriptManager.getTag(context, dto.getRelationId(), ManuscriptParameterType.tag), null);
			return true;
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private Map<String, String> buildInfos(RequestContext context, SubmitFormPluginData dto)
			throws DaoAppException, BlAppException {
		Map<String, String> infos = new HashMap<String, String>();
		infos.put(CustomerInfoEntity.KEY_ORIGINATE, "0");
		ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
				dto.getRelationId(), ManuscriptParameterType.manuscriptName);
		String extensionName = manuscriptParameterEntity == null ? null : manuscriptParameterEntity.getParameter();
		infos.put(CustomerInfoEntity.KEY_EXTENSIONNAME, extensionName);
		infos.put(CustomerInfoEntity.KEY_EMAIL, dto.getEmail());
		infos.put(CustomerInfoEntity.KEY_NAME, dto.getName());
		infos.put(CustomerInfoEntity.KEY_PHONE, dto.getPhone());
		return infos;
	}

	private int getRelationType(String relationId) {
		// 关联类型 关联类型：1 模板 2 专题 3 推广 值必顺是其中之一
		String tmp = null;
		if (relationId == null || relationId.length() <= 0)
			return -1;
		tmp = KeyFactory.getKeyHeadByKey(relationId);
		if (tmp == null || tmp.length() <= 0)
			return -1;
		if (tmp.equals(KeyFactory.KEY_TEMPLATE))
			return 1;
		else if (tmp.equals(KeyFactory.KEY_SPECIAL))
			return 2;
		else if (tmp.equals(KeyFactory.KEY_EXTENSION))
			return 3;
		else
			return -1;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		SearchFormPluginRequestData dto = null;
		SearchFormPluginData dto1 = null;
		CodeTable ct;
		Dao dao = null;
		PageResponse pageResponse = null;
		Iterator<Entity> ls = null;
		Dao pluginDao = null;
		PluginEntity pluginEntity = null;
		QueryPlugin queryPlugin = null;
		int relationType = -1;
		try {
			dto = context.getDomain(SearchFormPluginRequestData.class);
			this.valiPara(dto);
			this.valiParaItemStrNullOrEmpty(dto.getRelationId(), "关联编号");
			relationType = this.getRelationType(dto.getRelationId());
			this.valiParaItemNumBetween(1, 3, relationType, "关联类型");

			this.valiParaItemNumLassThan(0, dto.getStartCount(), "开始记录数");
			this.valiParaItemNumBetween(1, 1000, dto.getPageSize(), "页大小记录数");
			dto.setOrder(Order.DESC);
			String om = null;
			om = dto.getOrderMode();
			if (om != null && om.length() > 0) {
				om = om.toUpperCase();
				this.valiParaItemEnumNotExists(Order.class, om, "排序");
				dto.setOrder(Enum.valueOf(Order.class, om));
			}
			pluginDao = this.daoProxyFactory.getDao(context, PluginDao.class);
			this.valiDaoIsNull(pluginDao, "用户插件信息");
			queryPlugin = new QueryPlugin();
			queryPlugin.setRelationId(dto.getRelationId());
			queryPlugin.setRelationType(ManuscriptType.getEnum(relationType));
			queryPlugin.setPluginType(PluginType.FORMPLUGIN);
			pluginEntity = (PluginEntity) pluginDao.query(queryPlugin, false);
			this.valiDomainIsNull(pluginEntity, "用户插件信息");
			dao = this.daoProxyFactory.getDao(context);
			this.valiDaoIsNull(dao, "表单数据");
			dto1 = new SearchFormPluginData();
			dto1.setPageSize(dto.getPageSize());
			dto1.setStartCount(dto.getStartCount());
			dto1.setWord(dto.getWord());
			dto1.setPluginId(pluginEntity.getPluginId());
			ls = dao.query(dto1);
			pageResponse = new PageResponse();
			pageResponse.setList(ls);
			pageResponse.setPageSize(dto1.getPageSize());
			pageResponse.setStartCount(dto1.getStartCount());
			pageResponse.setTotalCount(dto1.getRecordCount());
			return pageResponse;

		} catch (BlAppException e) {
			throw e;
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
	private String saveCustomer(RequestContext context, Map<String, String> infos, String userId, List<String> tags,
			String customerId) throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(customerId)) {
			customerId = KeyFactory.newKey(KeyFactory.KEY_CUSTOMER);
			CustomerMainEntity main = new CustomerMainEntity();
			main.setCreatetime(new Date());
			main.setCustomerId(customerId);
			main.setUserId(userId);
			CustomerManager.saveCustomerMainEntity(context, main);
		}
		CustomerInfoEntity info = new CustomerInfoEntity();
		info.setCustomerId(customerId);
		for (Entry<String, String> map : infos.entrySet()) {
			// 信息
			info.setCustomerKey(map.getKey());
			info.setCustomerValue(map.getValue());
			CustomerManager.saveCustomerInfoEntity(context, info);
		}
		CustomerManager.saveTag(context, customerId, tags, null);
		return customerId;
	}
}
