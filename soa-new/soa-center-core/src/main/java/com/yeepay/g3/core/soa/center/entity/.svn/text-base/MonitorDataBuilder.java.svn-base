package com.yeepay.g3.core.soa.center.entity;

public class MonitorDataBuilder {
	private String dateStr;
	private String service;
	private String method;
	private String type;
	private String consumer;
	private String provider;
	private int success;
	private int failure;
	private int elapsed;
	private int concurrent;
	private int maxElapsed;
	private int maxConcurrent;

	public MonitorDataBuilder setDateStr(String dateStr) {
		this.dateStr = dateStr;
		return this;
	}

	public MonitorDataBuilder setService(String service) {
		this.service = service;
		return this;
	}

	public MonitorDataBuilder setMethod(String method) {
		this.method = method;
		return this;
	}

	public MonitorDataBuilder setType(String type) {
		this.type = type;
		return this;
	}

	public MonitorDataBuilder setConsumer(String consumer) {
		this.consumer = consumer;
		return this;
	}

	public MonitorDataBuilder setProvider(String provider) {
		this.provider = provider;
		return this;
	}

	public MonitorDataBuilder setSuccess(int success) {
		this.success = success;
		return this;
	}

	public MonitorDataBuilder setFailure(int failure) {
		this.failure = failure;
		return this;
	}

	public MonitorDataBuilder setElapsed(int elapsed) {
		this.elapsed = elapsed;
		return this;
	}

	public MonitorDataBuilder setConcurrent(int concurrent) {
		this.concurrent = concurrent;
		return this;
	}

	public MonitorDataBuilder setMaxElapsed(int maxElapsed) {
		this.maxElapsed = maxElapsed;
		return this;
	}

	public MonitorDataBuilder setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
		return this;
	}

	public MonitorData createMonitorData() {
		return new MonitorData(dateStr, service, method, type, consumer, provider, success, failure, elapsed, concurrent, maxElapsed, maxConcurrent);
	}
}