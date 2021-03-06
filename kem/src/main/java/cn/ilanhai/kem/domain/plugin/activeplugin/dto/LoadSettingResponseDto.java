package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载活动设置返回值
 * @author Nature
 *
 */
public class LoadSettingResponseDto extends AbstractEntity{

	private static final long serialVersionUID = -118987586249114752L;
	
	private List<PrizeSettingDto> prizes=new ArrayList<PrizeSettingDto>();
	private Integer drawTime;
	private Integer winTime;
	private Integer intervalTime;
	private Integer intervalTimeType;
	private Map<String,Boolean> prizeCollectInfo=new HashMap<String,Boolean>();
	private Map<String,Boolean> prizeCollectRequiredInfo=new HashMap<String,Boolean>();
	private String outerUrl;
	private String merchantPhone;
	
	private Date actStartTime;
	private Date actEndTime;
	/**
	 * 活动类型
	 */
	private String activeType;
	
	public List<PrizeSettingDto> getPrizes() {
		return prizes;
	}
	public void setPrizes(List<PrizeSettingDto> prizes) {
		this.prizes = prizes;
	}
	public Integer getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(Integer drawTime) {
		this.drawTime = drawTime;
	}
	public Integer getWinTime() {
		return winTime;
	}
	public void setWinTime(Integer winTime) {
		this.winTime = winTime;
	}
	public Integer getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	public Integer getIntervalTimeType() {
		return intervalTimeType;
	}
	public void setIntervalTimeType(Integer intervalTimeType) {
		this.intervalTimeType = intervalTimeType;
	}
	public Map<String, Boolean> getPrizeCollectInfo() {
		return prizeCollectInfo;
	}
	public void setPrizeCollectInfo(Map<String, Boolean> prizeCollectInfo) {
		this.prizeCollectInfo = prizeCollectInfo;
	}
	public Map<String, Boolean> getPrizeCollectRequiredInfo() {
		return prizeCollectRequiredInfo;
	}
	public void setPrizeCollectRequiredInfo(
			Map<String, Boolean> prizeCollectRequiredInfo) {
		this.prizeCollectRequiredInfo = prizeCollectRequiredInfo;
	}
	public String getOuterUrl() {
		return outerUrl;
	}
	public void setOuterUrl(String outerUrl) {
		this.outerUrl = outerUrl;
	}
	public String getMerchantPhone() {
		return merchantPhone;
	}
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}
	public Date getActStartTime() {
		return actStartTime;
	}
	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}
	public Date getActEndTime() {
		return actEndTime;
	}
	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
}
