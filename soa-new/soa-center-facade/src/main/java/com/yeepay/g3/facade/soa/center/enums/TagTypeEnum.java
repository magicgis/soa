package com.yeepay.g3.facade.soa.center.enums;

/**
 * 服务标签类别
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 下午12:08:56
 * @version:
 */
public enum TagTypeEnum {
	/**
	 * 应用标签
	 */
	APP("应用"),

	/**
	 * 服务标签
	 */
	SERVICE("服务");

	private String desc;

	private TagTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
