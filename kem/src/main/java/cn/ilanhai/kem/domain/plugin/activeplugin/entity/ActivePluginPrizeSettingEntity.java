package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 活动插件奖项设置类
 * 
 * @author Nature
 *
 */
public class ActivePluginPrizeSettingEntity extends AbstractEntity {

	private static final long serialVersionUID = 1057845780535254442L;

	// 记录ID
	protected Integer recordId;
	// 插件ID
	protected Integer pluginId;
	// 奖项名称
	protected String optionName;
	// 奖品名称
	protected String prizeName;
	// 剩余数量
	protected Integer amount;
	// 中奖概率
	protected Integer rate;

	public ActivePluginPrizeSettingEntity(Integer pluginId) {
		this.pluginId = pluginId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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
