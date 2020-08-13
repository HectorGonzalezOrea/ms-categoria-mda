package mx.com.nmp.establecimientoprecios.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @GetMapping(value = "/")
    public String index() {
    	log.info("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
