package dev.caio.tasks_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.caio.tasks_api.model.Task;
import dev.caio.tasks_api.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	Page<Task> findByCategoriaIgnoreCase(String categoria, Pageable pageable);
	
	Page<Task> findByUser(User user, Pageable pageable);
	
    Page<Task> findByUserAndCategoriaIgnoreCase(User user, String categoria, Pageable pageable);


}
