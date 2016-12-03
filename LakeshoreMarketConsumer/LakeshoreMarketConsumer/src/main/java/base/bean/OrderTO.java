package base.bean;

import java.util.List;

public class OrderTO {

	private int id;
	private int partnerId;
	private int customerId;
	private List<Integer> productIds;
	private String status;
	private List<LinkTO> links;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Integer> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}
	public List<LinkTO> getLinks() {
		return links;
	}
	public void setLinks(List<LinkTO> links) {
		this.links = links;
	}

}
