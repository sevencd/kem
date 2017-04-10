package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DrawPrizeResultEntity extends AbstractEntity {

	private static final long serialVersionUID = 2367896499855336581L;

	private Integer optionId;
	private String optionName;
	private Integer recordId;
	private String prizeNo;
	private String prizeName;

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
}
