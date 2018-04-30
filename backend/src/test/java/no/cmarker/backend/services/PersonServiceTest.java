package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import no.cmarker.backend.entities.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Christian Marker on 16/04/2018 at 14:02.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonServiceTest extends ServiceTestBase {
	
	@Autowired private PersonService personService;
	
	@Test
	public void testCreatePerson(){
		String name = "Christian";
		
		Integer id = personService.createPerson(name);
		
		assertNotNull(id);
	}
	
	@Test
	public void testGetPerson(){
		
		String name = "Christian";
		
		Integer id = personService.createPerson(name);
		
		Person person = personService.getPerson(id);
		
		assertTrue(person.getName().equals(name));
		
	}
}
