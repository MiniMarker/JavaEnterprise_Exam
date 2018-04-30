package no.cmarker.frontend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Christian Marker on 30/04/2018 at 09:16.
 */
@Named
@RequestScoped
public class UserInfoController {
	
	public String getUserName(){
		return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
}
