package com.ritesh.accountservice.exceptionhandler;

public class UserAccountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAccountException(String message, Throwable cause) {
		super(message, cause);
	}
	// ...
}
