package cn.ilanhai.kem.domain.paymentservice.PayConfig;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 支付配置实体
 * @author dgj
 *
 */
public class PayConfigResponseEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4981984744760090415L;
	private Integer id;
	private String sysKey;//key
	private String sysValue;//value
	private String describe;//描述
	private Integer type;//配置信息类型 0 支付宝，1微信
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysValue() {
		return sysValue;
	}
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
