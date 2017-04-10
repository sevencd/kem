package cn.ilanhai.kem.domain.email.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 邮件数据分析结果记录DTO
 * 
 * @author Nature
 *
 */
public class AnalysisDataRecordDto extends AbstractEntity {

	private static final long serialVersionUID = -2147318045646193410L;

	private int requestNum;// 请求数
	private int deliveredNum;// 发送数量
	private int invalidEmailsNum;// 无效邮件数量
	private int bounceNum;// 软退信
	private int spamReportedNum;// 垃圾邮件举报数量
	private int openNum;// 打开数量
	private int clickNum;// 点击数量
	private int uniqueOpensNum;// 独立打开数量
	private int uniqueClicksNum;// 独立点击数量
	private int unsubscribeNum;// 取消订阅数量
	private Date date;// 日期

	public int getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(int requestNum) {
		this.requestNum += requestNum;
	}

	public int getDeliveredNum() {
		return deliveredNum;
	}

	public void setDeliveredNum(int deliveredNum) {
		this.deliveredNum += deliveredNum;
	}

	public int getInvalidEmailsNum() {
		return invalidEmailsNum;
	}

	public void setInvalidEmailsNum(int invalidEmailsNum) {
		this.invalidEmailsNum += invalidEmailsNum;
	}

	public int getBounceNum() {
		return bounceNum;
	}

	public void setBounceNum(int bounceNum) {
		this.bounceNum += bounceNum;
	}

	public int getSpamReportedNum() {
		return spamReportedNum;
	}

	public void setSpamReportedNum(int spamReportedNum) {
		this.spamReportedNum += spamReportedNum;
	}

	public int getOpenNum() {
		return openNum;
	}

	public void setOpenNum(int openNum) {
		this.openNum += openNum;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum += clickNum;
	}

	public int getUniqueOpensNum() {
		return uniqueOpensNum;
	}

	public void setUniqueOpensNum(int uniqueOpensNum) {
		this.uniqueOpensNum += uniqueOpensNum;
	}

	public int getUniqueClicksNum() {
		return uniqueClicksNum;
	}

	public void setUniqueClicksNum(int uniqueClicksNum) {
		this.uniqueClicksNum += uniqueClicksNum;
	}

	public int getUnsubscribeNum() {
		return unsubscribeNum;
	}

	public void setUnsubscribeNum(int unsubscribeNum) {
		this.unsubscribeNum += unsubscribeNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
