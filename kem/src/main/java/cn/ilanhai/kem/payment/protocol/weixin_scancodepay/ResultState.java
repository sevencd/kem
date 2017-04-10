package cn.ilanhai.kem.payment.protocol.weixin_scancodepay;

public class ResultState {
	// 返回状态码 return_code 是 String(16) SUCCESS
	// SUCCESS/FAIL
	// 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	private ReturnCode returnCode = null;

	// 返回信息 return_msg 否 String(128) 签名失败
	// 返回信息，如非空，为错误原因
	// 签名失败 参数格式校验错误
	private String returnMsg = "";

	public ResultState() {

	}
	
	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public enum ReturnCode {
		SUCCESS, FAIL
	}
}
