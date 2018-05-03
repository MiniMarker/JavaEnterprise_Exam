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
import static org.junit.Assert.assertNull;

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
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBookPostWithWrongUsername(){
		
		Long bookId = bookService.createBook("LOTR", "Tolkien", "PG5100");
		
		//wrong username
		Long bookPostId = bookPostService.createBookPost("WRONG", bookId);
		assertNull(bookPostId);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBookPostWithWrongBookId(){
			String userName = "Bilbo";
			userService.createUser(userName, "Bar", "foo", "bar");
			
			//wrong bookId
			Long bookPostId2 = bookPostService.createBookPost(userName, 20000);
			assertNull(bookPostId2);
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
		
		List<BookPost> bookPostList = bookPostService.getAllBookPostsForPost(bookId);
		assertEquals(bookPostList.size(), 1);
	}
	
	@Test
	public void testGetAllPostsFromUser(){
		
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
	/*
	@Test
	public void deleteBookPost(){
		String userName = "Bilbo";
		String bookTitle = "LOTR";
		
		String userName2 = "Douglas";
		String bookTitle2 = "Dirk Gently";
		
		userService.createUser(userName, "Bar", "foo", "bar");
		userService.createUser(userName2, "42", "Douglas", "Adams");
		
		Long bookId = bookService.createBook(bookTitle, "Tolkien", "PG5100");
		Long bookId2 = bookService.createBook(bookTitle2, "Adams", "PG4200");
		
		Long bookPostId = bookPostService.createBookPost(userName, bookId);
		Long bookPost2Id = bookPostService.createBookPost(userName2, bookId);
		Long bookPost3Id = bookPostService.createBookPost(userName, bookId2);
		Long bookPost4Id = bookPostService.createBookPost(userName2, bookId2);
		
		assertEquals(2, bookPostService.getAllBookPostsForPost(bookId).size());
		
		bookPostService.deleteBookPost(bookPostId);
		
		assertEquals(1, bookPostService.getAllBookPostsForPost(bookId).size());
	}
	*/
}
