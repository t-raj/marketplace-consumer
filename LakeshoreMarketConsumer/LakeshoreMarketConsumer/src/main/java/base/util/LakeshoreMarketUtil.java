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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import base.bean.CustomerTO;
import base.bean.LinkTO;
import base.bean.OrderTO;
import base.bean.PartnerTO;
import base.bean.ProductTO;
import base.constant.Constant;
import base.form.CustomerForm;
import base.form.OrderForm;
import base.form.PartnerForm;
import base.form.ProductForm;
import base.jaxb.Customer;
import base.jaxb.Order;
import base.jaxb.Orders;
import base.jaxb.Partner;
import base.jaxb.Product;
import base.jaxb.Product.Link;
import base.jaxb.Products;

public class LakeshoreMarketUtil {

	/**
	 * Builds partner TO
	 * @param partner
	 * @return
	 */
	public static final PartnerTO buildPartnerTO(Partner partner) {
		PartnerTO partnerTO = new PartnerTO();
		if (partner != null) {
			partnerTO.setId(partner.getId());
			partnerTO.setFirstName(partner.getLogin());
			partnerTO.setLastName(partner.getLastName());
			partnerTO.setLogin(partner.getLogin());
			partnerTO.setPassword(partner.getPassword());
			partnerTO.setStreetAddress(partner.getStreetAddress());
			partnerTO.setState(partner.getState());
			partnerTO.setZip_code(partner.getZipCode());
			// deep clone of list array
			List<LinkTO> links = new ArrayList<LinkTO>();
			for (Partner.Link link : partner.getLink()) {
				LinkTO linkTO = new LinkTO();
				linkTO.setAction(link.getAction());
				linkTO.setMediaType(link.getMediaType());
				linkTO.setRel(link.getRel());
				linkTO.setUrl(link.getUrl());
				links.add(linkTO);
			}
			partnerTO.setLinkList(links);
		}
		return partnerTO;
	}

	/**
	 * Sends HTTP Request
	 * @param httpVerb
	 * @param url
	 * @param body
	 * @param  
	 * @return
	 * @throws IOException
	 */
	public static String sendHTTPRequest(String httpVerb, String urlRequest, String body) throws IOException {
		URL url = new URL(urlRequest);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		try { 
			urlConnection.setRequestMethod(httpVerb);
			urlConnection.setRequestProperty("Accept", Constant.MEDIA_TYPE_XML);
			urlConnection.setRequestProperty("Content-Type", Constant.MEDIA_TYPE_XML);

			if (body != null && !body.isEmpty()) {
				urlConnection.setDoOutput(true);
				OutputStream os = urlConnection.getOutputStream();
				os.write(body.getBytes("UTF-8"));
				os.close();
			}

			// no -op
			if(urlConnection.getResponseCode() == Constant.HTTP_RESPONSE_SUCCESS || urlConnection.getResponseCode() == 204){
			} else {
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

	/**
	 * Builds Product Form
	 * @param product
	 * @return
	 */
	public static ProductForm buildProductForm(Product product) {
		ProductTO productTO = buildProductTO(product);
		List<ProductTO> productTOList = new ArrayList<ProductTO>();
		productTOList.add(productTO);
		ProductForm productForm = new ProductForm();
		productForm.setProductTOList(productTOList);

		return productForm;
	}

	private static ProductTO buildProductTO(Products.Product product) {
		ProductTO productTO = new ProductTO();
		if (product != null) {
			productTO.setId(product.getProductId());
			productTO.setPrice(product.getPrice());
			productTO.setNumAvailable(product.getNumberAvailable());
			productTO.setDescription(product.getDescription());
		}
		return productTO;
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

	/**
	 * Build Link TO list
	 * @param links
	 * @return
	 */
	public static List<LinkTO> buildLinkTOList(List<Link> links) {
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

	/**
	 * Builds TO list
	 * @param links
	 * @return
	 */
	public static List<LinkTO> buildLinkTOListFromPartner(List<Partner.Link> links) {
		if (links == null || links.isEmpty()) {
			return null;
		}

		List<LinkTO> linkTOs = new ArrayList<LinkTO>();
		for (Partner.Link link : links) {
			LinkTO linkTO = new LinkTO();
			linkTO.setAction(link.getAction());
			linkTO.setMediaType(link.getMediaType());
			linkTO.setRel(link.getRel());
			linkTO.setUrl(link.getUrl());
			linkTOs.add(linkTO);
		}

		return linkTOs;
	}

	/**
	 * Builds Partner Form
	 * @param partner
	 * @return
	 */
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

	/**
	 * Create partner xml
	 * @param partner
	 * @return
	 * @throws JAXBException 
	 */
	public static String buildXML(Partner partner) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Partner.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(partner, sw);

		return sw.toString();
	}

	/**
	 * Unmarshal product xml
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	public static Product unmarshalProduct(String response) throws JAXBException {
		//convert xml file into Java file
		JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Product) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}

	/**
	 * Build customer form
	 * @param customer
	 * @return
	 */
	public static CustomerForm buildCustomerForm(Customer customer) {
		CustomerTO customerTO = buildCustomerTO(customer);
		List<CustomerTO> customerTOList = new ArrayList<CustomerTO>();
		customerTOList.add(customerTO);
		CustomerForm customerForm = new CustomerForm();
		customerForm.setCustomerTOList(customerTOList);

		return customerForm;
	}

	private static CustomerTO buildCustomerTO(Customer customer) {
		CustomerTO customerTO = new CustomerTO();
		if (customer != null) {
			customerTO.setId(customer.getId());
			customerTO.setFirstName(customer.getLogin());
			customerTO.setLastName(customer.getLastName());
			customerTO.setLogin(customer.getLogin());
			customerTO.setPassword(customer.getPassword());
			customerTO.setStreetAddress(customer.getStreetAddress());
			customerTO.setState(customer.getState());
			customerTO.setZip_code(customer.getZipCode());
			// deep clone of list array
			List<LinkTO> links = new ArrayList<LinkTO>();
			for (Customer.Link link : customer.getLink()) {
				LinkTO linkTO = new LinkTO();
				linkTO.setAction(link.getAction());
				linkTO.setMediaType(link.getMediaType());
				linkTO.setRel(link.getRel());
				linkTO.setUrl(link.getUrl());
				links.add(linkTO);
			}
			customerTO.setLinkList(links);
		}
		return customerTO;
	}

	/**
	 * Build Order form
	 * @param pushedOrders
	 * @return
	 */
	public static OrderForm buildOrderForm(List<Order> pushedOrders) {
		List<OrderTO> orderTOList = new ArrayList<OrderTO>();
		for (Order pushedOrder : pushedOrders) {
			OrderTO orderTO = buildOrderTO(pushedOrder);
			orderTOList.add(orderTO);
		}

		OrderForm orderForm = new OrderForm();
		orderForm.setOrderTOList(orderTOList);

		return orderForm;
	}

	private static OrderTO buildOrderTO(Order order) {
		OrderTO orderTO = new OrderTO();
		if (order != null) {
			orderTO.setCustomerId(order.getCustomerId());
			orderTO.setId(order.getOrderId());
			orderTO.setPartnerId(order.getPartnerId());
			orderTO.setStatus(order.getStatus());
			// deep clone of list array
			List<LinkTO> links = new ArrayList<LinkTO>();
			for (Order.Link link : order.getLink()) {
				LinkTO linkTO = new LinkTO();
				linkTO.setAction(link.getAction());
				linkTO.setMediaType(link.getMediaType());
				linkTO.setRel(link.getRel());
				linkTO.setUrl(link.getUrl());
				links.add(linkTO);
			}
			orderTO.setLinks(links);
			// TODO: for future enhancement: allow multiple products in pushed order 
			List<Integer> products = new ArrayList<Integer>();
			products.add(order.getPartnerId());
			orderTO.setProductIds(products);
		}
		return orderTO;
	}

	/**
	 * Unmarshal order xml
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	public static Orders unmarshalOrders(String response) throws JAXBException {
		//convert xml file into Java file
		// make sure the plural form not the singular form is unmarshalled
		JAXBContext jaxbContext = JAXBContext.newInstance(Orders.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Orders) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}


	/**
	 * 
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	public static Order unmarshalOrder(String response) throws JAXBException {
		//convert xml file into Java file
		JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Order) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}

	/**
	 * Build product form
	 * @param productList
	 * @return
	 */
	public static ProductForm buildProductForm(Products productList) {
		ProductForm productForm = new ProductForm();

		if (productList != null) {
			List<Products.Product> products = productList.getProduct();
			List<ProductTO> productTOList = new ArrayList<ProductTO>();
			for (Products.Product product : products) {
				ProductTO productTO = buildProductTO(product);
				productTOList.add(productTO);
			}

			productForm.setProductTOList(productTOList);
		}

		return productForm;
	}

	/**
	 * Unmarshal products
	 * @param response
	 * @return
	 * @throws JAXBException
	 */
	public static Products unmarshalProducts(String response) throws JAXBException {
		//convert xml file into Java file
		// make sure the plural form of products not the singular form is unmarshalled
		JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Products) jaxbUnmarshaller.unmarshal(new StringReader(response));
	}

	/**
	 * Build order form 
	 * @param order
	 * @return
	 */
	public static OrderForm buildOrderForm(Order order) {
		OrderTO orderTO = buildOrderTO(order);
		List<OrderTO> orderTOList = new ArrayList<OrderTO>();
		orderTOList.add(orderTO);
		OrderForm orderForm = new OrderForm();
		orderForm.setOrderTOList(orderTOList);

		return orderForm;
	}

	/**
	 * Marshal order object from xml
	 * @param order
	 * @return
	 * @throws JAXBException
	 */
	public static String buildXML(Order order) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(order, sw);

		return sw.toString();
	}

	/**
	 * Build order form with multiple orders
	 * @param orders
	 * @return
	 */
	public static OrderForm buildOrderForm(Orders orders) {
		if (orders != null) {
			return buildOrderForm(orders.getOrder());
		}
		return null;
	}

	public static Map<String,String> buildLinkMap(List<base.jaxb.Customer.Link> links) {
		Map<String,String> linkMap = new HashMap<String,String>();
		if (!isEmptyCustomer(links)) {
			for (Customer.Link link : links) {
				linkMap.put(link.getRel(), link.getUrl());
			}
		}
		return linkMap;

	}

	public static Map<String, String> buildLinkMapPartner(List<base.jaxb.Partner.Link> links) {
		Map<String,String> linkMap = new HashMap<String,String>();
		if (!isEmpty(links)) {
			for (Partner.Link link : links) {
				linkMap.put(link.getRel(), link.getUrl());
			}
		}
		return linkMap;
	}

	public static Map<String, String> buildVerbMap(List<base.jaxb.Customer.Link> links) {
		Map<String,String> linkMap = new HashMap<String,String>();
		if (!isEmptyCustomer(links)) {
			for (Customer.Link link : links) {
				linkMap.put(link.getRel(), link.getAction());
			}
		}
		return linkMap;
	}

	private static boolean isEmptyCustomer(List<base.jaxb.Customer.Link> links) {
		return links == null || links.isEmpty();
	}

	public static Map<String, String> buildVerbMapPartner(List<base.jaxb.Partner.Link> links) {
		Map<String,String> linkMap = new HashMap<String,String>();
		if (!isEmpty(links)) {
			for (Partner.Link link : links) {
				linkMap.put(link.getRel(), link.getAction());
			}
		}
		return linkMap;
	}

	public static void addToVerbMap(Map<String, String> verbMap, List<Link> links) {
		if (!isEmpty(links) && verbMap != null) {
			for (Link link : links) {
				verbMap.put(link.getRel(), link.getAction());
			}
		}

	}

	private static boolean isEmpty(List<?> links) {
		return links == null || links.isEmpty();
	}

	public static void addToLinkMap(Map<String, String> linkMap, List<Link> links) {
		if (!isEmpty(links) && linkMap != null) {
			for (Link link : links) {
				linkMap.put(link.getRel(), link.getUrl());
			}
		}

	}

	public static void addToLinkMap(Map<String, String> linkMap, Orders orders) {
		if (orders != null && !isEmpty(orders.getOrder())) {
			List<Order> orderList = orders.getOrder();
			for (Order order : orderList) {
				List<base.jaxb.Order.Link> links = order.getLink();
				for (base.jaxb.Order.Link link : links) {
					linkMap.put(link.getRel(), link.getUrl());
				}
			}

		}

	}

	public static void addToVerbMap(Map<String, String> verbMap, Orders orders) {
		if (orders != null && !isEmpty(orders.getOrder())) {
			List<Order> orderList = orders.getOrder();
			for (Order order : orderList) {
				List<base.jaxb.Order.Link> links = order.getLink();
				for (base.jaxb.Order.Link link : links) {
					verbMap.put(link.getRel(), link.getAction());
				}
			}
		}
	}



}
