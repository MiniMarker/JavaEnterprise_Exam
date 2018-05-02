package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Christian Marker on 02/05/2018 at 16:34.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MessageServiceTest extends ServiceTestBase {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void createMessage(){
		
		String senderUsername = "Bilbo";
		String receiverUsername = "Samwise";
		
		userService.createUser(senderUsername, "Shire", "Bilbo", "Baggins");
		userService.createUser(receiverUsername, "Mordor", "Samwise", "Gamgee");
		
		Long messageId = messageService.createMessage(senderUsername, receiverUsername, "DON'T PANIC!");
		
		assertNotNull(messageId);
		assertEquals(1, userService.getInbox(receiverUsername, false).size());
		assertEquals(1, userService.getOutbox(senderUsername).size());
	}
	
	@Test
	public void testMarkMessageAsRead(){
		String senderUsername = "Ford";
		String receiverUsername = "Arthur";
		
		userService.createUser(senderUsername, "Towel", "Ford", "Something");
		userService.createUser(receiverUsername, "42", "Arthur", "Dent");
		
		Long messageId = messageService.createMessage(senderUsername, receiverUsername, "DON'T PANIC!");
		
		assertNotNull(messageId);
		assertFalse(messageService.getMessage(messageId).isRead());
		
		messageService.markMessageAsRead(messageId);
		assertTrue(messageService.getMessage(messageId).isRead());
	}
}
