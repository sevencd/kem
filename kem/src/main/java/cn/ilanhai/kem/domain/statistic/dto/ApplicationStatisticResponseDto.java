package cn.ilanhai.kem.domain.statistic.dto;

import java.util.List;
import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 应用统计数据总览数据
 * 
 * @author csz
 *
 */
public class ApplicationStatisticResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 开通人数
	 */
	private long appCreateAmount;
	/**
	 * 注册人数
	 */
	private long appRegisterAmount;
	/**
	 * 开通率
	 */
	private String rate;
	/**
	 * 总发信数
	 */
	private long sendNum;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppCreateAmount() {
		return appCreateAmount;
	}

	public void setAppCreateAmount(long appCreateAmount) {
		this.appCreateAmount = appCreateAmount;
	}

	public long getAppRegisterAmount() {
		return appRegisterAmount;
	}

	public void setAppRegisterAmount(long appRegisterAmount) {
		this.appRegisterAmount = appRegisterAmount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public long getSendNum() {
		return sendNum;
	}

	public void setSendNum(long sendNum) {
		this.sendNum = sendNum;
	}
}
