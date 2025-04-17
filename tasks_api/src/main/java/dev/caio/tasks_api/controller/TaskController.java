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
import dev.caio.tasks_api.model.Task;
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
	public Task createTaskApi(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
		System.out.println("Requisição POST recebida");
		
		Task createdTask = taskService.createTask(createTaskDTO);
		
		//Tratamento temporário
		if (createdTask == null) {
			System.out.println("Erro ao criar tarefa");
			return null;
		}
		return createdTask;
	}
	
	//Endpoint -> BUSCAR Tarefa por ID (GET)
	
	@GetMapping("/{id}")
	public Task findTaskByIdApi(@PathVariable Long id) {
		
		System.out.println("Recebida a requisição GET para o id: " +id );
		Task task = taskService.findTaskById(id);
		
		System.out.println("Retornando dados da tarefa de ID: " + id);
		return task;
	}
	
	//Endpoint -> DELETAR Tarefa (DELETE)
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTaskApi(@PathVariable Long id)
	{
		System.out.println("Requisição DELETE recebida para api/tasks/" + id);
		taskService.deleteTask(id);
		System.out.println("Tarefa ID: " + id + "processada para deleção" );
	}
	
	//Endpoint -> Alterar estado da tarefa (PATCH)
	
	@PatchMapping("/{id}/complete")
	public Task markTaskAsCompletedApi(@PathVariable Long id) {
		System.out.println("Requisição PATCH recebida para concluir a tarefa de ID: " + id);
		
		Task updatedTask = taskService.markTaskAsCompleted(id);
		
		System.out.println("Retornando tarefa de ID: " + id + " após marcar como concluída");
		return updatedTask;
		
	}
	
	//Endpoint -> ATUALIZAR tarefa (PUT)
	
	@PutMapping("/{id}")
	public Task updateTaskApi (@PathVariable Long id, @Valid @RequestBody CreateTaskDTO taskData) {
		
		Task updateTask = taskService.updateTask(id, taskData);
		
		System.out.println("Retornando tarefa de ID: " +  id + " após atualização.");
		
		return updateTask;
		
	}


}
