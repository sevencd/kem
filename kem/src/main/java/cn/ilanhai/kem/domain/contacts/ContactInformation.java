package cn.ilanhai.kem.domain.contacts;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 同步联系人时，查询联系人实体
 * 
 * @author dgj
 *
 */
public class ContactInformation extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 261358777329869011L;

	private String name;
	private String valueNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValueNumber() {
		return valueNumber;
	}

	public void setValueNumber(String valueNumber) {
		this.valueNumber = valueNumber;
	}

}
