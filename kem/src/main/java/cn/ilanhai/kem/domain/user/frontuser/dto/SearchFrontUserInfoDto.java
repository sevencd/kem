package cn.ilanhai.kem.domain.user.frontuser.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchFrontUserInfoDto extends AbstractEntity {
	private static final long serialVersionUID = -3149572019354020155L;
	private String userID;
	private Integer infoType;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
}
