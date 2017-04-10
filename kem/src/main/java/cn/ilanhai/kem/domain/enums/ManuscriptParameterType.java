package cn.ilanhai.kem.domain.enums;

public enum ManuscriptParameterType {
	// 源数据(推广)
	extensionsource(0),
	// 用户标签
	tag(1),
	// 可发布状态
	publishstate(2),
	// 关键词
	keyword(3),
	// 发布名称
	publishname(4),
	// 发布审核时间 
	verifytime(5),
	// 上架/下架时间
	shelftime(6),
	// 下架原因
	bouncedReason(7),
	// 系统标签
	systag(8),
	// 审核名称
	verifyName(9),
	// 源数据(模板)
	templatesource(10),
	// 源数据(优秀案例)
	casesource(11),
	// 源数据(专题)
	specialsource(12),
	// 连接实体
	modelConfig(13),
	// 推广开始时间
	statrttime(14),
	// 推广结束时间
	endtime(15),
	// 活动开始时间
	statrtactivetime(16),
	// 活动结束时间
	endactivetime(17),
	// 生成目标(推广)
	extensiontarget(18),
	// 生成目标(案例)
	casetarget(19),
	// 发布时间
	publishtime(20),
	
	// 推广链接
	extensionurl(21),
	// 活动类型
	activeType(22),
	
	//稿件名称
	manuscriptName(23),
	
	//稿件封面
	manuscriptImg(24),
	
	//稿件主色调
	manuscriptMainColor(25),
	
	//稿件概述
	manuscriptSummary(26),
	
	//去版权次数
	unrights(27)
	;
	private Integer value;

	ManuscriptParameterType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
