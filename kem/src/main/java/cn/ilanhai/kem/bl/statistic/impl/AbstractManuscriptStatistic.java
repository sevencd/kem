package cn.ilanhai.kem.bl.statistic.impl;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;

/**
 * 抽象稿件统计
 * 
 * @author he
 *
 */
public abstract class AbstractManuscriptStatistic extends BaseBl {

	protected AbstractManuscriptStatistic() {

	}

	/**
	 * 生成
	 * 
	 * @param entity
	 */
	public void generate(App app, AbstractEntity entity) throws BlAppException,
			DaoAppException {
		ManuscriptVisitEntity manuscriptVisitEntity = null;
		if (!(entity instanceof ManuscriptVisitEntity))
			return;
		manuscriptVisitEntity = (ManuscriptVisitEntity) entity;
		this.generateData(app, manuscriptVisitEntity);
	}

	/**
	 * 生成数据
	 * 
	 * @param entity
	 */
	public abstract void generateData(App app, ManuscriptVisitEntity entity)
			throws BlAppException, DaoAppException;

	/**
	 * 数据生成工厂
	 * 
	 * @param entity
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static void generateFactory(App app, AbstractEntity entity)
			throws BlAppException, DaoAppException {
		areaDistribution(app, entity);
		total(app, entity);
		propagate(app, entity);
	}

	/**
	 * 区域数据生成
	 * 
	 * @param ctx
	 * @param entity
	 */
	private static void areaDistribution(App app, AbstractEntity entity) {
		AbstractManuscriptStatistic manuscriptStatistic = null;
		try {
			manuscriptStatistic = new ManuscriptStatisticAreaDistribution(null);
			manuscriptStatistic.generate(app, entity);
		} catch (BlAppException e) {
			e.printStackTrace();
		} catch (DaoAppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * pv数据生成
	 * 
	 * @param ctx
	 * @param entity
	 */
	private static void total(App app, AbstractEntity entity) {
		ManuscriptStatisticTotal manuscriptStatistic = null;
		try {
			manuscriptStatistic = new ManuscriptStatisticTotal(null);
			manuscriptStatistic.generate(app, entity);
		} catch (BlAppException e) {
			e.printStackTrace();
		} catch (DaoAppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * uv数据生成
	 * 
	 * @param entity
	 */
	private static void propagate(App app, AbstractEntity entity) {
		ManuscriptStatisticPropagate manuscriptStatistic = null;
		try {
			manuscriptStatistic = new ManuscriptStatisticPropagate(null);
			manuscriptStatistic.generate(app, entity);
		} catch (BlAppException e) {
			e.printStackTrace();
		} catch (DaoAppException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
