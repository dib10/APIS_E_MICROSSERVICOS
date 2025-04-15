package dev.caio.tasks_api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//(404) - Recurso não encontrado 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> body = new HashMap<>();
		body.put("message", ex.getMessage());
        System.out.println("ResourceNotFoundException: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

}