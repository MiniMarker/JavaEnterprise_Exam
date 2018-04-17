package no.cmarker.frontend.controller;

import no.cmarker.backend.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Christian Marker on 16/04/2018 at 14:47.
 */
@Named
@SessionScope
public class PersonController implements Serializable {
	
	@Autowired
	private PersonService personService;
	
	// TODO: 17/04/2018 Add logic to controller here
	
}
