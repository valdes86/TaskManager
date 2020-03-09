package com.mimacom.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3290658256271102527L;

	public TaskNotFoundException() {
	        super();
	    }

	public TaskNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }

	public TaskNotFoundException(String message) {
	        super(message);
	    }

	public TaskNotFoundException(Throwable cause) {
	        super(cause);
	    }
}
