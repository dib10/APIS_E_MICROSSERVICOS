package dev.caio.tasks_api.security;
import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.model.UserAuthenticated;
import dev.caio.tasks_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component; 

import java.util.List;
import java.util.Optional; 

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	
    private final UserRepository userRepository;
    
    @Autowired
    public CustomJwtAuthenticationConverter(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) { 
 
    	
    	//extrai o UserAuthenticated
        UserAuthenticated userAuthenticated = extractUser(jwt);
        
        //obtem as authorities 
        
        List<GrantedAuthority> authorities = List.copyOf(userAuthenticated.getAuthorities());
        
        // cria o token de autenticação para o spring security
        
        return new UsernamePasswordAuthenticationToken(userAuthenticated, null, authorities);
    }
    
    //método auxiliar p buscar usuario no banco  a partir de userId
    private UserAuthenticated extractUser(Jwt jwt) {
        
        Long userId = jwt.getClaim("userId");
        if (userId == null) {
            
            throw new IllegalArgumentException("Claim 'userId' ausente no token JWT");
        }
        // busca o usuário no repo pelo ID
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado com o ID: " + userId + " referenciado no token JWT"));
        return new UserAuthenticated(user);
    }
    
}


