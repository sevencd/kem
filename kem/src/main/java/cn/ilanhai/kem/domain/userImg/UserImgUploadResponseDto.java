package cn.ilanhai.kem.domain.userImg;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserImgUploadResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 6341457083012287033L;

	/**
	 * 图片绝对路径
	 */
	private List<String> absolutePath;

	public List<String> getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(List<String> absolutePath) {
		this.absolutePath = absolutePath;
	}

}
