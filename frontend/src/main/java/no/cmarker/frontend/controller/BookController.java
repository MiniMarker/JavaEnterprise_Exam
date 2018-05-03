package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.services.BookPostService;
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
	
	private long selectedBookId;
	
	@Autowired
	private BookService bookService;
	
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	public List<Book> getAllForSaleBooks(){
		return bookService.getAllBooks();
	}
	
	public void createBook(){
		bookService.createBook(addBookTitle, addBookAuthor, addBookCourse);
	}
	
	public Book getBook(String id){
		System.out.println(bookService.getBook(Long.parseLong(id)).getTitle());
		return bookService.getBook(Long.parseLong(id));
	}
	
	
	public String selectBook(long bookId){
		return "book_details.xhtml?book=" + bookId + "&faces-redirect=true";
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
	
	public long getSelectedBookId() {
		return selectedBookId;
	}
	
	public void setSelectedBookId(long selectedBookId) {
		this.selectedBookId = selectedBookId;
	}
}
