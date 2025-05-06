package dev.caio.tasks_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.caio.tasks_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	//verificação
	Boolean existsByUsername(String username);
	
	//verificação
	Boolean existsByEmail(String email);
	
	

}
