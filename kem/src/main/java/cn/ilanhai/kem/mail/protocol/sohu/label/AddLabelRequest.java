package cn.ilanhai.kem.mail.protocol.sohu.label;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;

public class AddLabelRequest extends MailInfo {
	public AddLabelRequest() {

	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getLabelName();
		if (!Str.isNullOrEmpty(tmp))
			map.put("labelName",  this.encode(tmp));

		return map;
	}

	/**
	 * 
	 */
	private String labelName;
}
