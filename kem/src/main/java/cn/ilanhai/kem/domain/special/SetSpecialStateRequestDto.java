package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 设置专题状态请求Dto
 * 
 * @author hy
 *
 */
public class SetSpecialStateRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 785516121305853494L;
	/**
	 * 专题id
	 */
	private String specialId;
	/**
	 * 专题状态
	 */
	private Integer state;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
