package com.example.impl.exception;

public class UsersDataNotPresent extends RuntimeException {
	private String message;

	public UsersDataNotPresent(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	

}
