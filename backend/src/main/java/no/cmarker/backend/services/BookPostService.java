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
	
	/**
	 * Create a new BookPost of given book
	 *
	 * @param username username of the user who publishes the bookpost
	 * @param bookId   id of the Book entity
	 * @return generated id of the BookPost
	 */
	public Long createBookPost(String username, long bookId) {
		
		//check if given User exists
		User user = em.find(User.class, username);
		if (user == null) {
			throw new IllegalArgumentException("Could not fint user with username: " + username);
		}
		
		//check if given Book exist
		Book book = em.find(Book.class, bookId);
		if (book == null) {
			throw new IllegalArgumentException("Could not fint book with id: " + bookId);
		}
		
		//add data to entity
		BookPost bookPost = new BookPost();
		bookPost.setSeller(user);
		bookPost.setBook(book);
		bookPost.setForSale(true);
		
		//commit data to DB
		em.persist(bookPost);
		
		//update foreign keys
		book.getBookPosts().add(bookPost);
		
		return bookPost.getId();
	}
	
	/**
	 * Change the forSale value of the BookPost with given id
	 *
	 * @param id id of the bookpost to update
	 */
	public void unmarkBookPostAsSellable(long id) {
		//get BookPost from DB
		BookPost bookPost = getBookPost(id);
		
		//update values
		bookPost.setForSale(false);
	}
	
	/**
	 * Get all info from DB about the BookPost with given id
	 *
	 * @param id id of BookPost to view
	 * @return BookPost -object containing all data from DB
	 */
	public BookPost getBookPost(long id) {
		return em.find(BookPost.class, id);
	}
	
	/**
	 * Get all BookPosts for a Book
	 *
	 * @param bookId id of Book
	 * @return List of all BookPosts
	 */
	public List<BookPost> getAllBookPostsForPost(long bookId) {
		
		//generate query
		TypedQuery<BookPost> query = em.createQuery("SELECT bp FROM BookPost bp WHERE bp.book.id = ?1", BookPost.class);
		query.setParameter(1, bookId);
		
		return query.getResultList();
	}
	
	/**
	 * Get all BookPosts for a Book that is marked for sale
	 *
	 * @param bookId id of Book
	 * @return List of all BookPosts
	 */
	public List<BookPost> getAllForSaleBookPostsForPost(long bookId) {
		
		//generate query
		TypedQuery<BookPost> query = em.createQuery("SELECT bp FROM BookPost bp WHERE bp.book.id = ?1 AND bp.forSale = true", BookPost.class);
		query.setParameter(1, bookId);
		
		return query.getResultList();
	}
	
	/**
	 * Get all BookPosts for a given user
	 *
	 * @param username username of user
	 * @return List of all BookPosts
	 */
	public List<BookPost> getAllBookPostsForUser(String username) {
		
		//generate query
		TypedQuery<BookPost> query = em.createQuery("SELECT bp FROM BookPost bp WHERE bp.seller.username = ?1", BookPost.class);
		query.setParameter(1, username);
		
		return query.getResultList();
	}
}
