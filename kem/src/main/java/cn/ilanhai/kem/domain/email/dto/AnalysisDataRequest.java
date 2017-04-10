package cn.ilanhai.kem.domain.email.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 获取邮件数据统计请求对象
 * @author Nature
 *
 */
public class AnalysisDataRequest  extends AbstractEntity{

	private static final long serialVersionUID = 926947716592359272L;

	private String emailId;
	private Date starttime;
	private Date endtime;
	private Integer days;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	
}
