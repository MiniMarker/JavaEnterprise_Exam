package no.cmarker.frontend.selenium.po;

import no.cmarker.backend.entities.Message;
import no.cmarker.backend.services.BookService;
import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

import javax.security.auth.login.LoginContext;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Christian Marker on 16/04/2018 at 15:27.
 */
public class IndexPO extends LayoutPO {
	
	public IndexPO(PageObject other) {
		super(other);
	}
	
	public IndexPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Index");
	}
	
	public SignUpPO goToSignUpPage(){
		
		clickAndWait("linkToSignUp");
		SignUpPO signUpPO = new SignUpPO(this);
		assertTrue(signUpPO.isOnPage());
		
		return signUpPO;
	}
	
	public LogInPO goToLogInPage(){
		
		clickAndWait("linkToLogIn");
		LogInPO logInPO = new LogInPO(this);
		assertTrue(logInPO.isOnPage());
		
		return logInPO;
	}
	
	public BookDetailsPO goToLogBookDetails(long tableRow){
		
		clickAndWait("bookTable:"+ tableRow +":linkToBookDetails_");
		BookDetailsPO bookDetailsPO = new BookDetailsPO(this);
		assertTrue(bookDetailsPO.isOnPage());
		
		return bookDetailsPO;
	}
	
	public MessagesPO goToMessages(){
		
		clickAndWait("linkToMessagePage");
		MessagesPO messagesPO = new MessagesPO(this);
		assertTrue(messagesPO.isOnPage());
		
		return messagesPO;
	}
	
	public void createBook(String title, String author, String course){
		
		setText("createBookForm:bookTitle", title);
		setText("createBookForm:bookAuthor", author);
		setText("createBookForm:bookCourse", course);
		
		clickAndWait("createBookForm:createBookButton");
	}
}
