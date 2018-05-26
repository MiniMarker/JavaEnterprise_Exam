package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.services.BookPostService;
import no.cmarker.backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christian Marker on 02/05/2018 at 19:29.
 */
@Named
@SessionScoped
public class BookPostController {
	
	@Autowired
	private BookPostService bookPostService;
	
	@Autowired
	private BookService bookService;
	
	private Map<Long, Boolean> bookPostMap = new HashMap<>();
	
	private long selectedBookId;
	
	/**
	 * Insted of using parameters in the URL i use a variable (selectedBookId) to let the user choose which book to view
	 *
	 * @param selectedBookId the selected books Id
	 * @return a redirect to BookDetails page
	 */
	public String openBookDetailPage(long selectedBookId) {
		this.selectedBookId = selectedBookId;
		return "book_details.xhtml?faces-redirect=true";
	}
	
	/**
	 * Creates a BookPost for the authenticated user
	 *
	 * @param bookId id og the selected book
	 */
	private void createBookPost(long bookId) {
		//get the authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		bookPostService.createBookPost(auth.getName(), bookId);
	}
	
	/**
	 * Get all info about the selected Book
	 *
	 * @return Book-object
	 */
	public Book getSelectedBook() {
		return bookService.getBook(selectedBookId);
	}
	
	/**
	 * Marks all the authenticated Users BookPost for the selected Book and setting all to not for sale
	 *
	 * @param bookId id of Book to update
	 */
	private void unmarkBookForSale(long bookId) {
		//get the authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		List<BookPost> bookPostLoggedInUser = new ArrayList<>();
		List<BookPost> usersBookPostList = bookPostService.getAllBookPostsForUser(auth.getName());
		
		//loops through all bookposts for a user and adds all bookposts for the selected book to a new ArrayList
		for (BookPost bookPost : usersBookPostList) {
			if (bookPost.getBook().getId() == bookId) {
				bookPostLoggedInUser.add(bookPost);
			}
		}
		
		//loops through the authenticated Users BookPost for the selected Book and setting all to false
		if (bookPostLoggedInUser.size() > 0) {
			bookPostLoggedInUser.forEach((element) -> element.setForSale(false));
		}
	}
	
	/**
	 * Method to handle the radio buttons in the table
	 * If the user clicks yes, a BookPost for the selected Book is created
	 * If the user clicks no, the BookPost is marked as not for sale.
	 *
	 * @param event event triggered by HTML code
	 */
	public void updateBookPostListener(ValueChangeEvent event) {
		
		boolean updatedValue = Boolean.parseBoolean(event.getNewValue().toString());
		Long bookId = (Long) ((UIInput) event.getSource()).getAttributes().get("bookId");
		
		if (updatedValue) {
			createBookPost(bookId);
		} else {
			unmarkBookForSale(bookId);
		}
	}
	
	/**
	 * Get all BookPosts for the selected Book that is marked as for sale
	 *
	 * @return List of BookPosts
	 */
	public List<BookPost> getAllForSaleBookPosts() {
		return bookPostService.getAllForSaleBookPostsForPost(selectedBookId);
	}
	
	/**
	 * Get all BookPosts for a given Book that is marked as for sale
	 *
	 * @param bookId id of Book to use
	 * @return List of BookPosts
	 */
	public List<BookPost> getAllForSaleBookPosts(long bookId) {
		return bookPostService.getAllForSaleBookPostsForPost(bookId);
	}
	
	public Map<Long, Boolean> getBookPostMap() {
		return bookPostMap;
	}
	
	public void setBookPostMap(Map<Long, Boolean> bookPostMap) {
		this.bookPostMap = bookPostMap;
	}
}
