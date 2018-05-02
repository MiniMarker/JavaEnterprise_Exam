package no.cmarker.frontend.controller;

import no.cmarker.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
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
	
	public String getMessageText() {
		return messageText;
	}
	
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public String sendMessage(String recieverUsername){
		String loggedInUsername = userInfoController.getUserName();
		messageService.createMessage(loggedInUsername, recieverUsername, messageText);
		return "?faces-redirect=true&amp;includeViewParams=true";
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
}
