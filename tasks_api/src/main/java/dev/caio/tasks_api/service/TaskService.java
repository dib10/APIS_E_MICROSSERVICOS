package dev.caio.tasks_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired //injeta instância do TaskRepository
	private TaskRepository taskRepository;
	

}
