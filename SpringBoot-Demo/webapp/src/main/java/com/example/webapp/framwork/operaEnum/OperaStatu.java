package com.example.webapp.framwork.operaEnum;

public enum OperaStatu {
	SUCCESS("1","操作成功"),
	FAIL("0","操作失败");
	String code;
	String desc;
	private OperaStatu(String code , String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
}
