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
	public void addProduct(int productNum, String description, int partnerId, int price, String linkUrl, String httpVerb) throws IOException, JAXBException {
		Product product = new Product();
		product.setNumberAvailable(1);
		product.setPartnerId(partnerId);
		product.setProductId(productNum);
		product.setPrice(price);
		product.setDescription(description);
		String body = LakeshoreMarketUtil.buildXML(product);
		LakeshoreMarketUtil.sendHTTPRequest(httpVerb, linkUrl, body);
	}

	@Override
	public Customer getCustomer(String login) throws JAXBException, IOException {
		String fullUrl = Constant.BASE_URL + "customers/" + login;
		return LakeshoreMarketUtil.unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET.toString(), fullUrl, null));
	}

	@Override
	public Partner getPartner(String login) throws IOException, JAXBException {
		String fullUrl = Constant.BASE_URL + "partners/" + login;
		return LakeshoreMarketUtil.unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET.toString(), fullUrl, null));
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
		String body = LakeshoreMarketUtil.buildXML(customer);
		String fullUrl = Constant.BASE_CONSUMER_URL + "customers/";

		return LakeshoreMarketUtil.unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST.toString(), fullUrl, body));		
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
		String body = LakeshoreMarketUtil.buildXML(partner);
		String linkUrl = Constant.BASE_CONSUMER_URL + "partners/";

		String response = LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST.toString(), linkUrl, body);		
	
		return LakeshoreMarketUtil.unmarshalPartner(response);
	}

	@Override
	public Orders pushOrdersToPartner(int partnerId, String linkUrl, String httpVerb) throws JAXBException, IOException {
		String fullUrl = linkUrl + partnerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(httpVerb, fullUrl, null));
	}

	@Override
	public Orders getAcknowledgement(int partnerId, String linkUrl, String httpVerb) throws JAXBException, IOException {
		String fullUrl = linkUrl + "/" + partnerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(httpVerb, fullUrl, null));
	}

	@Override
	public Products getProducts(int partnerID) throws JAXBException, IOException {
		String fullUrl = Constant.BASE_URL + "products/" + partnerID + "/true"; //boolean flag for active products
		return LakeshoreMarketUtil.unmarshalProducts(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET.toString(), fullUrl, null));
	}

	@Override
	public Order buyProduct(int productId, int customerId, int partnerId, String linkUrl, String httpVerb) throws JAXBException, IOException {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setStatus(Constant.ACCEPTED);
//		order.setProductId(productId);
		order.setPartnerId(partnerId);
		String body = LakeshoreMarketUtil.buildXML(order);

		return LakeshoreMarketUtil.unmarshalOrder(LakeshoreMarketUtil.sendHTTPRequest(httpVerb, linkUrl, body));
	}

	@Override
	public Order processPayment(int orderId, String linkUrl, String httpVerb) throws JAXBException, IOException {
		String fullUrl = linkUrl + "/" + orderId;
		return LakeshoreMarketUtil.unmarshalOrder(LakeshoreMarketUtil.sendHTTPRequest(httpVerb, fullUrl, null));
	}

	@Override
	public void cancelOrder(int orderId, String linkUrl, String httpVerb) throws JAXBException, IOException {
		String fullUrl = linkUrl  + "/" + orderId;
		String body = "";
		LakeshoreMarketUtil.sendHTTPRequest(httpVerb, fullUrl, body);
	}

	@Override
	public Orders getOrders(int customerId, String url, String verb) throws JAXBException, IOException {
		String fullUrl = url + customerId;
		return LakeshoreMarketUtil.unmarshalOrders(LakeshoreMarketUtil.sendHTTPRequest(verb, fullUrl, null));
	}

	@Override
	public Product getProduct(int productNumber) throws IOException, JAXBException {
		String fullUrl = Constant.BASE_URL + "products/" + productNumber;
		return LakeshoreMarketUtil.unmarshalProduct(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET.toString(), fullUrl, null));
		}

}
