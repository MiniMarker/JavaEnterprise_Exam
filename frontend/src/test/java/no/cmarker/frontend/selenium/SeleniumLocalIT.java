package no.cmarker.frontend.selenium;

import no.cmarker.Application;
import no.cmarker.backend.entities.Message;
import no.cmarker.frontend.selenium.po.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Christian Marker on 16/04/2018 at 15:26.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {
	
	@LocalServerPort
	private int port;
	private static WebDriver driver;
	private IndexPO home;
	
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	private String getUniqueId() {
		return "user_" + counter.getAndIncrement();
	}
	
	/*
		BEFORE TESTS
	 */
	@BeforeClass
	public static void initClass() {
		
		driver = SeleniumDriverHandler.getChromeDriver();
		
		if (driver == null) {
			//Do not fail the tests.
			throw new AssumptionViolatedException("Cannot find/initialize Chrome driver");
		}
	}
	
	@Before
	public void initTest() {
		
		getDriver().manage().deleteAllCookies();
		
		home = new IndexPO(getDriver(), getServerHost(), getServerPort());
		
		home.toStartingPage();
		
		assertTrue("Failed to start from Home Page", home.isOnPage());
	}
	
	/*
		AFTER TESTS
	 */
	
	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.close();
		}
	}
	
	protected WebDriver getDriver() {
		return driver;
	}
	
	protected String getServerHost() {
		return "localhost";
	}
	
	protected int getServerPort() {
		return port;
	}
	
	/*
		Tests
	 */
	@Test
	public void goToStartPage() {
		home.toStartingPage();
		assertTrue(home.isOnPage());
	}
	
	@Test
	public void testCreateUser() {
		home.toStartingPage();
		
		SignUpPO signUpPO = home.goToSignUpPage();
		assertTrue(signUpPO.isOnPage());
		
		String username = getUniqueId();
		String password = "IShallPass!";
		String firstname = "Gandalf";
		String lastname = "The Grey";
		
		signUpPO.createUser(username, password, firstname, lastname);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
	}
	
	@Test
	public void testCreateUserWithExistingUsername() {
		home.toStartingPage();
		
		SignUpPO signUpPO = home.goToSignUpPage();
		assertTrue(signUpPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		String firstname = "Gandalf";
		String lastname = "The Grey";
		
		signUpPO.createUser(username, password, firstname, lastname);
		
		assertTrue(signUpPO.isOnPage());
		
		assertFalse(home.isLoggedIn());
		assertFalse(home.getDriver().getPageSource().contains(username));
	}
	
	@Test
	public void logInUser() {
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
	}
	
	@Test
	public void testDefaultBooks() {
		home.toStartingPage();
		
		assertTrue(home.getRowsInTable("bookTitle") >= 2);
	}
	
	@Test
	public void testRegistrerSelling() {
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
		
		int numberOfSellers = getNumberOfSellers(1);
		int numberOfSellers2 = getNumberOfSellers(2);
		
		assertEquals(numberOfSellers, getNumberOfSellers(1));
		
		getYesCheckbox(0).click();
		assertEquals((numberOfSellers + 1), getNumberOfSellers(1));
		assertEquals(numberOfSellers2, getNumberOfSellers(2));
		
		getNoCheckbox(0).click();
		assertEquals(numberOfSellers, getNumberOfSellers(1));
		assertEquals(numberOfSellers2, getNumberOfSellers(2));
		
		home.logOut();
		
		assertEquals(numberOfSellers, getNumberOfSellers(1));
		assertEquals(numberOfSellers2, getNumberOfSellers(2));
	}
	
	@Test
	public void testBookDetails() {
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
		
		int numberOfSellers = getNumberOfSellers(1);
		
		getYesCheckbox(0).click();
		assertEquals((numberOfSellers + 1), getNumberOfSellers(1));
		
		BookDetailsPO bookDetailsPO = home.goToLogBookDetails(0);
		
		assertEquals((numberOfSellers + 1), bookDetailsPO.getRowsInTable("bookPostList"));
		assertEquals("Frodo Baggins", getSellerOfBook(numberOfSellers + 1));
		
		bookDetailsPO.logOut();
		
		home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username2 = "Bilbo90";
		String password2 = "Mordor";
		
		logInPO.logInUser(username2, password2);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username2));
		
		home.goToLogBookDetails(0);
		
		assertEquals((numberOfSellers + 1), bookDetailsPO.getRowsInTable("bookPostList"));
		assertEquals("Frodo Baggins", getSellerOfBook(numberOfSellers + 1));
	}
	
	@Test
	public void testCreateBook() {
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
		
		int currentRowsInTable = home.getRowsInTable("bookTitle");
		
		home.createBook("A Game of Thornes", "George RR Martin", "PRO200");
		
		assertEquals((currentRowsInTable + 1), home.getRowsInTable("bookTitle"));
	}
	/*
	@Test
	public void testGetOutbox(){
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		MessagesPO messagesPO = home.goToMessages();
		assertTrue(messagesPO.isOnPage());
		
		int originalmessagesPO.getRowsInTable("outboxList")
		
		assertEquals(2, messagesPO.getRowsInTable("outboxList"));
	}
	
	@Test
	public void testGetInbox(){
		home.toStartingPage();
		
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		
		String username = "Frodo123";
		String password = "Shire";
		
		logInPO.logInUser(username, password);
		
		MessagesPO messagesPO = home.goToMessages();
		assertTrue(messagesPO.isOnPage());
		
		assertEquals(2, messagesPO.getRowsInTable("inboxList"));
	}
	*/
	
	@Test
	public void testMessages(){
		home.toStartingPage();
		
		//Log in user 1
		LogInPO logInPO = home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		String username = "Frodo123";
		String password = "Shire";
		logInPO.logInUser(username, password);
		
		//get message data
		MessagesPO messagesPO = home.goToMessages();
		int user1OriginalInbox = messagesPO.getRowsInTable("inboxList");
		int user1OriginalOutbox = messagesPO.getRowsInTable("outboxList");
		
		//Create book
		home.toStartingPage();
		home.createBook("Harry Potter and The Deathly Hallows", "J.K. Rowling", "PRO200");
		
		//mark as for sale
		getYesCheckbox(1).click();
		//log out
		home.logOut();
		
		//log in user 2
		home.goToLogInPage();
		String username2 = "Bilbo90";
		String password2 = "Mordor";
		logInPO.logInUser(username2, password2);
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username2));
		
		//get message data
		home.goToMessages();
		int user2OriginalInbox = messagesPO.getRowsInTable("inboxList");
		int user2OriginalOutbox = messagesPO.getRowsInTable("outboxList");
		
		//go to bookDetails
		home.toStartingPage();
		BookDetailsPO bookDetailsPO = home.goToLogBookDetails(1);
		assertTrue(bookDetailsPO.isOnPage());
		
		//send message to seller
		bookDetailsPO.sendMessage("This is an autogenerated message",0);
		
		bookDetailsPO.toStartingPage();
		messagesPO = home.goToMessages();
		assertTrue(messagesPO.isOnPage());
		
		//assert that the message has been appended to the list
		assertEquals((user1OriginalOutbox + 1), messagesPO.getRowsInTable("outboxList"));
		
		//log out
		home.logOut();
		home.goToLogInPage();
		
		//log in user 1
		logInPO.logInUser(username, password);
		
		//check message data
		home.goToMessages();
		assertTrue(messagesPO.isOnPage());
		assertEquals((user1OriginalInbox + 1), messagesPO.getRowsInTable("inboxList"));
		
		//send message
		//because the list is sorted correctly this message will be in place 0
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		messagesPO.sendResponse("DON'T PANIC!", 0);
		assertEquals((user2OriginalOutbox + 1), messagesPO.getRowsInTable("outboxList"));
		
		//log in user 2
		home.logOut();
		home.goToLogInPage();
		assertTrue(logInPO.isOnPage());
		logInPO.logInUser(username2, password2);
		
		//check message data
		home.goToMessages();
		assertEquals((user2OriginalInbox + 1), messagesPO.getRowsInTable("inboxList"));
	}
	
	@Test
	public void testSendMessageFromMessagePage(){
		home.toStartingPage();
		
		SignUpPO signUpPO = home.goToSignUpPage();
		
		String username1 = "Arthur12";
		String password1 = "Towel";
		signUpPO.createUser(username1, password1, "Arthur", "Dent");
		signUpPO.logOut();
		
		home.toStartingPage();
		home.goToSignUpPage();
		
		String username2 = "Zaphod34";
		String password2 = "President";
		signUpPO.createUser(username2, password2, "Zaphod", "Beeblebrox");
		
		MessagesPO messagesPO = home.goToMessages();
		messagesPO.sendMessage(username1, "42");
		
		assertEquals(1, messagesPO.getRowsInTable("outboxList"));
	}
	
	private String getSellerOfBook(int rowInTable){
		return driver.findElement(By.xpath(
				"/html/body/table/tbody/tr["+ rowInTable +"]/td[1]/label"
		)).getText();
	}
	
	private WebElement getYesCheckbox(int rowInTable){
		return driver.findElement(By.xpath(
				"//*[@id='bookTable:" + rowInTable + ":sellBookForm']/table/tbody/tr/td[*=' Yes']/input"
		));
	}
	
	private WebElement getNoCheckbox(int rowInTable){
		return driver.findElement(By.xpath(
				"//*[@id='bookTable:" + rowInTable + ":sellBookForm']/table/tbody/tr/td[*=' No']/input"
		));
	}
	
	private int getNumberOfSellers(int rowInTable){
		return Integer.parseInt(driver.findElement(By.xpath(
				"//*[@id='bookTable']/tbody/tr[" + rowInTable + "]/td[4]/label")).getText()
		);
	}
}
