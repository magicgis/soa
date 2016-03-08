package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by menghao.chen
 */
public class SystemInfoDTO implements Serializable {
	private static final long serialVersionUID = -4695007878556914272L;

	private String version;
	private String host;
	private String os;
	private String java;
	private String cpu;
	private String locale;
	private String uptime;
	private Date time;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getJava() {
		return java;
	}

	public void setJava(String java) {
		this.java = java;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
