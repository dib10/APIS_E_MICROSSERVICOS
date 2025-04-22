-- src/test/resources/cleanup.sql
DELETE FROM task;
ALTER TABLE task ALTER COLUMN id RESTART WITH 1;