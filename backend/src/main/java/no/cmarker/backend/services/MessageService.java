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
	
	public Long createMessage(String senderUsername, String receiverUsername, String message){
		
		User sender = em.find(User.class, senderUsername);
		User receiver = em.find(User.class, receiverUsername);
		
		if (sender == null){
			throw new IllegalArgumentException("Could not find user: " + senderUsername);
		}
		
		if (receiver == null){
			throw new IllegalArgumentException("Could not find user: " + receiverUsername);
		}
		
		Message messageToBeSent = new Message();
		messageToBeSent.setSender(sender);
		messageToBeSent.setReciever(receiver);
		messageToBeSent.setMessage(message);
		messageToBeSent.setRead(false);
		messageToBeSent.setDate(LocalDateTime.now());
		
		em.persist(messageToBeSent);
		
		sender.getOutbox().add(messageToBeSent);
		receiver.getInbox().add(messageToBeSent);
		
		return messageToBeSent.getId();
	}
	
	public void markMessageAsRead(long id){
		Message message = getMessage(id);
		message.setRead(true);
	}
	
	public Message getMessage(long id){
		return em.find(Message.class, id);
	}
}
