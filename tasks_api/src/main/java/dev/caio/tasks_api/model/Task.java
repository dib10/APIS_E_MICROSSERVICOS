package dev.caio.tasks_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.caio.tasks_api.enums.Prioridade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	
	private LocalDate dataLimite;
	private Boolean concluida = false; // Inicializarei como false pois uma tarefa começa como "não concluída".
	
	private String categoria;
	
	@Column(updatable = false) // pra impedir que atualizem depois
	private LocalDateTime criadaEm;
	
	//Define a data e hora de criação antes do primeiro INSERT
	@PrePersist
	protected void onCreate() {
	
		this.criadaEm = LocalDateTime.now();
		
	}
	
	//construtor padrão
	public Task() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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


	public Boolean isConcluida() {
		return concluida;
	}


	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public LocalDateTime getCriadaEm() {
		return criadaEm;
	}

	//vou remover o set pq já tenho método onCreate
	//public void setCriadaEm(LocalDateTime criadaEm) {
		//this.criadaEm = criadaEm;
	//}

}
