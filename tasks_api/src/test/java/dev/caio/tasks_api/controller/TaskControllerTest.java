package dev.caio.tasks_api.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import dev.caio.tasks_api.enums.Prioridade;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class TaskControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	//Método p auxiliar tarefas futuras
	private Long createTaskandGetId(String titulo, String categoria, String dataLimite, Prioridade prioridade) throws Exception {
		
		String json = """

				{
                "titulo": "%s",
                "descricao": "Descricao para %s",
                "prioridade": "%s",
                "dataLimite": "%s",
                "categoria": "%s"
				 }
				
				
				""".formatted(titulo, titulo, prioridade.name(), dataLimite, categoria);
		
		MvcResult result = mockMvc.perform(post("/api/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
                .andExpect(status().isCreated())
                .andReturn();
		String responseBody = result.getResponse().getContentAsString();
		return Long.valueOf(JsonPath.read(responseBody, "$.id").toString());
				
	}
	
	
	// (1) Teste para Criar uma tarefa com dados válidos.
	
	@Test
	@DisplayName("1. Deve criar uma tarefa com dados válidos e retornar 201")
	
	void shouldCreateTaskSucessfully() throws Exception {
		String json = """
                {
                 "titulo": "Derrubar Titã Colossal",
                  "descricao": "Usar o DMT para alcançar a nuca e cortar",
                  "prioridade": "ALTA",
                  "dataLimite": "2025-12-25",
                  "categoria": "Tropa de Exploração"
                }
            """;
		
		//act e assert
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)) 
                .andDo(print())
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.id").exists())      				.andExpect(jsonPath("$.titulo").value("Derrubar Titã Colossal"))             				.andExpect(jsonPath("$.prioridade").value("ALTA"))            				.andExpect(jsonPath("$.dataLimite").value("2025-12-25"))              				.andExpect(jsonPath("$.categoria").value("Tropa de Exploração"))              				.andExpect(jsonPath("$.concluida").value(false));	
	}
	
	// (2) Teste para Tentar criar uma tarefa com dataLimite inválida.
	
	@Test
	@DisplayName("2. Deve Retornar erro 400 ao criar uma tarefa com data inválida")
	
	void shouldReturnBadRequestWhenDueDateIsInPaste() throws Exception {
		String dataLimiteInvalida = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE);
		
		String json = """
	            {
	             "titulo": "Investigar a Annie Leonhart",
	              "descricao": "Verificar se ela tem envolvimento com os titãs",
	              "prioridade": "MEDIA",
	              "dataLimite": "%s",
	              "categoria": "Investigação"
	            }
	        """.formatted(dataLimiteInvalida);
		
		//ACT E ASSERT
		
		 mockMvc.perform(post("/api/tasks")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json)) 
	                .andDo(print())
	                .andExpect(status().isBadRequest()); 
	                
		}
	
	// (3) Teste para 	Buscar tarefa existente por ID (caminho feliz).

	@Test
	@DisplayName("3. Deve retornar a tarefa correta quando o ID é válido")
	
	void shouldReturnTaskWhenIdIsValid() throws Exception {
		String titulo = "Investigar carne de titã";
		String categoria = "Investigação";
		String dataLimite = "2025-05-02";
		Prioridade prioridade = Prioridade.MEDIA;
		Long taskId = createTaskandGetId(titulo, categoria, dataLimite, prioridade);
		String descricaoEsperada = "Descricao para " + titulo;	
		
		//act e assert 
		
		 mockMvc.perform(get("/api/tasks/{id}", taskId))
		 .andDo(print())
	     .andExpect(status().isOk())
	     .andExpect(jsonPath("$.id").value(taskId))      		.andExpect(jsonPath("$.titulo").value(titulo))             		.andExpect(jsonPath("$.prioridade").value(prioridade.name()))  .andExpect(jsonPath("$.descricao").value(descricaoEsperada))

		.andExpect(jsonPath("$.dataLimite").value(dataLimite))
 
		.andExpect(jsonPath("$.categoria").value(categoria))
		.andExpect(jsonPath("$.concluida").value(false));	
		

	}
	
	// (4) Teste para 	Buscar tarefa por ID inválido (caminho triste).]
	@Test
	@DisplayName("4. Deve retornar erro 404 ao buscar tarefa com ID inválido")
	
	void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
		Long idInexistente = 9999L;
		
		//act e assert
		mockMvc.perform(get("/api/tasks/{id}",idInexistente))
		.andDo(print())
		.andExpect(status().isNotFound()); 
	}
	

	// (5) Teste para Tentar excluir tarefa concluída 
	@Test
	@DisplayName("5. Deve retornar erro 409 ao tentar excluir tarefa concluída")
	void shouldReturnConflictWhenDeletingCompletedTask() throws Exception {
		String titulo = "Task Concluída (cortar nuca do titã anômalo)";
		String categoria = "JáConcluídas";
		String dataLimite = "2025-06-01";
		Prioridade prioridade = Prioridade.BAIXA;
		Long taskId = createTaskandGetId(titulo, categoria, dataLimite, prioridade);

		// marcando como concluída
		mockMvc.perform(patch("/api/tasks/{id}/complete", taskId))
				.andExpect(status().isOk()); 
		mockMvc.perform(delete("/api/tasks/{id}", taskId)) 
		
		.andDo(print())
		.andExpect(status().isConflict()); 
	}

	
	
	

	}
	
	
	
	
	

