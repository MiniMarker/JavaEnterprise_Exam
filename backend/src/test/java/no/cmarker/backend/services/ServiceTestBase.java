package no.cmarker.backend.services;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Christian Marker on 16/04/2018 at 14:24.
 */
public class ServiceTestBase {
	
	@Autowired private ResetService resetService;

	@Before
	public void clanDatabase(){
		resetService.resetDatabase();
	}
}
