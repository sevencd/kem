package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.Date;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 按天数据统计返回值
 * @author Nature
 *
 */
public class StatdayListResponse  extends SohuResult{
	public StatdayListResponse() {

	}

	public StatdayListInfo getInfo() {
		return info;
	}

	public void setInfo(StatdayListInfo info) {
		this.info = info;
	}

	/**
	 * 信息
	 */
	private StatdayListInfo info;
}
