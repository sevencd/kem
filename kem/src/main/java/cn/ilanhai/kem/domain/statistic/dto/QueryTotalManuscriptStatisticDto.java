package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;

/**
 * 查询统计
 * 
 * @author he
 *
 */
public class QueryTotalManuscriptStatisticDto extends AbstractEntity {

	public QueryTotalManuscriptStatisticDto() {

	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public ManuscriptDataStatisticsEntity.StatisticsType getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(
			ManuscriptDataStatisticsEntity.StatisticsType statisticsType) {
		this.statisticsType = statisticsType;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 统计数量
	 */
	private long totalQuantity;
	/**
	 * 浏览url
	 */
	private String visitUrl;
	/**
	 * 类型
	 */
	private ManuscriptDataStatisticsEntity.StatisticsType statisticsType;
}
