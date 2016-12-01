package base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.PartnerForm;
import base.jaxb.Partner;
import base.util.LakeshoreMarketUtil;

/**
 * Allowing Partners to use your site to sell their products with functionalities such as:
	a. Need to register and create profile of partners
	b. Add product or products in market place
	c. Push orders that customers made to partners
	d. Get acknowledgement of order fulfillment

 * @author lbo
 *
 */
@Controller
public class PartnerController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();

	/**
	 * Find a partner - test class
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/partners")
	public ModelAndView findPartners(HttpServletRequest request, HttpServletResponse response) {
		try {
			Partner partner = lakeshoreServiceManager.getPartner(20202);
			PartnerForm partnerForm = LakeshoreMarketUtil.buildPartnerForm(partner);
			// put partnerForm in the model
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("partnerForm", partnerForm);
			
			return new ModelAndView("partner", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Register a partner
	 * @param login
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	// 2a. Need to register and create profile of partners
	@RequestMapping(value = "/partnerRegistration")
	public ModelAndView register(@RequestParam("login") String login, @RequestParam("password") String password, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("streetAddress") String streetAddress, 
			@RequestParam("city") String city,  @RequestParam("state") String state, @RequestParam("zip") String zip) {
		try {
			Partner partner = lakeshoreServiceManager.getPartner(20202);
			PartnerForm partnerForm = LakeshoreMarketUtil.buildPartnerForm(partner);
			// put partnerForm in the model
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("partnerForm", partnerForm);
			
			return new ModelAndView("partner", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Partner login
	 * @param login
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginPartner")
	public ModelAndView login(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			boolean isValid = lakeshoreServiceManager.isValidPartner(login, password);
			if (isValid) {
				return new ModelAndView("partner", null);		
			} 
			
			return new ModelAndView("index", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
}
