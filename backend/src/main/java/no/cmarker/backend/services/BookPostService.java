package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Christian Marker on 02/05/2018 at 14:22.
 */
@Service
@Transactional
public class BookPostService {
	
	@Autowired
	private EntityManager em;
	
	public Long createBookPost(String username, long bookId){
		
		User user = em.find(User.class, username);
		if (user == null){
			throw new IllegalArgumentException("Could not fint user with username: " + username);
		}
		
		Book book = em.find(Book.class, bookId);
		if (book == null){
			throw new IllegalArgumentException("Could not fint book with id: " + bookId);
		}
		
		BookPost bookPost = new BookPost();
		bookPost.setSeller(user);
		bookPost.setBook(book);
		bookPost.setForSale(true);
		
		em.persist(bookPost);
		
		book.getBookPosts().add(bookPost);
		
		return bookPost.getId();
	}
	
	public void unmarkBookPostAsSellable(long id){
		
		BookPost bookPost = getBookPost(id);
		
		bookPost.setForSale(false);
		
	}
	
	public BookPost getBookPost(long id){
		return em.find(BookPost.class, id);
	}
	
	public List<BookPost> getAllBookPosts(long bookId){
		TypedQuery<BookPost> query = em.createQuery("SELECT bp FROM BookPost bp WHERE bp.book.id = ?1", BookPost.class);
		query.setParameter(1, bookId);
		
		return query.getResultList();
	}
	
	public List<BookPost> getAllBookPostsForUser(String username){
		
		TypedQuery<BookPost> query = em.createQuery("SELECT bp FROM BookPost bp WHERE bp.seller.username = ?1", BookPost.class);
		query.setParameter(1, username);
		
		return query.getResultList();
		
		
	}
	
}
