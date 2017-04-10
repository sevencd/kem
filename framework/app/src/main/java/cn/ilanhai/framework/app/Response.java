package cn.ilanhai.framework.app;

import java.io.Serializable;
import java.net.URI;

/**
 * 定义以响应接口
 * 
 * @author he
 *
 */
public interface Response extends Serializable {
	String getSessionId();

	void setSessionId(String sessionId);

	Object getData();

	void setData(Object object);

	<T> T getData(Class<T> t);
}
