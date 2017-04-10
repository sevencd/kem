package cn.ilanhai.framework.app.args;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.uitl.*;

/**
 * 定义参数json格式
 * 
 * @author he
 *
 */
public class JSONArgs implements Args {
	private String json = "";

	public <T> T getDomain(Class<T> clazz) throws BlAppException {
		try {
			if (this.json == null || this.json.length() <= 0)
				return null;

			return FastJson.<T> json2Bean(this.json, clazz);
		} catch (Exception e) {
			throw new BlAppException(-1, "参数据类型转换错误");
		}
	}

	/**
	 * 获取json字符串
	 * 
	 * @return
	 */
	public String getJsonString() {
		return json;
	}

	/**
	 * 设置json字符串
	 * 
	 * @param json
	 */
	public void setJsonString(String json) {
		this.json = json;
	}
}
