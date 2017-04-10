package cn.ilanhai.kem.domain.rights.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveUnRightsTimesDto extends AbstractEntity {
	private static final long serialVersionUID = -3790625058535350695L;
	// 用户id
	private String userId;
	// 去版权次数增加次数
	private Integer addTimes;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getAddTimes() {
		return addTimes;
	}
	public void setAddTimes(Integer addTimes) {
		this.addTimes = addTimes;
	}
}
