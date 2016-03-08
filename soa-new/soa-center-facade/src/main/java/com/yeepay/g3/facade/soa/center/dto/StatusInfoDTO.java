package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;

/**
 * @author by menghao.chen
 */
public class StatusInfoDTO implements Serializable {
	private static final long serialVersionUID = 4679353594664790158L;

	private String name;
	private String level;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
