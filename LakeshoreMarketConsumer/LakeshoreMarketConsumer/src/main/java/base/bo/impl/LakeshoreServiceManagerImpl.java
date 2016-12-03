package base.bo.impl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import base.bo.LakeshoreServiceManager;
import base.constant.HTTPVerb;
import base.jaxb.Customer;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.util.LakeshoreMarketUtil;

/**
 * This class manages the http request and responses to the Lakeshore web service 
 * @author lbo
 *
 */
public class LakeshoreServiceManagerImpl implements LakeshoreServiceManager {

	@Override
	public Partner getPartner(int partnerId) throws IOException, JAXBException {
		String relativePath = "/partners/" + partnerId;
		return LakeshoreMarketUtil.unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
	}

	@Override
	public Product getProduct(int productNumber) throws IOException, JAXBException {
		Product product = null;
		String relativePath = "products/" + productNumber;
		String response = LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null);

		//convert xml file into Java file
		JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		product = (Product) jaxbUnmarshaller.unmarshal(new StringReader(response));
		
		return product;
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
	public boolean isValidCustomer(String login, String password) throws JAXBException, IOException {
		boolean isValid = false;
		String relativePath = "/customers/" + login;
		Customer customer = LakeshoreMarketUtil.unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
		if (customer != null && password.equals(customer.getPassword())) {
			isValid = true;
		}
		
		return isValid;
	}

	@Override
	public Partner isValidPartner(String login, String password) throws IOException, JAXBException {
		Partner partner = null; //return null if no valid partner
		String relativePath = "partners/" + login;
		partner = LakeshoreMarketUtil.unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath, null));
		if (partner != null && password.equals(partner.getPassword())) {
			return partner; // return the partner if there is a match
		}
		
		return null;
	}

	@Override
	public void registerCustomer(String id, String login, String password, String firstName, String lastName, String streetAddress,
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
		LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.POST, relativePath, body);		
	}
}
