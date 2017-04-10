package cn.ilanhai.kem.domain.material.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * SVG上传实体
 * 
 * @author hy
 *
 */
public class UserSVGUploadDto extends AbstractEntity {
	private static final long serialVersionUID = 6341457083012287033L;
	/**
	 * 图片保存路径
	 */
	private List<String> imgPath;
	/**
	 * SVG名称
	 */
	private List<String> imgName;

	/**
	 * SVG绝对路径
	 */
	private List<String> absolutePath;
	/**
	 * SVG类型 1 模板 0源文件
	 */
	private Integer type;

	private String materialId;
	private String materialTypeId;
	private String manuscriptId;

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

	public List<String> getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(List<String> absolutePath) {
		this.absolutePath = absolutePath;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}
}
