package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.BookPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Christian Marker on 02/05/2018 at 14:43.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookPostServiceTest extends ServiceTestBase {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookPostService bookPostService;
	
	@Test
	public void testCreateBookPost(){
		
		String userName = "Foo";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		Long bookId = bookService.createBook("LOTR", "Tolkien", "PG5100");
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		
		Book book = bookService.getBook(bookId);
		assertEquals(book.getBookPosts().size(), 1);
		assertEquals(book.getBookPosts().get(0).getBook().getTitle(), "LOTR");
	}
	
	@Test
	public void testGetBookPost(){
		
		String userName = "Foo";
		String bookTitle = "LOTR";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		Long bookId = bookService.createBook(bookTitle, "Tolkien", "PG5100");
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		
		BookPost bookPost = bookPostService.getBookPost(bookPostId);
		assertEquals(bookPost.getBook().getTitle(), bookTitle);
		
		List<BookPost> bookPostList = bookPostService.getAllBookPosts(bookId);
		assertEquals(bookPostList.size(), 1);
	}
	
	@Test
	public void testGetAllBooksFromUser(){
		
		String userName = "Foo";
		String bookTitle = "LOTR";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		Long bookId = bookService.createBook(bookTitle, "Tolkien", "PG5100");
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		
		List<BookPost> bookPostList = bookPostService.getAllBookPostsForUser(userName);
		assertEquals(bookPostList.size(), 1);
	}
	
	@Test
	public void testUnmarkBookPostAsSellable(){
		String userName = "Foo";
		String bookTitle = "LOTR";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		Long bookId = bookService.createBook(bookTitle, "Tolkien", "PG5100");
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		
		assertTrue(bookPostService.getBookPost(bookPostId).isForSale());
		
		bookPostService.unmarkBookPostAsSellable(bookPostId);
		assertFalse(bookPostService.getBookPost(bookPostId).isForSale());
	}
	
	@Test
	public void deleteBookPost(){
		String userName = "Foo";
		String bookTitle = "LOTR";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		Long bookId = bookService.createBook(bookTitle, "Tolkien", "PG5100");
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		
		assertEquals(1, bookPostService.getAllBookPosts(bookId).size());
		
		bookPostService.deleteBookPost(bookPostId);
		
		assertEquals(0, bookPostService.getAllBookPosts(bookId).size());
	}
}
