package cn.ilanhai.kem.domain.user.frontuser.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryFrontUserByCompany extends AbstractEntity {

	private static final long serialVersionUID = 8666382655824953013L;

	private String companyId;
	private String companyCode;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
