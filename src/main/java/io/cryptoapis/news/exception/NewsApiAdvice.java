package io.cryptoapis.news.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class NewsApiAdvice {
	  	@ExceptionHandler({ NotFoundException.class, NewsException.class,Exception.class })
	    public final ResponseEntity<ErrorResponse> handleException(Exception  ex, WebRequest request) {
	        HttpHeaders headers = new HttpHeaders();

	        if (ex instanceof NotFoundException) {
	            HttpStatus status = HttpStatus.NOT_FOUND;
	            NotFoundException nfe = (NotFoundException) ex;

	            return handleUserNotFoundException(nfe, headers, status, request);
	        }  else {
	            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	            return handleExceptionInternal(ex, null, headers, status, request);
	        }
	    }
	  /** Customize the response for NotFoundException. */
	    protected ResponseEntity<ErrorResponse> handleUserNotFoundException(NotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        List<String> errors = Collections.singletonList(ex.getMessage());
	        return handleExceptionInternal(ex, new ErrorResponse(status.ordinal(),errors.get(0)), headers, status, request);
	    }
	    
	    /** A single place to customize the response body of all Exception types. */
	    protected ResponseEntity<ErrorResponse> handleExceptionInternal(Exception ex, ErrorResponse body, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
	            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
	        }
	        return new ResponseEntity<>(new ErrorResponse(status.ordinal(),ex.getMessage()), headers, status);
	    }
}
