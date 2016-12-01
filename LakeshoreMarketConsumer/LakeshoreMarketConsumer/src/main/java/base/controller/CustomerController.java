package base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;

@Controller
public class CustomerController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	
	@RequestMapping(value = "/login")
	public ModelAndView authenticate(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			boolean isValid = lakeshoreServiceManager.isValidCustomer(login, password);
			if (isValid) {
				return new ModelAndView("customer", null);		
			} 
			
			return new ModelAndView("index", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "/register")
	public ModelAndView register(@RequestParam("id") String id, @RequestParam("login") String login, @RequestParam("password") String password, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("streetAddress") String streetAddress, 
			@RequestParam("city") String city,  @RequestParam("state") String state, @RequestParam("zip") String zip) {
		try {
			lakeshoreServiceManager.registerCustomer(id, login, password, firstName, lastName, streetAddress, city, state, zip);
			// return the customer info at the top of the page 
			return new ModelAndView("customer", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
}
