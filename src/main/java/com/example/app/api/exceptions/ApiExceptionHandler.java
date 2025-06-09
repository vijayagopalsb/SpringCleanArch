package com.example.app.api.exceptions;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.app.constants.ApiConstants;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	// 1. Custom User Not Found Exception - More Specific
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put(ApiConstants.TIMESTAMP, ZonedDateTime.now());
		body.put(ApiConstants.STATUS, HttpStatus.NOT_FOUND.value());
		body.put(ApiConstants.ERROR, "Not Found");
		body.put(ApiConstants.MESSAGE, ex.getMessage());
		body.put(ApiConstants.PATH, request.getRequestURI());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	// 2. Custom Duplicate Email Exception - More Specific
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateEmail(DuplicateEmailException duplicateEmailException,
			HttpServletRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put(ApiConstants.TIMESTAMP, ZonedDateTime.now());
		body.put(ApiConstants.STATUS, 409);
		body.put(ApiConstants.ERROR, "Conflict");
		body.put(ApiConstants.MESSAGE, duplicateEmailException.getMessage());
		body.put(ApiConstants.PATH, request.getRequestURI());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);

	}

	// 3. Data Integrity Violation - Generic Safety Net
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		return buildResponse(409, "Conflict", "A user already exists with this email address.",
				request.getRequestURI());
	}

	// 4. Catch-All for Unhandled Exceptions - Optional
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleAll(Exception ex, HttpServletRequest request) {
		return buildResponse(500, "Internal Server Error", ex.getMessage(), request.getRequestURI());
	}

	// --- Utility Method for Response Construction ---
	private ResponseEntity<Map<String, Object>> buildResponse(int status, String error, String message, String path) {
		Map<String, Object> body = new HashMap<>();
		body.put(ApiConstants.TIMESTAMP, ZonedDateTime.now());
		body.put(ApiConstants.STATUS, status);
		body.put( ApiConstants.ERROR, error);
		body.put(ApiConstants.MESSAGE, message);
		body.put(ApiConstants.PATH, path);
		return new ResponseEntity<>(body, HttpStatus.valueOf(status));
	}

}
