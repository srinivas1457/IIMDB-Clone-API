package com.example.impl.exception;

public class ReviewNotfoundByIdException extends RuntimeException {

	private String message;

	public ReviewNotfoundByIdException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	
}
