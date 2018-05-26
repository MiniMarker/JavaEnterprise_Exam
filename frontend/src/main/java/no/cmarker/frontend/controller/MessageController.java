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
	
	private String messageText;
	
	private String respondMessageText;
	private String recieverUsername;
	
	/**
	 * Send a message to a given user
	 *
	 * @param recieverUsername username of reciever
	 */
	public void sendMessage(String recieverUsername) {
		String loggedInUsername = userInfoController.getUserName();
		messageService.createMessage(loggedInUsername, recieverUsername, messageText);
		messageText = "";
	}
	
	/**
	 * Sends a response to a given message.
	 *
	 * @param recieverUsername recievers username
	 * @param messageId        Message's id
	 */
	public void respondToMessage(String recieverUsername, long messageId) {
		String loggedInUsername = userInfoController.getUserName();
		messageService.createMessage(loggedInUsername, recieverUsername, respondMessageText);
		markMessageAsRead(messageId);
		messageText = "";
	}
	
	/**
	 * Send a message to a User entered manually by the authenticated user
	 *
	 * @return appends a error message to the URL if the entered username doesnt exist in DB
	 */
	public String sendMessageFromMessagesPage() {
		String loggedInUsername = userInfoController.getUserName();
		try {
			messageService.createMessage(loggedInUsername, getRecieverUsername(), messageText);
			return null;
		} catch (Exception e) {
			return "/messages.xhtml?faces-redirect=true&error=true";
		}
	}
	
	public void markMessageAsRead(long id) {
		messageService.markMessageAsRead(id);
	}
	
	public String getRecieverUsername() {
		return recieverUsername;
	}
	
	public void setRecieverUsername(String recieverUsername) {
		this.recieverUsername = recieverUsername;
	}
	
	public String getRespondMessageText() {
		return respondMessageText;
	}
	
	public void setRespondMessageText(String respondMessageText) {
		this.respondMessageText = respondMessageText;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
