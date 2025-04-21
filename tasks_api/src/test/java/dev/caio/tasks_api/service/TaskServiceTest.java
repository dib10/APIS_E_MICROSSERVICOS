package dev.caio.tasks_api.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import dev.caio.tasks_api.dto.CreateTaskDTO;
import dev.caio.tasks_api.dto.TaskResponseDTO;
import dev.caio.tasks_api.enums.Prioridade;
import dev.caio.tasks_api.exception.InvalidTaskStateException;
import dev.caio.tasks_api.exception.ResourceNotFoundException;
import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TaskService taskService;

    // (1) - Teste: Criar uma tarefa com dados válidos.
    @Test
    @DisplayName("Deve criar uma Task com sucesso quando os dados são válidos.")
    void createTask_QuandoDadosValidos_DeveRetornarTaskResponseDTO() {
        System.out.println("\n INCIANDO -> Teste (1): Criar tarefa válida.");
        // ARRANGE
        // 1.1 DTO de Entrada (Válido)
        CreateTaskDTO dtoEntrada = new CreateTaskDTO();
        dtoEntrada.setTitulo("Teste válido");
        dtoEntrada.setPrioridade(Prioridade.BAIXA);
        dtoEntrada.setDataLimite(LocalDate.now().plusDays(1));
        dtoEntrada.setCategoria("Unit Test");
        // 1.2 Entidade antes de salvar
        Task taskAntesDeSalvar = new Task();
        taskAntesDeSalvar.setTitulo(dtoEntrada.getTitulo());
        taskAntesDeSalvar.setPrioridade(dtoEntrada.getPrioridade());
        taskAntesDeSalvar.setDataLimite(dtoEntrada.getDataLimite());
        taskAntesDeSalvar.setCategoria(dtoEntrada.getCategoria());
        // 1.3 Entidade depois de salvar
        Task taskSalva = new Task();
        ReflectionTestUtils.setField(taskSalva, "id", 1L); // ID via reflexão
        taskSalva.setTitulo(dtoEntrada.getTitulo());
        taskSalva.setPrioridade(dtoEntrada.getPrioridade());
        taskSalva.setDataLimite(dtoEntrada.getDataLimite());
        taskSalva.setCategoria(dtoEntrada.getCategoria());
        taskSalva.setConcluida(false);
        // 1.4 DTO de resposta esperada
        TaskResponseDTO dtoEsperado = new TaskResponseDTO();
        dtoEsperado.setId(1L);
        dtoEsperado.setTitulo(taskSalva.getTitulo());
        dtoEsperado.setPrioridade(taskSalva.getPrioridade());
        dtoEsperado.setDataLimite(taskSalva.getDataLimite());
        dtoEsperado.setCategoria(taskSalva.getCategoria());
        dtoEsperado.setConcluida(taskSalva.isConcluida());
        // 1.5 Config Mocks
        when(modelMapper.map(dtoEntrada, Task.class)).thenReturn(taskAntesDeSalvar);
        when(taskRepository.save(any(Task.class))).thenReturn(taskSalva);
        when(modelMapper.map(taskSalva,TaskResponseDTO.class)).thenReturn(dtoEsperado);

        // ACT
        TaskResponseDTO dtoResultado = taskService.createTask(dtoEntrada);

        //ASSERT
        assertNotNull(dtoResultado);
        assertEquals(dtoEsperado.getId(), dtoResultado.getId());
        assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());
           }

    // (2) - Teste: Tentar criar uma tarefa com dataLimite inválida.
    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a data limite estiver no passado.")
    void createTask_QuandoDataLimitePassado_DeveLancarExcecao( ) {
        System.out.println(" \n INCIANDO -> Teste (2): Criar tarefa data inválida.");
        // 2.1 DTO de Entrada (Inválido - passado)
        CreateTaskDTO dtoEntradaInvalida = new CreateTaskDTO();
        dtoEntradaInvalida.setTitulo("Tarefa com Data Passada");
        dtoEntradaInvalida.setPrioridade(Prioridade.MEDIA);
        dtoEntradaInvalida.setCategoria("Teste Erro");
        dtoEntradaInvalida.setDataLimite(LocalDate.now().minusDays(1));
        System.out.println("DTO com data inválida criado.");
        // 2.2 Mocks: aqui não precisa configurar o when.

        // 2.3 Verificando se a exceção é lançada
        System.out.println("Verificando se IllegalArgumentException é lançada.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(dtoEntradaInvalida);
        }, "Deveria ter lançado IllegalArgumentException para data no passado");
    }

    // (3) - Teste: Buscar tarefa existente por ID.
    @Test
    @DisplayName("Deve retornar TaskResponseDTO quando o ID existe")
    void findTaskById_QuandoIdExiste_DeveRetornarTaskResponseDTO() {
        System.out.println("\n INCIANDO -> Teste (3): Buscar ID existente.");
        // 3.1 ID de teste existente
        Long idExistente = 1L;
        // 3.2 Simulando a entidade Task
        Task taskEncontrada = new Task();
        ReflectionTestUtils.setField(taskEncontrada, "id", idExistente); 
        taskEncontrada.setTitulo("Tarefa Encontrada no Teste");
        taskEncontrada.setCategoria("Teste Unit");
        taskEncontrada.setConcluida(false);
        // 3.3 Dto de resposta esperada
        TaskResponseDTO dtoEsperado = new TaskResponseDTO();
        dtoEsperado.setId(idExistente);
        dtoEsperado.setTitulo(taskEncontrada.getTitulo());
        dtoEsperado.setCategoria(taskEncontrada.getCategoria());
        dtoEsperado.setConcluida(taskEncontrada.isConcluida());
        // 3.4 Config Mock Repository

when(taskRepository.findById(idExistente)).thenReturn(Optional.of(taskEncontrada));
        // 3.5 Config Mock ModelMapper
when(modelMapper.map(taskEncontrada, TaskResponseDTO.class)).thenReturn(dtoEsperado);

        // ACT
        TaskResponseDTO dtoResultado = taskService.findTaskById(idExistente);

        // ASSERT
        assertNotNull(dtoResultado);
        assertEquals(dtoEsperado.getId(), dtoResultado.getId());
        assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());
        assertEquals(dtoEsperado.getCategoria(), dtoResultado.getCategoria());
    }
    
    // (4) - Teste: Tentar buscar tarefa por ID inexistente (deve falhar)
    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o ID não existe")
void findTaskById_QuandoIdNaoExiste_DeveLancarResourceNotFoundException() {
        System.out.println(" \n INCIANDO -> Teste (4): Buscar ID inexistente.");
        // ARRANGE
        // 4.1 ID de teste inexistente
        Long idInexistente = 99L;
        // 4.2 Config Mock Repository para retornar vazio
        when(taskRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // 4.3 Verifica se a exceção correta é lançada
        System.out.println("ACT & ASSERT: Verificando se ResourceNotFoundException é lançada...");
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
           taskService.findTaskById(idInexistente);
        },"Deveria ter lançado ResourceNotFoundException para o ID inexistente");    
    }
    
    // (5) - Teste: Tentar excluir uma tarefa concluída (e receber erro 409).
    
    @Test
    @DisplayName("Deve lançar InvalidTaskStateException ao tentar deletar a tarefa concluída")
    void deleteTask_QuandoTarefaConcluida_DeveLancarInvalidTaskStateException() {
        System.out.println(" \n INCIANDO -> Teste (5): Deletar tarefa concluída (erro esperado).");
        
        //5.1 Arrange
        
        Long taskId = 1L;
        Task completedTask = new Task();
        ReflectionTestUtils.setField(completedTask, "id", taskId);
        completedTask.setTitulo("Tarefa concluída");
        completedTask.setConcluida(true); //concluida
        completedTask.setCategoria("Teste");
        
        //5.2 Config do Mock do repositório
        System.out.println("Configurando mock repository.findById  para retornar Tarefa Concluída.");

 when(taskRepository.findById(taskId)).thenReturn(Optional.of(completedTask));
 		
 		//5.3 Act e ASSSERT
 		System.out.println("Verificando se InvalidTaskStateException é lançada.");
 		  InvalidTaskStateException exception = assertThrows(InvalidTaskStateException.class,()-> {
 	            taskService.deleteTask(taskId);
 	        }, "Deveria lançar InvalidTaskStateException ao deletar uma tarefa concluída");	
    }
    
} 