package cn.ilanhai.kem.bl.statistic.impl;

import java.util.Date;
import java.util.Iterator;

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
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.statistic.ManuscriptStatistic;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.statistic.ManuscriptAreaStatisticsDao;
import cn.ilanhai.kem.dao.statistic.ManuscriptStatisticDao;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptStatisticDataEntity;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptAreaDto;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticPropagateDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticResidenceTimeDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticShareDataSubmitDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticTotalDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.QueryStatsticAreaDto;
import cn.ilanhai.kem.domain.statistic.dto.SearchResultStatsticAreaDto;
import cn.ilanhai.kem.domain.statistic.dto.SearchStatsticAreaDto;
import cn.ilanhai.kem.util.TimeUtil;

@Component("statistic")
public class ManuscriptStatisticImpl extends BaseBl implements ManuscriptStatistic {

	@Override
	public Entity total(RequestContext context) throws BlAppException, DaoAppException {
		ManuscriptStatisticTotalDtoRequest request = null;
		ManuscriptStatisticTotal total = null;
		CodeTable ct;
		try {
			request = context.getDomain(ManuscriptStatisticTotalDtoRequest.class);
			total = new ManuscriptStatisticTotal(context);
			return total.total(request);
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
	public Entity propagate(RequestContext context) throws BlAppException, DaoAppException {
		ManuscriptStatisticPropagateDtoRequest req = null;
		ManuscriptStatisticPropagate propagate = null;
		CodeTable ct;
		try {
			req = context.getDomain(ManuscriptStatisticPropagateDtoRequest.class);
			propagate = new ManuscriptStatisticPropagate(context);
			return propagate.propagate(req);
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
	public Entity residencetime(RequestContext context) throws BlAppException, DaoAppException {

		ManuscriptStatisticResidenceTimeDtoRequest req = null;
		ManuscriptStatisticResidenceTime residenceTime = null;
		CodeTable ct;
		try {
			String tmp = context.getDomain(String.class);
			req = new ManuscriptStatisticResidenceTimeDtoRequest();
			req.setVisitUrl(tmp);
			residenceTime = new ManuscriptStatisticResidenceTime(context);
			return residenceTime.residencetime(req);
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
	public boolean addresidencetime(RequestContext context) throws BlAppException, DaoAppException {
		ManuscriptStatisticResidenceTime residenceTime = null;
		CodeTable ct;
		try {

			residenceTime = new ManuscriptStatisticResidenceTime(context);
			return residenceTime.addresidencetime();
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
	public Entity sharelevel(RequestContext context) throws BlAppException, DaoAppException {

		return null;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity areadistribution(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("查询区域分布");
		CodeTable ct;
		try {

			this.checkFrontUserLogined(context);

			SearchStatsticAreaDto searchStatsticAreaDto = context.getDomain(SearchStatsticAreaDto.class);
			this.valiDomainIsNull(searchStatsticAreaDto, "区域分布查询");
			this.valiParaItemStrNullOrEmpty(searchStatsticAreaDto.getManuscriptId(), "稿件编号");
			valiParaNotNull(searchStatsticAreaDto.getStartCount(), "开始条数");
			valiParaNotNull(searchStatsticAreaDto.getPageSize(), "查询条数");

			// 验证是否有该稿件权限
			ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context,
					searchStatsticAreaDto.getManuscriptId());
			this.valiDomainIsNull(manuscriptEntity, "稿件编号错误", false);
			this.checkCurrentUser(context, manuscriptEntity.getUserId());
			// 封装查询实体
			QueryStatsticAreaDto queryStatsticAreaDto = new QueryStatsticAreaDto();
			queryStatsticAreaDto.setVisitUrl(searchStatsticAreaDto.getManuscriptId());
			queryStatsticAreaDto.setStartCount(searchStatsticAreaDto.getStartCount());
			queryStatsticAreaDto.setPageSize(searchStatsticAreaDto.getPageSize());
			Dao dao = BLContextUtil.getDao(context, ManuscriptAreaStatisticsDao.class);
			// 区域查询结果
			Iterator<Entity> lists = dao.query(queryStatsticAreaDto);
			// 封装返回数据
			SearchResultStatsticAreaDto searchResultStatsticAreaDto = new SearchResultStatsticAreaDto();
			searchResultStatsticAreaDto.setManuscriptId(searchStatsticAreaDto.getManuscriptId());
			searchResultStatsticAreaDto.setStartCount(searchStatsticAreaDto.getStartCount());
			searchResultStatsticAreaDto.setPageSize(searchStatsticAreaDto.getPageSize());
			searchResultStatsticAreaDto
					.setMaxQuantity(((ManuscriptAreaDto) dao.query(queryStatsticAreaDto, false)).getAreaQuantity());
			searchResultStatsticAreaDto.setList(lists);
			return searchResultStatsticAreaDto;
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
	public Entity arearanking(RequestContext context) throws BlAppException, DaoAppException {

		return null;
	}

	@Override
	public Entity channelanalysis(RequestContext context) throws BlAppException, DaoAppException {

		return null;
	}

	@Override
	public boolean sharedatasubmit(RequestContext context) throws BlAppException, DaoAppException {

		ManuscriptStatisticShareDataSubmitDtoRequest req = null;
		ManuscriptStatisticShareDataSubmit shareDataSubmit = null;
		CodeTable ct;
		try {
			req = context.getDomain(ManuscriptStatisticShareDataSubmitDtoRequest.class);
			shareDataSubmit = new ManuscriptStatisticShareDataSubmit(context);
			return shareDataSubmit.shareDataSubmit(req);
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
	public boolean collectmanuscriptdata(RequestContext context) throws BlAppException, DaoAppException {
		ManuscriptStatisticCollectManuscriptData manuscriptStatisticCollectManuscriptData = null;
		CodeTable ct;
		try {
			manuscriptStatisticCollectManuscriptData = new ManuscriptStatisticCollectManuscriptData(context);
			return manuscriptStatisticCollectManuscriptData.collectmanuscriptdata();
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

	private final Logger logger = Logger.getLogger(ManuscriptStatisticImpl.class);

}
