package cn.ilanhai.framework.common.session.enums;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.common.consts.ModuleCode;

public enum ClientTypes {

	ProdSys("frontsys"), // 编辑系统
	BackManageSys("backmanagesys"), // 后台管理
	Topic("topic");// 专题
	
	private static final Map<String, ClientTypes> stringToEnum = new HashMap<String, ClientTypes>();
    static {
        for(ClientTypes moduleCode : values()) {
            stringToEnum.put(moduleCode.toString(), moduleCode);
        }
    }
	
	public static boolean contains(String clientType){
		return stringToEnum.containsKey(clientType);
	}
	
	private String str;

	private ClientTypes(String string)

	{
		str = string;
	}

	@Override
	public String toString() {
		return str;
	}
}
