package dev.caio.tasks_api.service; 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

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
import dev.caio.tasks_api.exception.ResourceNotFoundException;
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
    	
    	// 1.1 DTO de Entrada (Válido)
    	CreateTaskDTO dtoEntrada = new CreateTaskDTO();
      dtoEntrada.setTitulo("Teste válido");
      dtoEntrada.setPrioridade(Prioridade.BAIXA);
      dtoEntrada.setDataLimite(LocalDate.now().plusDays(1)); // data futura 
      dtoEntrada.setCategoria("Unit Test");
      
    //1.2 Entidade antes de salvar
      
      
      Task taskAntesDeSalvar = new Task();
      taskAntesDeSalvar.setTitulo(dtoEntrada.getTitulo());
      taskAntesDeSalvar.setPrioridade(dtoEntrada.getPrioridade());
      taskAntesDeSalvar.setDataLimite(dtoEntrada.getDataLimite());
      taskAntesDeSalvar.setCategoria(dtoEntrada.getCategoria());
      
      //1.3 Entidade depois de salvar
      
      Task taskSalva = new Task();
      taskSalva.setId(1L); // L é de Long
      taskSalva.setTitulo(dtoEntrada.getTitulo());
     taskSalva.setPrioridade(dtoEntrada.getPrioridade());
 taskSalva.setDataLimite(dtoEntrada.getDataLimite());
 taskSalva.setCategoria(dtoEntrada.getCategoria());
 taskSalva.setConcluida(false);
 
 //1.4 DTO de resposta esperada
 
  TaskResponseDTO dtoEsperado = new TaskResponseDTO();
  dtoEsperado.setId(1L);
  dtoEsperado.setTitulo(taskSalva.getTitulo());
  dtoEsperado.setPrioridade(taskSalva.getPrioridade());
  dtoEsperado.setDataLimite(taskSalva.getDataLimite());
  dtoEsperado.setCategoria(taskSalva.getCategoria());
  dtoEsperado.setConcluida(taskSalva.isConcluida());
  
  // 1.5 config do Mock
  
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
    	
    	// 2.1 DTO de Entrada (Inválido - passado)
    	CreateTaskDTO dtoEntradaInvalida = new CreateTaskDTO();
    	dtoEntradaInvalida.setTitulo("Tarefa com Data Passada");
    	dtoEntradaInvalida.setPrioridade(Prioridade.MEDIA);
    	dtoEntradaInvalida.setCategoria("Teste Erro");
    	dtoEntradaInvalida.setDataLimite(LocalDate.now().minusDays(1)); // data no passado
    	
    	//2.2 Nesse caso, não vai ser necessário configurar 'when' para os mocks, pois a exceção deve ser lançada ANTES que o modelMapper.map ou taskRepository.save() sejam chamados.
        
    	   System.out.println("DTO com data inválida criado.");
    	 //2.3 Verificando se a mensagem de exceção é lançada
    	    
    	    System.out.println("ACT & ASSERT: Verificando se IllegalArgumentException é lançada...");
    	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
    	     
    	        taskService.createTask(dtoEntradaInvalida);
    	    }, "Deveria ter lançado IllegalArgumentException para data no passado");

    	   
    	   
    }
    
    // (3) - Teste: Buscar tarefa existente por ID.

    @Test
    @DisplayName("Deve retornar TaskResponseDTO quando o ID existe")
    void findTaskById_QuandoIdExiste_DeveRetornarTaskResponseDTO() {
    	System.out.println(" INCIANDO -> Teste para buscar tarefa existente por ID.");
    	
        // 3.1 ARRANGE (Organizar / Given)
    	Long idExistente = 1L;
    	
    	//3.2 Simulando a entidade Task
    	Task taskEncontrada = new Task();
    	taskEncontrada.setId(idExistente);
    	taskEncontrada.setTitulo("Tarefa Encontra no Teste");
    	taskEncontrada.setCategoria("Teste Unit");
    	taskEncontrada.setConcluida(false);
    	
    	//3.3 Dto de resposta esperada
    	TaskResponseDTO dtoEsperado = new TaskResponseDTO();
    	dtoEsperado.setId(idExistente);
    	dtoEsperado.setTitulo(taskEncontrada.getTitulo());
    	dtoEsperado.setCategoria(taskEncontrada.getCategoria());
    	dtoEsperado.setConcluida(taskEncontrada.isConcluida());
    	
    //3.4 Configuração do Mock do repositório
    	System.out.println("Configurando mock repository.findById que deve retornar Optional com Task");
    	when(taskRepository.findById(idExistente)).thenReturn(Optional.of(taskEncontrada));
    	
    //3.5 Configuração do Mock  do ModelMapper
    	System.out.println("Configurando mock modelMapper.map para retornar DTO esperado");
    	
        when(modelMapper.map(taskEncontrada, TaskResponseDTO.class)).thenReturn(dtoEsperado);

        //ACT
        System.out.println("Chamando taskService.findTaskById");
        TaskResponseDTO dtoResultado = taskService.findTaskById(idExistente);
        
        //ASSERT -comparando resultados
        System.out.println("Verificando Resultados");
        assertNotNull(dtoResultado);
        assertEquals(dtoEsperado.getId(), dtoResultado.getId());
        assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());
        assertEquals(dtoEsperado.getCategoria(), dtoResultado.getCategoria());

    }
    
    // (4) - Teste: Tentar buscar tarefa por ID inexistente (Falha esperada)
    
    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o ID não existe")
    void findTaskById_QuandoIdNaoExiste_DeveLancarResourceNotFoundException() {
    	System.out.println(" INCIANDO -> Teste para buscar tarefa por ID inexistente.");
    	
    	//4.1 ARRANGE
        Long idInexistente = 99L;
        
        //4.2 Configurando Mock do repositório
        System.out.println("Configurando mock repository.findId para retornar Optional vazio.");
        when(taskRepository.findById(idInexistente)).thenReturn(Optional.empty());
        
        //4.3 Act e Assert
        System.out.println("Verificando se ResourceNotFoundException é lançada.");
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
        // aqui eu chamo o método que DEVE falhar
       taskService.findTaskById(idInexistente);
        },"Deveria ter lançado ResourceNotFoundException para o ID inexistente");

    }

}