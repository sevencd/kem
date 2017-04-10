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
public class QueryManuscriptStatisticDto extends AbstractEntity {

	public QueryManuscriptStatisticDto() {

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
	
	public Date getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
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
	 * 开始时间 非必需 条件类型为3必需 (格式 yyyy-MM-dd 时间从00:00:00)
	 */
	private Date beginDateTime;
	/**
	 * 结束时间 非必需 条件类型为3必需 (格式 yyyy-MM-dd 时间从23:59:59)
	 */
	private Date endDateTime;

}
