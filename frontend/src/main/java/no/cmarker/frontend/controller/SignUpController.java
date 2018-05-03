package no.cmarker.frontend.controller;

import no.cmarker.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Christian Marker on 30/04/2018 at 09:15.
 */
@Named
@RequestScoped
public class SignUpController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	
	public String signUpUser(){
		
		boolean registered = false;
		try {
			registered = userService.createUser(username, password, firstname, lastname);
		}catch (Exception e){
			//nothing to do
		}
		
		if(registered){
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					userDetails,
					password,
					userDetails.getAuthorities());
			
			authenticationManager.authenticate(token);
			
			if (token.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(token);
			}
			
			return "/index.jsf?faces-redirect=true";
		} else {
			return "/signup.jsf?faces-redirect=true&error=true";
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
