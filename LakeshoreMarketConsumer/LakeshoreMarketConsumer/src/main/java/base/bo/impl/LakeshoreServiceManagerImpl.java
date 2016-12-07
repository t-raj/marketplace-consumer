package base.bo.impl;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.bo.LakeshoreServiceManager;
import base.constant.Constant;
import base.constant.HTTPVerb;
import base.jaxb.Customer;
import base.jaxb.Order;
import base.jaxb.Orders;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.jaxb.Products;
import base.util.LakeshoreMarketUtil;

/**
 * This class manages the http request and responses to the Lakeshore web service 
 * @author lbo
 *
 */
public class LakeshoreServiceManagerImpl implements LakeshoreServiceManager {

	@Override
	public Product getProduct(int productNumber) throws IOException, JAXBException {
		String relativePath = "products/" + productNumber;
		return LakeshoreMarketUtil.unmarshalProduct(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public void addProduct(int productNum, String description, int partnerId, int price) throws IOException, JAXBException {
		Product product = new Product();
		product.setNumberAvailable(1);
		product.setPartnerId(partnerId);
		product.setProductId(productNum);
		product.setPrice(price);
		product.setDescription(description);
		String relativePath = "products";
		String body = LakeshoreMarketUtil.buildXML(product);
		LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST, relativePath, body);
	}

	@Override
	public Customer getCustomer(String login) throws JAXBException, IOException {
		String relativePath = "/customers/" + login;
		return LakeshoreMarketUtil.unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Partner getPartner(String login) throws IOException, JAXBException {
		String relativePath = "partners/" + login;
		return LakeshoreMarketUtil.unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Customer registerCustomer(String id, String login, String password, String firstName, String lastName, String streetAddress,
			String city, String state, String zip) throws JAXBException, IOException {
		Customer customer = new Customer();
		customer.setCity(city);
		customer.setFirstName(firstName);
		//TODO: autoincrement id in db
		customer.setId(Integer.parseInt(id));
		customer.setLastName(lastName);
		customer.setLogin(login);
		customer.setPassword(password);
		customer.setStreetAddress(streetAddress);
		customer.setState(state);
		customer.setCity(city);
		customer.setZipCode(Integer.parseInt(zip));
		String relativePath = "customers";
		String body = LakeshoreMarketUtil.buildXML(customer);
		return LakeshoreMarketUtil.unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST, relativePath, body));		
	}

	@Override
	public Partner registerPartner(int id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException {
		Partner partner = new Partner();
		partner.setCity(city);
		partner.setFirstName(firstName);
		//TODO: autoincrement id in db
		partner.setId(id);
		partner.setLastName(lastName);
		partner.setLogin(login);
		partner.setPassword(password);
		partner.setStreetAddress(streetAddress);
		partner.setState(state);
		partner.setCity(city);
		partner.setZipCode(Integer.parseInt(zip));
		String relativePath = "partners";
		String body = LakeshoreMarketUtil.buildXML(partner);
		String response = LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST, relativePath, body);		
	
		return LakeshoreMarketUtil.unmarshalPartner(response);
	}

	@Override
	public Orders pushOrdersToPartner(int partnerId) throws JAXBException, IOException {
		String relativePath = "orders/pushedOrders/" + partnerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Orders getAcknowledgement(int partnerId) throws JAXBException, IOException {
		String relativePath = "orders/fulfilled/" + partnerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Products getProducts(int partnerID) throws JAXBException, IOException {
		String relativePath = "products/" + partnerID + "/true"; //boolean flag for active products
		return LakeshoreMarketUtil.unmarshalProducts(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Order buyProduct(int productId, int customerId, int partnerId) throws JAXBException, IOException {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setStatus(Constant.ACCEPTED);
//		order.setProductId(productId);
		order.setPartnerId(partnerId);
		String relativePath = "orders"; 
		String body = LakeshoreMarketUtil.buildXML(order);

		return LakeshoreMarketUtil.unmarshalOrder(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST, relativePath, body));
	}

	@Override
	public Order processPayment(int orderId) throws JAXBException, IOException {
		String relativePath = "orders/payment/" + orderId;
		return LakeshoreMarketUtil.unmarshalOrder(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.PUT,relativePath, null));
	}

	@Override
	public void cancelOrder(int orderId) throws JAXBException, IOException {
		String relativePath = "orders/" + orderId;
		String body = "";
		LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.DELETE, relativePath, body);
	}

	@Override
	public Orders getOrders(int customerId) throws JAXBException, IOException {
		String relativePath = "orders/" + customerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET,relativePath, null));
	}

}
