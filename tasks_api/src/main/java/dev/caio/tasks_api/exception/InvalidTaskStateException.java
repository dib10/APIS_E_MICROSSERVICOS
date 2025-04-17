package dev.caio.tasks_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidTaskStateException extends RuntimeException {
	
	public InvalidTaskStateException(String message) {
		super(message);
	}

}
