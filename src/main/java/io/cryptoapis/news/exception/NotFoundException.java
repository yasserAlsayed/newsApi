package io.cryptoapis.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends NewsException {
	private static final long serialVersionUID = -8530066808250620741L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
