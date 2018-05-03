package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Christian Marker on 03/05/2018 at 15:40.
 */
public class BookDetailsPO extends LayoutPO {
	
	public BookDetailsPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("BookDetails");
	}
	
	public void sendMessage(String message, long rowInTable){
		
		setText("bookPosts:"+ rowInTable +":sendMessageForm:messageField", message);
		clickAndWait("bookPosts:"+ rowInTable +":sendMessageForm:sendMessageButton");
	}
}
