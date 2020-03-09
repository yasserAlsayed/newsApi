package io.cryptoapis.news.exception;

import java.io.Serializable;

public class ErrorResponse implements Serializable{
	private static final long serialVersionUID = -370930104075428461L;
	private int errorCode;
	private String message;

	
	public ErrorResponse(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
