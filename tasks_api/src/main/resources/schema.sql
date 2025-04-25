CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    descricao VARCHAR(100),  
    prioridade VARCHAR(10) NOT NULL, 
    data_limite DATE NOT NULL, 
    concluida BOOLEAN NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    criada_em TIMESTAMP NOT NULL 
);