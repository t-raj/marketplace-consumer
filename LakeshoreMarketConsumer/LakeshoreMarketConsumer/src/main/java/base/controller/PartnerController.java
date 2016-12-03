package base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bean.LinkTO;
import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.OrderForm;
import base.form.PartnerForm;
import base.form.ProductForm;
import base.jaxb.Orders;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.util.LakeshoreMarketUtil;

/**
 * Allowing Partners to use your site to sell their products with functionalities such as:
	a. Need to register and create profile of partners
	b. Add product or products in market place
	c. Push orders that customers made to partners
	d. Get acknowledgement of order fulfillment

 * @author lbo
 */
@Controller
public class PartnerController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	// update the links for the partner once retrieved so that they can be used when links are clicked
	private List<LinkTO> partnerLinks;
	
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
	public ModelAndView register(@RequestParam("id") int id, @RequestParam("login") String login, @RequestParam("password") String password, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("streetAddress") String streetAddress, 
			@RequestParam("city") String city,  @RequestParam("state") String state, @RequestParam("zip") String zip) {
		try {
			Partner partner = lakeshoreServiceManager.registerPartner(id, login, password, firstName, lastName, streetAddress, city, state, zip);
			partnerLinks = LakeshoreMarketUtil.buildLinkTOListFromPartner(partner.getLink());

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
	@RequestMapping(value = "/partnerLogin")
	public ModelAndView login(@RequestParam("login") String login, @RequestParam("password") String password) {
		try {
			Partner partner = lakeshoreServiceManager.getPartner(login);
			if (partner != null && password != null && password.equals(partner.getPassword())) {
				partnerLinks = LakeshoreMarketUtil.buildLinkTOListFromPartner(partner.getLink());
				PartnerForm partnerForm = LakeshoreMarketUtil.buildPartnerForm(partner);
				// put partnerForm in the model
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("partnerForm", partnerForm);
				return new ModelAndView("partner", model);		
			} 
			
			// if validation failed 
			return new ModelAndView("index", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	// the form is submitted when a link is clicked. The link is passed as a ModelAttribute and is used to perform the correct action, e.g. add produc t
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ModelAndView find(@RequestParam("productNumber") String productNumber) {
		try {
			Product product = lakeshoreServiceManager.getProduct(Integer.parseInt(productNumber));
			ProductForm productForm = LakeshoreMarketUtil.buildProductForm(product);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);
			
			// redirect to customer page with product data 
			return new ModelAndView("customer", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	// 2b. Add product or products in market place
	@RequestMapping(value = "/addProduct")
	public ModelAndView showAddProductForm(@ModelAttribute("productForm") ProductForm productForm) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);

			// show add product form jsp
			return new ModelAndView("product", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	// 2b. Add product or products in market place
	@RequestMapping(value = "/addProductForm", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("productNumber") String productNumber, 
			@RequestParam("description") String description, @RequestParam("partner") String partner, 
			@RequestParam("price") String price) {
		try {
			Product product = lakeshoreServiceManager.addProduct(Integer.parseInt(productNumber), description, Integer.parseInt(partner), Integer.parseInt(price));
			// TODO: show all products
			ProductForm productForm = LakeshoreMarketUtil.buildProductForm(product);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);
			
			return new ModelAndView("partner", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	// 2c. Push orders to partner
	@RequestMapping(value = "/addProduct")
	public ModelAndView pushOrdersToPartner(@ModelAttribute("partnerForm") PartnerForm partnerForm) {
		try {
			Orders pushedOrders = lakeshoreServiceManager.pushOrdersToPartner(partnerForm.getPartnerTOList().get(0).getId());
			// TODO: null check for pushed orders
			OrderForm orderForm = LakeshoreMarketUtil.buildOrderForm(pushedOrders.getOrders());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderForm", orderForm);

			// show add product form jsp
			return new ModelAndView("order", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	// 2d. Get acknowledgement of fulfilled orders
	@RequestMapping(value = "/acknowledgement")
	public ModelAndView getAcknowledgement(@ModelAttribute("partnerForm") PartnerForm partnerForm) {
		try {
			Orders fulfilledOrders = lakeshoreServiceManager.getAcknowledgement(partnerForm.getPartnerTOList().get(0).getId());
			// TODO: null check for orders
			OrderForm orderForm = LakeshoreMarketUtil.buildOrderForm(fulfilledOrders.getOrders());
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderForm", orderForm);

			// show add product form jsp
			return new ModelAndView("order", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

}
