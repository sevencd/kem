package cn.ilanhai.kem.domain.statistic;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.BLContextUtil;

/**
 * 稿件浏览实体
 * 
 * @author he
 *
 */
public class ManuscriptVisitEntity extends AbstractEntity {
	public ManuscriptVisitEntity() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getManuscriptId(App context) throws BlAppException {
		return BLContextUtil.analysisUrl(context, this.url);
	}

	public static String getManuscriptId1(String url) throws BlAppException {
		if (Str.isNullOrEmpty(url))
			return null;
		Pattern pattern = Pattern.compile("\\d{12,255}");
		Matcher matcher = pattern.matcher(url);
		if (!matcher.find())
			return null;
		return matcher.group();
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private long id;
	/**
	 * 浏览稿件URL
	 */
	private String url;
	/**
	 * 浏览稿件ip
	 */
	private String ip;
	/**
	 * 浏览稿件时间
	 */
	private Date visitTime;
	/**
	 * 会话统编号
	 */
	private String sessionId;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 浏览UA
	 */
	private String ua;
}
