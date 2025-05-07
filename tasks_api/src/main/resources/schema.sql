DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL
);

-- tabela de junção

CREATE TABLE user_roles (
	user_id BIGINT NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY(user_id) REFERENCES users(id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE task (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(100),
	prioridade VARCHAR(10) NOT NULL,
	data_limite DATE NOT NULL,
	concluida BOOLEAN NOT NULL DEFAULT FALSE,
	categoria VARCHAR(50) NOT NULL,
	criada_em TIMESTAMP NOT NULL, 
	user_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
	
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

-- Usuário ADMIN com senha 'admin_password' hasheada PELA SUA APLICAÇÃO
INSERT INTO users(username, email, password) VALUES('admin_user', 'admin@example.com', '$2a$10$0ccbC/TD002t4Ctwwt9YXeNwZJ.gRdMn5cmf87BkJDnhZm9bnVqFa'); -- <<< HASH ATUALIZADO
INSERT INTO user_roles(user_id, role_id) VALUES( (SELECT ID FROM USERS WHERE USERNAME = 'admin_user'), (SELECT ID FROM ROLES WHERE NAME = 'ROLE_ADMIN') );

-- Usuário COMUM com senha 'user_password' hasheada PELA SUA APLICAÇÃO
INSERT INTO users(username, email, password) VALUES('common_user', 'user@example.com', '$2a$10$Uihu1adZCwdDCruLk2TY6ON/xmAH9JCeC8pDuF5piW/JWPXQ546vu'); -- <<< HASH ATUALIZADO
INSERT INTO user_roles(user_id, role_id) VALUES( (SELECT ID FROM USERS WHERE USERNAME = 'common_user'), (SELECT ID FROM ROLES WHERE NAME = 'ROLE_USER') );





