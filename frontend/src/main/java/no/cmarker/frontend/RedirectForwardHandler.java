package no.cmarker.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Christian Marker on 16/04/2018 at 14:47.
 */

@Controller
public class RedirectForwardHandler {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String forward(){
		return "forward:index.xhtml";
	}
}
