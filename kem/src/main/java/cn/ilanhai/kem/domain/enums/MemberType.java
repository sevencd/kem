package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IBaseEnum;

/**
 * 
 * 会员类别
 * @time 2017-03-21 16:46
 * @author csz
 */
public enum MemberType implements IBaseEnum  {
	VipUser(0,"会员"),//会员包括企业基础版，企业标准版，企业高级版
	ApplyTrial(1,"申请试用"),
	TrialEdition(2,"试用版"),
	SubAccount(3,"子账号"),
	/*BaseEdition(3,"企业基础版"),
	StandardEdition(4,"企业标准版"),
	SeniorEdition(5,"企业高级版")*/;
	
	private Integer code;

	private String 	name;
	
	private MemberType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	@Override
	public Integer getCode() {
		return code;
	}
	@Override
	public String getName() {
		return name;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}

}
