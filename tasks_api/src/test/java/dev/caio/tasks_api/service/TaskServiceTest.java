package dev.caio.tasks_api.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never; // Import para Mockito.never()
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; /
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
import dev.caio.tasks_api.model.User; 
import dev.caio.tasks_api.repository.TaskRepository;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq; // <<< VERIFIQUE ESTE

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
  
    @InjectMocks
    private TaskService taskService;

    private User mockCurrentUser;
    private User mockOtherUser;

    @BeforeEach // Roda antes de cada teste
    void setUp() {
        // Cria usuários mock/simples p poder usar nos testes
        mockCurrentUser = new User();
        mockCurrentUser.setId(1L); // ID do usuário "logado"
        mockCurrentUser.setUsername("current_user");

        mockOtherUser = new User();
        mockOtherUser.setId(2L); // ID de um usuário diferente
        mockOtherUser.setUsername("other_user");
    }

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
        // 1.2 Entidade antes de salvar (mapeada do DTO)
        Task taskAntesDeSalvar = new Task();
        
        taskAntesDeSalvar.setTitulo(dtoEntrada.getTitulo());
        taskAntesDeSalvar.setPrioridade(dtoEntrada.getPrioridade());
        taskAntesDeSalvar.setDataLimite(dtoEntrada.getDataLimite());
        taskAntesDeSalvar.setCategoria(dtoEntrada.getCategoria());

        // 1.3 Entidade depois de salvar (retorno esperado do repository.save)
        // Agora ela deve ter o usuário associado
        Task taskSalva = new Task();
        ReflectionTestUtils.setField(taskSalva, "id", 1L); // ID via reflexão
        taskSalva.setTitulo(dtoEntrada.getTitulo());
        taskSalva.setPrioridade(dtoEntrada.getPrioridade());
        taskSalva.setDataLimite(dtoEntrada.getDataLimite());
        taskSalva.setCategoria(dtoEntrada.getCategoria());
        taskSalva.setConcluida(false);
        taskSalva.setUser(mockCurrentUser); 

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
        // Mockito agora espera salvar uma Task que terá o usuário setado pelo serviço
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
             Task taskToSave = invocation.getArgument(0);
             assertEquals(mockCurrentUser, taskToSave.getUser()); // Verifica se o user foi setado
             ReflectionTestUtils.setField(taskToSave, "id", 1L); // Simula o ID gerado
             return taskToSave; // Retorna a task como se tivesse sido salva
        });
        // Mock para a conversão final para DTO
        when(modelMapper.map(any(Task.class), eq(TaskResponseDTO.class))).thenReturn(dtoEsperado);


        // ACT
       
        TaskResponseDTO dtoResultado = taskService.createTask(dtoEntrada, mockCurrentUser);

        //ASSERT
        assertNotNull(dtoResultado);
        assertEquals(dtoEsperado.getId(), dtoResultado.getId());
        assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());

        // Verifica se o save foi chamado 1 vez
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // (2) - Teste: Tentar criar uma tarefa com dataLimite inválida.
    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a data limite estiver no passado.")
    void createTask_QuandoDataLimitePassado_DeveLancarExcecao() {
        System.out.println(" \n INCIANDO -> Teste (2): Criar tarefa data inválida.");
        // 2.1 DTO de Entrada (Inválido - passado)
        CreateTaskDTO dtoEntradaInvalida = new CreateTaskDTO();
        dtoEntradaInvalida.setTitulo("Tarefa com Data Passada");
        dtoEntradaInvalida.setPrioridade(Prioridade.MEDIA);
        dtoEntradaInvalida.setCategoria("Teste Erro");
        dtoEntradaInvalida.setDataLimite(LocalDate.now().minusDays(1));
        System.out.println("DTO com data inválida criado.");
        // Não precisamos mockar nada aqui, pois a validação ocorre antes do save

        // 2.3 Verificando se a exceção é lançada
        System.out.println("Verificando se IllegalArgumentException é lançada.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            // <<< ATUALIZADO: Passa o mockCurrentUser >>>
            taskService.createTask(dtoEntradaInvalida, mockCurrentUser);
        }, "Deveria ter lançado IllegalArgumentException para data no passado");

        // Verifica se o save NUNCA foi chamado
        verify(taskRepository, never()).save(any(Task.class));
    }

    // (3) - Teste: Buscar tarefa existente por ID (pertencente ao usuário)
    @Test
    @DisplayName("Deve retornar TaskResponseDTO quando o ID existe e pertence ao usuário")
    void findTaskById_QuandoIdExisteEPertenceAoUsuario_DeveRetornarTaskResponseDTO() {
        System.out.println("\n INCIANDO -> Teste (3): Buscar ID existente e pertencente.");
        // ARRANGE
        Long idExistente = 1L;
        Task taskEncontrada = new Task();
        ReflectionTestUtils.setField(taskEncontrada, "id", idExistente);
        taskEncontrada.setTitulo("Tarefa do Current User");
        taskEncontrada.setUser(mockCurrentUser); // <<< Tarefa pertence ao mockCurrentUser

        TaskResponseDTO dtoEsperado = new TaskResponseDTO();
        dtoEsperado.setId(idExistente);
        dtoEsperado.setTitulo(taskEncontrada.getTitulo());

        // Config Mocks
        when(taskRepository.findById(idExistente)).thenReturn(Optional.of(taskEncontrada));
        when(modelMapper.map(taskEncontrada, TaskResponseDTO.class)).thenReturn(dtoEsperado);

        // ACT
        
        TaskResponseDTO dtoResultado = taskService.findTaskById(idExistente, mockCurrentUser);

        // ASSERT
        assertNotNull(dtoResultado);
        assertEquals(dtoEsperado.getId(), dtoResultado.getId());
        assertEquals(dtoEsperado.getTitulo(), dtoResultado.getTitulo());
        verify(taskRepository, times(1)).findById(idExistente);
    }

    // (3.1) - Teste: Tentar buscar tarefa existente por ID (NÃO pertencente ao usuário)
    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar ID que existe mas não pertence ao usuário")
    void findTaskById_QuandoIdExisteMasNaoPertenceAoUsuario_DeveLancarExcecao() {
        System.out.println("\n INCIANDO -> Teste (3.1): Buscar ID existente, mas de outro usuário.");
        // ARRANGE
        Long idExistente = 1L;
        Task taskEncontrada = new Task();
        ReflectionTestUtils.setField(taskEncontrada, "id", idExistente);
        taskEncontrada.setUser(mockOtherUser); // Tarefa pertence a OUTRO usuário

        // Config Mocks
        when(taskRepository.findById(idExistente)).thenReturn(Optional.of(taskEncontrada));
        // O modelMapper não deve ser chamado

        // ACT & ASSERT
        System.out.println("Verificando se ResourceNotFoundException é lançada para tarefa de outro usuário...");
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
           
            taskService.findTaskById(idExistente, mockCurrentUser);
        }, "Deveria ter lançado ResourceNotFoundException para tarefa de outro usuário");

        assertEquals("Tarefa não encontrada com ID: " + idExistente, exception.getMessage());
        verify(taskRepository, times(1)).findById(idExistente);
        verify(modelMapper, never()).map(any(), any()); // Verifica que não chegou a converter para DTO
    }

    // (4) - Teste: Tentar buscar tarefa por ID inexistente (deve falhar)
    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o ID não existe")
    void findTaskById_QuandoIdNaoExiste_DeveLancarResourceNotFoundException() {
        System.out.println(" \n INCIANDO -> Teste (4): Buscar ID inexistente.");
        // ARRANGE
        Long idInexistente = 99L;
        when(taskRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // ACT & ASSERT
        System.out.println("Verificando se ResourceNotFoundException é lançada...");
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            
            taskService.findTaskById(idInexistente, mockCurrentUser);
        }, "Deveria ter lançado ResourceNotFoundException para o ID inexistente");

        // Verifica a mensagem da exceção (pode variar um pouco dependendo de onde é lançada)
        // Neste caso, é lançada pelo findTaskByIdInternal -> findById.orElseThrow
        assertEquals("Tarefa não encontrada com ID: " + idInexistente, exception.getMessage());
        verify(taskRepository, times(1)).findById(idInexistente);
    }

    // (5) - Teste: Tentar excluir uma tarefa concluída (e receber erro 409).
    @Test
    @DisplayName("Deve lançar InvalidTaskStateException ao tentar deletar tarefa concluída e pertencente ao usuário")
    void deleteTask_QuandoTarefaConcluidaEPertenceAoUsuario_DeveLancarInvalidTaskStateException() {
        System.out.println(" \n INCIANDO -> Teste (5): Deletar tarefa concluída (erro esperado).");
        // ARRANGE
        Long taskId = 1L;
        Task completedTask = new Task();
        ReflectionTestUtils.setField(completedTask, "id", taskId);
        completedTask.setTitulo("Tarefa concluída");
        completedTask.setConcluida(true); // concluida
        completedTask.setUser(mockCurrentUser); 

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(completedTask));

        // ACT & ASSERT
        System.out.println("Verificando se InvalidTaskStateException é lançada.");
        InvalidTaskStateException exception = assertThrows(InvalidTaskStateException.class, () -> {
            
            taskService.deleteTask(taskId, mockCurrentUser);
        }, "Deveria lançar InvalidTaskStateException ao deletar uma tarefa concluída");

        assertEquals("Não é possível apagar uma tarefa que já foi concluída.", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).deleteById(any(Long.class)); // Verifica que deleteById não foi chamado
    }

    // (6) - Teste: Deletar tarefa existente, não concluída e pertencente ao usuário (caminho de sucesso)
    @Test
    @DisplayName("Deve chamar deleteById quando tarefa existe, não concluída e pertence ao usuário")
    void deleteTask_QuandoIdExisteNaoConcluidaEPertenceAoUsuario_DeveChamarDeleteById() {
        System.out.println(" INCIANDO -> Teste (6): Deletar tarefa válida.");
        // ARRANGE
        Long taskId = 2L;
        Task taskNaoConcluida = new Task();
        ReflectionTestUtils.setField(taskNaoConcluida, "id", taskId);
        taskNaoConcluida.setTitulo("Tarefa válida para deletar");
        taskNaoConcluida.setConcluida(false); // Não concluída
        taskNaoConcluida.setUser(mockCurrentUser); //Pertence ao usuário atual

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskNaoConcluida));
       
        // ACT
        
        taskService.deleteTask(taskId, mockCurrentUser);

        // ASSERT
        System.out.println("Verificando chamadas aos mocks.");
        verify(taskRepository, times(1)).findById(taskId); // Verifica se buscou
        verify(taskRepository, times(1)).deleteById(taskId); // Verifica se mandou deletar
    }

    // (6.1) - Teste: Tentar deletar tarefa não pertencente ao usuário
    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar deletar tarefa de outro usuário")
    void deleteTask_QuandoNaoPertenceAoUsuario_DeveLancarExcecao() {
        System.out.println(" INCIANDO -> Teste (6.1): Deletar tarefa de outro usuário.");
        // ARRANGE
        Long taskId = 3L;
        Task taskDeOutro = new Task();
        ReflectionTestUtils.setField(taskDeOutro, "id", taskId);
        taskDeOutro.setTitulo("Tarefa de Outro Usuário");
        taskDeOutro.setConcluida(false);
        taskDeOutro.setUser(mockOtherUser); 

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskDeOutro));

        // ACT & ASSERT
        System.out.println("Verificando se ResourceNotFoundException é lançada...");
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            
            taskService.deleteTask(taskId, mockCurrentUser);
        }, "Deveria ter lançado ResourceNotFoundException ao deletar tarefa de outro usuário");

        assertEquals("Tarefa não encontrada com ID: " + taskId, exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).deleteById(any(Long.class)); // Garante que não deletou
    }

    // depois devo adicionar testes  para updateTask e markTaskAsCompleted para verificar o dono
   
}