package no.cmarker.backend.services;

import no.cmarker.backend.entities.Message;
import no.cmarker.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Christian Marker on 02/05/2018 at 13:15.
 */
@Service
@Transactional
public class MessageService {
	
	@Autowired
	private EntityManager em;
	
	/**
	 * Create a new Message
	 *
	 * @param senderUsername   username of the sender
	 * @param receiverUsername username of the receiver
	 * @param message          body text of the message
	 * @return generated id of the Message
	 */
	public Long createMessage(String senderUsername, String receiverUsername, String message) {
		
		//check if senders user exist
		User sender = em.find(User.class, senderUsername);
		if (sender == null) {
			throw new IllegalArgumentException("Could not find user: " + senderUsername);
		}
		
		//check if receiver user exist
		User receiver = em.find(User.class, receiverUsername);
		if (receiver == null) {
			throw new IllegalArgumentException("Could not find user: " + receiverUsername);
		}
		
		//add data to entity
		Message messageToBeSent = new Message();
		messageToBeSent.setSender(sender);
		messageToBeSent.setReciever(receiver);
		messageToBeSent.setMessage(message);
		messageToBeSent.setRead(false);
		messageToBeSent.setDate(LocalDateTime.now());
		
		//commit to DB
		em.persist(messageToBeSent);
		
		//update foreign keys
		sender.getOutbox().add(messageToBeSent);
		receiver.getInbox().add(messageToBeSent);
		
		return messageToBeSent.getId();
	}
	
	/**
	 * Mark a given Message as read
	 *
	 * @param id id of Message
	 */
	public void markMessageAsRead(long id) {
		//get Message form DB
		Message message = getMessage(id);
		
		//update values
		message.setRead(true);
	}
	
	/**
	 * Get all info from DB about the Message with given id
	 *
	 * @param id id of Message
	 * @return Message -object containing all data from DB
	 */
	public Message getMessage(long id) {
		return em.find(Message.class, id);
	}
}
