package no.cmarker.backend.services;

import no.cmarker.backend.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * @author Christian Marker on 16/04/2018 at 14:00.
 */
@Service
@Transactional
public class PersonService {
	
	@Autowired private EntityManager entityManager;
	
	public Integer createPerson(String name){
		
		Person entity = new Person();
		entity.setName(name);
		entityManager.persist(entity);
		
		return entity.getId();
	}
	
}
