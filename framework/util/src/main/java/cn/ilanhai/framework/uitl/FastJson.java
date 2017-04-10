package cn.ilanhai.framework.uitl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json助手
 * 
 * @author he
 *
 */
public final class FastJson {

	/**
	 * 对象到json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String bean2Json(Object obj) {

		return JSON.toJSONString(obj, SerializerFeature.BrowserCompatible,
				SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteNullBooleanAsFalse,
				SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty);

	}

	/**
	 * json字符串到对象
	 * 
	 * @param jsonStr
	 * @param objClass
	 * @return
	 */
	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		return JSON.parseObject(jsonStr, objClass);
	}

}
