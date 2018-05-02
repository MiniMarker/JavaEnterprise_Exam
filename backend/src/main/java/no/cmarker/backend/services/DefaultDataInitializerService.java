package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
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
	private BookService bookService;
	
	@Autowired
	private BookPostService bookPostService;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initialize(){
		
		String userName = "Frodo123";
		
		userService.createUser(userName, "Shire", "Frodo", "Baggins");
		
		Long book1Id = attempt(() -> bookService.createBook("The hitchhikers guide to the galaxy", "Douglas Adams", "PG5100"));
		Long book2Id = attempt(() -> bookService.createBook("The hitchhikers guide to the galaxy", "Douglas Adams", "PG5100"));
		
		Long bookPost1Id = attempt(() -> bookPostService.createBookPost(userName, book1Id));
		
	}
	
	private <T> T attempt(Supplier<T> lambda) {
		try {
			return lambda.get();
		} catch (Exception e) {
			return null;
		}
	}
}
