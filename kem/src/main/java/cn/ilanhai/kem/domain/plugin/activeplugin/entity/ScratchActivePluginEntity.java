package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import cn.ilanhai.kem.domain.enums.ActivePluginType;

/**
 * 刮刮乐活动
 * @author Nature
 *
 */
public class ScratchActivePluginEntity extends ActivePluginEntity{

	private static final long serialVersionUID = 8495251619673659367L;

	public ScratchActivePluginEntity(){
		this.activePluginType=ActivePluginType.SCRATCH;
	}
}
