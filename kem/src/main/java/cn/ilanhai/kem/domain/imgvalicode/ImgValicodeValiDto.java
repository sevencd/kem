package cn.ilanhai.kem.domain.imgvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ImgValicodeValiDto extends AbstractEntity{

	private static final long serialVersionUID = 4409661858006293601L;

	private String moduleCode;
	private String imgCode;
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	
	
}
