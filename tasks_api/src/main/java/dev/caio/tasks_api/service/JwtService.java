package dev.caio.tasks_api.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import dev.caio.tasks_api.model.User; 



@Service
public class JwtService {
	private final JwtEncoder jwtEncoder;
	
	@Autowired
	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	public String generateToken(User user) {
		Instant now = Instant.now();
		long expire = 3600L;
		
		var claims = JwtClaimsSet.builder()
				.issuer("spring-security")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expire))
				.subject(user.getUsername())
				.claim("userId", user.getId())
				.build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

	}

}
