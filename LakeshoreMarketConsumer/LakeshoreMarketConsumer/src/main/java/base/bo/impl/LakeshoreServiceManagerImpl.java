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
		return unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath));
	}

	@Override
	public Product getProduct(int productNumber) throws IOException, JAXBException {
		Product product = null;
		String relativePath = "products/" + productNumber;
		String response = LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath);

		//convert xml file into Java file
		JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		product = (Product) jaxbUnmarshaller.unmarshal(new StringReader(response));
		
		return product;
	}

	@Override
	public void addProduct(int productNum, String description, String partner, String price) throws IOException {
		Product product = new Product();
		product.setNumberAvailable(1);
		product.setPartnerId(Integer.parseInt(partner));
		product.setPId(productNum);
		//TODO: set description
		String relativePath = "products";
		LakeshoreMarketUtil.sendHTTPRequestBody(HTTPVerb.POST, relativePath, product);
		
	}

	@Override
	public boolean isValidCustomer(String login, String password) throws JAXBException, IOException {
		boolean isValid = false;
		String relativePath = "/customers/" + login;
		Customer customer = unmarshalCustomer(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath));
		//TODO: need to return a password
		if (customer != null && password.equals(customer.getPassword())) {
			isValid = true;
		}
		
		return isValid;
	}

	@Override
	public boolean isValidPartner(String login, String password) throws IOException, JAXBException {
		boolean isValid = false;
		String relativePath = "/partners/" + login;
		Partner partner = unmarshalPartner(LakeshoreMarketUtil.sendHTTPRequest(HTTPVerb.GET, relativePath));
		//TODO: need to return a password
		if (partner != null && password.equals(partner.getPassword())) {
			isValid = true;
		}
		
		return isValid;
	}

	/**
	 * Convert xml string into Java object Partner
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	private Partner unmarshalPartner(String response) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Partner.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Partner) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}
	
	/**
	 * Convert xml string into Java object Customer
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	private Customer unmarshalCustomer(String response) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Customer) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}
}
