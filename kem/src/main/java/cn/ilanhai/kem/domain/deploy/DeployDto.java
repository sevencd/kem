package cn.ilanhai.kem.domain.deploy;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.domain.ContextDataDto;

public class DeployDto extends AbstractEntity {
	private static final long serialVersionUID = 372305567993130326L;
	private String modeId;
	private ContextDataDto data;
	private Boolean isEditor;
	private Integer pluginType;
	private Integer activeType;
	private Integer terminalType;

	public DeployDto() {

	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public ContextDataDto getData() {
		return data;
	}

	public void setData(ContextDataDto data) {
		this.data = data;
	}

	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

	public Boolean isEditor() {
		return isEditor;
	}

	public void setIsEditor(Boolean isEditor) {
		this.isEditor = isEditor;
	}

	public Integer getPluginType() {
		return pluginType;
	}

	public void setPluginType(Integer pluginType) {
		this.pluginType = pluginType;
	}

	public Integer getActiveType() {
		return activeType;
	}

	public void setActiveType(Integer activeType) {
		this.activeType = activeType;
	}

	/**
	 * 替换内容中的占位符
	 */
	public void analysisData() {
		// 根据id 替换内容中的占位符
		String content = BLContextUtil.analysisMainId(FastJson.bean2Json(data), modeId);
		// 再将替换后的内容 重新赋值
		data = FastJson.json2Bean(content, ContextDataDto.class);
	}
}
