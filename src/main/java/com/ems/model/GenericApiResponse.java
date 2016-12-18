package com.ems.model;

/**
 * Generic API Response Entity
 * @author reshmivn
 * @since 0.0.1
 */
public class GenericApiResponse {
	private String message;
	private Object data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
