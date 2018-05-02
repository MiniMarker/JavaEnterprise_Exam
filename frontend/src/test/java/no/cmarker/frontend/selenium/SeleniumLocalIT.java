package no.cmarker.frontend.selenium;

import no.cmarker.Application;
import no.cmarker.frontend.selenium.po.IndexPO;
import no.cmarker.frontend.selenium.po.PageTwoPO;
import no.cmarker.frontend.selenium.po.SignUpPO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Christian Marker on 16/04/2018 at 15:26.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {
	
	@LocalServerPort private int port;
	private static WebDriver driver;
	private IndexPO home;
	
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	private String getUniqueId(){
		return "user_" + counter.getAndIncrement();
	}
	
	
	/*
		BEFORE TESTS
	 */
	@BeforeClass
	public static void initClass(){
		
		driver = SeleniumDriverHandler.getChromeDriver();
		
		if(driver == null){
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
		if(driver != null) {
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
	public void goToStartPage(){
		home.toStartingPage();
		assertTrue(home.isOnPage());
	}
	/*
	@Test
	public void testCreateUser() {
		home.toStartingPage();
		
		SignUpPO signUpPO = home.goToSignUpPage();
		assertTrue(signUpPO.isOnPage());
		
		String username = getUniqueId();
		String password = "IShallPass!";
		
		signUpPO.createUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
	}
	
	@Test
	public void testCreateExistingUser(){
		home.toStartingPage();
		
		SignUpPO signUpPO = home.goToSignUpPage();
		assertTrue(signUpPO.isOnPage());
		
		String username = getUniqueId();
		String password = "IShallPass!";
		signUpPO.createUser(username, password);
		
		assertTrue(home.isLoggedIn());
		assertTrue(home.getDriver().getPageSource().contains(username));
		
		home.logOut();
		home.goToSignUpPage();
		
		assertTrue(signUpPO.isOnPage());
		
		signUpPO.createUser(username, password);
		
		assertTrue(signUpPO.isOnPage());
		assertTrue(signUpPO.getDriver().getPageSource().contains("User and/or password are wrong."));
	}
	
	@Test
	public void goToPageTwo(){
		home.toStartingPage();
		
		PageTwoPO pageTwoPO = home.goToPageTwo();
		
		assertTrue(pageTwoPO.isOnPage());
	}
	*/
	
}
