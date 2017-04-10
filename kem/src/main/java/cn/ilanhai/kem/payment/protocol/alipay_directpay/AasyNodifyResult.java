package cn.ilanhai.kem.payment.protocol.alipay_directpay;

/**
 * 支付宝通知
 * 
 * @author he
 *
 */
public class AasyNodifyResult {

	/**
	 * 交易状态
	 * 
	 * @author he
	 *
	 */
	public enum TradeStatus {
		/**
		 * 交易创建，等待买家付款。
		 */
		WAIT_BUYER_PAY,
		/**
		 * 在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易。
		 */
		TRADE_CLOSED,
		/**
		 * 交易成功，且可对该交易做操作，如：多级分润、退款等。
		 */
		TRADE_SUCCESS,
		/**
		 * 等待卖家收款（买家付款后，如果卖家账号被冻结）。
		 */
		TRADE_PENDING,
		/**
		 * 交易成功且结束，即不可再做任何操作
		 */
		TRADE_FINISHED;
	}

	// 基本参数

	// notify_time 通知时间 Date 通知的发送时间。 格式为yyyy-MM-dd HH:mm:ss。不可空 2009-08-12
	// 11:08:32
	private String notifyTime;
	// notify_type 通知类型 String 通知的类型。 不可空 trade_status_sync
	private String notifyType;
	// notify_id 通知校验ID String 通知校验ID。 不可空 70fec0c2730b27528665af4517c27b95
	private String notifyId;
	// sign_type 签名方式 String DSA、RSA、MD5三个值可选，必须大写。 不可空 DSA
	private String signType;
	// sign 签名 String 请参见本文档“附录：签名与验签”。
	// 不可空_p_w_l_h_j0b_gd_aejia7n_ko4_m%2Fu_w_jd3_nx_s_k_mxus9_hoxg_y_r_lunli_pmma29_t_q%3D%
	// 3D
	// 业务参数
	private String sign;
	// out_trade_no 商户网站唯一订单号 String(64) 对应商户网站的订单系统中的唯一订单号，非支付宝交易号。
	// 需保证在商户网站中的唯一性。是请求时对应的参数，原样返回。 可空 3618810634349901
	private String outTradeNo;
	// subject 商品名称 String(256)
	// 商品的标题/交易标题/订单标题/订单关键字等。它在支付宝的交易明细中排在第一列，对于财务对账尤为重要。是请求时对应的参数，原样通知回来。 可空
	// phone手机
	private String subject;
	// payment_type 支付类型 String(4) 只支持取值为1（商品购买）。 可空 1
	private String paymentType;
	// trade_no 支付宝交易号 String(64) 该交易在支付宝系统中的交易流水号。最长64位。 可空
	// 2014040311001004370000361525
	private String tradeNo;
	// trade_status 交易状态 String 取值范围请参见交易状态。 可空 TRADE_FINISHED
	private TradeStatus tradeStatus;
	// gmt_create 交易创建时间 Date 该笔交易创建的时间。 格式为yyyy-MM-dd HH:mm:ss。 可空 2008-10-22
	// 20:49:31
	private String gmtCreate;
	// gmt_payment 交易付款时间 Date 该笔交易的买家付款时间。 格式为yyyy-MM-dd HH:mm:ss。 可空
	// 2008-10-22 20:49:50
	private String gmtPayment;
	// gmt_close 交易关闭时间 Date 交易关闭时间。 格式为yyyy-MM-dd HH:mm:ss。可空 2008-10-22
	// 20:49:46
	private String gmClose;
	// refund_status 退款状态 String 取值范围请参见退款状态。 可空 REFUND_SUCCESS
	private String refundStatus;
	// gmt_refund 退款时间 Date 卖家退款的时间，退款通知时会发送。 格式为yyyy-MM-dd HH:mm:ss。 可空
	// 2008-10-29 19:38:25
	private String gmtRefund;
	// seller_email 卖家支付宝账号 String(100) 卖家支付宝账号，可以是email和手机号码。 可空
	// chao.chenc1@alipay.com
	private String sellerEmail;
	// buyer_email 买家支付宝账号 String(100) 买家支付宝账号，可以是Email或手机号码。 可空 13758698870
	private String buyerEmail;
	// seller_id 卖家支付宝账户号 String(30) 卖家支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字。 可空
	// 2088002007018916
	// buyer_id 买家支付宝账户号 String(30) 买家支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字。 可空
	// 2088002007013600
	private String sellerId;
	// price 商品单价 Number
	// 如果请求时使用的是total_fee，那么price等于total_fee；如果请求时使用的是price，那么对应请求时的price参数，原样通知回来。
	// 可空 10.00
	private String price;
	// total_fee 交易金额 Number 该笔订单的总金额。 请求时对应的参数，原样通知回来。 可空 10.00
	private String totalFee;
	// quantity 购买数量 Number
	// 如果请求时使用的是total_fee，那么quantity等于1；如果请求时使用的是quantity，那么对应请求时的quantity参数，原样通知回来。
	// 可空 1
	private String quantity;
	// body 商品描述 String(1000) 该笔订单的备注、描述、明细等。 对应请求时的body参数，原样通知回来。 可空 Hello
	private String body;
	// discount 折扣 Number 支付宝系统会把discount的值加到交易金额上，如果需要折扣，本参数为负数。 可空-5
	private String discount;
	// is_total_fee_adjust 是否调整总价 String(1) 该交易是否调整过价格。 可空 N
	private String isTotalFeeAdjust;
	// use_coupon 是否使用红包买家 String(1) 是否在交易过程中使用了红包。 可空 N
	private String useCoupon;
	// extra_common_param 公用回传参数 String 用于商户回传参数，该值不能包含“=”、“&”等特殊字符。
	// 如果用户请求时传递了该参数，则返回给商户时会回传该参数。 可空 你好，这是测试商户的广告。
	private String extraCommonParam;
	// business_scene 是否扫码支付 String 回传给商户此标识为qrpay时，表示对应交易为扫码支付。
	// 目前只有qrpay一种回传值。非扫码支付方式下，目前不会返回该参数。
	private String businessScene;

	public AasyNodifyResult() {

	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getGmClose() {
		return gmClose;
	}

	public void setGmClose(String gmClose) {
		this.gmClose = gmClose;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getIsTotalFeeAdjust() {
		return isTotalFeeAdjust;
	}

	public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
		this.isTotalFeeAdjust = isTotalFeeAdjust;
	}

	public String getUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(String useCoupon) {
		this.useCoupon = useCoupon;
	}

	public String getExtraCommonParam() {
		return extraCommonParam;
	}

	public void setExtraCommonParam(String extraCommonParam) {
		this.extraCommonParam = extraCommonParam;
	}

	public String getBusinessScene() {
		return businessScene;
	}

	public void setBusinessScene(String businessScene) {
		this.businessScene = businessScene;
	}

}
