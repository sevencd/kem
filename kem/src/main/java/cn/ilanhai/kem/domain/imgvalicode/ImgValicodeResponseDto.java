package cn.ilanhai.kem.domain.imgvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ImgValicodeResponseDto extends AbstractEntity {

	private static final long serialVersionUID = 4409661858006293601L;

	private Integer imgCodeId;
	private String imgCode;

	public Integer getImgCodeId() {
		return imgCodeId;
	}

	public void setImgCodeId(Integer imgCodeId) {
		this.imgCodeId = imgCodeId;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

}
