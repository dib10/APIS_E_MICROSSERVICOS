package dev.caio.tasks_api.dto.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
	
	@NotBlank(message = "O nome de usuário não pode ser vazio")
	@Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
	private String username;
	
	@NotBlank(message = " O email não pode ser vazio.")
	@Email(message = "Formato de email inválido.")
	@Size(max=100, message = "O email não pode exceder 100 caracteres")
	private String email;
	
	@NotBlank(message = " A senha não pode ser vazia")
	@Size(min = 6, max = 40 , message = "A senha deve ter entre 6 e 40 caracteres")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
