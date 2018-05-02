package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Christian Marker on 16/04/2018 at 15:30.
 */
public abstract class LayoutPO extends PageObject {
	
	public LayoutPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	public LayoutPO(PageObject other) {
		super(other);
	}
	
	public void toStartingPage() {
		getDriver().get(host + ":" + port);
	}
	
	//Check if item is in a table
	//the column in table must be a outputLabel and have the class 'dishName' (this need to be changed on the exam)
	public boolean hasDishByName(String name){
		List<WebElement> elements = driver.findElements(
				By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]"));
		return ! elements.isEmpty();
	}
	
	//do a logout
	public IndexPO logOut(){
		
		clickAndWait("logoutId");
		
		IndexPO indexPO = new IndexPO(this);
		assertTrue(indexPO.isOnPage());
		
		return indexPO;
	}
	
	//check if user is logged in
	public boolean isLoggedIn(){
		
		return getDriver().findElements(By.id("logoutId")).size() > 0 &&
				getDriver().findElements((By.id("linkToSignUp"))).isEmpty();
	}
	
	//Check a checkBox for a existing row in table
	public void selectDishByName(String name, boolean on){
		
		WebElement check = getCheckBoxForDish(name);
		
		if(on != check.isSelected()){
			check.click();
		}
	}
	
	//Check if checbox has been checked
	public boolean isDishSelected(String name){
		return getCheckBoxForDish(name).isSelected();
	}
	
	//the column in table must be a outputLabel and have the class 'dishName' (this need to be changed on the exam)
	private WebElement getCheckBoxForDish(String name){
		List<WebElement> elements = driver.findElements(
				By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]/parent::td/parent::tr//input"));
		
		return elements.get(0);
	}
	
}
