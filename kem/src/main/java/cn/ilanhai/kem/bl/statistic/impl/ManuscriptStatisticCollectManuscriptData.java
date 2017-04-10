package cn.ilanhai.kem.bl.statistic.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;

import cn.ilanhai.kem.dao.statistic.ManuscriptVisitDao;

import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.ManuscriptStatisticMsg;

public class ManuscriptStatisticCollectManuscriptData extends
		AbstractManuscriptStatistic {
	public ManuscriptStatisticCollectManuscriptData(RequestContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException {

	}

	public boolean collectmanuscriptdata() throws BlAppException,
			DaoAppException {
		logger.info("收集数据");
		String url = this.ctx.getQueryString("Referer");
		String ip = this.ctx.getQueryString("RemoteAddr");
		String requestTime = this.ctx.getQueryString("RequestTime");
		String sessionId = this.ctx.getSession().getSessionId();
		logger.info("url:" + url);
		logger.info("sessionId:" + sessionId);
		logger.info("requestTime:" + requestTime);
		logger.info("ip:" + ip);
		if (Str.isNullOrEmpty(url))
			throw new BlAppException(-1, "浏览url错误");
		if (Str.isNullOrEmpty(sessionId))
			throw new BlAppException(-1, "浏览会话错误");
		if (Str.isNullOrEmpty(requestTime))
			throw new BlAppException(-1, "浏览时间错误");
		if (Str.isNullOrEmpty(ip))
			throw new BlAppException(-1, "浏览ip错误");
		String id = ManuscriptVisitEntity.getManuscriptId1(url);
		if (Str.isNullOrEmpty(id))
			throw new BlAppException(-1, "从url获取稿件编号错误");
		ManuscriptVisitEntity manuscriptVisitEntity = null;
		manuscriptVisitEntity = new ManuscriptVisitEntity();
		manuscriptVisitEntity.setAddTime(new Date());
		manuscriptVisitEntity.setIp(ip);
		manuscriptVisitEntity.setSessionId(sessionId);
		manuscriptVisitEntity.setUa("");
		manuscriptVisitEntity.setUrl(id);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			manuscriptVisitEntity.setVisitTime(format.parse(requestTime));
		} catch (ParseException e) {
			throw new BlAppException(-1, e.getMessage());
		}
		Dao dao = this.ctx.getApplication().getApplicationContext()
				.getBean(ManuscriptVisitDao.class);
		this.valiDaoIsNull(dao, "浏览稿件数据访问对象");
		int val = dao.save(manuscriptVisitEntity);
		this.valiSaveDomain(val, "浏览稿件数据");
		// 异步计算数据
		ManuscriptStatisticMsg<ManuscriptVisitEntity> msg = null;
		msg = new ManuscriptStatisticMsg<ManuscriptVisitEntity>();
		msg.setMsgContent(manuscriptVisitEntity);
		QueueManager.getInstance().put(msg);
		return true;
	}

	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticCollectManuscriptData.class);
	/**
	 * 请求上下
	 */
	private RequestContext ctx = null;
}