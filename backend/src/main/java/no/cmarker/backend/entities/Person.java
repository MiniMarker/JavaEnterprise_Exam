package no.cmarker.backend.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Christian Marker on 16/04/2018 at 18:13.
 */

@Entity
public class Person {
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotBlank
	private String name;
	
	public Person() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
