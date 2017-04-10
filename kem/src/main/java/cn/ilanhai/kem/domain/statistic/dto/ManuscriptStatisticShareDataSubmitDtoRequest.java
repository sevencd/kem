package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptChannelStatisticsEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;

/**
 * 数据总览数据,客户端请求数据
 * 
 * @author he
 *
 */
public class ManuscriptStatisticShareDataSubmitDtoRequest extends
		AbstractEntity {
	public ManuscriptStatisticShareDataSubmitDtoRequest() {

	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public ManuscriptChannelStatisticsEntity.ChannelType getChannelType() {
		return ManuscriptChannelStatisticsEntity.ChannelType
				.valueOf(this.channel);

	}

	private static final long serialVersionUID = 1L;
	/**
	 * 渠道 0qq 1qq空间 2微信 3微博
	 */
	private int channel;
	/**
	 * 浏览url
	 */
	private String visitUrl;

}
