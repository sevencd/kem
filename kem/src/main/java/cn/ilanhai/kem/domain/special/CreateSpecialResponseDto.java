package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据模版创建专题响应Dto
 * 
 * @author hy
 *
 */
public class CreateSpecialResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 2932530049066042174L;
	/**
	 * 模版id
	 */
	private String speciaId;
	/**
	 * 专题名
	 */
	private Integer specialType;

	public String getSpeciaId() {
		return speciaId;
	}

	public void setSpeciaId(String speciaId) {
		this.speciaId = speciaId;
	}

	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}
}
