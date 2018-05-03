package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 03/05/2018 at 12:44.
 */
public class LogInPO extends LayoutPO {
	
	public LogInPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Login");
	}
	
	public IndexPO logInUser(String username, String password){
		setText("username", username);
		setText("password", password);
		clickAndWait("submit");
		
		IndexPO indexPO = new IndexPO(this);
		if (indexPO.isOnPage()){
			return indexPO;
		}
		
		return null;
	}
	
}
