package com.yeepay.g3.facade.soa.center.dubbo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class DependItem {

	private String application;

	private int index;

	private int level;

	private DependItem parent;

	private final List<Integer> recursives = new ArrayList<Integer>();

	public DependItem() {
	}

	public DependItem(String application, int level) {
		this.application = application;
		this.level = level;
	}

	public DependItem(DependItem parent, String application, int level, int index) {
		this.parent = parent;
		this.application = application;
		this.level = level;
		this.index = index;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public DependItem getParent() {
		return parent;
	}

	public void setParent(DependItem parent) {
		this.parent = parent;
	}

	public List<Integer> getRecursives() {
		return recursives;
	}

	public void addRecursive(int padding, int value) {
		while (recursives.size() < padding) {
			recursives.add(0);
		}
		recursives.add(value);
	}

}
