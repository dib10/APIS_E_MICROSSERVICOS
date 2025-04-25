# API de Gerenciamento de Contatos

Esta é uma API RESTful para gerenciamento de contatos desenvolvida com Spring Boot.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- MySQL
- SpringDoc OpenAPI (Swagger)
- Maven

## 📋 Pré-requisitos

- Java 21 ou superior
- MySQL
- Maven

## 🔧 Configuração do Ambiente

1. Clone o repositório:
```bash
git clone https://github.com/dib10/APIS_E_MICROSSERVICOS.git
cd contacts-api
```

2. Configure o banco de dados MySQL no arquivo `application.properties`

3. Execute o projeto:
```bash
mvn spring-boot:run
```

## 📚 Documentação da API

A documentação da API está disponível através do Swagger UI. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

## 🛠️ Principais Funcionalidades

- Gerenciamento completo de contatos (CRUD)
- Validação de dados
- Documentação interativa com Swagger

## 🔍 Estrutura do Projeto

O projeto segue a arquitetura em camadas padrão do Spring:

- `controllers/`: Endpoints da API
- `models/`: Entidades e modelos de dados
- `repositories/`: Interfaces de acesso ao banco de dados

## 📦 Dependências Principais

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- MySQL Connector
- SpringDoc OpenAPI UI

