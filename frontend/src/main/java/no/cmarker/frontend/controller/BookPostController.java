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
	
	public String openBookDetailPage(long selectedBookId){
		this.selectedBookId = selectedBookId;
		return "book_details.xhtml?faces-redirect=true";
	}
	
	private void createBookPost(long bookId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		bookPostService.createBookPost(auth.getName(), bookId);
	}
	
	public Book getSelectedBook(){
		return bookService.getBook(selectedBookId);
	}
	
	private void unmarkBookForSale(long bookId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		List<BookPost> bookPostLoggedInUser = new ArrayList<>();
		
		List<BookPost> usersBookPostList = bookPostService.getAllBookPostsForUser(auth.getName());
		
		for (BookPost bookPost: usersBookPostList) {
			if (bookPost.getBook().getId() == bookId){
				bookPostLoggedInUser.add(bookPost);
			}
		}
		
		if (bookPostLoggedInUser.size() > 0) {
			bookPostLoggedInUser.forEach((element) -> element.setForSale(false));
		}
	}
	
	public void updateBookPostListener(ValueChangeEvent event) {
		
		boolean updatedValue = Boolean.parseBoolean(event.getNewValue().toString());
		Long bookId = (Long) ((UIInput) event.getSource()).getAttributes().get("bookId");
		
		if (updatedValue) {
			createBookPost(bookId);
		} else {
			unmarkBookForSale(bookId);
		}
	}
	
	public List<BookPost> getAllForSaleBookPosts() {
		
		return bookPostService.getAllForSaleBookPostsForPost(selectedBookId);
	}
	
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
