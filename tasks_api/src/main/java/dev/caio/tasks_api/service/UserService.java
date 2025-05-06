package dev.caio.tasks_api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.dto.dto.authentication.UserRegistrationDTO;
import dev.caio.tasks_api.enums.ERole;
import dev.caio.tasks_api.exception.ResourceNotFoundException;
import dev.caio.tasks_api.exception.UserAlreadyExistsException;
import dev.caio.tasks_api.model.Role;
import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.repository.RoleRepository;
import dev.caio.tasks_api.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	// Injetando PasswordEncoder
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	
	public User registerUser(UserRegistrationDTO registrationDto) {
		
		//verificando se o username já existe
		if (userRepository.existsByUsername(registrationDto.getUsername())) {
			throw new UserAlreadyExistsException("Erro: Nome de usuário já está sendo utilizado.");
		}
		//verificando se o email já existe
				if (userRepository.existsByEmail(registrationDto.getEmail())) {
					throw new UserAlreadyExistsException("Erro: Email já está sendo utilizado.");
				}
				
				// criando novo usuário
				User user = new User(
		                registrationDto.getUsername(),
		                registrationDto.getEmail(),
		                // criptografando a senha
		                passwordEncoder.encode(registrationDto.getPassword())
		        );
				
				// definindo o role padrão 
				 Set<Role> roles = new HashSet<>();
			        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
			                .orElseThrow(() -> new ResourceNotFoundException("Erro: Role padrão USER não encontrada."));
			        roles.add(userRole);
			        user.setRoles(roles);
			        
			        return userRepository.save(user);

						
		
	}

}
