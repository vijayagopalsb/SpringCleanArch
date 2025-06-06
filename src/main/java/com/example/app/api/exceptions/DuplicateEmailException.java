package com.example.app.api.exceptions;

public class DuplicateEmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String email) {

		super("A user already exists with email: " + email);

	}

}
