package base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.CustomerForm;
import base.form.OrderForm;
import base.form.ProductForm;
import base.jaxb.Customer;
import base.jaxb.Order;
import base.jaxb.Product;
import base.util.LakeshoreMarketUtil;

@Controller
public class CustomerController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	private CustomerForm customerForm;
	private OrderForm orderForm;
	private int customerId;
	private int orderId;
	private Product searchResult;
	//TODO: create a map of string rel and url returned from the web service to be used in navigating the web service
	
	/**
	 * Authenticate customer
	 * @param login
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView authenticate(@RequestParam("login") String login, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			Customer customer = lakeshoreServiceManager.getCustomer(login);
			if (customer != null && password != null && password.equals(customer.getPassword())) {
				CustomerForm customerForm = LakeshoreMarketUtil.buildCustomerForm(customer);
				// get orders for this customer
				orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customer.getId()));

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("customerForm", customerForm);
				model.put("orderForm", orderForm);
				
				// cache the customer form
				HttpSession session = request.getSession();
				session.setAttribute("customerId", customer.getId());
			
				this.customerForm = customerForm;
				this.customerId = customer.getId();
				
				return new ModelAndView("customerOrder", model);		
			} 

			return new ModelAndView("index", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	/**
	 * Register customer
	 * @param id
	 * @param login
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zip
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/registerCustomer")
	public ModelAndView register(@RequestParam("id") String id, @RequestParam("login") String login, @RequestParam("password") String password, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("streetAddress") String streetAddress, 
			@RequestParam("city") String city,  @RequestParam("state") String state, @RequestParam("zip") String zip, HttpServletRequest request, HttpServletResponse response) {
		try {
			Customer customer = lakeshoreServiceManager.registerCustomer(id, login, password, firstName, lastName, streetAddress, city, state, zip);

			CustomerForm customerForm = LakeshoreMarketUtil.buildCustomerForm(customer);
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(Integer.parseInt(id)));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);
			model.put("orderForm", orderForm);
			
			// cache the customer form
			this.customerForm = customerForm;
			// cache the customer form
			HttpSession session = request.getSession();
			session.setAttribute("customerId", id);
			this.customerId = Integer.parseInt(id);
			
			return new ModelAndView("customerOrder", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	/**
	 * Show search product form
	 * @return
	 */
	// 1a. Search by product 
	@RequestMapping(value = "/searchProducts")
	public ModelAndView showSearchProductForm() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);
			model.put("orderForm", orderForm);

			return new ModelAndView("customerProduct", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	/**
	 * Get search product result
	 * @param productNumber
	 * @return
	 */
	// 1a. Search by product 
	@RequestMapping(value = "/searchProductResult")
	public ModelAndView showSearchProductResult(@RequestParam("productNumber") String productNumber) {
		try {
			Product product = lakeshoreServiceManager.getProduct(Integer.parseInt(productNumber));
			
			// cache search result to be used in creating the order
			searchResult = product;
			
			ProductForm productForm = LakeshoreMarketUtil.buildProductForm(product);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);
			model.put("customerForm", customerForm);

			return new ModelAndView("customerProductResult", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Buy product
	 * @param request
	 * @param response
	 * @return
	 */
	// 1a. Buy a product 
	@RequestMapping(value = "/buy")
	public ModelAndView buyProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			Order order = lakeshoreServiceManager.buyProduct(searchResult.getProductId(), customerId, searchResult.getPartnerId());
			OrderForm orderForm = LakeshoreMarketUtil.buildOrderForm(order);
			// cache order form
			this.orderForm = orderForm;
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderForm", orderForm);
			model.put("customerForm", customerForm);

			return new ModelAndView("customerOrder", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Cancel order
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	// 1f. Cancel order
	@RequestMapping(value = "/cancel")
	public ModelAndView cancelOrder(@QueryParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			lakeshoreServiceManager.cancelOrder(Integer.parseInt(id));
			// reload the page with updated data
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);
			model.put("orderForm", orderForm);
			
			return new ModelAndView("customerOrder", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Pay for order
	 * @param id
	 * @return
	 */
	// 1c. Pay for order 
	@RequestMapping(value = "/payment")
	public ModelAndView showPaymentForm(@QueryParam("id") String id) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);

			if (id != null && !id.isEmpty()) {
				orderId = Integer.parseInt(id);
			}

			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId));
			model.put("orderForm", orderForm);

			return new ModelAndView("customerOrderPayment", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	/**
	 * Process payment
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/processPayment")
	public ModelAndView processPayment(HttpServletRequest request, HttpServletResponse response) {
		try {
			//TODO null check on orderTO
			lakeshoreServiceManager.processPayment(orderId);
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);
			model.put("orderForm", orderForm);
			model.put("message", "Payment processed");
			return new ModelAndView("customerOrder", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	/**
	 * Pay for order
	 * @param request
	 * @param response
	 * @return
	 */
	// 1c. Pay for order 
	@RequestMapping(value = "/status")
	public ModelAndView checkStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("customerForm", customerForm);
			model.put("orderForm", orderForm);
			
			return new ModelAndView("customerOrder", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

}
