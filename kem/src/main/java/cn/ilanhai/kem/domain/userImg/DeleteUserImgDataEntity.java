package cn.ilanhai.kem.domain.userImg;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteUserImgDataEntity extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	private String imgId;
	private Integer userId;

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
