package cn.ilanhai.kem.bl.plugin.activeplugin;

import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.NineGridActivePluginEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ScratchActivePluginEntity;

public class ActivePluginFac {

	public static ActivePluginEntity createActivePlugin(ActivePluginType activePluginType){
		
		ActivePluginEntity entity=null;
		switch(activePluginType){
		case NINEGRID:
			entity=new NineGridActivePluginEntity();
			break;
		case SCRATCH:
			entity=new ScratchActivePluginEntity();
			break;
		}
		
		return entity;
	}
}
