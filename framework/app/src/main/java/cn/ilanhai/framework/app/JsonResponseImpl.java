package cn.ilanhai.framework.app;

import java.net.URI;

public class JsonResponseImpl extends ResponseImpl {
	private int code = 0xffffffff;
	private String desc = "";
	private long currentTimeMillis = 0;
	private URI location = null;
	private String interfaceDocUrl = null;

	public JsonResponseImpl() {
		this.currentTimeMillis = System.currentTimeMillis();
	}

	public JsonResponseImpl(int code, String desc, Object data, URI location) {
		this();
		this.code = code;
		this.desc = desc;
		this.data = data;
		this.location = location;

	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {

		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getCurrentTimeMillis() {
		return this.currentTimeMillis;
	}

	public URI getLocation() {
		return this.location;
	}

	public void setLocation(URI location) {
		this.location = location;
	}

	public String getInterfaceDocUrl() {
		return interfaceDocUrl;
	}

	public void setInterfaceDocUrl(String interfaceDocUrl) {
		this.interfaceDocUrl = interfaceDocUrl;
	}
}
