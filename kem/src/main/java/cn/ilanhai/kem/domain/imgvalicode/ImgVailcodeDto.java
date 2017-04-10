package cn.ilanhai.kem.domain.imgvalicode;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserType;

public class ImgVailcodeDto extends AbstractEntity {

	private static final long serialVersionUID = 2187576464212213175L;

	// 模块代码
	private String moduleCode;

	public ImgVailcodeDto() {

	}
	
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}
