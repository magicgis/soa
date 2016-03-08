package com.yeepay.g3.app.soa.center.dto;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Title: 响应消息<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company:<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 12-7-2 上午8:32
 */
public class ResponseMessage {

	private Status status;

	private String errMsg;

	private Map<String, Object> data = Maps.newHashMap();

	public ResponseMessage(Object message) {
		this.status = Status.SUCCESS;
		if (message instanceof String) {
			this.errMsg = (String) message;
		} else {
			this.errMsg = message != null ? message.toString() : null;
		}
	}

	public ResponseMessage(Status status, Object message) {
		this.status = status;
		if (message instanceof String) {
			this.errMsg = (String) message;
		} else {
			this.errMsg = message != null ? message.toString() : null;
		}
	}

	public ResponseMessage setStatus(Status status) {
		this.status = status;
		return this;
	}

	public ResponseMessage setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	/**
	 * 自定义返回键值，其中value必须可序列化
	 *
	 * @param key
	 * @param value
	 */
	public ResponseMessage(String key, Object value) {
		this.status = Status.SUCCESS;
		this.errMsg = "";
		this.data.put(key, value);
	}

	public ResponseMessage put(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	public String getStatus() {
		return this.status.value;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public enum Status {

		SUCCESS("success"), INFO("info"), WARNING("warning"), ERROR("error");

		private String value;

		Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
