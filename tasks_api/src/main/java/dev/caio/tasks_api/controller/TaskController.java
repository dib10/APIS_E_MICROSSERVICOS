package dev.caio.tasks_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.dto.TaskResponseDTO;
import dev.caio.tasks_api.model.UserAuthenticated;
import dev.caio.tasks_api.service.TaskService;
import jakarta.validation.Valid;

@RestController // para controladores REST
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	//Endpoint -> CRIAR Tarefa (POST)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TaskResponseDTO createTaskApi(@Valid @RequestBody CreateTaskDTO createTaskDTO, @AuthenticationPrincipal UserAuthenticated authenticatedUser) {
        System.out.println("Requisição POST recebida para criar tarefa pelo usuário: " + (authenticatedUser != null ? authenticatedUser.getUsername() : "ANÔNIMO (ERRO)"));
        
        if(authenticatedUser == null) {
            System.err.println("ERRO: Usuário autenticado é nulo ao tentar criar tarefa.");

        }
        
        TaskResponseDTO createdTaskDto = taskService.createTask(createTaskDTO, authenticatedUser.getUser());
		
        System.out.println("Retornando DTO da tarefa criada ID: " + createdTaskDto.getId() + " para o usuário: " + authenticatedUser.getUsername());
        return createdTaskDto;
	}
	
	//Endpoint -> BUSCAR Tarefa por ID (GET) - 
		@GetMapping("/{id}")
		public TaskResponseDTO findTaskByIdApi(@PathVariable Long id,
                @AuthenticationPrincipal UserAuthenticated authenticatedUser) { 
	        System.out.println("Recebida a requisição GET para a tarefa ID: " + id + " pelo usuário: " + authenticatedUser.getUsername());

	        TaskResponseDTO taskDto = taskService.findTaskById(id, authenticatedUser.getUser());
	        System.out.println("Retornando dados DTO da tarefa de ID: " + id + " para o usuário: " + authenticatedUser.getUsername());
			return taskDto;
		}
	
		//Endpoint -> DELETAR Tarefa (DELETE) 
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteTaskApi(@PathVariable Long id) 
		{
			System.out.println("Requisição DELETE recebida para api/tasks/" + id);
			taskService.deleteTask(id);
			System.out.println("Tarefa ID: " + id + " processada para deleção" );
		}
	
	//Endpoint -> Alterar estado da tarefa (PATCH)
	
		@PatchMapping("/{id}/complete")
		public TaskResponseDTO markTaskAsCompletedApi(@PathVariable Long id) { 
			System.out.println("Requisição PATCH recebida para concluir a tarefa de ID: " + id);
			TaskResponseDTO updatedTaskDto = taskService.markTaskAsCompleted(id);
			System.out.println("Retornando tarefa DTO de ID: " + id + " após marcar como concluída");
			return updatedTaskDto;
		}

	
	//Endpoint -> ATUALIZAR tarefa (PUT)
	
		@PutMapping("/{id}")
		public TaskResponseDTO updateTaskApi (@PathVariable Long id, @Valid @RequestBody CreateTaskDTO taskData) { 
	        System.out.println("Requisição PUT recebida para atualizar tarefa ID: " + id);
			TaskResponseDTO updatedTaskDto = taskService.updateTask(id, taskData);
			System.out.println("Retornando tarefa DTO de ID: " +  id + " após atualização.");
			return updatedTaskDto;
		}
		//Endpoint -> LISTAR  tarefas com paginação (GET)
		
		 @GetMapping
		    public Page<TaskResponseDTO> findAllPaginatedApi(Pageable pageable,
		                                                     @AuthenticationPrincipal UserAuthenticated authenticatedUser) { // 1. Adicionado @AuthenticationPrincipal
		        System.out.println("Requisição GET recebida para /api/tasks do usuário: " + authenticatedUser.getUsername() + " com Pageable: " + pageable);
		        // 2. Passa o User para o serviço
		        Page<TaskResponseDTO> resultPage = taskService.findAllPaginated(authenticatedUser.getUser(), pageable);
		        System.out.println("Retornando Page de TaskResponseDTO para o usuário: " + authenticatedUser.getUsername());
		        return resultPage;
		    }
		
		// Endpoint para BUSCAR Tarefas por Categoria  
		
		 @GetMapping("/search")
		    public Page<TaskResponseDTO> searchCategoriaApi(@RequestParam String categoria, Pageable pageable,
		                                                    @AuthenticationPrincipal UserAuthenticated authenticatedUser) { // Adicionado @AuthenticationPrincipal
		        System.out.println("Requisição GET recebida para busca por categoria '" + categoria + "' do usuário: " + authenticatedUser.getUsername());
		        // Passa o User para o serviço
		        Page<TaskResponseDTO> resultPage = taskService.findByCategoriaPaginated(authenticatedUser.getUser(), categoria, pageable);
		        System.out.println("Retornando página de tarefas encontradas para o usuário: " + authenticatedUser.getUsername() + " na categoria: " + categoria);
		        return resultPage;
		    }

}
