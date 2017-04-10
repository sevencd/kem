package cn.ilanhai.framework.app;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.args.*;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.uitl.Str;

/**
 * 定义请求上下文
 * 
 * @author he
 *
 */
public class RequestContextImpl implements RequestContext {
	private static Logger logger = Logger.getLogger(RequestContextImpl.class);
	// 参数
	private Args args = null;
	// 应用
	private Application application = null;
	// 服务定位
	private URI location = null;
	private Map<String, String> query = null;
	private Session session = null;

	public void setApplication(Application application) {
		this.application = application;
	}

	public Application getApplication() {
		return this.application;

	}

	public URI getLocation() {
		return this.location;
	}

	public String getApplicationIdFromLocation() {
		if (location == null)
			return null;
		return location.getHost();

	}

	public String getApplicationServiceNameFromLocation() {
		return this.getLocationPathSection(0x01);
	}

	public String getApplicationActionNameFromLocation() {
		return this.getLocationPathSection(0x02);
	}

	private String getLocationPathSection(int index) {
		String path = "";
		String[] sectionArray = null;

		if (location == null)
			return null;
		path = location.getPath();
		if (Str.isNullOrEmpty(path))
			return null;
		sectionArray = path.split("/");
		if (sectionArray == null || sectionArray.length <= 0)
			return null;
		if (index >= sectionArray.length)
			return null;
		return sectionArray[index];

	}

	public void setLocation(URI location) {
		this.location = location;
		String queryString = null;
		String[] tmp = null;
		String[] kv = null;
		String str = null;
		if (this.location == null)
			return;
		queryString = this.location.getQuery();
		if (Str.isNullOrEmpty(queryString))
			return;
		tmp = queryString.split("&");
		if (tmp == null || tmp.length <= 0)
			return;
		this.query = new HashMap<String, String>();
		for (int i = 0; i < tmp.length; i++) {
			str = tmp[i];
			if (str.indexOf('=') < 0)
				continue;
			kv = str.split("=");
			if (kv == null || kv.length < 2)
				continue;
			if (Str.isNullOrEmpty(kv[0]))
				continue;
			try {
				this.query.put(kv[0], URLDecoder.decode(kv[1], "utf-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
	}

	public Args getArgs() {
		return this.args;
	}

	public void setArgs(Args args) {
		this.args = args;
	}

	public <T> T getDomain(Class<T> clazz) throws BlAppException {
		return this.args.<T> getDomain(clazz);
	}

	public String getQueryString(String key) {
		if (this.query == null)
			return null;
		if (!this.query.containsKey(key))
			return null;
		return this.query.get(key);
	}

	public Session getSession() {
		return this.session;
	}

	void setSession(Session session) {
		this.session = session;
	}

}
