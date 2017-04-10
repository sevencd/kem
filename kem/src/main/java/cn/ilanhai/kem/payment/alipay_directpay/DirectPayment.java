package cn.ilanhai.kem.payment.alipay_directpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.Null;
import org.apache.log4j.helpers.NullEnumeration;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.payment.AbstractPayment;
import cn.ilanhai.kem.payment.PaymentConfig;
import cn.ilanhai.kem.payment.PaymentInfo;
import cn.ilanhai.kem.payment.protocol.alipay_directpay.AasyNodifyResult;

/**
 * 对支付宝即时到账交易接口封装
 * 
 * @author he
 *
 */
public class DirectPayment extends AbstractPayment {
	/**
	 * 签名方式
	 */
	private final String SIGN_TYPE = "RSA";
	/**
	 * 字符编码格式 目前支持 gbk 或 utf-8
	 */
	private final String INPUT_CHARSET = "UTF-8";
	/**
	 * 支付类型 ，无需修改
	 */
	private final String PAYMENT_TYPE = "1";
	/**
	 * 调用的接口名，无需修改
	 */
	private final String SERVICE = "create_direct_pay_by_user";
	/**
	 * API
	 */
	private final String DIRECT_PAY_API = "https://mapi.alipay.com/gateway.do";

	/**
	 * 请求参数(参数详情见:https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0
	 * .UssjlI&treeId=62&articleId=104743&docType=1)
	 */
	private Map<String, String> paramerter = null;

	public DirectPayment() {
		this.paramerter = new HashMap<String, String>();
		// this.setParamerter("service", SERVICE);
		// this.setParamerter("partner", "00000000000000000000");
		// this.setParamerter("_input_charset", INPUT_CHARSET);
		// this.setParamerter("sign_type", SIGN_TYPE);
		// this.setParamerter("sign", null);
		// this.setParamerter("notify_url", "http://www.xxx.com");
		// this.setParamerter("return_url", null);
		// //
		// this.setParamerter("out_trade_no", null);
		// this.setParamerter("subject", null);
		// this.setParamerter("payment_type", PAYMENT_TYPE);
		// this.setParamerter("total_fee", null);
		// this.setParamerter("seller_id", null);
		// this.setParamerter("seller_email", null);
		// this.setParamerter("seller_account_name", null);
		// this.setParamerter("buyer_id", null);
		// this.setParamerter("buyer_email", null);
		// this.setParamerter("buyer_account_name", null);
		// this.setParamerter("price", null);
		// this.setParamerter("quantity", null);
		// this.setParamerter("body", null);
		// this.setParamerter("show_url", null);
		// this.setParamerter("paymethod", null);
		// this.setParamerter("enable_paymethod", null);
		// this.setParamerter("anti_phishing_key", null);
		// this.setParamerter("exter_invoke_ip", null);
		// this.setParamerter("extra_common_param", null);
		// this.setParamerter("it_b_pay", null);
		// this.setParamerter("token", null);
		// this.setParamerter("qr_pay_mode", null);
		// this.setParamerter("qrcode_width", null);
		// this.setParamerter("need_buyer_realnamed", null);
		// this.setParamerter("hb_fq_param", null);
		// this.setParamerter("goods_type", null);
		// this.setParamerter("service", SERVICE);
		// this.setParamerter("partner", "00000000000000000000");
		// this.setParamerter("seller_id", "00000000000000000000");
		// this.setParamerter("_input_charset", INPUT_CHARSET);
		// this.setParamerter("payment_type", PAYMENT_TYPE);
		// this.setParamerter("notify_url", "http://www.xxx.com");
		// this.setParamerter("return_url", null);
		// this.setParamerter("anti_phishing_key", null);
		// this.setParamerter("exter_invoke_ip", null);
		// this.setParamerter("out_trade_no", "out_trade_no");
		// this.setParamerter("subject", "subject");
		// this.setParamerter("total_fee", "total_fee");
		// this.setParamerter("body", "body");

	}

	@Override
	public String pay(PaymentConfig config, PaymentInfo info)
			throws AppException {
		CodeTable ct;
		String tmp = null;
		DirectPayPaymentConfig cfg = null;
		Map<String, String> params = null;
		String paramsStr = null;
		ct = CodeTable.BL_PAYMENT_ERROR;
		if (!(config instanceof DirectPayPaymentConfig))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		cfg = (DirectPayPaymentConfig) config;
		if (cfg == null)
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		this.setParamerter("service", SERVICE);
		tmp = cfg.getPublicKey();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		this.setParamerter("_input_charset", INPUT_CHARSET);
		this.setParamerter("payment_type", PAYMENT_TYPE);
		tmp = cfg.getPartner();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		this.setParamerter("partner", tmp);
		tmp = cfg.getSellerId();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		this.setParamerter("seller_id", tmp);
		tmp = cfg.getNotifyUrl();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		this.setParamerter("notify_url", tmp);
		tmp = cfg.getReturnUrl();
		if (!Str.isNullOrEmpty(tmp))
			this.setParamerter("return_url", tmp);
		tmp = cfg.getPrivateKey();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "支付宝参数配置错误");
		if (info == null)
			throw new AppException(ct.getValue(), "定单信息错误");
		tmp = info.getOrderId();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "定单信息错误");
		this.setParamerter("out_trade_no", tmp);
		tmp = info.getName();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "定单信息错误");
		this.setParamerter("subject", tmp);
		tmp = info.getTotalFee();
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "定单信息错误");
		this.setParamerter("total_fee", tmp);
		tmp = info.getDescription();
		if (!Str.isNullOrEmpty(tmp))
			this.setParamerter("body", tmp);
		params = this.buildParamerter(this.paramerter, cfg.getPrivateKey());
		paramsStr = this.concat1(params);
		return String.format("%s?_input_charset=%s&%s", DIRECT_PAY_API,
				INPUT_CHARSET, paramsStr);
	}

	private void setParamerter(String key, String value) {
		String tmp = null;

		if (Str.isNullOrEmpty(key))
			return;
		this.paramerter.put(key, value);

	}

	/**
	 * 构建请求参数
	 * 
	 * @param paramerter
	 * @return
	 */
	private Map<String, String> buildParamerter(Map<String, String> paramerter,
			String privateKey) {
		Map<String, String> res = null;
		String sign = null;
		// 除去数组中的空值和签名参数
		res = this.paramerterFilter(paramerter);
		// 生成签名结果
		sign = this.sign(res, privateKey);
		// 签名结果与签名方式加入请求提交参数组中
		res.put("sign", sign);
		res.put("sign_type", SIGN_TYPE);
		return res;

	}

	/**
	 * 请求参数过滤
	 * 
	 * @param paramerter
	 *            请求参数
	 * @return
	 */
	private Map<String, String> paramerterFilter(Map<String, String> paramerter) {
		Map<String, String> res = null;
		String value = null;
		res = new HashMap<String, String>();
		if (paramerter == null || paramerter.size() <= 0)
			return res;
		for (String key : paramerter.keySet()) {
			value = paramerter.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}

			res.put(key, value);
		}
		return res;
	}

	/**
	 * 生成签名结果
	 * 
	 * @param paramerter
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	private String sign(Map<String, String> paramerter, String privateKey) {
		String prestr = null;
		String signStr = "";
		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr = this.concat(paramerter);
		signStr = RSA.sign(prestr, privateKey, INPUT_CHARSET);
		return signStr;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private String concat(Map<String, String> params) {
		List<String> keys = null;
		keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		String key = null;
		String value = null;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = params.get(key);
			System.out.println(String.format("%s=%s", key, value));
			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	private String concat1(Map<String, String> params) {
		List<String> keys = null;
		keys = new ArrayList<String>(params.keySet());
		String prestr = "";
		String key = null;
		String value = null;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = params.get(key);
			value = URLEncoder.encode(value);
			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	@Override
	public String nodify(Map<String, String> params) {
		AasyNodifyResult anr = null;
		AasyNodifyResult.TradeStatus tradeStatus;
		String tmp = null;
		try {
			if (params == null || params.size() <= 0)
				return null;
			tmp = params.get("trade_status");
			if (tmp == null || tmp.length() <= 0)
				return null;
			tradeStatus = tmp
					.equalsIgnoreCase(AasyNodifyResult.TradeStatus.TRADE_SUCCESS
							.toString()) ? AasyNodifyResult.TradeStatus.TRADE_SUCCESS
					: null;
			if (tradeStatus != AasyNodifyResult.TradeStatus.TRADE_SUCCESS)
				return null;
			tmp = params.get("out_trade_no");
			anr = new AasyNodifyResult();
			anr.setTradeStatus(tradeStatus);
			anr.setOutTradeNo(tmp);
			return tmp;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
