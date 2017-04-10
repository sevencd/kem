package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 停留时间请求
 * 
 * @author he
 *
 */
public class ManuscriptStatisticResidenceTimeDtoRequest extends AbstractEntity {
	public ManuscriptStatisticResidenceTimeDtoRequest() {

	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 浏览url
	 */
	private String visitUrl;
}
