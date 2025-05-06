package dev.caio.tasks_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.caio.tasks_api.enums.ERole;
import dev.caio.tasks_api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(ERole name);

}
