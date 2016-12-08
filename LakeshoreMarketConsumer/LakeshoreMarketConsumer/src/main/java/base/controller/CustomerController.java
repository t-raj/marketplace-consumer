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
import base.constant.Constant;
import base.form.CustomerForm;
import base.form.OrderForm;
import base.form.ProductForm;
import base.jaxb.Customer;
import base.jaxb.Order;
import base.jaxb.Orders;
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
	//map of string rel and url returned from the web service to be used in navigating the web service
	private Map<String,String> linkMap = new HashMap<String,String>();
	// map of http verbs for each rel action
	private Map<String,String> verbMap = new HashMap<String,String>();
	
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
				// add links to map
				linkMap = LakeshoreMarketUtil.buildLinkMap(customer.getLink());
				// add http verbs to map
				verbMap = LakeshoreMarketUtil.buildVerbMap(customer.getLink());
				
				CustomerForm customerForm = LakeshoreMarketUtil.buildCustomerForm(customer);

				// get orders for this customer
				Orders orders = lakeshoreServiceManager.getOrders(customer.getId(), linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"));
				orderForm = LakeshoreMarketUtil.buildOrderForm(orders);
				// add order links to map
				LakeshoreMarketUtil.addToLinkMap(linkMap, orders);
				LakeshoreMarketUtil.addToVerbMap(verbMap, orders);
		
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
			// add links to map
			linkMap = LakeshoreMarketUtil.buildLinkMap(customer.getLink());
			// add http verbs to map
			verbMap = LakeshoreMarketUtil.buildVerbMap(customer.getLink());
		
			CustomerForm customerForm = LakeshoreMarketUtil.buildCustomerForm(customer);
			// get orders for this customer
			Orders orders = lakeshoreServiceManager.getOrders(Integer.parseInt(id), linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"));
			orderForm = LakeshoreMarketUtil.buildOrderForm(orders);
			
			// add order links to map
			LakeshoreMarketUtil.addToLinkMap(linkMap, orders);
			LakeshoreMarketUtil.addToVerbMap(verbMap, orders);
				
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
			
			// add actions to maps
			if (product != null) {
				LakeshoreMarketUtil.addToLinkMap(linkMap, product.getLink());
				LakeshoreMarketUtil.addToVerbMap(verbMap, product.getLink());
			}

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
			Order order = lakeshoreServiceManager.buyProduct(searchResult.getProductId(), customerId, searchResult.getPartnerId(), linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "buy"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "buy"));
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
			lakeshoreServiceManager.cancelOrder(Integer.parseInt(id), linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "cancel"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "cancel"));
			// reload the page with updated data
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId, linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders")));

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
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId, linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders")));
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
			lakeshoreServiceManager.processPayment(orderId, linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "payment"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "payment"));
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId, linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders")));

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
	@RequestMapping(value = "/orders")
	public ModelAndView checkStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			// get orders for this customer
			orderForm = LakeshoreMarketUtil.buildOrderForm(lakeshoreServiceManager.getOrders(customerId, linkMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders"), verbMap.get(Constant.BASE_CONSUMER_URL_KEY + "orders")));

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
