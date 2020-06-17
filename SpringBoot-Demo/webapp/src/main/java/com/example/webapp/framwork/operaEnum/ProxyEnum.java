package com.example.webapp.framwork.operaEnum;

public class ProxyEnum {
	public enum LogTypeEnum{
		UNKNOW("99","未知");
		String code;
		String desc;
		LogTypeEnum(String code,String desc) {
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

	public enum OperaTypeEnum{
		UNKONWN("9999","未知操作");
		String code;
		String desc;
		OperaTypeEnum(String code,String desc) {
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
}
