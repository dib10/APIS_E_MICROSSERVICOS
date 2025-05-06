package dev.caio.tasks_api.config;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import dev.caio.tasks_api.security.CustomJwtAuthenticationConverter;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
	
	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;
	
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;
    
    private final CustomJwtAuthenticationConverter customJwtAuthenticationConverter;
    
    @Autowired
    public SecurityConfig(CustomJwtAuthenticationConverter customJwtAuthenticationConverter) {
        this.customJwtAuthenticationConverter = customJwtAuthenticationConverter;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
       
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
    	return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }
    
    @Bean
    public JwtEncoder jwtEncoder() {
    	RSAKey jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
  ImmutableJWKSet<com.nimbusds.jose.proc.SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
  return new NimbusJwtEncoder(jwks);
  
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	
    	return authenticationConfiguration.getAuthenticationManager();

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
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(this.customJwtAuthenticationConverter))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            return http.build();
        }
}