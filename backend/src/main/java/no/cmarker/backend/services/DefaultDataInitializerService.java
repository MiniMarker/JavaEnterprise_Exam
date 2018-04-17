package no.cmarker.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

/**
 * @author Christian Marker on 16/04/2018 at 17:51.
 */

@Service
public class DefaultDataInitializerService {
	
	@Autowired
	private PersonService personService;
	
	@PostConstruct
	public void initialize(){
		Integer id1 = attempt(() -> personService.createPerson("Ola"));
		
	}
	
	private <T> T attempt(Supplier<T> lambda) {
		try {
			return lambda.get();
		} catch (Exception e) {
			return null;
		}
	}
}
