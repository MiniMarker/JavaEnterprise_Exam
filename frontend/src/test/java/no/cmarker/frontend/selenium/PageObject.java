package no.cmarker.frontend.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;


public abstract class PageObject {
	
	protected final WebDriver driver;
	protected final String host;
	protected final int port;
	
	private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());
	
	public static String getUniqueId() {
		return "foo" + counter.incrementAndGet();
	}
	
	public PageObject(WebDriver driver, String host, int port) {
		this.driver = driver;
		this.host = host;
		this.port = port;
	}
	
	public PageObject(PageObject other) {
		this(other.getDriver(), other.getHost(), other.getPort());
	}
	
	public abstract boolean isOnPage();
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void refresh(){
		driver.navigate().refresh();
	}
	
	public void clickAndWait(String id){
		WebElement element = driver.findElement(By.id(id));
		element.click();
		waitForPageToLoad();
	}
	
	public String getText(String id){
		return driver.findElement(By.id(id)).getText();
	}
	
	public int getInteger(String id){
		String text = getText(id);
		
		return Integer.parseInt(text);
	}
	
	public void setText(String id, String text){
		WebElement element = driver.findElement(By.id(id));
		element.clear();
		element.sendKeys(text);
	}
	
	protected Boolean waitForPageToLoad() {
		
		//FluentWait
		return new WebDriverWait(driver, 10).until(driver -> {
			try {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				
				return Boolean.parseBoolean(jsExecutor.executeScript(
						"return /loaded|complete/.test(document.readyState);").toString()
				);
				
			} catch (NoSuchWindowException e) {
				System.out.println("Your Window Name not found, found window: " + driver.getTitle());
				System.out.println(e.getMessage());
				return false;
			}
			
		});
		/*
		//WebDriverWait - Didn't get this to work
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 10); //give up after 10 seconds
		
		//keep executing the given JS till it returns "true", when page is fully loaded and ready
		return wait.until((ExpectedCondition<Boolean>) input -> {
			String res = jsExecutor.executeScript("return /loaded|complete/.test(document.readyState);").toString();
			return Boolean.parseBoolean(res);
		});
		*/
	}
}
