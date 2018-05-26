package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import no.cmarker.backend.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Christian Marker on 02/05/2018 at 14:43.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest extends ServiceTestBase {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookPostService bookPostService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void createBook() {
		
		String title = "Foo";
		String author = "Bar";
		String course = "PG5100";
		
		Long id = bookService.createBook(title, author, course);
		assertNotNull(id);
	}
	
	@Test
	public void testGetBook() {
		
		String title = "Foo";
		String author = "Bar";
		String course = "PG5100";
		
		Long bookId = bookService.createBook(title, author, course);
		assertNotNull(bookId);
		
		Book book = bookService.getBook(bookId);
		assertEquals(book.getId(), bookId);
	}
	
	@Test
	public void getAllBooks() {
		String title = "Foo";
		String author = "Bar";
		String course = "PG5100";
		
		Long bookId = bookService.createBook(title, author, course);
		assertNotNull(bookId);
		
		List<Book> resultList = bookService.getAllBooks();
		assertEquals(resultList.size(), 1);
	}
	
	@Test
	public void testGetAllBooksWithMoreThenOnePost() {
		
		userService.createUser("Hello", "World", "Samwise", "Baggins");
		
		Long book1Id = bookService.createBook("Foo", "Bar", "PG5100");
		Long book2Id = bookService.createBook("A Game of Thrones", "George R.R. Martin", "PG5100");
		
		List<Book> allBooksList = bookService.getAllBooks();
		assertEquals(2, allBooksList.size());
		
		Long bookPostId = bookPostService.createBookPost("Hello", book1Id);
		
		List<Book> resultList = bookService.getAllBooksWithMoreThanOnePost();
		assertEquals(1, resultList.size());
	}
	
	@Test
	public void deleteBook() {
		String title = "Foo";
		String author = "Bar";
		String course = "PG5100";
		
		Long bookId = bookService.createBook(title, author, course);
		assertNotNull(bookId);
		
		assertTrue(bookService.deleteBook(bookId));
		List<Book> resultList = bookService.getAllBooks();
		assertEquals(resultList.size(), 0);
		
	}
	
}
