package cn.ilanhai.kem.payment.protocol.weixin_scancodepay;

/**
 * 通知返回结果
 * 
 * @author he
 *
 */
public class ReturnResult {
	// 返回状态码 return_code 是 String(16) SUCCESS SUCCESS/FAIL
	private String returnCode;
	// SUCCESS表示商户接收通知成功并校验成功
	// 返回信息 return_msg 否 String(128) OK 返回信息，如非空，为错误原因：签名失败 参数格式校验错误
	private String returnMsg;

	public ReturnResult() {

	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String toXml() {
		return String
				.format("<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>",
						this.returnCode, this.returnMsg);
	}
}
