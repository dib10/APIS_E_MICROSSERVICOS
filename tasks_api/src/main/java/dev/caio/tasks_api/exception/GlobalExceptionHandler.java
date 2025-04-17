package dev.caio.tasks_api.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	// (400) - Erros de Validação @Valid
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		//Iterando sobre todos os erros de validação encontrados
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			
			String errorMessage = error.getDefaultMessage();
			
			errors.put(fieldName, errorMessage);
		});
		
		//debug
		System.out.println("Erro de validação capturado");
			
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		
	}
	
	// (409) - Conflito de estado
	
	@ExceptionHandler(InvalidTaskStateException.class)
	public ResponseEntity<Map<String, String>> handleInvalidTaskStateException(InvalidTaskStateException ex){
		
		// CRIANDO o corpo da resposta 
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("message", ex.getMessage());
		
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
		
	}
	
	// Handler para validação de data da tarefa
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
	    Map<String, String> errorDetails = new HashMap<>();
	    errorDetails.put("error", ex.getMessage());
	    
	    System.out.println("Tratando IllegalArgumentException (400): " + ex.getMessage());
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	
	

}