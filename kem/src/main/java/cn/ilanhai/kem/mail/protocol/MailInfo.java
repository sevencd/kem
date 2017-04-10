package cn.ilanhai.kem.mail.protocol;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;

/**
 * 邮件信息
 * 
 * @author he
 *
 */
public class MailInfo {
	public MailInfo() {

	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	protected String listToString(List<String> list, char split) {
		if (list == null || list.size() <= 0)
			return null;
		StringBuilder builder = null;
		builder = new StringBuilder();
		int offset = list.size() - 1;
		for (int i = 0; i < offset; i++)
			builder.append(list.get(i)).append(split);
		builder.append(list.get(offset));
		return builder.toString();
	}

	protected String mapToJsonString(Map<String, String> map) {
		if (map == null || map.size() <= 0)
			return null;
		return FastJson.bean2Json(map);
	}

	protected String getFullAddress(String address) {
		if (Str.isNullOrEmpty(address))
			return null;
		return address + "@mail.cloudmarkee.com";
	}

	public Map<String, String> map(Map<String, String> map1,
			Map<String, String> map2) {
		if (map1 == null || map1.size() <= 0)
			return map2;
		Iterator<String> iterator = map2.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (map1.containsKey(key))
				map1.remove(key);
			map1.put(key, map2.get(key));

		}
		return map1;
	}

	protected String encode(String str) {
		if (Str.isNullOrEmpty(str))
			return str;
		return URLEncoder.encode(str);
	}

	protected DataType dataType;

	/**
	 * 数据类型
	 * 
	 * @author he
	 *
	 */
	public enum DataType {
		/**
		 * 
		 */
		GeneralSendSohuMail,
		/**
		 * 
		 */
		AddAddress,
		/**
		 * 
		 */
		AddMember,
		/**
		 * 
		 */
		DeleteAddress,
		/**
		 * 
		 */
		DeleteMember,
		/**
		 * 
		 */
		QueryAddressList,
		/**
		 * 
		 */
		QueryMemberList,
		/**
		 * 
		 */
		QueryMember,
		/**
		 * 
		 */
		UpdateAddress,
		/**
		 * 
		 */
		UpdateMember;
	}
}
