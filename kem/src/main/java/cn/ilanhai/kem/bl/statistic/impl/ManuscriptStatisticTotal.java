package cn.ilanhai.kem.bl.statistic.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity.StatisticsType;
import cn.ilanhai.kem.domain.statistic.ManuscriptStatisticDataEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticTotalDtoItem;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticTotalDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticTotalDtoResponse;
import cn.ilanhai.kem.domain.statistic.dto.QueryManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryTotalManuscriptStatisticDto;

/**
 * 数据总览数据逻辑
 * 
 * @author he
 *
 */
public class ManuscriptStatisticTotal extends AbstractManuscriptStatistic {
	public ManuscriptStatisticTotal(RequestContext ctx) {
		this.ctx = ctx;
	}

	// pv统计
	@Override
	public void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException {
		if (app == null || entity == null) {
			this.logger.info("上下文或数据错误");
			return;
		}
		if (Str.isNullOrEmpty(entity.getUrl())) {
			this.logger.info("url错误");
			return;
		}
		if (entity.getAddTime() == null) {
			this.logger.info("添加时间错误");
			return;
		}

		Dao dao = app.getApplicationContext().getBean(
				ManuscriptDataStatisticsDao.class);
		if (dao == null) {
			this.logger.info("数据统计访问错误");
			return;
		}
		QueryOneManuscriptStatisticDto oneManuscriptStatisticDto = null;
		oneManuscriptStatisticDto = new QueryOneManuscriptStatisticDto();
		oneManuscriptStatisticDto.setVisitUrl(entity.getUrl());
		oneManuscriptStatisticDto.setAddDateTime(entity.getAddTime());
		oneManuscriptStatisticDto.setStatisticsType(StatisticsType.Pv);
		Entity e = dao.query(oneManuscriptStatisticDto, false);
		ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		if (e instanceof ManuscriptDataStatisticsEntity) {
			manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) e;
			manuscriptDataStatisticsEntity.setUpdateTime(new Date());
		}
		if (manuscriptDataStatisticsEntity == null) {
			manuscriptDataStatisticsEntity = new ManuscriptDataStatisticsEntity();
			manuscriptDataStatisticsEntity.setAddTime(entity.getAddTime());
			manuscriptDataStatisticsEntity.setStatisticsType(StatisticsType.Pv);
			manuscriptDataStatisticsEntity.setUpdateTime(entity.getAddTime());
			manuscriptDataStatisticsEntity.setVisitUrl(entity.getUrl());
		}
		manuscriptDataStatisticsEntity
				.setQuantity(manuscriptDataStatisticsEntity.getQuantity() + 1);
		if (dao.save(manuscriptDataStatisticsEntity) <= 0) {
			this.logger.info("保存PV数据失败");
			return;
		}

	}

	/**
	 * 跟据条件获取数据总览数据
	 * 
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public ManuscriptStatisticTotalDtoResponse total(
			ManuscriptStatisticTotalDtoRequest req) throws BlAppException,
			DaoAppException {
		// 验证参数
		this.valiPara(req);
		this.valiParaItemNumBetween(0, 4, req.getType(), "数据类型");
		this.valiParaItemStrNullOrEmpty(req.getVisitUrl(), "浏览url");
		if (req.getStatisticsType() == StatisticsType.ResidenceTime)
			throw new BlAppException(-1, "不支持此类型数据统计");
		// 获取统计数量 pv uv share sharerate
		// sharerate
		if (req.getStatisticsType() == StatisticsType.ShareRate) {
			return shareRate(req);
		}
		// pv uv share
		Dao dao = this.ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "稿件数据统计");

		QueryTotalManuscriptStatisticDto totalManuscriptStatisticDto = null;
		totalManuscriptStatisticDto = new QueryTotalManuscriptStatisticDto();
		totalManuscriptStatisticDto.setStatisticsType(req.getStatisticsType());
		totalManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		Iterator<Entity> iterator = dao
				.query((Entity) totalManuscriptStatisticDto);
		// 获取最近2条数据
		QueryManuscriptStatisticDto manuscriptStatisticDto = null;
		manuscriptStatisticDto = new QueryManuscriptStatisticDto();
		manuscriptStatisticDto.setStatisticsType(req.getStatisticsType());
		manuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		manuscriptStatisticDto.setBeginDateTime(this.getYesterday());
		manuscriptStatisticDto.setEndDateTime(this.getToday());
		iterator = dao.query((Entity) manuscriptStatisticDto);
		// 返回值
		ManuscriptStatisticTotalDtoResponse manuscriptStatisticTotalDtoResponse = null;
		manuscriptStatisticTotalDtoResponse = new ManuscriptStatisticTotalDtoResponse();
		manuscriptStatisticTotalDtoResponse
				.setTotalQuantity(totalManuscriptStatisticDto
						.getTotalQuantity());
		List<ManuscriptStatisticTotalDtoItem> data = null;
		data = manuscriptStatisticTotalDtoResponse.getData();
		boolean isExistToday = false;
		boolean isExistYesterday = false;
		while (iterator.hasNext()) {
			ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
			manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) iterator
					.next();
			if (manuscriptDataStatisticsEntity == null)
				continue;
			if (manuscriptDataStatisticsEntity.isToday())
				isExistToday = true;
			if (manuscriptDataStatisticsEntity.isYesterday())
				isExistYesterday = true;
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem
					.setDateTime(manuscriptDataStatisticsEntity.getAddTime());
			manuscriptStatisticTotalDtoItem
					.setEndDateTime(manuscriptDataStatisticsEntity
							.getUpdateTime());
			manuscriptStatisticTotalDtoItem
					.setQuantity(manuscriptDataStatisticsEntity.getQuantity());
			data.add(manuscriptStatisticTotalDtoItem);

		}
		// 检查是否有今天数据
		if (!isExistToday) {
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem.setQuantity(0);
			manuscriptStatisticTotalDtoItem.setDateTime(new Date());
			manuscriptStatisticTotalDtoItem.setEndDateTime(new Date());
			data.add(0, manuscriptStatisticTotalDtoItem);
		}
		// 检查是否有昨天数据
		if (!isExistYesterday) {
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem.setQuantity(0);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			manuscriptStatisticTotalDtoItem.setDateTime(calendar.getTime());
			manuscriptStatisticTotalDtoItem.setEndDateTime(calendar.getTime());
			data.add(manuscriptStatisticTotalDtoItem);
		}
		return manuscriptStatisticTotalDtoResponse;
	}

	/**
	 * 分享率
	 * 
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	private ManuscriptStatisticTotalDtoResponse shareRate(
			ManuscriptStatisticTotalDtoRequest req) throws BlAppException,
			DaoAppException {
		Dao dao = this.ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "稿件数据统计");
		// pv total
		QueryTotalManuscriptStatisticDto pvTotalManuscriptStatisticDto = null;
		pvTotalManuscriptStatisticDto = new QueryTotalManuscriptStatisticDto();
		pvTotalManuscriptStatisticDto.setStatisticsType(StatisticsType.Pv);
		pvTotalManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		dao.query((Entity) pvTotalManuscriptStatisticDto);
		// share total
		QueryTotalManuscriptStatisticDto shareTotalManuscriptStatisticDto = null;
		shareTotalManuscriptStatisticDto = new QueryTotalManuscriptStatisticDto();
		shareTotalManuscriptStatisticDto
				.setStatisticsType(StatisticsType.Share);
		shareTotalManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		dao.query((Entity) shareTotalManuscriptStatisticDto);

		// pv limit 2
		QueryManuscriptStatisticDto pvManuscriptStatisticDto = null;
		pvManuscriptStatisticDto = new QueryManuscriptStatisticDto();
		pvManuscriptStatisticDto.setStatisticsType(StatisticsType.Pv);
		pvManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		pvManuscriptStatisticDto.setBeginDateTime(this.getYesterday());
		pvManuscriptStatisticDto.setEndDateTime(this.getToday());
		Iterator<Entity> pvIterator = dao
				.query((Entity) pvManuscriptStatisticDto);
		// share limit 2

		QueryManuscriptStatisticDto shareManuscriptStatisticDto = null;
		shareManuscriptStatisticDto = new QueryManuscriptStatisticDto();
		shareManuscriptStatisticDto.setStatisticsType(StatisticsType.Share);
		shareManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		shareManuscriptStatisticDto.setBeginDateTime(this.getYesterday());
		shareManuscriptStatisticDto.setEndDateTime(this.getToday());
		Iterator<Entity> shareIterator = dao
				.query((Entity) shareManuscriptStatisticDto);
		List<Entity> shareList = null;
		shareList = new ArrayList<Entity>();
		while (shareIterator.hasNext())
			shareList.add(shareIterator.next());
		// 返回值
		ManuscriptStatisticTotalDtoResponse manuscriptStatisticTotalDtoResponse = null;
		manuscriptStatisticTotalDtoResponse = new ManuscriptStatisticTotalDtoResponse();
		// 求总率
		double pvTotal = pvTotalManuscriptStatisticDto.getTotalQuantity();
		double shareTotal = shareTotalManuscriptStatisticDto.getTotalQuantity();
		double tempTotal = 0;
		manuscriptStatisticTotalDtoResponse.setTotalQuantity(0);

		if (pvTotal > 0) {
			tempTotal = shareTotal / pvTotal * 100;
			BigDecimal bigDecimal = new BigDecimal(tempTotal);
			tempTotal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			manuscriptStatisticTotalDtoResponse.setTotalQuantity(tempTotal);
		}
		List<ManuscriptStatisticTotalDtoItem> data = null;
		data = manuscriptStatisticTotalDtoResponse.getData();
		boolean isExistToday = false;
		boolean isExistYesterday = false;
		while (pvIterator.hasNext()) {
			ManuscriptDataStatisticsEntity pvManuscriptDataStatisticsEntity = null;
			ManuscriptDataStatisticsEntity shareManuscriptDataStatisticsEntity = null;
			pvManuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) pvIterator
					.next();
			if (pvManuscriptDataStatisticsEntity == null)
				continue;
			if (pvManuscriptDataStatisticsEntity.isToday()) {
				shareManuscriptDataStatisticsEntity = this
						.getManuscriptDataStatisticsEntity(
								shareList.iterator(), 1);
				if (shareManuscriptDataStatisticsEntity == null)
					continue;
				{
					isExistToday = true;
				}
			}
			if (pvManuscriptDataStatisticsEntity.isYesterday()) {
				shareManuscriptDataStatisticsEntity = this
						.getManuscriptDataStatisticsEntity(
								shareList.iterator(), 2);
				if (shareManuscriptDataStatisticsEntity == null)
					continue;
				else {
					isExistYesterday = true;
				}
			}
			double pvQuantity = pvManuscriptDataStatisticsEntity.getQuantity();
			double shareQuantity = shareManuscriptDataStatisticsEntity
					.getQuantity();
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem.setQuantity(0);
			if (pvQuantity > 0) {
				tempTotal = shareQuantity / pvQuantity * 100;
				BigDecimal bigDecimal = new BigDecimal(tempTotal);
				tempTotal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				manuscriptStatisticTotalDtoItem.setQuantity(tempTotal);
			}

			manuscriptStatisticTotalDtoItem
					.setDateTime(pvManuscriptDataStatisticsEntity.getAddTime());
			manuscriptStatisticTotalDtoItem
					.setEndDateTime(pvManuscriptDataStatisticsEntity
							.getUpdateTime());
			data.add(manuscriptStatisticTotalDtoItem);

		}
		// 检查是否有今天数据
		if (!isExistToday) {
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem.setQuantity(0);
			manuscriptStatisticTotalDtoItem.setDateTime(new Date());
			manuscriptStatisticTotalDtoItem.setEndDateTime(new Date());
			data.add(0, manuscriptStatisticTotalDtoItem);
		}
		// 检查是否有昨天数据
		if (!isExistYesterday) {
			ManuscriptStatisticTotalDtoItem manuscriptStatisticTotalDtoItem = null;
			manuscriptStatisticTotalDtoItem = new ManuscriptStatisticTotalDtoItem();
			manuscriptStatisticTotalDtoItem.setQuantity(0);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			manuscriptStatisticTotalDtoItem.setDateTime(calendar.getTime());
			manuscriptStatisticTotalDtoItem.setEndDateTime(calendar.getTime());
			data.add(manuscriptStatisticTotalDtoItem);
		}
		return manuscriptStatisticTotalDtoResponse;
	}

	/**
	 * 获取今天时间
	 * 
	 * @return
	 */
	private Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取今天昨天
	 * 
	 * @return
	 */
	private Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取分享数据
	 * 
	 * @param iterator
	 * @param type
	 *            1 今天 2昨天
	 * @return
	 */
	private ManuscriptDataStatisticsEntity getManuscriptDataStatisticsEntity(
			Iterator<Entity> iterator, int type) {

		if (iterator == null || !iterator.hasNext())
			return null;

		while (iterator.hasNext()) {

			ManuscriptDataStatisticsEntity shareManuscriptDataStatisticsEntity = null;
			shareManuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) iterator
					.next();
			if (shareManuscriptDataStatisticsEntity == null)
				continue;
			if (shareManuscriptDataStatisticsEntity.isToday() && type == 1)
				return shareManuscriptDataStatisticsEntity;
			if (shareManuscriptDataStatisticsEntity.isYesterday() && type == 2)
				return shareManuscriptDataStatisticsEntity;
		}
		return null;
	}

	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticTotal.class);
	/**
	 * 请求上下
	 */
	private RequestContext ctx = null;
}
