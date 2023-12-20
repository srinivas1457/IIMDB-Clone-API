package com.example.impl.exception;

public class MovieDataNotPresent extends RuntimeException {
	private String message;

	public MovieDataNotPresent(String message) {
		super(message);
		this.message = message;
	}

	
	

}
