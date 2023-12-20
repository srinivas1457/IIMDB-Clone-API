package com.example.impl.exception;

public class MovieNotFoundByIdException extends RuntimeException {
	private String message;

	public MovieNotFoundByIdException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}


}
