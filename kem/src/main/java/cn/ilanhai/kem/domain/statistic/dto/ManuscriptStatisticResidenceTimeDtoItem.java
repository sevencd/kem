package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 停留时间条目
 * 
 * @author he
 *
 */
public class ManuscriptStatisticResidenceTimeDtoItem extends AbstractEntity {
	public ManuscriptStatisticResidenceTimeDtoItem() {

	}

	public int getRangeBoundaryMinValue() {
		return rangeBoundaryMinValue;
	}

	public void setRangeBoundaryMinValue(int rangeBoundaryMinValue) {
		this.rangeBoundaryMinValue = rangeBoundaryMinValue;
	}

	public int getRangeBoundaryMaxValue() {
		return rangeBoundaryMaxValue;
	}

	public void setRangeBoundaryMaxValue(int rangeBoundaryMaxValue) {
		this.rangeBoundaryMaxValue = rangeBoundaryMaxValue;
	}

	public int getUvQuantity() {
		return uvQuantity;
	}

	public void setUvQuantity(int uvQuantity) {
		this.uvQuantity = uvQuantity;
	}

	public double getRateResidenceTimeBoundary() {
		return rateResidenceTimeBoundary;
	}

	public void setRateResidenceTimeBoundary(double rateResidenceTimeBoundary) {
		this.rateResidenceTimeBoundary = rateResidenceTimeBoundary;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 停留时间范围边界最小值
	 */
	private int rangeBoundaryMinValue;
	/**
	 * 停留时间范围边界最大值(当小于0时表示无穷大)
	 */
	private int rangeBoundaryMaxValue;
	/**
	 * 停留时间范围内uv数
	 */
	private int uvQuantity;
	/**
	 * 停留时间范围uv数量与总pv比率(两位小数) 总pv/停留时间范围内 uv数
	 */
	private double rateResidenceTimeBoundary;

}
