package com.mirabilis.account.web;

import java.io.Serializable;

public class ApiMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public ApiMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
