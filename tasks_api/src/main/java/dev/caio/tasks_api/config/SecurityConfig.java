package dev.caio.tasks_api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
	
	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
       
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
    	return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita csrf
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()      
     .requestMatchers("/api/users/register").permitAll() 
                .anyRequest().authenticated()                       
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {
                    
                })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
        return http.build();
    }
}