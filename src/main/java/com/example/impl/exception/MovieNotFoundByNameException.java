package com.example.impl.exception;

public class MovieNotFoundByNameException extends RuntimeException {
	
	private String message;

	public MovieNotFoundByNameException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}


}
