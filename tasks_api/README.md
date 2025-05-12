# 📝 API de Gerenciamento de Tarefas

API RESTful para gerenciar tarefas pessoais (to-do list), permitindo operações CRUD por usuário, com autenticação e autorização, foco em boas práticas de desenvolvimento, validações, tratamento de exceções e testes automatizados.

## ✨ Funcionalidades

* **Autenticação:** Autenticação de usuários via JWT (endpoint `/api/auth/authenticate`).
* **Registro:** Registro de novos usuários (endpoint `/api/users/register`).
* **Gerenciamento de Tarefas por Usuário:**
    * Criação de novas tarefas associadas ao usuário autenticado, com validação de dados.
    * Listagem de tarefas do usuário autenticado com suporte a paginação e ordenação.
    * Busca de tarefas do usuário autenticado por ID.
    * Busca de tarefas do usuário autenticado por categoria.
    * Marcação de tarefas do usuário autenticado como concluídas.
    * Atualização completa de tarefas existentes do usuário autenticado (se não estiverem concluídas).
    * Remoção de tarefas do usuário autenticado (se não estiverem concluídas).
* **Administração:** Listagem de todas as tarefas do sistema (requer privilégio de ADMIN, endpoint `/api/tasks/admin/all`).
* **Tratamento de Exceções:** Tratamento global de exceções (Recurso Não Encontrado, Estado Inválido, Validação, Usuário Já Existe, Erros de Autenticação).

## 📋 Requisitos e Dependências

* Java 17 
* Maven 
* Spring Boot 3.4.4+ 
* Spring Data JPA 
* Spring Web 
* Spring Security (com OAuth2 Resource Server para JWT) 
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
    git clone [https://github.com/dib10/APIS_E_MICROSSERVICOS/main/tasks_api](https://github.com/dib10/APIS_E_MICROSSERVICOS/main/tasks_api)
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

A aplicação será iniciada e a API estará disponível, geralmente na porta 8080. O console H2 estará acessível em `/h2-console`. As chaves pública e privada para JWT são carregadas dos arquivos `app.pub` e `app.key` no classpath.

## 🚀 Como Usar

A API expõe os seguintes endpoints:

**Autenticação e Registro:**

| Verbo HTTP | Caminho                | Descrição                             | Corpo da Requisição (`application/json`)                                                    | Resposta (Exemplo - `application/json`)                                             |
| :--------- | :--------------------- | :------------------------------------ | :------------------------------------------------------------------------------------------ | :---------------------------------------------------------------------------------- |
| `POST`     | `/api/auth/authenticate` | Autentica um usuário e retorna JWT. | `{"username": "...", "password": "..."}`                                           | `{"token": "eyJ...", "type": "Bearer"}` (Retorna 401 se inválido)         |
| `POST`     | `/api/users/register`  | Registra um novo usuário.             | `{"username": "...", "email": "...", "password": "..."}`                          | Mensagem de sucesso (Retorna 409 se usuário/email já existe)          |

**Gerenciamento de Tarefas (Requer Autenticação JWT - Header `Authorization: Bearer <token>`)**

| Verbo HTTP | Caminho                  | Descrição                                                        | Corpo da Requisição (`application/json`)                                                                                                | Resposta (Exemplo - `application/json`)                                                                                                      |
| :--------- | :----------------------- | :--------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------- |
| `POST`     | `/api/tasks`             | Cria uma nova tarefa para o usuário autenticado.                 | `{"titulo": "...", "descricao": "...", "prioridade": "BAIXA|MEDIA|ALTA", "dataLimite": "YYYY-MM-DD", "categoria": "..."}` | `{"id": 1, "titulo": "...", "descricao": "...", "prioridade": "...", "dataLimite": "...", "concluida": false, "categoria": "...", "criadaEm": "..."}` |
| `GET`      | `/api/tasks`             | Lista tarefas do usuário autenticado com paginação/ordenação.    | _Nenhum_ (Use query params `?page=0&size=10&sort=prioridade,asc`)                                                                       | Objeto `Page` do Spring Data contendo `TaskResponseDTO`s.                                                                          |
| `GET`      | `/api/tasks/{id}`        | Busca tarefa do usuário autenticado por ID.                      | _Nenhum_                                                                                                                                | `{"id": ..., "titulo": "...", ...}` (Retorna 404 se não encontrada ou pertence a outro usuário)                                  |
| `GET`      | `/api/tasks/search`      | Busca tarefas do usuário autenticado por categoria com paginação. | _Nenhum_ (Use query params `?categoria=...&page=0&size=10`)                                                                              | Objeto `Page` do Spring Data contendo `TaskResponseDTO`s.                                                                          |
| `PATCH`    | `/api/tasks/{id}/complete` | Marca tarefa do usuário autenticado como concluída.             | _Nenhum_                                                                                                                                | `{"id": ..., "concluida": true, ...}` (Retorna 404 se não encontrada/outro usuário, 409 se já concluída)                         |
| `PUT`      | `/api/tasks/{id}`        | Atualiza completamente uma tarefa do usuário autenticado.         | `{"titulo": "...", "descricao": "...", "prioridade": "BAIXA|MEDIA|ALTA", "dataLimite": "YYYY-MM-DD", "categoria": "..."}` | `{"id": ..., "titulo": "...", ...}` (Retorna 404 se não encontrada/outro usuário, 409 se concluída, 400 se data inválida) |
| `DELETE`   | `/api/tasks/{id}`        | Remove uma tarefa do usuário autenticado.                        | _Nenhum_                                                                                                                                | Status 204 No Content (Retorna 404 se não encontrada/outro usuário, 409 se concluída)                                           |
| `GET`      | `/api/tasks/admin/all`   | **(ADMIN)** Lista todas as tarefas do sistema.                   | _Nenhum_ (Use query params `?page=0&size=10&sort=...`)                                                                                 | Objeto `Page` do Spring Data contendo `TaskResponseDTO`s.                                                                          |

Você pode acessar a documentação interativa da API (Swagger UI) em `/swagger-ui.html` ao rodar a aplicação.

## ✅ Testes

O projeto inclui testes unitários para a camada de serviço (`TaskServiceTest`) e testes de integração para a camada de controle (`TaskControllerTest`). Você pode executar os testes utilizando o Maven:

```bash
mvn test
