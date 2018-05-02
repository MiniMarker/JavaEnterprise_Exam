package no.cmarker.backend.services;

import no.cmarker.backend.entities.Book;
import no.cmarker.backend.entities.BookPost;
import no.cmarker.backend.entities.Message;
import no.cmarker.backend.entities.User;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Christian Marker on 16/04/2018 at 14:24.
 */
public abstract class ServiceTestBase {
	
	@Autowired
	private ResetService resetService;
	
	@Before
	@After
	public void cleanDatabase() {
		resetService.resetDatabase();
	}
}
