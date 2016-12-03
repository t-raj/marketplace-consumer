package base.bo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.jaxb.Customer;
import base.jaxb.Orders;
import base.jaxb.Partner;
import base.jaxb.Product;

public interface LakeshoreServiceManager {
	
	Product getProduct(int productNumber) throws IOException, JAXBException;

	Product addProduct(int productNum, String description, int partnerId, int price) throws IOException, JAXBException;

	Customer getCustomer(String login) throws JAXBException, IOException;
	
	Partner getPartner(String login) throws IOException, JAXBException;

	Customer registerCustomer(String id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException;

	Partner registerPartner(int id, String login, String password, String firstName, String lastName,
			String streetAddress, String city, String state, String zip) throws JAXBException, IOException;

	Orders pushOrdersToPartner(int partnerId) throws JAXBException, IOException;

	Orders getAcknowledgement(int partnerId) throws JAXBException, IOException;

}
