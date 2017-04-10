package cn.ilanhai.framework.common.configuration.app;

import java.util.*;
import java.net.*;

import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.configuration.ds.DsConf;

/**
 * 定义应用配置
 * 
 * @author he
 *
 */
public class ConfigureImpl implements Configure {
	private Map settings = null;
	private String beansConfPath = null;
	private boolean enable = true;
	private String name = "";
	private String jarFileName = "";
	private String packageName = "";
	private String id = "";
	private String startupClassName = "";
	private List<MQConf> mqConf = null;
	private List<DsConf> dsConf = null;

	public ConfigureImpl() {
		this.settings = new HashMap<String, Object>();
		this.mqConf = new ArrayList<MQConf>();
		this.dsConf = new ArrayList<DsConf>();
	}

	public Map getSettings() {
		return settings;
	}

	public String getBeansConfPath() {
		return beansConfPath;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setBeansConfPath(String beansConfPath) {
		this.beansConfPath = beansConfPath;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJarFileName() {
		return this.jarFileName;
	}

	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartupClassName() {
		return startupClassName;
	}

	public void setStartupClassName(String startupClassName) {
		this.startupClassName = startupClassName;
	}

	public List<MQConf> getMQConf() {
		return this.mqConf;
	}
	public List<DsConf> getDsConf() {
		return this.dsConf;
	}

}
