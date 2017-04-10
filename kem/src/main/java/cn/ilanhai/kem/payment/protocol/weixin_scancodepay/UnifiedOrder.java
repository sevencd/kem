package cn.ilanhai.kem.payment.protocol.weixin_scancodepay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import cn.ilanhai.framework.uitl.Str;

//微信统一订单结构   详见:https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1
public class UnifiedOrder extends AbstractUnifiedOrder {
	private static final String TAG = "UnifiedOrderEntity";
	// 商品描述 body 是 String(32) Ipad mini 16G 白色 商品或支付单简要描述
	private String body = "";
	// 商品详情 detail 否 String(8192) Ipad mini 16G 白色 商品名称明细列表
	private String detail = "";
	// 附加数据 attach 否 String(127) 深圳分店 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	private String attach = "";
	// 商户订单号 out_trade_no 是 String(32) 20150806125346 商户系统内部的订单号,32个字符内、可包含字母,
	// 其他说明见商户订单号
	private String outTradeNo = "";
	// 货币类型 fee_type 否 String(16) CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	private String feeType = "";
	// 总金额 total_fee 是 Int 888 订单总金额，单位为分，详见支付金额
	private String totalFee = "";
	// 终端IP spbill_create_ip 是 String(16) 123.12.12.123
	// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String spbillCreateIp = "";
	// 交易起始时间 time_start 否 String(14) 20091225091010
	// 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	private String timeStart = "";
	// 交易结束时间 time_expire 否 String(14) 20091227091010
	// 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	// 注意：最短失效时间间隔必须大于5分钟
	private String timeExpire = "";
	// 商品标记 goods_tag 否 String(32) WXG 商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
	private String goodsTag = "";
	// 通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php
	// 接收微信支付异步通知回调地址
	private String notifyUrl = "";
	// 交易类型 trade_type 是 String(16) JSAPI 取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
	private TradeType tradeType = null;
	// 商品ID product_id 否 String(32) 12235413214070356458058
	// trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	private String productId = "";
	// 指定支付方式 limit_pay 否 String(32) no_credit no_credit--指定不能使用信用卡支付
	private String limitPay = "";
	// 用户标识 openid 否 String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
	// trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	private String openId = "";

	private String appKey = "";

	private String key = "";

	public UnifiedOrder(String appKey) {
		this.appKey = appKey;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String genPackageSign(Map<String, String> params) {
		StringBuilder sb = null;
		String packageSign = "";
		byte[] buff = null;
		List<String> keys = null;
		sb = new StringBuilder();
		keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			sb.append(keys.get(i));
			sb.append('=');
			sb.append(params.get(keys.get(i)));
			sb.append('&');
		}
		sb.append("key=");
		sb.append(this.key);
		packageSign = sb.toString();
		System.out.println(packageSign);
		buff = packageSign.getBytes();
		packageSign = AbstractUnifiedOrder.getMessageDigest(buff);
		packageSign = packageSign.toUpperCase();
		return packageSign;
	}

	private String toXml(Map<String, String> params) {

		StringBuilder sb = null;
		List<String> keys = null;
		keys = new ArrayList<String>(params.keySet());
		sb = new StringBuilder();
		sb.append("<xml>");
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			sb.append("<" + keys.get(i) + ">");
			sb.append("<![CDATA[" + params.get(keys.get(i)) + "]]>");
			sb.append("</" + keys.get(i) + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public String toXmlString() {
		StringBuffer xml = null;
		String sign = "";
		Map<String, String> packageParams = null;
		String xmlstring = "";
		NameValuePair nvp = null;
		try {
			xml = new StringBuffer();
			xml.append("</xml>");
			packageParams = new HashMap<String, String>();
			packageParams.put("appid", this.getAppId());

			// this.setBody("weixin");
			// nvp = new BasicNameValuePair("body", this.getBody());
			// packageParams.add(nvp);
			packageParams.put("body", this.getBody());
			if (!Str.isNullOrEmpty(this.getDetail()))
				packageParams.put("detail", this.getDetail());

			packageParams.put("mch_id", this.getMchId());

			packageParams.put("nonce_str", this.getNonceStr());

			packageParams.put("notify_url", this.getNotifyUrl());

			packageParams.put("out_trade_no", this.getOutTradeNo());

			this.setSpbillCreateIp("127.0.0.1");
			packageParams.put("spbill_create_ip", this.getSpbillCreateIp());

			// this.setTotalFee("1");
			packageParams.put("total_fee", this.getTotalFee());

			this.setTradeType(TradeType.NATIVE);
			packageParams.put("trade_type", this.getTradeType().toString());

			// this.setOpenId("92d27e2b0378ab772e152fca7f9ba38f");
			// packageParams.put("openid", this.getOpenId().toString());

			sign = genPackageSign(packageParams);
			this.setSign(sign);
			packageParams.put("sign", this.getSign());

			xmlstring = toXml(packageParams);

			return new String(xmlstring.getBytes(), "UTF-8");

		} catch (Exception e) {
			return null;
		}

	}

}
