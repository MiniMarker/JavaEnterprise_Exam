package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Christian Marker on 16/04/2018 at 15:27.
 */
public class IndexPO extends LayoutPO {
	public IndexPO(PageObject other) {
		super(other);
	}
	
	public IndexPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	public void toStartingPage() {
		getDriver().get(host + ":" + port);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Index");
	}
	
	public PageTwoPO goToPageTwo() {
		
		clickAndWait("linkToPageTwo");
		PageTwoPO pageTwoPO = new PageTwoPO(this);
		assertTrue(pageTwoPO.isOnPage());
		
		return pageTwoPO;
	}
}
