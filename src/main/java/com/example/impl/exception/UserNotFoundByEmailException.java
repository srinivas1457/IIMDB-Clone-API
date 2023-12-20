package com.example.impl.exception;

public class UserNotFoundByEmailException extends RuntimeException {
	private String message;

	public UserNotFoundByEmailException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
