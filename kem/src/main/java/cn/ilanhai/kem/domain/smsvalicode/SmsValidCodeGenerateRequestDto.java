package cn.ilanhai.kem.domain.smsvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserType;

/**
 * 生成验证码请求
 * 
 * @author Nature
 *
 */
public class SmsValidCodeGenerateRequestDto extends AbstractEntity {

	// 模块代码
	private String moduleCode;
	/**
	 * 电话号码
	 */
	private String phoneNo;

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
	
}
