package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 * @author Christian Marker on 02/05/2018 at 17:22.
 */
@Named
@SessionScoped
public class BookController {
	
	public String addBookTitle;
	public String addBookAuthor;
	public String addBookCourse;
	
	@Autowired
	private BookService bookService;
	
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}
	
	public List<Book> getAllForSaleBooks() {
		return bookService.getAllBooks();
	}
	
	public void createBook() {
		bookService.createBook(addBookTitle, addBookAuthor, addBookCourse);
	}
	
	public String getAddBookTitle() {
		return addBookTitle;
	}
	
	public void setAddBookTitle(String addBookTitle) {
		this.addBookTitle = addBookTitle;
	}
	
	public String getAddBookAuthor() {
		return addBookAuthor;
	}
	
	public void setAddBookAuthor(String addBookAuthor) {
		this.addBookAuthor = addBookAuthor;
	}
	
	public String getAddBookCourse() {
		return addBookCourse;
	}
	
	public void setAddBookCourse(String addBookCourse) {
		this.addBookCourse = addBookCourse;
	}
}
