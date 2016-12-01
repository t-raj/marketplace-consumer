package base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import base.bean.PartnerTO;
import base.bean.ProductTO;
import base.constant.Constant;
import base.constant.HTTPVerb;
import base.form.PartnerForm;
import base.form.ProductForm;
import base.jaxb.Partner;
import base.jaxb.Product;

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

	public static final String sendHTTPRequest(HTTPVerb httpVerb, String relativePath) throws IOException {
		URL url = new URL(Constant.BASE_URL + relativePath);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		try { 
			urlConnection.setRequestMethod(httpVerb.toString());
			urlConnection.setRequestProperty("Accept", Constant.MEDIA_TYPE_XML);

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
			productTO.setId(product.getPId());
			productTO.setPrice(product.getPrice());
			productTO.setNumAvailable(product.getNumberAvailable());
			//TODO: product.getDescription
		}
		return productTO;
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

	public static void sendHTTPRequestBody(HTTPVerb httpVerb, String relativePath, Product product) throws IOException {
		URL url = new URL(Constant.BASE_URL + relativePath);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		try { 
			urlConnection.setRequestMethod(httpVerb.toString());
			urlConnection.setRequestProperty("Content-type", Constant.MEDIA_TYPE_XML);
			urlConnection.setDoOutput(true);
//			TODO: use marshaller to build to xml instead of doing it manually 
//			HttpPost httppost = new HttpPost(url);
//			InputStream inputStream=new ByteArrayInputStream(xmlString.getBytes());//init your own inputstream
//			InputStreamEntity inputStreamEntity=new InputStreamEntity(inputStream,xmlString.getBytes());
//			httppost.setEntity(inputStreamEntity);
//			
//			OutputStreamWriter writer = null;
//			try {
//			    writer = new OutputStreamWriter(urlConnection.getOutputStream(), charset);
//			    writer.write(query); // Write POST query string (if any needed).
//			} finally {
//			    if (writer != null) try { writer.close(); } catch (IOException logOrIgnore) {}
//			}
//
//			InputStream result = urlConnection.getInputStream();
			// Now do your thing with the result.
//			OutputStream os = urlConnection.getOutputStream();
//			String xml = buildXML(product);
//			os.write(xml.getBytes("UTF-8"));
//			os.close();

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

	}

	private static String buildXML(Product product) {
		if (product == null) {
			return null;
		}

		StringBuilder xml = new StringBuilder();
		/*
		 * <Product>
   			 <pId>0</pId>
   			 <price>0</price>
   			 <partnerId>0</partnerId>
   			 <numberAvailable>0</numberAvailable>
		   </Product>
		 */
		xml.append("<Product>");
		xml.append("<pId>");
		xml.append(product.getPartnerId());
		xml.append("</pId>");

		xml.append("<price>");
		xml.append(product.getPrice());
		xml.append("</price>");

		xml.append("<partnerId>");
		xml.append(product.getPartnerId());
		xml.append("</partnerId>");

		xml.append("<numberAvailable>");
		xml.append(product.getNumberAvailable());
		xml.append("</numberAvailable>");

		xml.append("</Product>");
		
		return xml.toString();
	}


}
