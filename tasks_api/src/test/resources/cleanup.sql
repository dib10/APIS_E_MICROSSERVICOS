-- src/test/resources/cleanup.sql

-- basicamente o script serve para limpar a tabela task do banco em memória, antes da execução dos testes para evitar erros

-- apaga todos registros
DELETE FROM task;
-- reinicia a seq de numeração
ALTER TABLE task ALTER COLUMN id RESTART WITH 1;