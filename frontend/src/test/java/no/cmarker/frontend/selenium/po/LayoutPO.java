package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

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
}
