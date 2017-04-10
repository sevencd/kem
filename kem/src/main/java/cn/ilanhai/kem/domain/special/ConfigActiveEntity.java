package cn.ilanhai.kem.domain.special;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 配置实体
 * 
 * @author hy
 *
 */
public class ConfigActiveEntity extends AbstractEntity {

	private static final long serialVersionUID = -3265462403176886224L;
	/**
	 * 活动配置id
	 */
	private Integer activeId;
	/**
	 * 专题id
	 */
	private String specialId;
	/**
	 * 设置开始时间
	 */
	private Date startTime;
	/**
	 * 设置结束时间
	 */
	private Date endTime;

	public Integer getActiveId() {
		return activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
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
}
