package base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import base.bean.PartnerTO;
import base.constant.Constant;
import base.constant.HTTPVerb;
import base.jaxb.Partner;

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
	
	public static final String sendHTTPGetResponse(HTTPVerb httpVerb, String relativePath) throws IOException {
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
}
