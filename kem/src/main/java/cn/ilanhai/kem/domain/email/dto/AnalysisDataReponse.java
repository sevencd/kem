package cn.ilanhai.kem.domain.email.dto;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 邮件数据分析结果
 * @author Nature
 *
 */
public class AnalysisDataReponse extends AbstractEntity{

	private static final long serialVersionUID = 4445617767732166842L;

	private List<AnalysisDataRecordDto> list=new ArrayList<AnalysisDataRecordDto>();
	
	private	int requestTotalNum;//请求总数
	private int deliveredTotalNum;//发送总数量
	private int invalidEmailsTotalNum;//无效邮件总数量
	private int bounceTotalNum;//软退信总
	private int spamReportedTotalNum;//垃圾邮件举报总数量
	private int openTotalNum;//打开总数量
	private int clickTotalNum;//点击总数量
	private int uniqueOpensTotalNum;//独立打开总数量
	private int uniqueClicksTotalNum;//独立点击总数量
	private int unsubscribeTotalNum;//取消订阅总数量
	
	public List<AnalysisDataRecordDto> getList() {
		return list;
	}
	public void setList(List<AnalysisDataRecordDto> list) {
		this.list = list;
	}
	public int getRequestTotalNum() {
		return requestTotalNum;
	}
	public void setRequestTotalNum(int requestTotalNum) {
		this.requestTotalNum = requestTotalNum;
	}
	public int getDeliveredTotalNum() {
		return deliveredTotalNum;
	}
	public void setDeliveredTotalNum(int deliveredTotalNum) {
		this.deliveredTotalNum = deliveredTotalNum;
	}
	public int getInvalidEmailsTotalNum() {
		return invalidEmailsTotalNum;
	}
	public void setInvalidEmailsTotalNum(int invalidEmailsTotalNum) {
		this.invalidEmailsTotalNum = invalidEmailsTotalNum;
	}
	public int getBounceTotalNum() {
		return bounceTotalNum;
	}
	public void setBounceTotalNum(int bounceTotalNum) {
		this.bounceTotalNum = bounceTotalNum;
	}
	public int getSpamReportedTotalNum() {
		return spamReportedTotalNum;
	}
	public void setSpamReportedTotalNum(int spamReportedTotalNum) {
		this.spamReportedTotalNum = spamReportedTotalNum;
	}
	public int getOpenTotalNum() {
		return openTotalNum;
	}
	public void setOpenTotalNum(int openTotalNum) {
		this.openTotalNum = openTotalNum;
	}
	public int getClickTotalNum() {
		return clickTotalNum;
	}
	public void setClickTotalNum(int clickTotalNum) {
		this.clickTotalNum = clickTotalNum;
	}
	public int getUniqueOpensTotalNum() {
		return uniqueOpensTotalNum;
	}
	public void setUniqueOpensTotalNum(int uniqueOpensTotalNum) {
		this.uniqueOpensTotalNum = uniqueOpensTotalNum;
	}
	public int getUniqueClicksTotalNum() {
		return uniqueClicksTotalNum;
	}
	public void setUniqueClicksTotalNum(int uniqueClicksTotalNum) {
		this.uniqueClicksTotalNum = uniqueClicksTotalNum;
	}
	public int getUnsubscribeTotalNum() {
		return unsubscribeTotalNum;
	}
	public void setUnsubscribeTotalNum(int unsubscribeTotalNum) {
		this.unsubscribeTotalNum = unsubscribeTotalNum;
	}
}
