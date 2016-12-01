package base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.ProductForm;
import base.jaxb.Product;
import base.util.LakeshoreMarketUtil;

@Controller
public class ProductController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ModelAndView find(@RequestParam("productNumber") String productNumber) {
		try {
			int productNum = Integer.parseInt(productNumber);
			Product product = lakeshoreServiceManager.getProduct(productNum);
			ProductForm productForm = LakeshoreMarketUtil.buildProductForm(product);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);
			
			// redirect to product page with product data 
			return new ModelAndView("customer", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

	// 2b. Add product or products in market place
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("productNumber") String productNumber, 
			@RequestParam("description") String description, @RequestParam("partner") String partner, 
			@RequestParam("price") String price) {
		try {
			int productNum = Integer.parseInt(productNumber);
			//TODO: create a product pojo to be marshalled and sent as the request body  
			lakeshoreServiceManager.addProduct(productNum, description, partner, price);
			
			return new ModelAndView("partner", null);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}


}