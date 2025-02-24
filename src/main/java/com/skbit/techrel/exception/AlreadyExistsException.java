package com.skbit.techrel.exception;

public class AlreadyExistsException extends RuntimeException {

	private String msg;

	public AlreadyExistsException(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return msg;
	}

}
