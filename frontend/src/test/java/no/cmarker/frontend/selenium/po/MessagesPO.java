package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * @author Christian Marker on 03/05/2018 at 19:12.
 */
public class MessagesPO extends LayoutPO {
	
	public MessagesPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Messages");
	}
	
	public void sendResponse(String message, long rowInColumn){
		setText("inboxTable:"+ rowInColumn +":responseForm:responseText", message);
		clickAndWait("inboxTable:"+ rowInColumn +":responseForm:sendResponseButton");
	}
	
	public void sendMessage(String username, String message){
		setText("sendMessageForm:sendMessageUsername", username);
		setText("sendMessageForm:sendMessageText", message);
		clickAndWait("sendMessageForm:sendMessageButton");
	}
	
}
