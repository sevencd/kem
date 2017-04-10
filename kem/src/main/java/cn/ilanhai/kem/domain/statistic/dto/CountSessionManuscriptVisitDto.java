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
public class CountSessionManuscriptVisitDto extends AbstractEntity {

	public CountSessionManuscriptVisitDto() {

	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private static final long serialVersionUID = 1L;

	private String sessionId;
	private int quantity;

}
