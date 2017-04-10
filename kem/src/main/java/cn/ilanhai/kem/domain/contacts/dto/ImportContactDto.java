package cn.ilanhai.kem.domain.contacts.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.MapDto;

public class ImportContactDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2744655648763680206L;
	private Integer contactType;
	private List<ImportDataDto> datas;

	private String importType;
	private String customerType;
	private List<MapDto> customerDatas;

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public List<ImportDataDto> getDatas() {
		return datas;
	}

	public void setDatas(List<ImportDataDto> datas) {
		this.datas = datas;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public List<MapDto> getCustomerDatas() {
		return customerDatas;
	}

	public void setCustomerDatas(List<MapDto> customerDatas) {
		this.customerDatas = customerDatas;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
}
