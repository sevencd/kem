package cn.ilanhai.kem.domain.smsvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserType;

public class SmsValidateCodeValiRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 3399971529111812919L;

	private String phoneNo;
	private String moduleCode;
	private String smsCode;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
