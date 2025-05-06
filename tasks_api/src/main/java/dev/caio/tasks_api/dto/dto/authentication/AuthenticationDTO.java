package dev.caio.tasks_api.dto.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationDTO {
	
	@NotBlank(message = "Por favor, informe o nome do usuário")
	private String username;
	
	@NotBlank(message = "Por favor, informe a senha")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
