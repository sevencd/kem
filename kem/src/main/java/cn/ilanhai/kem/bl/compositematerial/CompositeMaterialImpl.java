package cn.ilanhai.kem.bl.compositematerial;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.compositematerial.CompositeMaterialDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.Order;
import cn.ilanhai.kem.domain.PageResponse;
import cn.ilanhai.kem.domain.compositematerial.CMCategory;
import cn.ilanhai.kem.domain.compositematerial.CMInfoResponse;
import cn.ilanhai.kem.domain.compositematerial.CMSaveRequest;
import cn.ilanhai.kem.domain.compositematerial.CMState;
import cn.ilanhai.kem.domain.compositematerial.CMlistItemResponse;
import cn.ilanhai.kem.domain.compositematerial.CompositeMaterialEntity;
import cn.ilanhai.kem.domain.compositematerial.QueryCMData;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("compositeMaterial")
public class CompositeMaterialImpl extends BaseBl implements CompositeMaterial {
	
	private Logger logger = Logger.getLogger(CompositeMaterialImpl.class);

	protected String getValue(RequestContext context, String key) {
		Map<String, Object> settings = null;
		if (key == null || key.length() <= 0)
			return null;
		settings = context.getApplication().getConfigure().getSettings();
		if (settings == null)
			return null;
		if (!settings.containsKey(key))
			return null;
		return (String) settings.get(key);
	}

	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public boolean save(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		CMSaveRequest cmSaveRequest = null;
		String userId = "";
		try {
			
			userId = this.getSessionUserId(context);
			cmSaveRequest = context.getDomain(CMSaveRequest.class);
			logger.info("请求为："+cmSaveRequest);
			if (!this.save(userId, context, cmSaveRequest))
				return false;
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private boolean save(String userId, RequestContext ctx,
			CMSaveRequest cmSaveRequest) throws BlAppException, DaoAppException {
		Dao dao = null;
		CompositeMaterialEntity compositeMaterialEntity = null;
		String host = null;
		try {
			host = this.getValue(ctx, "serviceName");
			if (host == null || host.length() <= 0)
				throw new BlAppException(-1, "资源host地址错误");
			if (Str.isNullOrEmpty(userId))
				throw new BlAppException(-1, "用户编号错误");
			this.valiPara(cmSaveRequest);
			this.valiParaItemStrNullOrEmpty(cmSaveRequest.getIconUrl(), "图标地址");
			this.valiParaItemStrNullOrEmpty(cmSaveRequest.getData(), "数据");
			this.valiParaItemStrNullOrEmpty(cmSaveRequest.getType(), "自定义类型");
			if (cmSaveRequest.getClientType() == null)
				throw new BlAppException(-1, "客户端类型错误");
			this.valiParaItemNumBetween(1, 2, cmSaveRequest.getClientType(),
					"客户端类型");
			dao = this.daoProxyFactory.getDao(ctx,CompositeMaterialDao.class);
			this.valiDaoIsNull(dao, "组合素材");
			compositeMaterialEntity = new CompositeMaterialEntity();
			if (!Str.isNullOrEmpty(cmSaveRequest.getGroupId())) {
				compositeMaterialEntity.setId(cmSaveRequest.getGroupId());
			}else {
				compositeMaterialEntity.setId(KeyFactory.newKey(KeyFactory.KEY_COMPOSITEMATERIAL));
			}
			compositeMaterialEntity.setAddTime(new Date());
			compositeMaterialEntity.setUpdateTime(new Date());
			compositeMaterialEntity.setCategory(CMCategory.Sys);
			compositeMaterialEntity.setData(cmSaveRequest.getData());
			compositeMaterialEntity.setIconUrl(cmSaveRequest.getIconUrl()
					.replace(host, "").trim());
			compositeMaterialEntity.setState(CMState.New);
			compositeMaterialEntity.setUserId(userId);
			compositeMaterialEntity.setType(cmSaveRequest.getType());
			compositeMaterialEntity
					.setClientType(cmSaveRequest.getClientType());
			if (dao.save(compositeMaterialEntity) <= 0)
				return false;
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public PageResponse list(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		String userId = "";
		QueryCMData queryCMData = null;
		try {
			//this.checkFrontUserLogined(context);
			//userId = this.getSessionUserId(context);
			queryCMData = context.getDomain(QueryCMData.class);
			return this.list(userId, context, queryCMData);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private PageResponse list(String userId, RequestContext ctx,
			QueryCMData queryCMData) throws BlAppException, DaoAppException {
		Dao dao = null;
		CMlistItemResponse cMlistItemResponse = null;
		Iterator<Entity> queryData = null;
		PageResponse pageResponse = null;
		ArrayList<Entity> notifyDataResponses = null;
		CompositeMaterialEntity compositeMaterialEntity = null;
		String host = null;
		try {  
			host = this.getValue(ctx, "serviceName");
			logger.info("serviceName:"+host);
			if (host == null || host.length() <= 0)
				throw new BlAppException(-1, "资源host地址错误");
			//if (Str.isNullOrEmpty(userId))
				//throw new BlAppException(-1, "用户编号错误");
			this.valiPara(queryCMData);
			//queryCMData.setUserId(userId);
			//this.valiParaItemNumLassThan(0, queryCMData.getUserId(), "用户编号");
			this.valiParaItemNumLassThan(0, queryCMData.getStartCount(),
					"开始记录数");
			this.valiParaItemNumBetween(1, 1000, queryCMData.getPageSize(),
					"页大小记录数");
			queryCMData.setOrder(Order.DESC);
			String om = null;
			om = queryCMData.getOrderMode();
			if (om != null && om.length() > 0) {
				om = om.toUpperCase();
				this.valiParaItemEnumNotExists(Order.class, om, "排序");
				queryCMData.setOrder(Enum.valueOf(Order.class, om));
			}
			if (queryCMData.getClientType() != null)
				this.valiParaItemNumBetween(1, 2, queryCMData.getClientType(),
						"客户端类型");
			dao = this.daoProxyFactory.getDao(ctx,CompositeMaterialDao.class);
			this.valiDaoIsNull(dao, "组合素材");
			pageResponse = new PageResponse();
			pageResponse.setList(null);
			queryData = dao.query(queryCMData);
			if (queryData != null) {
				notifyDataResponses = new ArrayList<Entity>();
				while (queryData.hasNext()) {
					compositeMaterialEntity = (CompositeMaterialEntity) queryData
							.next();
					if (compositeMaterialEntity == null)
						continue;
					cMlistItemResponse = new CMlistItemResponse();
					String path = compositeMaterialEntity.getIconUrl();
					if (!path.contains("://")) {
						path = host + path;
					}
					logger.info("path:"+path);
					cMlistItemResponse.setIconUrl(path);
					cMlistItemResponse.setId(compositeMaterialEntity.getId());
					cMlistItemResponse.setType(compositeMaterialEntity
							.getType());
					cMlistItemResponse.setClientType(compositeMaterialEntity
							.getClientType());
					notifyDataResponses.add(cMlistItemResponse);
				}
				pageResponse.setList(notifyDataResponses.iterator());
			}
			pageResponse.setPageSize(queryCMData.getPageSize());
			pageResponse.setStartCount(queryCMData.getStartCount());
			pageResponse.setTotalCount(queryCMData.getRecordCount());
			return pageResponse;

		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity info(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		String id = new String();
		try {
			//this.checkFrontUserLogined(context);
			id = context.getDomain(String.class);
			return this.info(id, context);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private Entity info(String id, RequestContext ctx) throws BlAppException,
			DaoAppException {
		Dao dao = null;
		CMInfoResponse cmInfoResponse = null;
		CompositeMaterialEntity compositeMaterialEntity = null;
		IdDto idDto = null;
		String host = null;
		try {
			host = this.getValue(ctx, "serviceName");
			if (host == null || host.length() <= 0)
				throw new BlAppException(-1, "资源host地址错误");
			if (Str.isNullOrEmpty(id))
				throw new BlAppException(-1, "编号错误");
			dao = this.daoProxyFactory.getDao(ctx,CompositeMaterialDao.class);
			this.valiDaoIsNull(dao, "组合素材");
			IdEntity<String> idName = new IdEntity<String>();
			idName.setId(id);
			compositeMaterialEntity = (CompositeMaterialEntity) dao.query(
					idName, false);
			this.valiDomainIsNull(compositeMaterialEntity, "组合素材");
			cmInfoResponse = new CMInfoResponse();
			cmInfoResponse.setId(compositeMaterialEntity.getId());
			cmInfoResponse.setIconUrl(host
					+ compositeMaterialEntity.getIconUrl());
			cmInfoResponse.setData(compositeMaterialEntity.getData());
			cmInfoResponse.setType(compositeMaterialEntity.getType());
			cmInfoResponse.setClientType(compositeMaterialEntity
					.getClientType());
			return cmInfoResponse;

		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

}
