package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class FrontUserInfoEntity extends AbstractEntity {
	private static final long serialVersionUID = -3149572019354020155L;
	private String userID;
	private String info;
	private Integer infoType;
	private Integer infoState;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Integer getInfoState() {
		return infoState;
	}

	public void setInfoState(Integer infoState) {
		this.infoState = infoState;
	}
}
