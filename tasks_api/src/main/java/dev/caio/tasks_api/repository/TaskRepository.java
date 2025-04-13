package dev.caio.tasks_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.caio.tasks_api.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
