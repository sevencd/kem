package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 邮件按天统计值信息
 * @author Nature
 *
 */
public class StatdayListInfo {

	private List<StatdayListResponseRecord> dataList=new ArrayList<StatdayListResponseRecord>();

	public List<StatdayListResponseRecord> getDataList() {
		return dataList;
	}

	public void setDataList(List<StatdayListResponseRecord> dataList) {
		this.dataList = dataList;
	}

	
}
