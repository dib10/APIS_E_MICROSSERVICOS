package dev.caio.tasks_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.caio.tasks_api.enums.Prioridade;
import lombok.Data;

//lombokar aqui
@Data
public class TaskResponseDTO {
	private Long id;
	private String titulo;
	private String descricao;
	private Prioridade prioridade;
	private LocalDate dataLimite;
	private boolean concluida;
	private String categoria;
	private LocalDateTime criadaEm;
	
	

}
