package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 抽奖奖项实体对象
 * 
 * @author Nature
 *
 */
public class DrawOptionEntity extends AbstractEntity {

	private static final long serialVersionUID = -9219586685105392413L;

	private Integer optionId;
	private Integer prizeRecordId;
	private String optionName;
	private String prizeName;
	private Integer heavy;

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getPrizeRecordId() {
		return prizeRecordId;
	}

	public void setPrizeRecordId(Integer prizeRecordId) {
		this.prizeRecordId = prizeRecordId;
	}

	public Integer getHeavy() {
		return heavy;
	}

	public void setHeavy(Integer heavy) {
		this.heavy = heavy;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

}
