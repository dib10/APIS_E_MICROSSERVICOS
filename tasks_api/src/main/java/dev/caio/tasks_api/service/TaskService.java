package dev.caio.tasks_api.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.repository.TaskRepository;

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
	

}
