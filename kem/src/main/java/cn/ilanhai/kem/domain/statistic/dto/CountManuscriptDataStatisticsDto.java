package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;

/**
 * 数据统计条数据
 * 
 * @author he
 *
 */
public class CountManuscriptDataStatisticsDto extends AbstractEntity {
	public CountManuscriptDataStatisticsDto() {

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

	public long getCountQuantity() {
		return countQuantity;
	}

	public void setCountQuantity(long countQuantity) {
		this.countQuantity = countQuantity;
	}

	public String getVisitUrl() {
		return visitUrl;
	}
	
	public ManuscriptDataStatisticsEntity.StatisticsType getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(
			ManuscriptDataStatisticsEntity.StatisticsType statisticsType) {
		this.statisticsType = statisticsType;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	private static final long serialVersionUID = 1L;
	private int minValue;
	private int maxValue;
	private long countQuantity;
	/**
	 * 类型
	 */
	private ManuscriptDataStatisticsEntity.StatisticsType statisticsType;
	/**
	 * 浏览url
	 */
	private String visitUrl;

}
