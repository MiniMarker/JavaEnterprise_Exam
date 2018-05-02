package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 17/04/2018 at 13:33.
 */
public class PageTwoPO extends LayoutPO {
	
	public PageTwoPO(PageObject other) {
		super(other);
	}
	
	public PageTwoPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("PageTwo");
	}
	
	
}
