package cn.ilanhai.kem.domain.enums;

public enum PluginType {

	/**
	 * 表单插件
	 */
	FORMPLUGIN(1),

	/**
	 * 活动插件
	 */
	ACTIVEPLUGIN(2);
	
	private final int value;

	private PluginType(int value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public static PluginType getEnum(Integer value){
		switch(value){
		case 1:return FORMPLUGIN;
		case 2:return ACTIVEPLUGIN;
		}
		return null;
	}
}
