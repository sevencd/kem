package cn.ilanhai.framework.common.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能模块编码
 * @author Nature
 *
 */
public enum ModuleCode {

	FRONTUSER_RESGIST("frontuser_regist"),
	FORGET_PWD("forget_pwd");
	
	private String code;
	private ModuleCode(String code){
		this.code=code;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
	
	private static final Map<String, ModuleCode> stringToEnum = new HashMap<String, ModuleCode>();
    static {
        for(ModuleCode moduleCode : values()) {
            stringToEnum.put(moduleCode.toString(), moduleCode);
        }
    }
	
	public static boolean contains(String moduleCode){
		return stringToEnum.containsKey(moduleCode);
	}
}
