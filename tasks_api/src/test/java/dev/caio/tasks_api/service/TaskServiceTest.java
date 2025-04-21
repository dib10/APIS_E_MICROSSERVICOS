package dev.caio.tasks_api.service; 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.dto.TaskResponseDTO;
import dev.caio.tasks_api.enums.Prioridade;
import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.repository.TaskRepository; 

@ExtendWith(MockitoExtension.class) 
class TaskServiceTest {

    @Mock 
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks // instância do service com mocks injetados
    private TaskService taskService;

    // Variáveis para os testes
    private CreateTaskDTO createTaskDto;
    private Task taskEntity;
    private TaskResponseDTO taskResponseDto;

    @BeforeEach
    void setUp() {
        createTaskDto = new CreateTaskDTO();
        taskEntity = new Task();
        taskResponseDto = new TaskResponseDTO();
        System.out.println("Executando setUp()");
    }
    
    // (1) - Teste: Criar uma tarefa com dados válidos.
    
    @Test
    @DisplayName("Deve criar uma Task com sucesso quando os dados são válidos.")
    void createTask_QuandoDadosValidos_DeveRetornarTaskResponseDTO() {
    	
    	System.out.println(" INCIANDO -> Teste para criar tarefas quando dados são válidos.");
    	
    	// 1. DTO de Entrada (Válido)
    	CreateTaskDTO dtoEntrada = new CreateTaskDTO();
      dtoEntrada.setTitulo("Teste válido");
      dtoEntrada.setPrioridade(Prioridade.BAIXA);
      dtoEntrada.setDataLimite(LocalDate.now().plusDays(1)); // data futura 
      dtoEntrada.setCategoria("Unit Test");
      
    //2. Entidade antes de salvar
      
      
      Task taskAntesDeSalvar = new Task();
      taskAntesDeSalvar.setTitulo(dtoEntrada.getTitulo());
      taskAntesDeSalvar.setPrioridade(dtoEntrada.getPrioridade());
      taskAntesDeSalvar.setDataLimite(dtoEntrada.getDataLimite());
      taskAntesDeSalvar.setCategoria(dtoEntrada.getCategoria());
      
      //3. Entidade depois de salvar
      
      Task taskSalva = new Task();
      taskSalva.setId(1L); // L é de Long
      taskSalva.setTitulo(dtoEntrada.getTitulo());
     taskSalva.setPrioridade(dtoEntrada.getPrioridade());
 taskSalva.setDataLimite(dtoEntrada.getDataLimite());
 taskSalva.setCategoria(dtoEntrada.getCategoria());
 taskSalva.setConcluida(false);
 
 //4. DTO de resposta esperada
 
  TaskResponseDTO dtoEsperado = new TaskResponseDTO();
  dtoEsperado.setId(1L);
  dtoEsperado.setTitulo(taskSalva.getTitulo());
  dtoEsperado.setPrioridade(taskSalva.getPrioridade());
  dtoEsperado.setDataLimite(taskSalva.getDataLimite());
  dtoEsperado.setCategoria(taskSalva.getCategoria());
  dtoEsperado.setConcluida(taskSalva.isConcluida());
  
  // 5. config do Mock
  
  when(modelMapper.map(dtoEntrada, Task.class)).thenReturn(taskAntesDeSalvar);
  when(taskRepository.save(any(Task.class))).thenReturn(taskSalva);
  when(modelMapper.map(taskSalva,TaskResponseDTO.class)).thenReturn(dtoEsperado);
  
  //***** Act
  TaskResponseDTO dtoResultado = taskService.createTask(dtoEntrada);
  
  //***** Assert
  
  assertNotNull(dtoResultado);
  assertEquals(dtoEsperado.getId(), dtoResultado.getId()); //checa se os id batem
  assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());

    }
    
    
    
 // (2) - Teste: Tentar criar uma tarefa com dataLimite inválida.

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a data limite estiver no passado.")
    
    void createTask_QuandoDataLimitePassado_DeveLancarExcecao( ) {
    	System.out.println(" INCIANDO -> Teste para criar tarefa no passado e lançar exceção.");
    	
    	// 1. DTO de Entrada (Inválido - passado)
    	CreateTaskDTO dtoEntradaInvalida = new CreateTaskDTO();
    	dtoEntradaInvalida.setTitulo("Tarefa com Data Passada");
    	dtoEntradaInvalida.setPrioridade(Prioridade.MEDIA);
    	dtoEntradaInvalida.setCategoria("Teste Erro");
    	dtoEntradaInvalida.setDataLimite(LocalDate.now().minusDays(1)); // data no passado
    	
    	//2. Nesse caso, não vai ser necessário configurar 'when' para os mocks, pois a exceção deve ser lançada ANTES que o modelMapper.map ou taskRepository.save() sejam chamados.
        
    	   System.out.println("DTO com data inválida criado.");
    	 //3. Verificando se a mensagem de exceção é lançada
    	    
    	    System.out.println("ACT & ASSERT: Verificando se IllegalArgumentException é lançada...");
    	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    	     
    	        taskService.createTask(dtoEntradaInvalida);
    	    }, "Deveria ter lançado IllegalArgumentException para data no passado");

    	   
    	   
    }
    

}