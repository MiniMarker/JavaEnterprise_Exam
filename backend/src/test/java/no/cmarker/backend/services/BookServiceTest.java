package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author Christian Marker on 02/05/2018 at 14:43.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest extends ServiceTestBase {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookPostService bookPostService;
	
	@Test
	public void createBook(){
		
		String title = "Foo";
		String author = "Bar";
		String course = "PG5100";
		
		Long id = bookService.createBook(title, author, course);
		assertNotNull(id);
	}
	
}
