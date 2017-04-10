package cn.ilanhai.kem.domain.statistic;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ManuscriptStatisticDataEntity extends AbstractEntity {
	private static final long serialVersionUID = -515408637751927046L;

	private long totalId;
	private String manuscriptId;
	private String url;
	private Date requestTime;
	private Date stayTime;
	private String sessionId;
	private String ip;

	public long getTotalId() {
		return totalId;
	}

	public void setTotalId(long totalId) {
		this.totalId = totalId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getStayTime() {
		return stayTime;
	}

	public void setStayTime(Date stayTime) {
		this.stayTime = stayTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
