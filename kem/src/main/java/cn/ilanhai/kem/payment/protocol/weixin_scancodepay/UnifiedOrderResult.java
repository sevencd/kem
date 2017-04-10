package cn.ilanhai.kem.payment.protocol.weixin_scancodepay;

//微信统一订单结构   详见:https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1
public class UnifiedOrderResult extends AbstractUnifiedOrder {
	// 业务结果 result_code 是 String(16) SUCCESS SUCCESS/FAIL
	private ResultCode resultCode = null;
	// 错误代码 err_code 否 String(32) SYSTEMERROR 详细参见第6节错误列表
	private ErrCode errCode = null;
	// 错误代码描述 err_code_des 否 String(128) 系统错误 错误返回的信息描述
	private String errCodeDesc = "";
	// 交易类型 trade_type 是 String(16) JSAPI
	// 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
	private TradeType tradeType = null;
	// 预支付交易会话标识 prepay_id 是 String(64) wx201410272009395522657a690389285100
	// 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	private String prepayId = "";
	// 二维码链接 code_url 否 String(64) URl：weixin：//wxpay/s/An4baqw
	// trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
	private String codeUrl = "";

	public UnifiedOrderResult() {

	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}

	public ErrCode getErrCode() {
		return errCode;
	}

	public void setErrCode(ErrCode errCode) {
		this.errCode = errCode;
	}

	public void setErrCode(String errCode) {
		ErrCode ec = null;
		errCode = errCode.toUpperCase();
		if (errCode.equals(ErrCode.APPID_MCHID_NOT_MATCH.toString())) {
			ec = ErrCode.APPID_MCHID_NOT_MATCH;
		} else if (errCode.equals(ErrCode.APPID_NOT_EXIST.toString())) {
			ec = ErrCode.APPID_NOT_EXIST;
		} else if (errCode.equals(ErrCode.LACK_PARAMS.toString())) {
			ec = ErrCode.LACK_PARAMS;
		} else if (errCode.equals(ErrCode.MCHID_NOT_EXIST.toString())) {
			ec = ErrCode.MCHID_NOT_EXIST;
		} else if (errCode.equals(ErrCode.NOAUTH.toString())) {
			ec = ErrCode.NOAUTH;
		} else if (errCode.equals(ErrCode.NOT_UTF8.toString())) {
			ec = ErrCode.NOT_UTF8;
		} else if (errCode.equals(ErrCode.NOTENOUGH.toString())) {
			ec = ErrCode.NOTENOUGH;
		} else if (errCode.equals(ErrCode.ORDERCLOSED.toString())) {
			ec = ErrCode.ORDERCLOSED;
		} else if (errCode.equals(ErrCode.ORDERPAID.toString())) {
			ec = ErrCode.ORDERPAID;
		} else if (errCode.equals(ErrCode.OUT_TRADE_NO_USED.toString())) {
			ec = ErrCode.OUT_TRADE_NO_USED;
		} else if (errCode.equals(ErrCode.POST_DATA_EMPTY.toString())) {
			ec = ErrCode.POST_DATA_EMPTY;
		} else if (errCode.equals(ErrCode.REQUIRE_POST_METHOD.toString())) {
			ec = ErrCode.REQUIRE_POST_METHOD;
		} else if (errCode.equals(ErrCode.SIGNERROR.toString())) {
			ec = ErrCode.SIGNERROR;
		} else if (errCode.equals(ErrCode.SYSTEMERROR.toString())) {
			ec = ErrCode.SYSTEMERROR;
		} else if (errCode.equals(ErrCode.XML_FORMAT_ERROR.toString())) {
			ec = ErrCode.XML_FORMAT_ERROR;
		} else {

		}
		this.errCode = ec;
	}

	public String getErrCodeDesc() {
		return errCodeDesc;
	}

	public void setErrCodeDesc(String errCodeDesc) {
		this.errCodeDesc = errCodeDesc;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public void setTradeType(String tradeType) {
		AbstractUnifiedOrder.TradeType tt = null;
		tradeType = tradeType.toUpperCase();
		if (tradeType.equals(AbstractUnifiedOrder.TradeType.APP.toString())) {
			tt = AbstractUnifiedOrder.TradeType.APP;
		}
		if (tradeType.equals(AbstractUnifiedOrder.TradeType.JSAPI.toString())) {
			tt = AbstractUnifiedOrder.TradeType.JSAPI;
		}
		if (tradeType.equals(AbstractUnifiedOrder.TradeType.NATIVE.toString())) {
			tt = AbstractUnifiedOrder.TradeType.NATIVE;
		} else {

		}
		this.tradeType = tt;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public enum ResultCode {
		SUCCESS, FAIL
	}

	public enum ErrCode {
		// NOAUTH 商户无此接口权限 商户未开通此接口权限 请商户前往申请此接口权限
		NOAUTH,
		// NOTENOUGH 余额不足 用户帐号余额不足 用户帐号余额不足，请用户充值或更换支付卡后再支付
		NOTENOUGH,
		// ORDERPAID 商户订单已支付 商户订单已支付，无需重复操作 商户订单已支付，无需更多操作
		ORDERPAID,
		// ORDERCLOSED 订单已关闭 当前订单已关闭，无法支付 当前订单已关闭，请重新下单
		ORDERCLOSED,
		// SYSTEMERROR 系统错误 系统超时 系统异常，请用相同参数重新调用
		SYSTEMERROR,
		// APPID_NOT_EXIST APPID不存在 参数中缺少APPID 请检查APPID是否正确
		APPID_NOT_EXIST,
		// MCHID_NOT_EXIST MCHID不存在 参数中缺少MCHID 请检查MCHID是否正确
		MCHID_NOT_EXIST,
		// APPID_MCHID_NOT_MATCH appid和mch_id不匹配 appid和mch_id不匹配
		// 请确认appid和mch_id是否匹配
		APPID_MCHID_NOT_MATCH,
		// LACK_PARAMS 缺少参数 缺少必要的请求参数 请检查参数是否齐全
		LACK_PARAMS,
		// OUT_TRADE_NO_USED 商户订单号重复 同一笔交易不能多次提交 请核实商户订单号是否重复提交
		OUT_TRADE_NO_USED,
		// SIGNERROR 签名错误 参数签名结果不正确 请检查签名参数和方法是否都符合签名算法要求
		SIGNERROR,
		// XML_FORMAT_ERROR XML格式错误 XML格式错误 请检查XML参数格式是否正确
		XML_FORMAT_ERROR,
		// REQUIRE_POST_METHOD 请使用post方法 未使用post传递参数 请检查请求参数是否通过post方法提交
		REQUIRE_POST_METHOD,
		// POST_DATA_EMPTY post数据为空 post数据不能为空 请检查post数据是否为空
		POST_DATA_EMPTY,
		// NOT_UTF8 编码格式错误 未使用指定编码格式 请使用NOT_UTF8编码格式
		NOT_UTF8
	}

	@Override
	public String toXmlString() {
		return null;
	}

}
