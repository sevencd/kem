package cn.ilanhai.kem.bl.tag.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.tag.SysTag;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.tag.MyBatisSysTagDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.tag.DeleteTagRequestEntity;
import cn.ilanhai.kem.domain.tag.OrderDataEntity;
import cn.ilanhai.kem.domain.tag.OrderTagRequestEntity;
import cn.ilanhai.kem.domain.tag.ResponseTagEntity;
import cn.ilanhai.kem.domain.tag.SearchTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SearchTagResponceEntity;
import cn.ilanhai.kem.domain.tag.SetSelectionTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SysTagEntity;

@Component("tag")
public class SysTagImpl extends BaseBl implements SysTag {
	private static String desc = "标签";

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public SearchTagResponceEntity search(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取入参
			SearchTagRequestEntity request = context.getDomain(SearchTagRequestEntity.class);
			valiPara(request);
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				request.setUserId("0");
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				request.setUserId(this.getSessionUserId(context));
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,MyBatisSysTagDao.class);
			valiDaoIsNull(dao, desc);
			// 查询结果
			Iterator<Entity> resultDatas = dao.query(request);
			CountDto count = (CountDto) dao.query(request, false);
			// 返回结果
			SearchTagResponceEntity result = new SearchTagResponceEntity();
			List<Entity> datas = transformation(resultDatas);
			// 数据集
			result.setList(datas);
			result.setPageSize(request.getPageSize());
			result.setStartCount(request.getStartCount());
			result.setTotalCount(count.getCount());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取入参
			DeleteTagRequestEntity request = context.getDomain(DeleteTagRequestEntity.class);
			valiPara(request);
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,MyBatisSysTagDao.class);
			valiDaoIsNull(dao, desc);
			// 删除
			int deleteCount = dao.delete(request);
			valiDeleteDomain(deleteCount, desc);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void setselection(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取入参
			SysTagEntity request = context.getDomain(SysTagEntity.class);
			valiPara(request);
			valiParaItemBooleanNull(request.getIsSelection(), desc);
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,MyBatisSysTagDao.class);
			valiDaoIsNull(dao, desc);
			SetSelectionTagRequestEntity queryRequest = new SetSelectionTagRequestEntity();
			queryRequest.setTagId(request.getTagId());
			SysTagEntity sysTagEntity = (SysTagEntity) dao.query(queryRequest, true);
			if (sysTagEntity == null) {
				String tmp = null;
				ct = CodeTable.BL_TAG_UPDATEId_ERROR;
				tmp = ct.getDesc();
				tmp = String.format(tmp, desc);
				throw new BlAppException(ct.getValue(), tmp);
			}
			sysTagEntity.setIsSelection(request.getIsSelection());
			// 更新数据
			int deleteCount = dao.save(sysTagEntity);
			valiUpdateDomain(deleteCount, desc);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void add(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取入参
			SysTagEntity request = context.getDomain(SysTagEntity.class);
			valiPara(request);
			// 设置userId的值
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				request.setUserId("0");
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				request.setUserId(this.getSessionUserId(context));
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			// 校验tagName
			// StringVerifyUtil.tagNameVerify(request.getTagName());
			this.transformationBackTag(request.getTagName());

			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,MyBatisSysTagDao.class);
			valiDaoIsNull(dao, desc);

			ResponseTagEntity responseTagEntity = (ResponseTagEntity) dao.query(request, true);
			if (responseTagEntity != null) {
				ct = CodeTable.BL_TAG_ADDEXISTS_ERROR;
				String tmp = ct.getDesc();
				tmp = String.format(tmp, desc);
				throw new BlAppException(ct.getValue(), tmp);
			}
			request.setIsSelection(false);
			request.setOrderNum(0);
			request.setQuoteNum(0);
			request.setCreatetime(new Date());
			// 报错数据
			int deleteCount = dao.save(request);
			valiUpdateDomain(deleteCount, desc);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void order(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取入参
			OrderTagRequestEntity request = context.getDomain(OrderTagRequestEntity.class);
			valiPara(request);
			List<OrderDataEntity> data = request.getData();
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context,MyBatisSysTagDao.class);
			valiDaoIsNull(dao, desc);
			SetSelectionTagRequestEntity queryRequest;
			SysTagEntity sysTagEntity;
			// 更新数据
			for (OrderDataEntity orderDataEntity : data) {
				queryRequest = new SetSelectionTagRequestEntity();
				queryRequest.setTagId(orderDataEntity.getTageId());
				sysTagEntity = (SysTagEntity) dao.query(queryRequest, true);
				sysTagEntity.setOrderNum(orderDataEntity.getOrderNum());
				dao.save(sysTagEntity);
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 标签集合排序
	 * 
	 * @param data
	 */
	// private void orderList(List<OrderDataEntity> data) {
	// Collections.sort(data, new Comparator<OrderDataEntity>() {
	// @Override
	// public int compare(OrderDataEntity o1, OrderDataEntity o2) {
	// return o2.getOrderNum() - o1.getOrderNum();
	// }
	// });
	// }
}
