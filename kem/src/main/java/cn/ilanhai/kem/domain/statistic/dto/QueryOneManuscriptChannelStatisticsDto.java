package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptChannelStatisticsEntity;

/**
 * 查询统计dto
 * 
 * @author he
 *
 */
public class QueryOneManuscriptChannelStatisticsDto extends AbstractEntity {

	public QueryOneManuscriptChannelStatisticsDto() {

	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public ManuscriptChannelStatisticsEntity.ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(
			ManuscriptChannelStatisticsEntity.ChannelType channelType) {
		this.channelType = channelType;
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
	private ManuscriptChannelStatisticsEntity.ChannelType channelType;

	/**
	 * 添加时间
	 */
	private Date addDateTime;

}
