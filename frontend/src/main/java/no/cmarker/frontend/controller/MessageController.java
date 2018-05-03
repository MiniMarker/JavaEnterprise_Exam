package no.cmarker.frontend.controller;

import no.cmarker.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 * @author Christian Marker on 02/05/2018 at 19:44.
 */
@Named
@SessionScoped
public class MessageController {
	
	@Autowired
	public UserInfoController userInfoController;
	
	@Autowired
	public MessageService messageService;
	
	@Autowired
	public BookController bookController;
	
	private boolean showInputField = false;
	private String messageText;
	private String recieverUsername;
	
	public String getMessageText() {
		return messageText;
	}
	
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public void sendMessage(String recieverUsername){
		String loggedInUsername = userInfoController.getUserName();
		messageService.createMessage(loggedInUsername, recieverUsername, messageText);
	}
	
	public void sendMessageFromMessagesPage(){
		String loggedInUsername = userInfoController.getUserName();
		messageService.createMessage(loggedInUsername, getRecieverUsername(), messageText);
	}
	
	public void markMessageAsRead(long id){
		messageService.markMessageAsRead(id);
	}
	
	public boolean isShowInputField() {
		return showInputField;
	}
	
	public void setShowInputField(boolean showInputField) {
		this.showInputField = showInputField;
	}
	
	public String getRecieverUsername() {
		return recieverUsername;
	}
	
	public void setRecieverUsername(String recieverUsername) {
		this.recieverUsername = recieverUsername;
	}
}
