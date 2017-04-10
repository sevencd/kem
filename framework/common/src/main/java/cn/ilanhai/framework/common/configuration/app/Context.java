package cn.ilanhai.framework.common.configuration.app;

public class Context {
	private String name;
	private String confFile;
	private Configure configure;
	public Context(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConfFile() {
		return confFile;
	}
	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}
	public Configure getConfigure() {
		return configure;
	}
	public void setConfigure(Configure configure) {
		this.configure = configure;
	}
}
