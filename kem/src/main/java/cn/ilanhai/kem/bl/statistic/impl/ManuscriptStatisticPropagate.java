package cn.ilanhai.kem.bl.statistic.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;


import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao;
import cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity.StatisticsType;
import cn.ilanhai.kem.domain.statistic.dto.CountSessionManuscriptVisitDto;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticPropagateDtoItem;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticPropagateDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticPropagateDtoResponse;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticPropagateDtoRequest.DateRange;
import cn.ilanhai.kem.domain.statistic.dto.QueryManuscriptStatisticDto;


/**
 * 数据传播数据逻辑
 * 
 * @author he
 *
 */
public class ManuscriptStatisticPropagate extends AbstractManuscriptStatistic {
	public ManuscriptStatisticPropagate(RequestContext ctx) {
		this.ctx = ctx;
	}

	// uv 统计
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
		// 检查会话是否存在
		Dao dao = app.getApplicationContext().getBean(ManuscriptVisitDao.class);
		if (dao == null) {
			this.logger.info("浏览数据访问错误");
			return;
		}
		CountSessionManuscriptVisitDto queryOneManuscriptVisitDto = null;
		queryOneManuscriptVisitDto = new CountSessionManuscriptVisitDto();
		queryOneManuscriptVisitDto.setSessionId(entity.getSessionId());
		dao.query(queryOneManuscriptVisitDto);
		// 是同一个sseion当作一个uv
		if (queryOneManuscriptVisitDto.getQuantity() > 1) {
			this.logger.info("此操作为同一个会话，不增加UV");
			return;
		}
		// 写入uv数据
		dao = app.getApplicationContext().getBean(
				ManuscriptDataStatisticsDao.class);
		if (dao == null) {
			this.logger.info("数据统计访问错误");
			return;
		}
		QueryOneManuscriptStatisticDto oneManuscriptStatisticDto = null;
		oneManuscriptStatisticDto = new QueryOneManuscriptStatisticDto();
		oneManuscriptStatisticDto.setVisitUrl(entity.getUrl());
		oneManuscriptStatisticDto.setAddDateTime(entity.getAddTime());
		oneManuscriptStatisticDto.setStatisticsType(StatisticsType.Uv);
		Entity e = dao.query(oneManuscriptStatisticDto, false);
		ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		if (e instanceof ManuscriptDataStatisticsEntity) {
			manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) e;
			manuscriptDataStatisticsEntity.setUpdateTime(new Date());
		}
		if (manuscriptDataStatisticsEntity == null) {
			manuscriptDataStatisticsEntity = new ManuscriptDataStatisticsEntity();
			manuscriptDataStatisticsEntity.setAddTime(entity.getAddTime());
			manuscriptDataStatisticsEntity.setStatisticsType(StatisticsType.Uv);
			manuscriptDataStatisticsEntity.setUpdateTime(entity.getAddTime());
			manuscriptDataStatisticsEntity.setVisitUrl(entity.getUrl());
		}
		manuscriptDataStatisticsEntity
				.setQuantity(manuscriptDataStatisticsEntity.getQuantity() + 1);

		if (dao.save(manuscriptDataStatisticsEntity) <= 0) {
			this.logger.info("保存UV数据失败");
			return;
		}

	}

	/**
	 * 跟据条件获取数据传播数据
	 * 
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public ManuscriptStatisticPropagateDtoResponse propagate(
			ManuscriptStatisticPropagateDtoRequest req) throws BlAppException,
			DaoAppException {
		// 验证参数
		this.valiPara(req);
		this.valiParaItemNumBetween(0, 3, req.getType(), "数据类型");
		this.valiParaItemStrNullOrEmpty(req.getVisitUrl(), "浏览url");
		this.valiParaItemNumBetween(0, 2, req.getDateRangeType(), "时间条件类型");
		if (req.getDateRange() == DateRange.Range) {
			if (req.getBeginDateTime() == null || req.getEndDateTime() == null)
				throw new BlAppException(-1, "开始时间或结束时间错误");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(req.getBeginDateTime());
			calendar.add(Calendar.MONTH, MONTH);
			calendar.set(Calendar.HOUR, HOUR);
			calendar.set(Calendar.MINUTE, MINUTE);
			calendar.set(Calendar.SECOND, SECOND);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			if (req.getEndDateTime().getTime() > calendar.getTime().getTime())
				throw new BlAppException(-1, String.format("结束时间不能大于%s",
						format.format(calendar.getTime())));
		}
		Dao dao = this.ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "数据统计数据访问");
		QueryManuscriptStatisticDto manuscriptStatisticDto = null;
		manuscriptStatisticDto = new QueryManuscriptStatisticDto();
		manuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		manuscriptStatisticDto.setStatisticsType(req.getStatisticsType());
		manuscriptStatisticDto.setBeginDateTime(req.getBeginDateTime());
		manuscriptStatisticDto.setEndDateTime(req.getEndDateTime());
		Iterator<Entity> iterator = dao.query((Entity) manuscriptStatisticDto);
		ManuscriptStatisticPropagateDtoResponse response = null;
		response = new ManuscriptStatisticPropagateDtoResponse();
		while (iterator.hasNext()) {
			ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
			ManuscriptStatisticPropagateDtoItem manuscriptStatisticPropagateDtoItem = null;
			manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) iterator
					.next();
			if (manuscriptDataStatisticsEntity == null)
				continue;
			manuscriptStatisticPropagateDtoItem = new ManuscriptStatisticPropagateDtoItem();
			manuscriptStatisticPropagateDtoItem
					.setDateTime(manuscriptDataStatisticsEntity.getAddTime());
			manuscriptStatisticPropagateDtoItem
					.setQuantity(manuscriptDataStatisticsEntity.getQuantity());
			;
			response.getData().add(manuscriptStatisticPropagateDtoItem);
		}
		return response;

	}

	private final int MONTH = 3;
	private final int HOUR = 23;
	private final int MINUTE = 59;
	private final int SECOND = 59;
	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticPropagate.class);
	/**
	 * 请求上下
	 */
	private RequestContext ctx = null;
}
