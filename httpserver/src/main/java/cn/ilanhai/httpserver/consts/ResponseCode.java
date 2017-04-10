package cn.ilanhai.httpserver.consts;

public enum ResponseCode {

	SUCCESS(0,"请求成功"),
	REQUEST_DATA_ERROR(1,"请求参数错误"),
	REQUEST_VALIDATE_WRONG(2,"验证码错误"),
	SESSION_STATE_ERROR(3,"会话状态错误"),
	FRONTUSER_LOGIN_ERROR(4,"用户名或者密码错误"),
	BACKUSER_LOGIN_ERROR(5,"用户名或者密码错误");
	
	private String str;
	private int num;
    
    private ResponseCode(int num,String str)
     
    {
    	this.str=str;
    	this.num=num;
    }
    @Override
    public String toString() {
    	return str;
    }
    public int getCode(){
    	return num;
    }
}
