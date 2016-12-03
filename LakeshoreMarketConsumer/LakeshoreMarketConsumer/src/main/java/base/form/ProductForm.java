package base.form;

import java.util.List;

import base.bean.ProductTO;


public class ProductForm {
	
	private List<ProductTO> productTOList;
	private String selectedLink;
	
	public List<ProductTO> getProductTOList() {
		return productTOList;
	}

	public void setProductTOList(List<ProductTO> productTOList) {
		this.productTOList = productTOList;
	}

	public String getSelectedLink() {
		return selectedLink;
	}

	public void setSelectedLink(String selectedLink) {
		this.selectedLink = selectedLink;
	}

}
