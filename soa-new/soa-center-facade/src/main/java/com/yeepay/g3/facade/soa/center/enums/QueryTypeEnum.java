package com.yeepay.g3.facade.soa.center.enums;

/**
 * 服务查询类型枚举类
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:30:39
 * @version:
 */
public enum QueryTypeEnum {
	/**
	 * 按服务查询
	 */
	SERVICE("服务"),

	/**
	 * 按服务标签查询
	 */
	TAG("标签"),

	APP("应用"),

	IP("机器"),

	ROUTER("路由");

	private String desc;

	private QueryTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
