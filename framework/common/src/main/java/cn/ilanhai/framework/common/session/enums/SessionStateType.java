package cn.ilanhai.framework.common.session.enums;

import java.util.HashMap;
import java.util.Map;

public enum SessionStateType {
	ANONYMOUS_SESSION_STATE("anonymous_state"), 
	FRONT_USER_LOGINED_STATE("frontuser_logined_state"),
	BACK_USER_LOGINED_STATE("backuser_logined_state");
	private String value;

	private SessionStateType(String value) {
		this.value = value;
	}

//	public String getValue() {
//		return this.value;
//	}
	public String toString() {
		return this.value;
	};

	private static final Map<String, SessionStateType> stringToEnum = new HashMap<String, SessionStateType>();
    static {
        for(SessionStateType sessionStateType : values()) {
            stringToEnum.put(sessionStateType.toString(), sessionStateType);
        }
    }

    public static SessionStateType stringtoEnum(String value){
    	if(value==null)return null;
    	if(stringToEnum.containsKey(value)){
    		return stringToEnum.get(value);
    	}
    	return null;
    }

}
