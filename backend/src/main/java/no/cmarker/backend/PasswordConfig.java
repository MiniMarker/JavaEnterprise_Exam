package no.cmarker.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Christian Marker on 16/04/2018 at 14:05.
 */
@Configuration
public class PasswordConfig {
	
	/**
	 * Import BCryptPasswordEncoder for password encoding/decoding
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
