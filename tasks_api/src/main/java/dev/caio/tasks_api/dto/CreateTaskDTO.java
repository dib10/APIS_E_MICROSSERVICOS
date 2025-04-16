package dev.caio.tasks_api.dto;

import java.time.LocalDate;

import dev.caio.tasks_api.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTaskDTO {
	
	@NotBlank(message = "O título da tarefa não pode ser vazio.")
	private String titulo;
	
	private String descricao;
	@NotNull(message = "O campo prioridade não pode estar nulo.")
	private Prioridade prioridade;
	
	@NotNull(message = "O campo data limite não pode ser nulo.")
	private LocalDate dataLimite;
	
	@NotNull(message = "O campo categoria não pode ser nulo.")
	private String categoria;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Prioridade getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	

}
