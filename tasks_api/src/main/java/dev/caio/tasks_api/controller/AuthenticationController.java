package dev.caio.tasks_api.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.caio.tasks_api.dto.dto.authentication.AuthenticationDTO;
import dev.caio.tasks_api.model.User;
import dev.caio.tasks_api.model.UserAuthenticated;
import dev.caio.tasks_api.service.JwtService;
import jakarta.validation.Valid;    

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        try {
            //cria objeto de autenbticação com as credenciais fornecidas
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getUsername(),
                    authenticationDTO.getPassword()
            );

            //Tenta autenticar
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Se a autenticação foi bem-sucedida, o usuario estará no objeto Authentication e o UserDetailService retorna um UserAuthenticated.
            UserAuthenticated userAuthenticated = (UserAuthenticated) authentication.getPrincipal();

            
            User userEntity = userAuthenticated.getUser();

            // gerando o token jwt usando o JwtService e a entidade User
            String jwtToken = jwtService.generateToken(userEntity);

            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", jwtToken);
            tokenResponse.put("type", "Bearer"); 
           
            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            // Se a autenticação falhar retorna 401 Unauthorized
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciais inválidas");
            errorResponse.put("message", e.getMessage()); 
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}