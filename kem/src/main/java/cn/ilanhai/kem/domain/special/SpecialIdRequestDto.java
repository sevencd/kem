package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 专题id请求Dto
 * 
 * @author hy
 *
 */
public class SpecialIdRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 5619399549760330593L;
	/**
	 * 专题id
	 */
	private String specialId;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

}
