package cn.ilanhai.kem.domain.imgvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ValidateStatus;

/**
 * 
 * @author he
 *
 */
public class StatusDto extends AbstractEntity {
	private ValidateStatus status;

	public StatusDto() {
	}

	public ValidateStatus getStatus() {
		return status;
	}

	public void setStatus(ValidateStatus status) {
		this.status = status;
	}
	
}
