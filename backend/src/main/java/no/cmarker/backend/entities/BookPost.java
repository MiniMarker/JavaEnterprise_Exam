package no.cmarker.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Christian Marker on 02/05/2018 at 14:11.
 */
@Entity
public class BookPost {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Book book;
	
	@NotNull
	private boolean forSale;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private User seller;
	
	public BookPost(){
	
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public boolean isForSale() {
		return forSale;
	}
	
	public void setForSale(boolean forSale) {
		this.forSale = forSale;
	}
	
	public User getSeller() {
		return seller;
	}
	
	public void setSeller(User seller) {
		this.seller = seller;
	}
}
