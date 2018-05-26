package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Christian Marker on 02/05/2018 at 13:15.
 */
@Service
@Transactional
public class BookService {
	
	@Autowired
	private EntityManager em;
	
	/**
	 * Create a new Book
	 *
	 * @param title  title of Book
	 * @param author author of Book
	 * @param course relevant course for Book
	 * @return generated id of the Book
	 */
	public Long createBook(String title, String author, String course) {
		
		//add data to entity
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setCourse(course);
		
		//commit data to DB
		em.persist(book);
		
		return book.getId();
	}
	
	/**
	 * Get all info from DB about the Book with given id
	 *
	 * @param id id of Book to view
	 * @return Book -object containing all data from DB
	 */
	public Book getBook(long id) {
		return em.find(Book.class, id);
	}
	
	/**
	 * Delete a Book from the DB
	 *
	 * @param id id of Book-entity to delete
	 * @return success status
	 */
	public boolean deleteBook(long id) {
		
		//get book
		Book book = getBook(id);
		
		//check if book exits
		if (book != null) {
			em.remove(book);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get all Books in DB
	 * (ordered by title)
	 *
	 * @return List of all Books
	 */
	public List<Book> getAllBooks() {
		
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b order by b.title", Book.class);
		return query.getResultList();
	}
	
	/**
	 * Get all Books in DB with more than one BookPost
	 * (ordered by title)
	 *
	 * @return List of all Books with more than one BookPost
	 */
	public List<Book> getAllBooksWithMoreThanOnePost() {
		
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.bookPosts.size >= 1 order by b.title", Book.class);
		return query.getResultList();
		
	}
	
	
}
