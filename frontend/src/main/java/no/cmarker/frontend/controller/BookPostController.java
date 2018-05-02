package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.services.BookPostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 * @author Christian Marker on 02/05/2018 at 19:29.
 */
@Named
@SessionScoped
public class BookPostController {
	
	@Autowired
	private BookPostService bookPostService;
	
	public List<BookPost> getAllBookPosts(long id){
		return bookPostService.getAllBookPosts(id);
	}
}
