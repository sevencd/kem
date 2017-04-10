package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

public class DrawPrizeResponseDto {

	private Integer recordId;
	private Integer optionId;
	private String optionName;
	private String prizeName;
	private String prizeNo;
	private String merchantPhone;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
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
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getPrizeNo() {
		return prizeNo;
	}
	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}
	public String getMerchantPhone() {
		return merchantPhone;
	}
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}
	
	
}
