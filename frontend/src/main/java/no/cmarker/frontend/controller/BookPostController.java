package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.services.BookPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
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
	
	private Map<Long, Boolean> bookPostMap;
	
	public void createBookPost(long bookId){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		bookPostService.createBookPost(auth.getName(), bookId);
	}
	
	public void updateBookPost(boolean sellBook, long bookId){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (sellBook){
			bookPostService.createBookPost(auth.getName(), bookId);
		} else {
			bookPostService.deleteBookPost(bookId);
		}
	}
	
	
	public List<BookPost> getAllBookPosts(long id){
		return bookPostService.getAllBookPosts(id);
	}
	
	public Map<Long, Boolean> getBookPostMap() {
		return bookPostMap;
	}
	
	public void setBookPostMap(Map<Long, Boolean> bookPostMap) {
		this.bookPostMap = bookPostMap;
	}
}
