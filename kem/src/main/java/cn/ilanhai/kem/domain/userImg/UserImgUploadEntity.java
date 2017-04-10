package cn.ilanhai.kem.domain.userImg;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserImgUploadEntity extends AbstractEntity {
	private static final long serialVersionUID = 6341457083012287033L;
	/**
	 * 图片路径
	 */
	private String imgPath;
	/**
	 * 图片名称
	 */
	private String imgName;
	/**
	 * 图片md5
	 */
	private String imgMd5;
	/**
	 * 图片类型
	 */
	private String type;
	/**
	 * 图片大小
	 */
	private long size;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 创建时间
	 * 
	 */
	private Date createtime;
	/**
	 * 图片ID
	 */
	private String imgId;
	
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

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getImgMd5() {
		return imgMd5;
	}

	public void setImgMd5(String imgMd5) {
		this.imgMd5 = imgMd5;
	}
}
