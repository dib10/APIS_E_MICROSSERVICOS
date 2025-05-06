package dev.caio.tasks_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.caio.tasks_api.dto.dto.authentication.UserRegistrationDTO;
import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDto) 
	{
		try {
            User registeredUser = userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso! ID: " + registeredUser.getId());
 
		}
		catch (Exception e) {
			throw e;
		}
	}

}
