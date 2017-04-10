package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 预览专题响应Dto
 * 
 * @author hy
 *
 */
public class PreviewSpecialResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 785516121305853494L;
	/**
	 * 专题id
	 */
	private String specialId;
	/**
	 * 预览路径
	 */
	private String previewURL;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}
}
