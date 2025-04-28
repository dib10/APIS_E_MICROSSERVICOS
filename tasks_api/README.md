# 📝 API de Gerenciamento de Tarefas (Em desenvolvimento)

API RESTful para gerenciar tarefas pessoais (to-do list), permitindo operações CRUD, com foco em boas práticas de desenvolvimento, validações, tratamento de exceções e testes automatizados.

## ✨ Funcionalidades

* Criação de novas tarefas com validação de dados.
* Listagem de tarefas com suporte a paginação e ordenação.
* Busca de tarefas por ID.
* Busca de tarefas por categoria.
* Marcação de tarefas como concluídas.
* Atualização completa de tarefas existentes (se não estiverem concluídas).
* Remoção de tarefas (se não estiverem concluídas).
* Tratamento global de exceções (Recurso Não Encontrado, Estado Inválido, Validação).

## 📋 Requisitos e Dependências

* Java 17
* Maven
* Spring Boot 3.4.4+
* Spring Data JPA
* Spring Web
* Bean Validation
* H2 Database (para desenvolvimento/testes)
* Lombok
* ModelMapper
* Springdoc OpenAPI (Swagger UI)
* Mockito e JUnit 5 (para testes)

As dependências são gerenciadas pelo Maven e estão listadas no arquivo `pom.xml`.

## ⚙️ Instalação e Configuração

1.  Clone o repositório:
    ```bash
    git clone https://github.com/dib10/APIS_E_MICROSSERVICOS/main/tasks_api
    ```
2.  Navegue até o diretório do projeto:
    ```bash
    cd tasks_api
    ```
3.  Construa o projeto utilizando Maven:
    ```bash
    mvn clean install
    ```
4.  Execute a aplicação Spring Boot:
    ```bash
    mvn spring-boot:run
    ```

A aplicação será iniciada e a API estará disponível, geralmente na porta 8080. O console H2 estará acessível em `/h2-console`.

## 🚀 Como Usar

A API expõe os seguintes endpoints na base `/api/tasks`:

| Verbo HTTP | Caminho              | Descrição                                       | Corpo da Requisição (`application/json`)                                                                                                | Resposta (Exemplo - `application/json`)                                                                                              |
| :--------- | :------------------- | :---------------------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------- |
| `POST`     | `/api/tasks`         | Cria uma nova tarefa.                           | `{"titulo": "...", "descricao": "...", "prioridade": "BAIXA|MEDIA|ALTA", "dataLimite": "YYYY-MM-DD", "categoria": "..."}`               | `{"id": 1, "titulo": "...", "descricao": "...", "prioridade": "...", "dataLimite": "...", "concluida": false, "categoria": "...", "criadaEm": "..."}` |
| `GET`      | `/api/tasks`         | Lista tarefas com paginação e ordenação.        | _Nenhum_ (Utilize parâmetros de query `?page=0&size=10&sort=prioridade,asc`)                                                            | Objeto `Page` do Spring Data contendo `TaskResponseDTO`s.                                                                              |
| `GET`      | `/api/tasks/{id}`    | Busca tarefa por ID.                            | _Nenhum_                                                                                                                                | `{"id": ..., "titulo": "...", ...}` (Retorna 404 se não encontrada)                                                                      |
| `GET`      | `/api/tasks/search`  | Busca tarefas por categoria com paginação.      | _Nenhum_ (Utilize parâmetro de query `?categoria=...&page=0&size=10`)                                                                   | Objeto `Page` do Spring Data contendo `TaskResponseDTO`s.                                                                              |
| `PATCH`    | `/api/tasks/{id}/complete` | Marca tarefa como concluída.                    | _Nenhum_                                                                                                                                | `{"id": ..., "concluida": true, ...}` (Retorna 409 se já concluída)                                                                      |
| `PUT`      | `/api/tasks/{id}`    | Atualiza completamente uma tarefa.              | `{"titulo": "...", "descricao": "...", "prioridade": "BAIXA|MEDIA|ALTA", "dataLimite": "YYYY-MM-DD", "categoria": "..."}`               | `{"id": ..., "titulo": "...", ...}` (Retorna 404 se não encontrada, 409 se concluída)                                                    |
| `DELETE`   | `/api/tasks/{id}`    | Remove uma tarefa.                              | _Nenhum_                                                                                                                                | Status 204 No Content (Retorna 404 se não encontrada, 409 se concluída)                                                                 |

Você pode acessar a documentação interativa da API (Swagger UI) em `/swagger-ui.html` ao rodar a aplicação.

## ✅ Testes

O projeto inclui testes unitários para a camada de serviço (`TaskServiceTest`) e testes funcionais para a camada de controle (`TaskControllerTest`). Você pode executar os testes utilizando o Maven:
```bash
    mvn test
    
