package no.cmarker.backend.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Christian Marker on 30/04/2018 at 09:10.
 */
@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@NotBlank
	@Size(max = 32)
	private String username;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Boolean enabled;
	
	@NotBlank
	@Size(max = 62)
	private String firstname;
	
	@NotBlank
	@Size(max = 62)
	private String lastname;
	
	@OneToMany
	private List<Message> inbox;
	
	@OneToMany
	private List<Message> outbox;
	
	public User() {
		inbox = new ArrayList<>();
		outbox = new ArrayList<>();
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
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	
	public List<Message> getInbox() {
		return inbox;
	}
	
	public void setInbox(List<Message> inbox) {
		this.inbox = inbox;
	}
	
	public List<Message> getOutbox() {
		return outbox;
	}
	
	public void setOutbox(List<Message> outbox) {
		this.outbox = outbox;
	}
}
