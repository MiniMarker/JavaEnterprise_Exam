package no.cmarker.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * This method is used for development cases to fill the DB with default data
	 */
	@PostConstruct
	public void initialize() {
		
		String userName1 = "Frodo123";
		String userName2 = "Bilbo90";
		
		userService.createUser(userName1, "Shire", "Frodo", "Baggins");
		userService.createUser(userName2, "Mordor", "Bilbo", "Baggins");
		
		Long book1Id = attempt(() -> bookService.createBook("The hitchhikers guide to the galaxy", "Douglas Adams", "PG5100"));
		Long book2Id = attempt(() -> bookService.createBook("Dirk Gently", "Douglas Adams", "PG5100"));
		
		Long bookPost1Id = attempt(() -> bookPostService.createBookPost(userName1, book1Id));
		Long bookPost2Id = attempt(() -> bookPostService.createBookPost(userName2, book1Id));
		Long bookPost4Id = attempt(() -> bookPostService.createBookPost(userName2, book2Id));
		
		Long message1Id = attempt(() -> messageService.createMessage(userName2, userName1, "Hello!"));
		Long message2Id = attempt(() -> messageService.createMessage(userName2, userName1, "I'm going on an adventure!"));
		Long message3Id = attempt(() -> messageService.createMessage(userName1, userName2, "Wait for me"));
		Long message4Id = attempt(() -> messageService.createMessage(userName1, userName2, "I want to join"));
		
	}
	
	private <T> T attempt(Supplier<T> lambda) {
		try {
			return lambda.get();
		} catch (Exception e) {
			return null;
		}
	}
}
