package com.ritesh.accountservice.exceptionhandler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author rites
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, " Access denied ", new HttpHeaders(), HttpStatus.FORBIDDEN, request);

	}

	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class,
			InvalidRequestException.class })
	public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage() + " Invalid request", new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAllRequest(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
