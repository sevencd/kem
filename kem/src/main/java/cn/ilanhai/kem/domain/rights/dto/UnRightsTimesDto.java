package cn.ilanhai.kem.domain.rights.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UnRightsTimesDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4153439742607943024L;
	
	private int unrightsTimes;
	private int totailTimes;

	public int getUnrightsTimes() {
		return unrightsTimes;
	}

	public void setUnrightsTimes(int unrightsTimes) {
		this.unrightsTimes = unrightsTimes;
	}

	public int getTotailTimes() {
		return totailTimes;
	}

	public void setTotailTimes(int totailTimes) {
		this.totailTimes = totailTimes;
	}
}
