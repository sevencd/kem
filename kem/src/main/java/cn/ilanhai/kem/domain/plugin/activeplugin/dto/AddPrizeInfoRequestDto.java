package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class AddPrizeInfoRequestDto extends AbstractEntity{

	private static final long serialVersionUID = -8193506072832124815L;

	private Map<String,String> prizeCollectInfo;
	private String relationId;
	
	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Map<String, String> getPrizeCollectInfo() {
		return prizeCollectInfo;
	}

	public void setPrizeCollectInfo(Map<String, String> prizeCollectInfo) {
		this.prizeCollectInfo = prizeCollectInfo;
	}
	
	
}
