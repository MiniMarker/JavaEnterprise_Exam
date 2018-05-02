package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.User;
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
	
	public Long createBook(String title, String author, String course){
		
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setCourse(course);
		
		em.persist(book);
		
		return book.getId();
	}
	
	public Book getBook(long id) {
		return em.find(Book.class, id);
	}
	
	public boolean deleteBook(long id){
		
		Book book = getBook(id);
		
		if (book != null){
			em.remove(book);
			return true;
		}
		
		return false;
	}
	
	public List<Book> getAllBooks(){
		
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b order by b.title", Book.class);
		return query.getResultList();
	}
	
	public List<Book> getAllBooksWithMoreThanOnePost(){
		
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.bookPosts.size >= 1 order by b.title", Book.class);
		return query.getResultList();
		
	}
	
	
}
