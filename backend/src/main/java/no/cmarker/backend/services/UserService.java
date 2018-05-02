package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

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
		//user.setRoles(Collections.singleton("USER"));
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEnabled(true);
		
		em.persist(user);
		
		return true;
	}
	
}
