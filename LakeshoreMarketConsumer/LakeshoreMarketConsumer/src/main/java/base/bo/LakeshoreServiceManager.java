package base.bo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.jaxb.Partner;
import base.jaxb.Product;

public interface LakeshoreServiceManager {
	
	Partner getPartner(int partnerId) throws IOException, JAXBException;

	Product getProduct(int productNumber) throws IOException, JAXBException;

	void addProduct(int productNum, String description, int partnerId, int price) throws IOException, JAXBException;

	boolean isValidCustomer(String login, String password) throws JAXBException, IOException;
	
	Partner isValidPartner(String login, String password) throws IOException, JAXBException;

	void registerCustomer(String id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException;

}
