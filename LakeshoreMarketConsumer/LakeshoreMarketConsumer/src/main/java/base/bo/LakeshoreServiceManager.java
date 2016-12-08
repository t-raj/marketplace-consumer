package base.bo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.jaxb.Customer;
import base.jaxb.Order;
import base.jaxb.Orders;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.jaxb.Products;

public interface LakeshoreServiceManager {

	/**
	 * Get product from db
	 * @param productNumber
	 * @param linkUrl 
	 * @param httpVerb 
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	Product getProduct(int productNumber) throws IOException, JAXBException;

	/**
	 * add product
	 * @param productNum
	 * @param description
	 * @param partnerId
	 * @param price
	 * @throws IOException
	 * @throws JAXBException
	 */
	void addProduct(int productNum, String description, int partnerId, int price, String linkUrl, String httpVerb) throws IOException, JAXBException;

	/**
	 * Get Customer
	 * @param login
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Customer getCustomer(String login) throws JAXBException, IOException;

	/**
	 * Get partner
	 * @param login
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	Partner getPartner(String login) throws IOException, JAXBException;

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
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Customer registerCustomer(String id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException;

	/**
	 * Register partner
	 * @param id
	 * @param login
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Partner registerPartner(int id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException;

	/**
	 * Push orders to partner
	 * @param partnerId
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Orders pushOrdersToPartner(int partnerId, String linkUrl, String httpVerb) throws JAXBException, IOException;

	/**
	 * Get acknowledgement
	 * @param partnerId
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Orders getAcknowledgement(int partnerId, String linkUrl, String httpVerb) throws JAXBException, IOException;

	/**
	 * Get products
	 * @param partnerID
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Products getProducts(int partnerID) throws JAXBException, IOException;

	/**
	 * Buy product
	 * @param productId
	 * @param customerId
	 * @param partnerId
	 * @param httpVerb 
	 * @param url 
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Order buyProduct(int productId, int customerId, int partnerId, String url, String httpVerb) throws JAXBException, IOException;

	/**
	 * Process payment
	 * @param orderId
	 * @param httpVerb 
	 * @param url 
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Order processPayment(int orderId, String url, String httpVerb) throws JAXBException, IOException;

	/**
	 * Cancel order
	 * @param orderId
	 * @param httpVerb 
	 * @param url 
	 * @throws JAXBException
	 * @throws IOException
	 */
	void cancelOrder(int orderId, String url, String httpVerb) throws JAXBException, IOException;

	/**
	 * Get orders
	 * @param customerId
	 * @param verb 
	 * @param url 
	 * @param httpVerb 
	 * @param url 
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	Orders getOrders(int customerId, String url, String verb) throws JAXBException, IOException;

}
