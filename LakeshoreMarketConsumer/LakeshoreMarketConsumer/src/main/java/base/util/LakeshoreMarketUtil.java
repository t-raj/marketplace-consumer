package base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import base.bean.LinkTO;
import base.bean.PartnerTO;
import base.bean.ProductTO;
import base.constant.Constant;
import base.constant.HTTPVerb;
import base.form.PartnerForm;
import base.form.ProductForm;
import base.jaxb.Customer;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.jaxb.Product.Link;

public class LakeshoreMarketUtil {

	public static final PartnerTO buildPartnerTO(Partner partner) {
		PartnerTO partnerTO = new PartnerTO();
		if (partner != null) {
			partnerTO.setId(partner.getId());
			partnerTO.setFirstName(partner.getLogin());
			partnerTO.setLastName(partner.getLastName());
		}
		return partnerTO;
	}

	public static String sendHTTPRequest(HTTPVerb httpVerb, String relativePath, String body) throws IOException {
		URL url = new URL(Constant.BASE_URL + relativePath);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		try { 
			urlConnection.setRequestMethod(httpVerb.toString());
			urlConnection.setRequestProperty("Accept", Constant.MEDIA_TYPE_XML);
			urlConnection.setRequestProperty("Content-type", Constant.MEDIA_TYPE_XML);
			
			if (body != null && !body.isEmpty()) {
				urlConnection.setDoOutput(true);
				OutputStream os = urlConnection.getOutputStream();
				os.write(body.getBytes("UTF-8"));
				os.close();
			}
	
			if(urlConnection.getResponseCode() != Constant.HTTP_RESPONSE_SUCCESS){
				throw new RuntimeException("Failed : HTTP error code : " + urlConnection.getResponseCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader bufferReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
		StringBuffer response = new StringBuffer();
		String inputLine = null;

		while ((inputLine = bufferReader.readLine()) != null) {
			response.append(inputLine);
		}
		bufferReader.close();
		
		return response.toString();
	}

	public static ProductForm buildProductForm(Product product) {
		ProductTO productTO = buildProductTO(product);
		List<ProductTO> productTOList = new ArrayList<ProductTO>();
		productTOList.add(productTO);
		ProductForm productForm = new ProductForm();
		productForm.setProductTOList(productTOList);

		return productForm;
	}

	private static ProductTO buildProductTO(Product product) {
		ProductTO productTO = new ProductTO();
		if (product != null) {
			productTO.setId(product.getProductId());
			productTO.setPrice(product.getPrice());
			productTO.setNumAvailable(product.getNumberAvailable());
			productTO.setDescription(product.getDescription());
			productTO.setLinks(buildLinkTOList(product.getLink()));
		}
		return productTO;
	}

	private static List<LinkTO> buildLinkTOList(List<Link> links) {
		if (links == null || links.isEmpty()) {
			return null;
		}
		
		List<LinkTO> linkTOs = new ArrayList<LinkTO>();
		for (Link link : links) {
			LinkTO linkTO = new LinkTO();
			linkTO.setAction(link.getAction());
			linkTO.setMediaType(link.getMediaType());
			linkTO.setRel(link.getRel());
			linkTO.setUrl(link.getUrl());
			linkTOs.add(linkTO);
		}
		
		return linkTOs;
	}

	public static PartnerForm buildPartnerForm(Partner partner) {
		PartnerTO partnerTO = buildPartnerTO(partner);
		// add partnerTO to list of partnerTo in partnerForm
		List<PartnerTO> partnerTOList = new ArrayList<PartnerTO>();
		partnerTOList.add(partnerTO);
		PartnerForm partnerForm = new PartnerForm();
		partnerForm.setPartnerTOList(partnerTOList);

		return partnerForm;
	}


	/**
	 * Convert xml string into Java object Partner
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	public final static Partner unmarshalPartner(String response) throws JAXBException {
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
	public final static Customer unmarshalCustomer(String response) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Customer) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}

	/**
	 * Create customer xml 
	 * @param customer
	 * @return
	 * @throws JAXBException 
	 */
	public static String buildXML(Customer customer) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(customer, sw);

		return sw.toString();
	}
	
	/**
	 * Create customer xml 
	 * @param customer
	 * @return
	 * @throws JAXBException 
	 */
	public static String buildXML(Product product) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(product, sw);

		return sw.toString();
	}


}
