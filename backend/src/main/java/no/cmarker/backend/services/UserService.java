package no.cmarker.backend.services;

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
	
	/**
	 * Create a new User
	 *
	 * @param username  username of User
	 * @param password  password (plain text) of the User
	 * @param firstname firstname of the User
	 * @param lastname  lastname of the User
	 * @return status of the entity creation
	 */
	public boolean createUser(String username, String password, String firstname, String lastname) {
		
		//check if there already exist a user with th given username
		if (em.find(User.class, username) != null) {
			return false;
		}
		
		//use BCryptPasswordEncoder to hash the given password
		String hashedPassword = passwordEncoder.encode(password);
		
		//add data to entity
		User user = new User();
		user.setUsername(username);
		user.setPassword(hashedPassword); //hashed password
		user.setRoles(Collections.singleton("USER"));
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEnabled(true);
		
		//commit to DB
		em.persist(user);
		
		return true;
	}
	
	/**
	 * Get a given users inbox
	 *
	 * @param username username of user
	 * @return List of Messages
	 */
	public List<Message> getInbox(String username) {
		
		//generate query
		TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.reciever.username = ?1 ORDER BY m.date DESC", Message.class);
		query.setParameter(1, username);
		
		return query.getResultList();
	}
	
	/**
	 * Get a given users outbox
	 *
	 * @param username username of user
	 * @return List of Messages
	 */
	public List<Message> getOutbox(String username) {
		
		//generate query
		TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.sender.username = ?1 ORDER BY m.date DESC", Message.class);
		query.setParameter(1, username);
		
		return query.getResultList();
	}
}
