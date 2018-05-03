package no.cmarker.frontend.selenium;

import no.cmarker.Application;
import no.cmarker.frontend.selenium.po.BookDetailsPO;
import no.cmarker.frontend.selenium.po.IndexPO;
import no.cmarker.frontend.selenium.po.LogInPO;
import no.cmarker.frontend.selenium.po.SignUpPO;
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
		
		assertEquals(2, home.getRowsInTable("bookTitle"));
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
		
		home.createBook("A Game of Thornes", "George RR Martin", "PRO200");
		
		assertEquals(3, home.getRowsInTable("bookTitle"));
	}
	
	@Test
	public void testGetInbox(){
	
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
