package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 是否存在该用户
 * 
 * @author hy
 *
 */
public class IsExistResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -7551523331827466615L;
	private Boolean isExist;

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public IsExistResponseDto(Boolean isExist) {
		this.isExist = isExist;
	}
}
