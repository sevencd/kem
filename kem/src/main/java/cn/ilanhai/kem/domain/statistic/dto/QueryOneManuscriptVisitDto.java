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
public class QueryOneManuscriptVisitDto extends AbstractEntity {

	public QueryOneManuscriptVisitDto() {

	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private static final long serialVersionUID = 1L;

	private String sessionId;

}
