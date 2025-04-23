package dev.caio.tasks_api.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType; 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
	
	
	
}
