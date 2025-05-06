package dev.caio.tasks_api.service;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.dto.TaskResponseDTO;
import dev.caio.tasks_api.exception.InvalidTaskStateException;
import dev.caio.tasks_api.exception.ResourceNotFoundException;
import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.repository.TaskRepository;


@Service
public class TaskService {

	@Autowired //injeta instância do TaskRepository
	private TaskRepository taskRepository;
	@Autowired // para o spring injetar o Bean ModelMapper
	private ModelMapper modelMapper;

    // Método auxiliar interno para retornar entidade 
    private Task findTaskByIdInternal(Long id) {
         System.out.println("SERVICE (Internal): Buscando entidade Task ID: " + id);
         // Busca no repositório e lança exceção 404 se não encontrar
         return taskRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
    }

    // Método auxiliar para conversão (DTO)
    
	private TaskResponseDTO convertToDto(Task task) {
        System.out.println("SERVICE: Convertendo Task ID " + task.getId() + " para TaskResponseDTO.");
		return modelMapper.map(task, TaskResponseDTO.class);
	}

	public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, User currentUser) {
    System.out.println("SERVICE: Criando tarefa para o usuário ID: " + currentUser.getId());

	{
		if (createTaskDTO.getDataLimite() != null && createTaskDTO.getDataLimite().isBefore(LocalDate.now())) {
			System.err.println("ERRO!! A data limite não pode ser anterior à data atual"); 
throw new IllegalArgumentException("Data limite não pode ser anterior à data atual.");
		}
		Task newTask = modelMapper.map(createTaskDTO, Task.class);
		
        newTask.setUser(currentUser);

        System.out.println("Salvando a tarefa '" + newTask.getTitulo() + "' para o usuário " + currentUser.getUsername());
        Task savedTask = taskRepository.save(newTask);
        System.out.println("Tarefa salva com ID: " + savedTask.getId() + " associada ao usuário ID: " + savedTask.getUser().getId());
        return convertToDto(savedTask);
        }
	}

	//*******Buscar tarefa por ID - 
	public TaskResponseDTO findTaskById(Long id) {
	System.out.println("SERVICE: Buscando tarefa com ID: " + id + " para retornar DTO.");
	Task task = findTaskByIdInternal(id); //  método interno
	System.out.println("SERVICE: Tarefa encontrada: ID " + task.getId());
	return convertToDto(task); 
	}

	// ******* DELETAR tarefa 
	public void deleteTask(Long id) {
		System.out.println("Solicitação recebida para deletar a tarefa de ID: " + id);
		Task task = findTaskByIdInternal(id); //método interno
		if (Boolean.TRUE.equals(task.isConcluida())) {
System.out.println("ERRO - Tentativa de excluir tarefa concluída - ID: " + id + ")");

throw new InvalidTaskStateException("Não é possível apagar uma tarefa que já foi concluída.");
		}
		taskRepository.deleteById(id);
	System.out.println("Tarefa de ID: " + id + " deletada com sucesso!");
	}
	
	// ****** PATCH tarefa concluída - 
	public TaskResponseDTO markTaskAsCompleted(Long id) { 
		System.out.println("Solicitação recebida para marcar a tarefa de ID: " + id);
		Task task = findTaskByIdInternal(id); //método interno
		if (Boolean.TRUE.equals(task.isConcluida())) {
			System.err.println("A tarefa de ID: " + id + " já se encontra concluída."); 
			throw new InvalidTaskStateException("A tarefa (ID: " + id +") já está marcada como concluída.");
		}
		task.setConcluida(true);
		System.out.println("Alterando o estado da tarefa de ID: " + id + " para concluída.");
		Task updatedTask = taskRepository.save(task);
		System.out.println("Tarefa ID: " + id + " atualizada e salva no banco."); 
		return convertToDto(updatedTask); // 
	}

	// **** Atualizar COMPLETAMENTE uma tarefa existente (PUT)
	public TaskResponseDTO updateTask(Long id, CreateTaskDTO taskData) { 
		System.out.println("Solicitação recebida para ATUALIZAR tarefa de ID: " + id);
		Task existingTask = findTaskByIdInternal(id); //método interno
		if (Boolean.TRUE.equals(existingTask.isConcluida())) {
            System.err.println("ERRO - Tentativa de atualizar tarefa concluída"); 
            throw new InvalidTaskStateException("Não é possível atualizar uma tarefa que está concluída.");
		}
		if (taskData.getDataLimite() != null && taskData.getDataLimite().isBefore(LocalDate.now())) {
			System.err.println("ERRO: Nova data limite é inferior a hoje para a tarefa de ID: " + id); 
			throw new IllegalArgumentException("Data limite não pode ser anterior à data atual.");
		}
		existingTask.setTitulo(taskData.getTitulo());
		existingTask.setDescricao(taskData.getDescricao());
		existingTask.setPrioridade(taskData.getPrioridade());
		existingTask.setDataLimite(taskData.getDataLimite());
		existingTask.setCategoria(taskData.getCategoria());
		System.out.println("Dados da Tarefa de ID: " + id + " preparados para atualização.");
	Task savedTask = taskRepository.save(existingTask);
	System.out.println("Tarefa de ID: " + id + " atualizada no banco.");
	return convertToDto(savedTask);
	}
	
	// ****** Paginação - buscar todas as tarefas de forma paginada (GET)
	
	public Page<TaskResponseDTO> findAllPaginated(Pageable pageable) {
		System.out.println("Buscando tarefas com paginação: " + pageable);
		
		Page<Task> taskPage = taskRepository.findAll(pageable);
		Page<TaskResponseDTO>taskDtoPage = taskPage.map(this::convertToDto);
		 System.out.println("Retornando página ");
		    return taskDtoPage;

	}
	
	// ****** Paginação - buscar as tarefas de forma paginada por categorias (GET)
	
	public Page<TaskResponseDTO> findByCategoriaPaginated(String categoria, Pageable pageable) {
		
	    System.out.println("Buscando tarefas paginadas pela categoria");
	    //1. Chama método criado no TaskRepository
	    Page<Task> taskPage = taskRepository.findByCategoriaIgnoreCase(categoria, pageable);
	    //2. Mapeia e Converte a Page<Task> pra Page<TaskResponseDTO>
	    Page<TaskResponseDTO> taskDtoPage = taskPage.map(this::convertToDto);
	    
	    System.out.println("Retornando página para categoria buscada");
	    
	    return taskDtoPage;
	
	}

} 