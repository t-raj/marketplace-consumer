package base.bean;

import java.util.List;

public class ProductTO {
	
	private int id;
	private String description;
	private int partnerID;
	private int numAvailable;
	private int price;
	private boolean active;
	private List<LinkTO> links;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(int partnerID) {
		this.partnerID = partnerID;
	}
	public int getNumAvailable() {
		return numAvailable;
	}
	public void setNumAvailable(int numAvailable) {
		this.numAvailable = numAvailable;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<LinkTO> getLinks() {
		return links;
	}
	public void setLinks(List<LinkTO> links) {
		this.links = links;
	}

}
