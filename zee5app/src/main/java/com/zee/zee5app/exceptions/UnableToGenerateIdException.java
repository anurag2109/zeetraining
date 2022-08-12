package com.zee.zee5app.exceptions;

public class UnableToGenerateIdException extends Exception {
	//step to create the custom exception:
	// it should have toString method: either write @ToString above the class or override the toString() method
	// super call
	public UnableToGenerateIdException(String msg) {
		super(msg);
	}
	@Override
	public String toString() {
		return super.getMessage();
	}
}
