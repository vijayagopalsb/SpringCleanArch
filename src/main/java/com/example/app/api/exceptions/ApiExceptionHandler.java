package com.example.app.api.exceptions;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", ZonedDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		body.put("message", ex.getMessage());
		body.put("path", request.getRequestURI());
		// Optionally, add more fields, e.g., "path"
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND); 
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateEmail(DuplicateEmailException duplicateEmailException, HttpServletRequest  request) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", ZonedDateTime.now());
		body.put("status", 409);
		body.put("error", "Conflict");
		body.put("message", duplicateEmailException.getMessage());
		body.put("path", request.getRequestURI());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
		
	}

}
