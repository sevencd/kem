package cn.ilanhai.kem.domain.userImg;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserImgUploadDto extends AbstractEntity {
	private static final long serialVersionUID = 6341457083012287033L;
	/**
	 * 图片保存路径
	 */
	private List<String> imgPath;
	/**
	 * 图片名称
	 */
	private List<String> imgName;

	/**
	 * 图片绝对路径
	 */
	private List<String> absolutePath;

	/**
	 * 图片绝对路径
	 */
	private List<String> imgMd5;
	/**
	 * 图片id
	 */
	private List<String> imgIds;

	private String imgMd5Search;
	/**
	 * 图片类型
	 */
	private String type;
	/**
	 * 图片大小
	 */
	private long size;

	private String materialId;
	private Integer terminalType;
	private String manuscriptId;
	// 图片id
	private String imgId;

	public List<String> getImgPath() {
		return imgPath;
	}

	public void setImgPath(List<String> imgPath) {
		this.imgPath = imgPath;
	}

	public List<String> getImgName() {
		return imgName;
	}

	public void setImgName(List<String> imgName) {
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

	public List<String> getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(List<String> absolutePath) {
		this.absolutePath = absolutePath;
	}

	public List<String> getImgMd5() {
		return imgMd5;
	}

	public void setImgMd5(List<String> imgMd5) {
		this.imgMd5 = imgMd5;
	}

	public String getImgMd5Search() {
		return imgMd5Search;
	}

	public void setImgMd5Search(String imgMd5Search) {
		this.imgMd5Search = imgMd5Search;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public List<String> getImgIds() {
		return imgIds;
	}

	public void setImgIds(List<String> imgIds) {
		this.imgIds = imgIds;
	}
}
