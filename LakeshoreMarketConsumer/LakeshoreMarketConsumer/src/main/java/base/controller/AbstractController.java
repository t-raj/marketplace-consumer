package base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import base.bean.PartnerTO;
import base.bean.ProductTO;
import base.bo.LakeshoreServiceManager;
import base.bo.impl.LakeshoreServiceManagerImpl;
import base.form.PartnerForm;
import base.form.ProductForm;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.util.LakeshoreMarketUtil;

@Controller
public class AbstractController {

	private LakeshoreServiceManager lakeshoreServiceManager = new LakeshoreServiceManagerImpl();
	
	@RequestMapping(value = "/login")
	public ModelAndView authenticateCustomer(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();

			Customer customer = lakeshoreServiceManager.sendHTTPGetCustomer(20202);
			// convert partner to partnerTO
			PartnerTO partnerTO = LakeshoreMarketUtil.buildPartnerTO(partner);
			// add partnerTO to list of partnerTo in partnerForm
			List<PartnerTO> partnerTOList = new ArrayList<PartnerTO>();
			partnerTOList.add(partnerTO);
			PartnerForm partnerForm = new PartnerForm();
			partnerForm.setPartnerTOList(partnerTOList);
			// put partnerForm in the model
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("partnerForm", partnerForm);
			
			return new ModelAndView("product", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("welcome");
		}
	}
	
	@RequestMapping(value = "/products")
	public ModelAndView findProducts(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();

			Product product = lakeshoreServiceManager.sendHTTPGetProduct(20202);
			ProductTO productTO = LakeshoreMarketUtil.buildProductTO(product);
			List<ProductTO> productTOList = new ArrayList<ProductTO>();
			productTOList.add(productTO);
			ProductForm productForm = new ProductForm();
			productForm.setProductTOList(productTOList);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productForm", productForm);
			
			return new ModelAndView("welcome", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "/partners")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			Partner partner = lakeshoreServiceManager.sendHTTPGetPartner(20202);
			// convert partner to partnerTO
			PartnerTO partnerTO = LakeshoreMarketUtil.buildPartnerTO(partner);
			// add partnerTO to list of partnerTo in partnerForm
			List<PartnerTO> partnerTOList = new ArrayList<PartnerTO>();
			partnerTOList.add(partnerTO);
			PartnerForm partnerForm = new PartnerForm();
			partnerForm.setPartnerTOList(partnerTOList);
			// put partnerForm in the model
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("partnerForm", partnerForm);
			
			return new ModelAndView("welcome", model);		
		} catch(Exception e) {
			e.getMessage();
			return new ModelAndView("error");
		}
	}

}