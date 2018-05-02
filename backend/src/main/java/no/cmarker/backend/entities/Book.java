package no.cmarker.backend.entities;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Marker on 02/05/2018 at 12:30.
 */
@Entity
public class Book {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Size(max = 128)
	private String title;
	
	@NotBlank
	private String author;
	
	@NotBlank
	private String course;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BookPost> bookPosts;
	
	public Book() {
		bookPosts = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getCourse() {
		return course;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public List<BookPost> getBookPosts() {
		return bookPosts;
	}
	
	public void setBookPosts(List<BookPost> bookPosts) {
		this.bookPosts = bookPosts;
	}
}
