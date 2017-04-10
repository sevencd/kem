package cn.ilanhai.kem.domain.userImg;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteUserImgDataDto extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	private String imgId;
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
}
