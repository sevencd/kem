package cn.ilanhai.kem.domain.rights;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 去版权次数实体
 * @author hy
 *
 */
public class UnRightsTimesEntity extends AbstractEntity {

	private static final long serialVersionUID = -5549327303181734103L;
	/**
	 * 去版权id
	 */
	private Integer unrightsId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 去版权次数
	 */
	private int unrightsTimes;
	/**
	 * 去版权总次数
	 */
	private int totailTimes;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Integer getUnrightsId() {
		return unrightsId;
	}
	public void setUnrightsId(Integer unrightsId) {
		this.unrightsId = unrightsId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUnrightsTimes() {
		return unrightsTimes;
	}
	public void setUnrightsTimes(int unrightsTimes) {
		this.unrightsTimes = unrightsTimes;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getTotailTimes() {
		return totailTimes;
	}
	public void setTotailTimes(int totailTimes) {
		this.totailTimes = totailTimes;
	}
}
