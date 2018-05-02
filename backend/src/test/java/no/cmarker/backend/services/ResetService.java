package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.entities.Message;
import no.cmarker.backend.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Christian Marker on 16/04/2018 at 14:24.
 */
@Service
@Transactional
public class ResetService {

	@PersistenceContext
	private EntityManager em;
	
	

	public void resetDatabase(){
		Query query = em.createNativeQuery("delete from user_roles");
		query.executeUpdate();
		
		deleteEntity(BookPost.class);
		deleteEntity(Book.class);
		deleteEntity(Message.class);
		deleteEntity(User.class);
		
	}
	
	public void deleteEntityById(Class<?> entity, Object id){
		Object obj = em.find(entity, id);
		em.joinTransaction();
		em.remove(obj);
	}

	protected void deleteEntity(Class<?> entity){

		if (entity == null || entity.getAnnotation(Entity.class) == null){
			throw new IllegalArgumentException("Invalid non-entity class");
		}

		String name = entity.getSimpleName();

		Query query = em.createQuery("DELETE FROM " + name);
		query.executeUpdate();

	}

}
