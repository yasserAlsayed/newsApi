package io.cryptoapis.news.exception;

public class NewsException extends RuntimeException {
	private static final long serialVersionUID = -123L;
	private String errorMessage;

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public NewsException() {
		super();
	}

	public NewsException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
}
