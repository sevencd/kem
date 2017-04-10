package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity;

/**
 * 数据总览数据,客户端请求数据
 * 
 * @author he
 *
 */
public class ManuscriptStatisticTotalDtoRequest extends AbstractEntity {
	public ManuscriptStatisticTotalDtoRequest() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public ManuscriptDataStatisticsEntity.StatisticsType getStatisticsType() {
		return ManuscriptDataStatisticsEntity.StatisticsType.valueOf(this.type);
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 数据类型 必需 0表示pv 1表示uv 2表示分享 3表示分享率
	 */
	private int type;
	/**
	 * 浏览url
	 */
	private String visitUrl;

}
