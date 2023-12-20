package com.example.impl.exception;

public class ReviewDataNotPresent extends RuntimeException {
	private String message;

	public ReviewDataNotPresent(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	

}
