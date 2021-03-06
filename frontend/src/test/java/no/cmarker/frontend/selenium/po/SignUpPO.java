package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 01/05/2018 at 22:55.
 */
public class SignUpPO extends LayoutPO {
	
	public SignUpPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("SignUp");
	}
	
	public IndexPO createUser(String username, String password, String firstname, String lastname){
		
		setText("username", username);
		setText("password", password);
		setText("firstname", firstname);
		setText("lastname", lastname);
		clickAndWait("submit");
		
		IndexPO indexPO = new IndexPO(this);
		
		if(indexPO.isOnPage()){
			return indexPO;
		}
		return null;
	}
	
	
}
