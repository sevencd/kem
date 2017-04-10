package cn.ilanhai.kem.bl.statistic.impl;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.statistic.iparea.IpAreaEntity;
import cn.ilanhai.kem.bl.statistic.iparea.IpToArea;
import cn.ilanhai.kem.bl.statistic.iparea.impl.BaiduIPUtilImpl;
import cn.ilanhai.kem.bl.statistic.iparea.impl.TaobaoIPUtilImpl;
import cn.ilanhai.kem.dao.statistic.ManuscriptAreaStatisticsDao;
import cn.ilanhai.kem.domain.statistic.ManuscriptAreaEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;

public class ManuscriptStatisticAreaDistribution extends
		AbstractManuscriptStatistic {
	public ManuscriptStatisticAreaDistribution(RequestContext ctx) {
		this.ctx = ctx;
	}

	private final Logger logger = Logger
			.getLogger(ManuscriptStatisticAreaDistribution.class);

	@Override
	public void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemObjectNull(app, "全局上下文");
		logger.info("生成区域信息:[" + entity + "]");
		String ip = entity.getIp();
		// 根据ip获取地域名称
//		IpToArea ipToArea = new BaiduIPUtilImpl();
		logger.info("根据ip[" + ip + "] 获取区域信息");
		IpToArea ipToArea = new TaobaoIPUtilImpl();
		IpAreaEntity ipAreaEntity = ipToArea.getAreaEntity(ip);
		// 获取请求url 的稿件id
		String urlid = entity.getUrl();
		logger.info("稿件id:[" + urlid + "]");
		BLContextUtil.valiParaItemStrNullOrEmpty(urlid, "访问路径不是推广访问", false);
		logger.info("保存地域实体");
		int val = getDao(app).save(
				buildSaveEntity(ipAreaEntity.getProvince(),
						ipAreaEntity.getCity(), urlid, entity));
		BLContextUtil.valiSaveDomain(val, "地域统计");
	}

	/**
	 * 封装保存实体
	 * 
	 * @param areaName
	 * @param url
	 * @return
	 */
	private ManuscriptAreaEntity buildSaveEntity(String areaName,
			String areaCity, String urlid, ManuscriptVisitEntity entity) {
		ManuscriptAreaEntity manuscriptAreaEntity = new ManuscriptAreaEntity();
		manuscriptAreaEntity.setAreaName(areaName);
		manuscriptAreaEntity.setAreaCity(areaCity);
		manuscriptAreaEntity.setVisitUrl(urlid);
		manuscriptAreaEntity.setAreaAddTime(entity.getAddTime());
		manuscriptAreaEntity.setAreaUpdateTime(entity.getVisitTime());
		manuscriptAreaEntity.setAreaQuantity(1);
		return manuscriptAreaEntity;
	}

	/**
	 * 请求上下
	 */
	private RequestContext ctx = null;

	private Dao getDao(App app) {
		return app.getApplicationContext().getBean(
				ManuscriptAreaStatisticsDao.class);
	}
}
