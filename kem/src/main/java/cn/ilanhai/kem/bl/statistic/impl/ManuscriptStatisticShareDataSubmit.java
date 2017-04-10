package cn.ilanhai.kem.bl.statistic.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.dao.statistic.ManuscriptChannelStatisticsDao;
import cn.ilanhai.kem.dao.statistic.ManuscriptDataStatisticsDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptChannelStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity.StatisticsType;
import cn.ilanhai.kem.domain.statistic.dto.ManuscriptStatisticShareDataSubmitDtoRequest;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptChannelStatisticsDto;
import cn.ilanhai.kem.domain.statistic.dto.QueryOneManuscriptStatisticDto;

public class ManuscriptStatisticShareDataSubmit extends
		AbstractManuscriptStatistic {
	public ManuscriptStatisticShareDataSubmit(RequestContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException {

	}

	/**
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean shareDataSubmit(
			ManuscriptStatisticShareDataSubmitDtoRequest req)
			throws BlAppException, DaoAppException {
		this.valiPara(req);
		String url = req.getVisitUrl();
		if (Str.isNullOrEmpty(url)) {
			url = this.ctx.getQueryString("Referer");
			if (Str.isNullOrEmpty(url))
				throw new BlAppException(-1, "浏览url地址错误");
		}
		if (url.equalsIgnoreCase("none"))
			throw new BlAppException(-1, "浏览url地址错误");
		this.valiParaItemNumBetween(0, 3, req.getChannel(), "渠道类型");

		String id = ManuscriptVisitEntity.getManuscriptId1(url);
		if (Str.isNullOrEmpty(id))
			throw new BlAppException(-1, "从url获取稿件编号错误");
		req.setVisitUrl(id);
		Dao dao = ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptDataStatisticsDao.class);
		this.valiDaoIsNull(dao, "数据统计对象");
		// 写入分享统计数据
		QueryOneManuscriptStatisticDto oneManuscriptStatisticDto = null;
		oneManuscriptStatisticDto = new QueryOneManuscriptStatisticDto();
		oneManuscriptStatisticDto.setVisitUrl(req.getVisitUrl());
		oneManuscriptStatisticDto.setAddDateTime(new Date());
		oneManuscriptStatisticDto.setStatisticsType(StatisticsType.Share);
		Entity e = dao.query(oneManuscriptStatisticDto, false);
		ManuscriptDataStatisticsEntity manuscriptDataStatisticsEntity = null;
		if (e instanceof ManuscriptDataStatisticsEntity) {
			manuscriptDataStatisticsEntity = (ManuscriptDataStatisticsEntity) e;
			manuscriptDataStatisticsEntity.setUpdateTime(new Date());
		}
		if (manuscriptDataStatisticsEntity == null) {
			manuscriptDataStatisticsEntity = new ManuscriptDataStatisticsEntity();
			manuscriptDataStatisticsEntity.setAddTime(oneManuscriptStatisticDto
					.getAddDateTime());
			manuscriptDataStatisticsEntity
					.setStatisticsType(StatisticsType.Share);
			manuscriptDataStatisticsEntity
					.setUpdateTime(oneManuscriptStatisticDto.getAddDateTime());
			manuscriptDataStatisticsEntity.setVisitUrl(req.getVisitUrl());
		}
		manuscriptDataStatisticsEntity
				.setQuantity(manuscriptDataStatisticsEntity.getQuantity() + 1);
		if (dao.save(manuscriptDataStatisticsEntity) <= 0)
			throw new BlAppException(-1, "保存分享统计数据出错");
		// 写入渠道数据
		dao = ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptChannelStatisticsDao.class);
		this.valiDaoIsNull(dao, "渠道统计对象");
		QueryOneManuscriptChannelStatisticsDto oneManuscriptChannelStatisticsDto = null;
		oneManuscriptChannelStatisticsDto = new QueryOneManuscriptChannelStatisticsDto();
		oneManuscriptChannelStatisticsDto
				.setAddDateTime(oneManuscriptStatisticDto.getAddDateTime());
		oneManuscriptChannelStatisticsDto.setChannelType(req.getChannelType());
		oneManuscriptChannelStatisticsDto.setVisitUrl(req.getVisitUrl());
		e = dao.query(oneManuscriptChannelStatisticsDto, false);
		ManuscriptChannelStatisticsEntity manuscriptChannelStatisticsEntity = null;
		if (e instanceof ManuscriptChannelStatisticsEntity) {
			manuscriptChannelStatisticsEntity = (ManuscriptChannelStatisticsEntity) e;
			manuscriptChannelStatisticsEntity.setUpdateTime(new Date());
		}
		if (manuscriptChannelStatisticsEntity == null) {
			manuscriptChannelStatisticsEntity = new ManuscriptChannelStatisticsEntity();
			manuscriptChannelStatisticsEntity
					.setAddTime(oneManuscriptStatisticDto.getAddDateTime());
			manuscriptChannelStatisticsEntity.setChannelType(req
					.getChannelType());
			manuscriptChannelStatisticsEntity
					.setUpdateTime(oneManuscriptStatisticDto.getAddDateTime());

			manuscriptChannelStatisticsEntity.setVisitUrl(req.getVisitUrl());

		}
		manuscriptChannelStatisticsEntity
				.setQuantity(manuscriptChannelStatisticsEntity.getQuantity() + 1);
		if (dao.save(manuscriptChannelStatisticsEntity) <= 0)
			throw new BlAppException(-1, "保存渠道统计数据出错");
		return true;
	}

	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticShareDataSubmit.class);
	/**
	 * 请求上下
	 */
	private RequestContext ctx = null;
}
