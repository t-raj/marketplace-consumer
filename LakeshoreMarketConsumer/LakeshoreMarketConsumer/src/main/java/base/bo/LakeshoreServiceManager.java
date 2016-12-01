package base.bo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.jaxb.Partner;
import base.jaxb.Product;

public interface LakeshoreServiceManager {
	
	Partner getPartner(int partnerId) throws IOException, JAXBException;

	Product getProduct(int productNumber) throws IOException, JAXBException;

	void addProduct(int productNum, String description, String partner, String price) throws IOException;

	boolean isValidCustomer(String login, String password) throws JAXBException, IOException;
	
	boolean isValidPartner(String login, String password) throws IOException, JAXBException;

}
