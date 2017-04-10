package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载付费配置入参
 * @author dgj
 *
 */
public class PayInfoLoadEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280084810583685192L;
	//查询套餐的类型
	private Integer type;
	//套餐id，用于查询套餐详情
	private Integer id;
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


}
