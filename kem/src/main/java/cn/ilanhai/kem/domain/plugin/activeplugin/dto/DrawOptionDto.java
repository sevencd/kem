package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DrawOptionDto extends AbstractEntity{

	private static final long serialVersionUID = 5925940667885904647L;
	
	private Integer optionId;
	private String optionName;
	
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
	
	
	

}
