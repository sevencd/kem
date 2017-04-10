package cn.ilanhai.kem.domain.smsvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ValidateStatus;

public class SmsValidateCodeStatusDto extends AbstractEntity{

	private static final long serialVersionUID = 5598297352394129355L;

	private ValidateStatus status;

	public ValidateStatus getStatus() {
		return status;
	}

	public void setStatus(ValidateStatus status) {
		this.status = status;
	}
	
}
