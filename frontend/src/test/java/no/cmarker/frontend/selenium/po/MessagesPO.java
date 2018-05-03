package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 03/05/2018 at 19:12.
 */
public class MessagesPO extends LayoutPO {
	
	public MessagesPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	public MessagesPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return false;
	}
}
