package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.util.TimeUtil;

public class StatdayListRequest extends MailInfo {

	// 标签ID列表
	private List<String> labelIdList = new ArrayList<String>();
	private List<String> apiUserList=new ArrayList<String>();
	private Date startdate;
	private Date enddate;

	private final char SPLIT = ';';
	

	public List<String> getLabelIdList() {
		return labelIdList;
	}
	public void setLabelIdList(List<String> labelIdList) {
		this.labelIdList = labelIdList;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public List<String> getApiUserList() {
		return apiUserList;
	}
	public void setApiUserList(List<String> apiUserList) {
		this.apiUserList = apiUserList;
	}
	public Map<String, String> toMap() {
		Map<String, String> map = null;
		map = new HashMap<String, String>();
		if (startdate != null) {
			map.put("startDate", TimeUtil.format(startdate));
		}
		if (enddate != null) {
			map.put("endDate", TimeUtil.format(enddate));
		}
		if (labelIdList != null && labelIdList.size() > 0) {
			map.put("labelIdList", this.listToString(labelIdList, SPLIT));
		}
		if(apiUserList!=null&&apiUserList.size()>0){
			map.put("apiUserList", this.listToString(apiUserList, SPLIT));
		}

		return map;
	}

}
