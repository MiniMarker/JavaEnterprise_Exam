package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 01/05/2018 at 22:55.
 */
public class SignUpPO extends LayoutPO {
	
	public SignUpPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	public SignUpPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("SignUp");
	}
	
	public IndexPO createUser(String username, String password){
		
		setText("username", username);
		setText("password", password);
		clickAndWait("submit");
		
		IndexPO indexPO = new IndexPO(this);
		
		if(indexPO.isOnPage()){
			return indexPO;
		}
		
		return null;
	}
	
	
}
