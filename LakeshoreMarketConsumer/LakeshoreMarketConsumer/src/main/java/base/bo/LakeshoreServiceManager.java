package base.bo;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import base.jaxb.Partner;

public interface LakeshoreServiceManager {
	
	Partner sendHTTPGetPartner(int partnerId) throws IOException, JAXBException;
	

}
