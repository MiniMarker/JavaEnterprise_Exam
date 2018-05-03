package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Message;
import no.cmarker.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 09:16.
 */
@Named
@RequestScoped
public class UserInfoController {
	
	@Autowired
	private UserService userService;
	
	public String goToMessagePage(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		return "messages.xhtml?user=" + username + "&faces-redirect=true";
	}
	
	public String getUserName(){
		return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
	public List<Message> getInbox(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getInbox(auth.getName());
	}
	
	
	
	public List<Message> getOutbox(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getOutbox(auth.getName());
	}
	
}
