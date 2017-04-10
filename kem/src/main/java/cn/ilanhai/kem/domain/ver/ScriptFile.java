package cn.ilanhai.kem.domain.ver;

import org.springframework.core.io.Resource;

public class ScriptFile extends VerEntity {
	private static final long serialVersionUID = 1L;
	private String script;
	private Resource resource = null;

	public ScriptFile() {

	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public int compareTo(VerEntity o) {
		int val = super.compareTo(o);
		return val;
	}

}
