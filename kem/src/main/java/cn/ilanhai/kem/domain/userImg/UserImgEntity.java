package cn.ilanhai.kem.domain.userImg;

import java.util.Date;
import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserImgEntity extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	private String imgId;
	private String imgPath;
	private String imgType;
	private Date createtime;
	private String userId;

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
