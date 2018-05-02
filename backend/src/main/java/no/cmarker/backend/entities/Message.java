package no.cmarker.backend.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Christian Marker on 02/05/2018 at 12:35.
 */
@Entity
public class Message {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private User sender;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private User reciever;
	
	@NotNull
	private boolean read;
	
	@NotBlank
	@Size(max = 256)
	private String message;
	
	@NotNull
	private LocalDate date;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getReciever() {
		return reciever;
	}
	
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public boolean isRead() {
		return read;
	}
	
	public void setRead(boolean read) {
		this.read = read;
	}
}
