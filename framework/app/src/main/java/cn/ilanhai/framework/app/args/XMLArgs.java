package cn.ilanhai.framework.app.args;

import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 定义参数xml格式
 * 
 * @author he
 *
 */
public class XMLArgs implements Args {
	private String xml = "";

	public <T> T getDomain(Class<T> clazz) throws BlAppException {
		try {
			return null;
		} catch (Exception e) {
			throw new BlAppException(-1, "参数据类型转换错误");
		}
	}

	/**
	 * 获取xml字符串
	 * 
	 * @return
	 */
	public String getXmlString() {
		return xml;
	}

	/**
	 * 设置xml字符串
	 * 
	 * @param xml
	 */
	public void setXmlString(String xml) {
		this.xml = xml;
	}
}
