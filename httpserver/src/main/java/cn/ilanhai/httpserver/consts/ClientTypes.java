package cn.ilanhai.httpserver.consts;

public enum ClientTypes {

	ProdSys("frontsys"), // 编辑系统
	BackManageSys("backmanagesys"), // 后台管理
	topic("topic");// 专题

	private String str;

	private ClientTypes(String string)

	{
		str = string;
	}

	@Override
	public String toString() {
		return str;
	}
}
