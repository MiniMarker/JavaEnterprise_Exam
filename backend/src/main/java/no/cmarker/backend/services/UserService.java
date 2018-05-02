package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.Message;
import no.cmarker.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 09:11.
 */

@Service
@Transactional
public class UserService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean createUser(String username, String password, String firstname, String lastname){
		
		String hashedPassword = passwordEncoder.encode(password);
		
		if (em.find(User.class, username) != null) {
			return false;
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(hashedPassword);
		user.setRoles(Collections.singleton("USER"));
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEnabled(true);
		
		em.persist(user);
		
		return true;
	}
	
	public List<Message> getInbox(String username, boolean onlyNotRead){
		
		TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.reciever.username = ?1 AND m.read = ?2", Message.class);
		query.setParameter(1, username);
		query.setParameter(2, onlyNotRead);
		
		return query.getResultList();
	}
	
	public List<Message> getOutbox(String username){
		
		TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.sender.username = ?1", Message.class);
		query.setParameter(1, username);
		
		return query.getResultList();
	}
	
}
