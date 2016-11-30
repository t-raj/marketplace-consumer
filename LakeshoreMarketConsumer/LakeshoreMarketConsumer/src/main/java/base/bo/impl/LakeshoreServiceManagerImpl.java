package base.bo.impl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import base.bo.LakeshoreServiceManager;
import base.constant.HTTPVerb;
import base.jaxb.Partner;
import base.util.LakeshoreMarketUtil;

public class LakeshoreServiceManagerImpl implements LakeshoreServiceManager {

	@Override
	public Partner sendHTTPGetPartner(int partnerId) throws IOException, JAXBException {
		Partner user = null;
		String relativePath = "/partners/" + partnerId;
		String response = LakeshoreMarketUtil.sendHTTPGetResponse(HTTPVerb.GET, relativePath);

		//convert xml file into Java file
		JAXBContext jaxbContext = JAXBContext.newInstance(Partner.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		user = (Partner) jaxbUnmarshaller.unmarshal(new StringReader(response));
		
		return user;
	}
	
}
