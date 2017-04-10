package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class DrawOptionRsponseDto extends AbstractEntity {
	private static final long serialVersionUID = 4962177239183060407L;
	// 用户中奖信息采集
	private Map<String, Boolean> prizeCollectRequiredInfo;
	private Map<String, Boolean> prizeCollectInfo;
	private List<Entity> list;
	private Integer drawPrizeTime;

	public Map<String, Boolean> getPrizeCollectRequiredInfo() {
		return prizeCollectRequiredInfo;
	}

	public void setPrizeCollectRequiredInfo(Map<String, Boolean> prizeCollectRequiredInfo) {
		this.prizeCollectRequiredInfo = prizeCollectRequiredInfo;
	}

	public Map<String, Boolean> getPrizeCollectInfo() {
		return prizeCollectInfo;
	}

	public void setPrizeCollectInfo(Map<String, Boolean> prizeCollectInfo) {
		this.prizeCollectInfo = prizeCollectInfo;
	}

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}

	public Integer getDrawPrizeTime() {
		return drawPrizeTime;
	}

	public void setDrawPrizeTime(Integer drawPrizeTime) {
		this.drawPrizeTime = drawPrizeTime;
	}
}
