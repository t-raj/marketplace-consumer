package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AbstractController {
	
	@RequestMapping(value = "/")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			
			Map<String, Object> model = new HashMap<String, Object>();
//			Product
//			model.put("productForm", value)
			return new ModelAndView("home", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

//	public final ResultSet getSenators() throws IOException, JAXBException {
		//To get the current members of Congress for a particular chamber, state and (for House requests) district, use the following URI structure:
		//http://api.nytimes.com/svc/politics/{version}/us/legislative/congress/members/{chamber}/{state}/{district*}/current[.response-format]?api-key={your-API-key}
//		String serviceURL = Constant.REQUEST_1 + Constant.API_VERSION + Constant.REQUEST_CURRENT_MEMBERS_2 + Constant.CHAMBER_SENATE + Constant.DELIMITER_SLASH + Constant.ILLINOIS + Constant.DELIMITER_SLASH + Constant.REQUEST_CURRENT_MEMBERS_3 + Constant.CONGRESS_API_KEY; //NOTE: THIS URL WILL TRUNCATE DECIMAL TO INT. E.G. ORDER# 2.1 (INVALID) BECOMES ORDER 2 (VALID)
//		ResultSet currentSenateMembers = sendHttpRequestGet(serviceURL);
		
//		return currentSenateMembers;
//	}
	
	private ResultSet sendHttpRequestGet(String inputURL) throws IOException, JAXBException { //the URLs given by Niraj all return a JSON; no extra processing of tokens, date time, etc is needed!
		URL url = new URL(inputURL);								//String userAgent = "Mozilla/5.0";
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/xml");		
		
	    if (urlConnection.getResponseCode() != 200)
        {
            throw new RuntimeException("Failed : HTTP error code : " + urlConnection.getResponseCode());
        }

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
		StringBuffer response = new StringBuffer();
		String inputLine = null;
		
		while ((inputLine = bufferReader.readLine()) != null) {
			response.append(inputLine);
		}
		bufferReader.close();
    
        JAXBContext jaxbContext = JAXBContext.newInstance(ResultSet.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ResultSet user = (ResultSet) jaxbUnmarshaller.unmarshal(new StringReader(response.toString()));

//        user.getResults().getMember();
        return user;
	}
}
