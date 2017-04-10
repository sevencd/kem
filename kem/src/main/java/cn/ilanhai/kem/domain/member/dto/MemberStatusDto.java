package cn.ilanhai.kem.domain.member.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MemberStatusDto extends AbstractEntity{

	private static final long serialVersionUID = 5960152281356533072L;

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
