package cn.ilanhai.kem.domain.special;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 发布设置实体
 * 
 * @author hy
 *
 */
public class ConfigEntity extends AbstractEntity {

	private static final long serialVersionUID = 3162660801871712614L;
	/**
	 * id
	 */
	private Integer configId;
	/**
	 * 模块连接id
	 */
	private Integer modelConfigId;
	/**
	 * 设置开始时间
	 */
	private Date startTime;
	/**
	 * 设置结束时间
	 */
	private Date endTime;
	/**
	 * 主色调
	 */
	private String mainColor;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}
}
