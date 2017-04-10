package cn.ilanhai.kem.domain.special;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 复制专题
 * 
 * @author hy
 *
 */
public class CopySpecialDto extends AbstractEntity {
	private static final long serialVersionUID = -6239823640323198733L;
	private String specialId;
	private String specialName;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
}
