package dev.caio.tasks_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.dto.TaskResponseDTO;
import dev.caio.tasks_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController // para controladores REST
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	//Endpoint -> CRIAR Tarefa (POST)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TaskResponseDTO createTaskApi(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
		System.out.println("Requisição POST recebida");

TaskResponseDTO createdTaskDto = taskService.createTask(createTaskDTO);
		
        System.out.println("Retornando DTO da tarefa criada ID: " + createdTaskDto.getId());
		
		
		return createdTaskDto;
	}
	
	//Endpoint -> BUSCAR Tarefa por ID (GET) - CORRIGIDO
		@GetMapping("/{id}")
		public TaskResponseDTO findTaskByIdApi(@PathVariable Long id) { 
			System.out.println("Recebida a requisição GET para o id: " +id );
			TaskResponseDTO taskDto = taskService.findTaskById(id);
			System.out.println("Retornando dados DTO da tarefa de ID: " + id);
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
		public Page<TaskResponseDTO> findAllPaginatedApi(Pageable pageable) {
			
		    System.out.println("Requisição GET  recebida para /api/tasks com Pageable: " + pageable);
		    Page<TaskResponseDTO> resultPage = taskService.findAllPaginated(pageable);
		    System.out.println("Retornando Page de TaskResponseDTO.");
		    return resultPage;

		}
	
}
