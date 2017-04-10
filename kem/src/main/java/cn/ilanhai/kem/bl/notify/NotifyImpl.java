package cn.ilanhai.kem.bl.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
import cn.ilanhai.kem.dao.notify.NotifyDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.Order;
import cn.ilanhai.kem.domain.PageResponse;
import cn.ilanhai.kem.domain.notify.NotifyEntity;
import cn.ilanhai.kem.domain.notify.NotifyType;
import cn.ilanhai.kem.domain.notify.QueryUserNotifyData;
import cn.ilanhai.kem.domain.notify.QueryUserNotifyDataResponse;
import cn.ilanhai.kem.domain.notify.UserNotifyCount;
import cn.ilanhai.kem.domain.notify.UserNotifyCountResponse;

@Component("notify")
public class NotifyImpl extends BaseBl implements Notify {

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity count(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		String userId = "";
		try {
			this.checkFrontUserLogined(context);
			userId = this.getSessionUserId(context);
			return this.count(context, userId);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public PageResponse search(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		String userId = "";
		QueryUserNotifyData userNotifyData = null;
		try {
			this.checkFrontUserLogined(context);
			userId = this.getSessionUserId(context);
			userNotifyData = context.getDomain(QueryUserNotifyData.class);
			this.valiPara(userNotifyData);
			userNotifyData.setTarget(userId);
			return this.search(context, userNotifyData);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean status(RequestContext context) throws BlAppException,
			DaoAppException {
		CodeTable ct;
		int id = -1;
		try {
			this.checkFrontUserLogined(context);
			id = context.getDomain(Integer.class);
			return this.status(context, id);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private Entity count(RequestContext ctx, String userId) throws BlAppException,
			DaoAppException {
		Dao dao = null;
		UserNotifyCount userNotifyCount = null;
		UserNotifyCountResponse userNotifyCountResponse = null;
		try {
			if (Str.isNullOrEmpty(userId))
				throw new BlAppException(-1, "用户编号错误");
			dao = this.daoProxyFactory.getDao(ctx,NotifyDao.class);
			this.valiDaoIsNull(dao, " 通知");
			userNotifyCount = new UserNotifyCount();
			userNotifyCount.setTarget(userId);
			dao.query(userNotifyCount, false);
			userNotifyCountResponse = new UserNotifyCountResponse();
			userNotifyCountResponse.setCount(userNotifyCount.getCount());
			userNotifyCountResponse
					.setReadCount(userNotifyCount.getReadCount());
			if (userNotifyCountResponse.getCount() == null)
				userNotifyCountResponse.setCount(0);
			if (userNotifyCountResponse.getReadCount() == null)
				userNotifyCountResponse.setReadCount(0);
			return userNotifyCountResponse;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private PageResponse search(RequestContext ctx,
			QueryUserNotifyData userNotifyData) throws BlAppException,
			DaoAppException {
		Dao dao = null;
		QueryUserNotifyDataResponse userNotifyDataResponse = null;
		Iterator<Entity> queryData = null;
		PageResponse pageResponse = null;
		ArrayList<Entity> notifyDataResponses = null;
		NotifyEntity notifyEntity = null;
		try {
			this.valiPara(userNotifyData);
			this.valiParaItemStrNullOrEmpty(userNotifyData.getTarget(), "用户编号不能为空", false);
			this.valiParaItemNumLassThan(0, userNotifyData.getStartCount(),
					"开始记录数");
			this.valiParaItemNumBetween(1, 1000, userNotifyData.getPageSize(),
					"页大小记录数");
			String om = null;
			userNotifyData.setOrder(Order.DESC);
			om = userNotifyData.getOrderMode();
			if (om != null && om.length() > 0) {
				om = om.toUpperCase();
				this.valiParaItemEnumNotExists(Order.class, om, "排序");
				userNotifyData.setOrder(Enum.valueOf(Order.class, om));
			}
			dao = this.daoProxyFactory.getDao(ctx,NotifyDao.class);
			this.valiDaoIsNull(dao, "通知");
			pageResponse = new PageResponse();
			pageResponse.setList(null);
			queryData = dao.query(userNotifyData);
			if (queryData != null) {
				notifyDataResponses = new ArrayList<Entity>();
				while (queryData.hasNext()) {
					notifyEntity = (NotifyEntity) queryData.next();
					if (notifyEntity == null)
						continue;
					userNotifyDataResponse = new QueryUserNotifyDataResponse();
					userNotifyDataResponse
							.setAddTime(notifyEntity.getAddTime());
					userNotifyDataResponse
							.setContent(notifyEntity.getContent());
					userNotifyDataResponse.setId(notifyEntity.getId());
					userNotifyDataResponse.setNotifyType(notifyEntity
							.getNotifyType().getval());
					userNotifyDataResponse.setRead(notifyEntity.getRead());
					userNotifyDataResponse.setReadTime(notifyEntity
							.getReadTime());
					userNotifyDataResponse.setUpdateTime(notifyEntity
							.getUpdateTime());
					notifyDataResponses.add(userNotifyDataResponse);
				}
				pageResponse.setList(notifyDataResponses.iterator());
			}
			pageResponse.setPageSize(userNotifyData.getPageSize());
			pageResponse.setStartCount(userNotifyData.getStartCount());
			pageResponse.setTotalCount(userNotifyData.getRecordCount());
			return pageResponse;

		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private boolean status(RequestContext ctx, int id) throws BlAppException,
			DaoAppException {
		Dao dao = null;
		NotifyEntity notifyEntity = null;
		IdDto dto = null;
		Entity entity = null;
		try {
			if (id <= 0)
				throw new BlAppException(-1, "编号错误");
			dao = this.daoProxyFactory.getDao(ctx,NotifyDao.class);
			this.valiDaoIsNull(dao, "通知");
			dto = new IdDto();
			dto.setId(id);
			entity = dao.query(dto, false);
			this.valiDomainIsNull(entity, "通知");
			notifyEntity = (NotifyEntity) entity;
			if (notifyEntity.getRead())
				throw new BlAppException(-1, "已经是阅读后的状态");
			notifyEntity.setRead(true);
			int val = -1;
			val = dao.save(notifyEntity);
			this.valiSaveDomain(val, "通知");
			return true;

		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean create(RequestContext ctx, String source, String traget,
			String content, NotifyType notifyType) throws BlAppException,
			DaoAppException {
		NotifyEntity entity = null;
		entity = new NotifyEntity();
		entity.setContent(content);
		entity.setTarget(traget);
		entity.setSource(source);
		entity.setNotifyType(notifyType);
		return this.create(ctx, entity);
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean create(RequestContext ctx, NotifyEntity notifyEntity)
			throws BlAppException, DaoAppException {
		Dao dao = null;
		try {
			if (ctx == null || notifyEntity == null)
				return false;
			if (notifyEntity.getNotifyType() == null)
				return false;
			if (notifyEntity.getSource() == null)
				return false;
			if (notifyEntity.getTarget() == null)
				return false;
			dao = this.daoProxyFactory.getDao(ctx, NotifyDao.class);
			this.valiDaoIsNull(dao, "通知");
			notifyEntity.setUpdateTime(new Date());
			notifyEntity.setAddTime(new Date());
			notifyEntity.setReadTime(new Date());
			notifyEntity.setRead(false);
			if (dao.save(notifyEntity) <= 0)
				return false;
			return true;

		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			throw e;
		}
	}
}
