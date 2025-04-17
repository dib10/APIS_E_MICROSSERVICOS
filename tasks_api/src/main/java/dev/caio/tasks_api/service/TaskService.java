package dev.caio.tasks_api.service;
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.exception.InvalidTaskStateException;
import dev.caio.tasks_api.exception.ResourceNotFoundException; // 
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
            throw new IllegalArgumentException("Data limite não pode ser anterior à data atual.");
			
		}
		
		Task newTask = modelMapper.map(createTaskDTO, Task.class);
		System.out.println("Salvando a tarefa " + newTask.getTitulo());
		
        Task savedTask = taskRepository.save(newTask);
        System.out.println("Tarefa salva com ID: " + savedTask.getId());
        return savedTask;

	}
	
	//*******Buscar tarefa por ID
	
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
	// ******* DELETAR tarefa
	public void deleteTask(Long id) {
		System.out.println("Solicitação recebida para deletar a tarefa de ID: " + id);
		Task task = findTaskById(id);
		
		if (Boolean.TRUE.equals(task.isConcluida())) {
	        System.err.println("ERRO - Tentativa de excluir tarefa concluída - ID: " + id + ")");
	        
	        throw new InvalidTaskStateException("Não é possível apagar uma tarefa que já foi concluída.");
		}
	        
	        //Se a tarefa existir -> deletar
	        taskRepository.deleteById(id);
	        System.out.println("Tarefa de ID: " + id + " deletada com sucesso!");
		
		
	}
	
	
	// ****** PATCH tarefa concluída
	
	public Task markTaskAsCompleted(Long id) {
		System.out.println("Solicitação recebida para marcar a tarefa de ID: " + id);
		// 1. Busca tarefa pelo ID
		Task task = findTaskById(id);
		
		//2. verificando se a tarefa já está concluída
		
		if (Boolean.TRUE.equals(task.isConcluida())) {
			System.out.println("A tarefa de ID: " + id + "já se encontra concluída.");
			throw new InvalidTaskStateException("A taerefa (ID: " + id +") já está marcada como concluída.");
			
		}
		
		//3. Se não estiver, alterar estado para True
		
		task.setConcluida(true);
		System.out.println("Alterando o estado da tarefa de ID: " + id + "para concluída.");
		
		//4. Salva a task atualizada no banco
		
		Task updatedTask = taskRepository.save(task);
		
		System.out.println("Tarefa ID: " + id + "atualizada e salva no banco de ");
		
		return updatedTask;
		
		
	}
	
	// **** Atualizar COMPLETAMENTE uma tarefa existente (PUT).
	
	//Create taskDTO reutilizado, pois os campos são os mesmo
	public Task updateTask(Long id, CreateTaskDTO taskData) {
		
		System.out.println("Solicitação recebida para ATUALIZAR tarefa de ID: " + id);
		
		//1. Buscando se a tarefa existe.
		
		Task existingTask = findTaskById(id);
		
		//2. Verificando se a tarefa está concluída para lançar a exceção
		
		if (Boolean.TRUE.equals(existingTask.isConcluida())) {
		System.out.println("ERRO - Tentativa de atualizar tarefa concluída");
		throw new InvalidTaskStateException("Não é possível atualizar uma tarefa que está concluída.");
			
}
		
		//3. Validando a nova data limite antes de atualizar
		
		if (taskData.getDataLimite() != null && taskData.getDataLimite().isBefore(LocalDate.now())) {
			System.out.println("ERRO: Nova data limite é inferior a hoje para a tarefa de ID: " + id);
	         throw new IllegalArgumentException("Data limite não pode ser anterior à data atual.");

		}
        // 4. Atualizando os campos da tarefa
		existingTask.setTitulo(taskData.getTitulo());
		existingTask.setDescricao(taskData.getDescricao());
		existingTask.setPrioridade(taskData.getPrioridade());
		existingTask.setDataLimite(taskData.getDataLimite());
		existingTask.setCategoria(taskData.getCategoria());
		
		System.out.println("Dados da Tarefa de ID: " + id + "preparados para atualização.");
	    Task savedTask = taskRepository.save(existingTask);
	    System.out.println("Tarefa de ID: " + id + " atualizada no banco.");
	    return savedTask;
	}

}

