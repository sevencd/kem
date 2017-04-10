package cn.ilanhai.kem.bl.smsright;

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
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.smsright.SearchSmsDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.smsright.dto.DeleteSmsDto;
import cn.ilanhai.kem.domain.smsright.dto.QuerySmsDto;
import cn.ilanhai.kem.domain.smsright.dto.QuerySmsResponseDto;
import cn.ilanhai.kem.domain.smsright.dto.SearchSmsDto;
import cn.ilanhai.kem.domain.smsright.dto.SmsLoadDtoRequest;
import cn.ilanhai.kem.domain.smsright.dto.SmsSendDtoRequest;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("sms")
public class SmsRightImpl extends BaseBl implements SmsRight {
	private Logger logger = Logger.getLogger(SmsRightImpl.class);

	@Override
	public Entity load(RequestContext ctx) throws BlAppException,
			DaoAppException {
		SmsLoadDtoRequest request = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(ctx);
			String tmp = ctx.getDomain(String.class);
			request = new SmsLoadDtoRequest();
			request.setSmsId(tmp);
			return SmsRightManager.load(ctx, request);
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
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity send(RequestContext ctx) throws BlAppException,
			DaoAppException {
		SmsSendDtoRequest request = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(ctx);
			request = ctx.getDomain(SmsSendDtoRequest.class);
			String userId = null;
			userId = this.getSessionUserId(ctx);
			return SmsRightManager.send(ctx, request, userId);
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
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public Entity search(RequestContext context) throws BlAppException,
			DaoAppException {

		SearchSmsDto request;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			request = context.getDomain(SearchSmsDto.class);
			valiPara(request);
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(),
					"开始数量不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(),
					"查询数量不能为空");
			Dao dao = BLContextUtil.getDao(context, SearchSmsDao.class);

			QuerySmsDto query = new QuerySmsDto();
			query.setKeyword(request.getKeyword());
			query.setPageSize(request.getPageSize());
			query.setStartCount(request.getStartCount());
			query.setUserId(getSessionUserId(context));
			QuerySmsResponseDto queryContactsResponseDto = new QuerySmsResponseDto();
			queryContactsResponseDto.setList(dao.query(query));
			queryContactsResponseDto.setPageSize(request.getPageSize());
			queryContactsResponseDto.setStartCount(request.getStartCount());
			queryContactsResponseDto.setTotalCount(((CountDto) dao.query(query,
					false)).getCount());
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
	public void delete(RequestContext context) throws BlAppException,
			DaoAppException {
		DeleteSmsDto request;
		CodeTable ct;
		try {
			logger.info("进入删除");
			this.checkFrontUserLogined(context);
			request = context.getDomain(DeleteSmsDto.class);
			valiPara(request);
			request.setUserId(getSessionUserId(context));
			logger.info("删除:" + request);
			Dao dao = BLContextUtil.getDao(context, SearchSmsDao.class);
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
}
