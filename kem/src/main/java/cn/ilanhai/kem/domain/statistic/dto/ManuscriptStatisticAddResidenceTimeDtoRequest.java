package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Calendar;
import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptChannelStatisticsEntity;

/**
 * 数据传播数据,客户端请求
 * 
 * @author he
 *
 */
public class ManuscriptStatisticAddResidenceTimeDtoRequest extends
		AbstractEntity {
	public ManuscriptStatisticAddResidenceTimeDtoRequest() {

	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int second;
	/**
	 * 浏览url
	 */
	private String visitUrl;

}
