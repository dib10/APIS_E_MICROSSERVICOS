package br.ifsp.contacts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Esse DTO será específico para entrada de dados para criar um endereço

public class AddressInputDTO {
	
	@NotBlank(message = "O campo rua não pode estar vazio. ")
	private String rua;
	
	@NotBlank(message = "O campo cidade não pode estar vazio. ")
	
	private String cidade;
	
	@NotBlank(message = "O campo estado não pode estar vazio. ")
	@Size(min=2,max=2, message = "O estado deve ter 2 caracteres")
	private String estado;
	
	@NotBlank(message = "O campo CEP não pode estar vazio. ")
	@Size(min=8,max=9, message = "O CEP deve ter no máximo 9 caracteres")
	private String cep;

	
	public AddressInputDTO() {
	}


	public AddressInputDTO(@NotBlank(message = "O campo rua não pode estar vazio. ") String rua,
			@NotBlank(message = "O campo cidade não pode estar vazio. ") String cidade,
			@NotBlank(message = "O campo estado não pode estar vazio. ") @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres") String estado,
			@NotBlank(message = "O campo CEP não pode estar vazio. ") @Size(min = 8, max = 9, message = "O CEP deve ter no máximo 9 caracteres") String cep) {
		super();
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
	}


	public String getRua() {
		return rua;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}	
	
	
	
	
	
	

}
