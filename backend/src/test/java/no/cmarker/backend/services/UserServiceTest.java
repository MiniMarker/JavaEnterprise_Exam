package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Christian Marker on 02/05/2018 at 14:43.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase {
	
	@Test
	public void test(){
		boolean hei = true;
		assertTrue(hei);
	}
	
}
