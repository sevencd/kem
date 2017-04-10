package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import cn.ilanhai.kem.domain.enums.ActivePluginType;

/**
 * 九宫格活动插件
 * @author Nature
 *
 */
public class NineGridActivePluginEntity extends ActivePluginEntity{

	private static final long serialVersionUID = -8932817558859104021L;

	public NineGridActivePluginEntity(){
		this.activePluginType=ActivePluginType.NINEGRID;
	}

}
