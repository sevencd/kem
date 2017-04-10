package cn.ilanhai.kem.domain.paymentservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.PayStatus;
import cn.ilanhai.kem.domain.enums.PayWay;

/**
 * 支付订单实体
 * @author Nature
 *
 */
public class PaymentOrderEntity extends AbstractEntity{

	public static final String MEMBER="开通会员";
	public static final String UNRIGHT="去版权";
	public static final String EMAIL="购买邮件";
	public static final String SMS="购买短信";
	public static final String CUSTOMERCLUE = "客户线索";//客户线索
	public static final String B2B = "B2B营销";
	public static final String SUBACCOUNT = "子账号";//子账号
	public static final String PUBLISHNUM = "发布次数";//发布次数

	
	private static final long serialVersionUID = 6590816025243165047L;
	
	//订单ID
	private String orderId;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	//用户ID
	private String userId;
	//支付状态：0 等待支付 1 已支付
	private int payStatus;
	//支付时间
	private Date payTime;
	//支付方式
	private int payWay;
	//实付金额
	private double payAmount;
	//应付金额
	private double amountPayable;
	//描述
	private String description;
	//服务套餐编号
	private Integer packageServiceId;
	//订单详情
	private List<PaymentOrderInfoEntity> orderInfo=new ArrayList<PaymentOrderInfoEntity>();
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public double getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(double amountPayable) {
		this.amountPayable = amountPayable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<PaymentOrderInfoEntity> getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(List<PaymentOrderInfoEntity> orderInfo) {
		this.orderInfo = orderInfo;
	}
	public Integer getPackageServiceId() {
		return packageServiceId;
	}
	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
	
	
}
