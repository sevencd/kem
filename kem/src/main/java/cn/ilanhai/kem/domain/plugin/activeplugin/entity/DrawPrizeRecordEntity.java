package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DrawPrizeRecordEntity extends AbstractEntity{

	private static final long serialVersionUID = -3147735973730049444L;

	private Integer recordId;
	private Integer pluginId;
	private String phoneNo;
	private String name;
	private String address;
	private String qq;
	private Date createtime;
	private String prizeName;
	private String prizeNo;
	private Integer exchangeState;
	private Date exchangeTime;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getPluginId() {
		return pluginId;
	}
	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getPrizeNo() {
		return prizeNo;
	}
	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}
	public Integer getExchangeState() {
		return exchangeState;
	}
	public void setExchangeState(Integer exchangeState) {
		this.exchangeState = exchangeState;
	}
	public Date getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	
	
}
