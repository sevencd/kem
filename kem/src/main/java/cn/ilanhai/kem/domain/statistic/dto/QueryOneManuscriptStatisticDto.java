package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;

/**
 * 查询统计dto
 * 
 * @author he
 *
 */
public class QueryOneManuscriptStatisticDto extends AbstractEntity {

	public QueryOneManuscriptStatisticDto() {

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

	public Date getAddDateTime() {
		return addDateTime;
	}

	public void setAddDateTime(Date addDateTime) {
		this.addDateTime = addDateTime;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 浏览url
	 */
	private String visitUrl;
	/**
	 * 类型
	 */
	private ManuscriptDataStatisticsEntity.StatisticsType statisticsType;

	/**
	 * 添加时间
	 */
	private Date addDateTime;

}
