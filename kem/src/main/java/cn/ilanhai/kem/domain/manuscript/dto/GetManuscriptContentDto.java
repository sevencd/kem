package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class GetManuscriptContentDto extends AbstractEntity{
	private static final long serialVersionUID = 6362626228850408584L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
