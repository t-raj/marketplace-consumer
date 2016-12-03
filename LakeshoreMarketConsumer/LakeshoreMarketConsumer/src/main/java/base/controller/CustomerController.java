package base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.CustomerForm;
import base.jaxb.Customer;
import base.util.LakeshoreMarketUtil;

@Controller
public class CustomerController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	
	@RequestMapping(value = "/login")
	public ModelAndView authenticate(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			Customer customer = lakeshoreServiceManager.getCustomer(login);
			if (customer != null && password != null && password.equals(customer.getPassword())) {
				CustomerForm customerForm = LakeshoreMarketUtil.buildCustomerForm(customer);
				// put partnerForm in the model
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("customerForm", customerForm);
		
				return new ModelAndView("customer", model);		
			} 
			
			return new ModelAndView("index", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "/registerCustomer")
	public ModelAndView register(@RequestParam("id") String id, @RequestParam("login") String login, @RequestParam("password") String password, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("streetAddress") String streetAddress, 
			@RequestParam("city") String city,  @RequestParam("state") String state, @RequestParam("zip") String zip) {
		try {
			lakeshoreServiceManager.registerCustomer(id, login, password, firstName, lastName, streetAddress, city, state, zip);
			return new ModelAndView("customer", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
}
