package com.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException2 extends RuntimeException {
	
	public ResourceNotFoundException2() {
		this("Resource not found!");
	}

	public ResourceNotFoundException2(String message) {
		this(message, null);
	}

	public ResourceNotFoundException2(String message, Throwable cause) {
		super(message, cause);
	}

}
