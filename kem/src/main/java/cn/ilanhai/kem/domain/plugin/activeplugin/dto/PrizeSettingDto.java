package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 数据传输中 返回奖项设置的DTO
 * 
 * @author Nature
 *
 */
public class PrizeSettingDto extends AbstractEntity {

	private static final long serialVersionUID = 9056971852766330245L;

	private String optionName;
	private String prizeName;
	private Integer amount;
	private Integer rate;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

}
