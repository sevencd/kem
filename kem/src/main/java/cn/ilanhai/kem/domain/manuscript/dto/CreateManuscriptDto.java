package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.MapDto;
import cn.ilanhai.kem.domain.enums.ManuscriptType;

public class CreateManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = -2609663571236706158L;
	/**
	 * 内容 不同稿件 生成的内容不一致
	 */
	private MapDto options;
	/**
	 * 稿件类型 1:模版 2:专题 3:推广 4:优秀案例
	 */
	private ManuscriptType manuscriptType;

	// private String manuscriptId;
	// private String manuscriptName;
	// private Boolean isEnable;
	// private String tags;
	public MapDto getOptions() {
		return options;
	}

	public void setOptions(MapDto options) {
		this.options = options;
	}

	/**
	 * 稿件类型 1:模版 2:专题 3:推广 4:优秀案例
	 * 
	 * @return
	 */
	public ManuscriptType getManuscriptType() {
		return manuscriptType;
	}

	public void setManuscriptType(Integer manuscriptType) {
		this.manuscriptType = ManuscriptType.getEnum(manuscriptType);
	}
}
