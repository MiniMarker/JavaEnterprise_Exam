package no.cmarker.frontend;

import no.cmarker.Application;
import org.springframework.boot.SpringApplication;

/**
 * @author Christian Marker on 16/04/2018 at 15:23.
 */
public class LocalApplicationRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, "--spring.profiles.active=test");
	}
}
