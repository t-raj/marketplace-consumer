package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import constant.Constant;

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

	private ResultSet sendHTTPGetPartner(String inputURL, int partnerId) throws IOException, JAXBException{
		ResultSet user = null;
		URL url = new URL(Constant.BASE_URL+ "/partners/"+ partnerId);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		try { 
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Accept", "application/xml");

			if(urlConnection.getResponseCode() != 200){
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

		//convert xml file into Java file(ResultSet)
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ResultSet.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			user = (ResultSet) jaxbUnmarshaller.unmarshal(new StringReader(response.toString()));

			while(user.next()){
				String firstName = user.getString("firstName");
				String lastName = user.getString("lastName");
				String streetAdd = user.getString("streetAddress");
				String city = user.getString("city");
				String state = user.getString("state");
				int zip = user.getInt("zip_code");

				System.out.println("partner with Id: "+ partnerId + "\t" + "first Name: "+firstName+
						"\t"+ "last Name: "+lastName +"\t"+ "Street Address: "+streetAdd+
						"\t"+ "city: " + city + "\t" + "state: " + state
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
}