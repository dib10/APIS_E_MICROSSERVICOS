package dev.caio.tasks_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.model.UserAuthenticated;
import dev.caio.tasks_api.repository.UserRepository;
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    private UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserAuthenticated(user);
    }
}