package dev.caio.tasks_api.service;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.repository.TaskRepository;
import dev.caio.tasks_api.exception.ResourceNotFoundException; // 


@Service
public class TaskService {
	
	@Autowired //injeta instância do TaskRepository
	private TaskRepository taskRepository;
	@Autowired // para o spring injetar o Bean ModelMapper
	private ModelMapper modelMapper;
	
	public Task createTask(CreateTaskDTO createTaskDTO) {
		if (createTaskDTO.getDataLimite() != null && createTaskDTO.getDataLimite().isBefore(LocalDate.now())) {
			System.out.println("ERRO!! A data limite não pode ser anterior à data atual");
			return null;
			
		}
		
		Task newTask = modelMapper.map(createTaskDTO, Task.class);
		System.out.println("Salvando a tarefa " + newTask.getTitulo());
		
        Task savedTask = taskRepository.save(newTask);
        System.out.println("Tarefa salva com ID: " + savedTask.getId());
        return savedTask;

	}
	
	//Buscar tarefa por ID
	
	public Task findTaskById(Long id) { // Vou usar o nome findTaskById para consistência
	    System.out.println("SERVICE: Tentando encontrar tarefa com ID: " + id);
	    //Optional. Garantimos que a task exista, se não lança a exceção personalizada
	    Optional<Task> taskOptional = taskRepository.findById(id);

	    Task task = taskOptional.orElseThrow(() ->
	        new ResourceNotFoundException("Tarefa não encontrada com ID: " + id)
	    ); 

	    System.out.println("SERVICE: Tarefa encontrada: ID " + task.getId());
	    return task; 
	}
}
