package cn.ilanhai.kem.bl.statistic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.Null;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.And;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.user.frontuser.Frontuser;
import cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao;
import cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptStatisticDataEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity.StatisticsType;
import cn.ilanhai.kem.domain.statistic.dto.CountManuscriptDataStatisticsDto;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticResidenceTimeDtoItem;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticResidenceTimeDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticResidenceTimeDtoResponse;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptVisitDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryTotalManuscriptStatisticDto;

public class ManuscriptStatisticResidenceTime extends
		AbstractManuscriptStatistic {
	public ManuscriptStatisticResidenceTime(RequestContext ctx) {
		this.ctx = ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.ilanhai.kem.bl.statistic.impl.AbstractManuscriptStatistic#generateData
	 * (cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity)
	 */

	@Override
	public void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException {
		// if (app == null || entity == null)
		// return;
		// // ResidenceTime
		// if (Str.isNullOrEmpty(entity.getUrl()))
		// return;
		// if (entity.getAddTime() == null)
		// return;
		// Dao dao = app.getApplicationContext().getBean(
		// ManuscriptDataStatisticsDao.class);
		// if (dao == null)
		// return;
		// QueryOneManuscriptStatisticDto oneManuscriptStatisticDto = null;
		// oneManuscriptStatisticDto = new QueryOneManuscriptStatisticDto();
		// oneManuscriptStatisticDto.setVisitUrl(entity.getUrl());
		// oneManuscriptStatisticDto.setAddDateTime(entity.getAddTime());
		// oneManuscriptStatisticDto
		// .setStatisticsType(StatisticsType.ResidenceTime);
		// Entity e = dao.query(oneManuscriptStatisticDto, false);
		// ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		// if (e instanceof ManuscriptStatisticDataEntity) {
		// manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) e;
		// manuscriptDataStatisticsEntity.setUpdateTime(new Date());
		// }
		// if (manuscriptDataStatisticsEntity == null) {
		// manuscriptDataStatisticsEntity = new
		// ManuscriptDataStatisticsEntity();
		// manuscriptDataStatisticsEntity.setAddTime(entity.getAddTime());
		// manuscriptDataStatisticsEntity
		// .setStatisticsType(StatisticsType.ResidenceTime);
		// manuscriptDataStatisticsEntity.setUpdateTime(entity.getAddTime());
		// manuscriptDataStatisticsEntity.setVisitUrl(entity.getUrl());
		// }
		// manuscriptDataStatisticsEntity
		// .setQuantity(manuscriptDataStatisticsEntity.getQuantity() + 1);
		// if (dao.save(manuscriptDataStatisticsEntity) <= 0)
		// return;
	}

	/**
	 * 页面离开回调
	 * 
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean addresidencetime() throws BlAppException, DaoAppException {
		if (this.ctx == null)
			return false;
		Date date = new Date();
		String sessionId = this.ctx.getSession().getSessionId();
		if (Str.isNullOrEmpty(sessionId))
			return false;
		Dao dao = ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptVisitDao.class);
		this.valiDaoIsNull(dao, "浏览数据访问对象");
		QueryOneManuscriptVisitDto queryOneManuscriptVisitDto = null;
		queryOneManuscriptVisitDto = new QueryOneManuscriptVisitDto();
		queryOneManuscriptVisitDto.setSessionId(sessionId);
		Entity e = dao.query(queryOneManuscriptVisitDto, false);
		ManuscriptVisitEntity manuscriptVisitEntity = null;
		if (e instanceof ManuscriptVisitEntity)
			manuscriptVisitEntity = (ManuscriptVisitEntity) e;
		if (manuscriptVisitEntity == null)
			return false;
		dao = ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "统计数据访问对象");
		ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		manuscriptDataStatisticsEntity = new ManuscriptDataStatisticsEntity();
		manuscriptDataStatisticsEntity.setAddTime(date);
		manuscriptDataStatisticsEntity
				.setStatisticsType(StatisticsType.ResidenceTime);
		manuscriptDataStatisticsEntity.setUpdateTime(new Date());
		manuscriptDataStatisticsEntity.setVisitUrl(manuscriptVisitEntity
				.getUrl());
		Long tmp = manuscriptDataStatisticsEntity.getAddTime().getTime()
				- manuscriptVisitEntity.getVisitTime().getTime();
		manuscriptDataStatisticsEntity.setQuantity(tmp.intValue());
		int val = dao.save(manuscriptDataStatisticsEntity);
		this.valiSaveDomain(val, "数据统计");
		return true;

	}

	/**
	 * 停留时间
	 * 
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public ManuscriptStatisticResidenceTimeDtoResponse residencetime(
			ManuscriptStatisticResidenceTimeDtoRequest req)
			throws BlAppException, DaoAppException {
		this.valiPara(req);
		this.valiParaItemStrNullOrEmpty(req.getVisitUrl(), "浏览url");
		Dao dao = this.ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "数据统计访问对象");
		List<Segment> segments = this.buildSegment();
		if (segments == null || segments.size() <= 0)
			throw new BlAppException(-1, "生成数据段出错");
		double pv = this.getTotal(req.getVisitUrl(), StatisticsType.Pv);
		double uv = this.getTotal(req.getVisitUrl(), StatisticsType.Uv);
		double uvResidencetime = this.getTotal(req.getVisitUrl(),
				StatisticsType.ResidenceTime);
		ManuscriptStatisticResidenceTimeDtoResponse response = null;
		response = new ManuscriptStatisticResidenceTimeDtoResponse();

		response.setTotalResidenceTime(new Double(uvResidencetime).intValue());
		response.setTotalUvQuantity(new Double(uv).intValue());
		response.setAverageResidenceTime(0);
		if (uvResidencetime > 0)
			response.setAverageResidenceTime(uv / uvResidencetime);
		CountManuscriptDataStatisticsDto countManuscriptDataStatisticsDto = null;
		countManuscriptDataStatisticsDto = new CountManuscriptDataStatisticsDto();
		countManuscriptDataStatisticsDto.setVisitUrl(req.getVisitUrl());
		ManuscriptStatisticResidenceTimeDtoItem residenceTimeDtoItem = null;
		double tmp = 0;
		for (Segment segment : segments) {
			int min = segment.getMinValue() * 1000;
			countManuscriptDataStatisticsDto.setMinValue(min);
			int max = segment.getMaxValue() <= 0 ? Integer.MAX_VALUE : segment
					.getMaxValue() * 1000;
			countManuscriptDataStatisticsDto.setMaxValue(max);
			countManuscriptDataStatisticsDto
					.setStatisticsType(StatisticsType.ResidenceTime);
			dao.query(countManuscriptDataStatisticsDto);

			tmp = countManuscriptDataStatisticsDto.getCountQuantity();
			residenceTimeDtoItem = new ManuscriptStatisticResidenceTimeDtoItem();
			residenceTimeDtoItem
					.setRangeBoundaryMinValue(segment.getMinValue());
			residenceTimeDtoItem
					.setRangeBoundaryMaxValue(segment.getMaxValue());
			residenceTimeDtoItem.setUvQuantity(new Double(tmp).intValue());
			residenceTimeDtoItem.setRateResidenceTimeBoundary(0);
			if (tmp > 0) {
				double tmpVal = pv / tmp * 100;
				BigDecimal bigDecimal = new BigDecimal(tmpVal);
				tmpVal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				residenceTimeDtoItem.setRateResidenceTimeBoundary(tmpVal);
			}
			response.getData().add(residenceTimeDtoItem);
		}

		return response;
	}

	/**
	 * @param visitUrl
	 * @param statisticsType
	 * @return
	 * @throws DaoAppException
	 */
	private long getTotal(String visitUrl,
			ManuscriptDataStatisticsEntity.StatisticsType statisticsType)
			throws DaoAppException {
		if (Str.isNullOrEmpty(visitUrl))
			return 0;
		if (statisticsType == null)
			return 0;
		Dao dao = ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		if (dao == null)
			return 0;
		QueryTotalManuscriptStatisticDto totalManuscriptStatisticDto = null;
		totalManuscriptStatisticDto = new QueryTotalManuscriptStatisticDto();
		totalManuscriptStatisticDto.setStatisticsType(statisticsType);
		totalManuscriptStatisticDto.setVisitUrl(visitUrl);
		dao.query((Entity) totalManuscriptStatisticDto);
		return totalManuscriptStatisticDto.getTotalQuantity();
	}

	/**
	 * 生成段
	 * 
	 * @return
	 */
	private List<Segment> buildSegment() {
		ArrayList<Segment> segments = null;
		Segment segment = null;
		segments = new ArrayList<ManuscriptStatisticResidenceTime.Segment>();
		int value = SEGMENT_INIT_VALUE;
		for (int i = 1; i <= SEGMENT_COUNT; i++) {
			segment = new Segment();
			if (i == 1) {
				segment.setMinValue(value);
			} else {
				segment.setMinValue(value + 1);
			}
			value = value + SEGMENT_SETP;
			if (i == SEGMENT_COUNT) {
				segment.setMaxValue(-1);
			} else {

				segment.setMaxValue(value);
			}

			segments.add(segment);
		}
		return segments;
	}

	/**
	 * 段数量
	 */
	private final int SEGMENT_COUNT = 10;
	/**
	 * 段陟长
	 */
	private final int SEGMENT_SETP = 10;
	/**
	 * 段初始值
	 */
	private final int SEGMENT_INIT_VALUE = 0;
	/**
	 * 日志
	 */
	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticResidenceTime.class);
	/**
	 * 上下文
	 */
	private RequestContext ctx = null;

	/**
	 * 段
	 * 
	 * @author he
	 *
	 */
	public class Segment {
		public Segment() {

		}

		public int getMinValue() {
			return minValue;
		}

		public void setMinValue(int minValue) {
			this.minValue = minValue;
		}

		public int getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}

		/**
		 * 最小值
		 */
		private int minValue;
		/**
		 * 最大值
		 */
		private int maxValue;
	}

}
