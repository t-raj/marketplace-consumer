package base.form;

import java.util.List;

import base.bean.LinkTO;
import base.bean.ProductTO;


public class ProductForm {
	
	private List<ProductTO> productTOList;
	private LinkTO selectedLink;
	
	public List<ProductTO> getProductTOList() {
		return productTOList;
	}

	public void setProductTOList(List<ProductTO> productTOList) {
		this.productTOList = productTOList;
	}

	public LinkTO getSelectedLink() {
		return selectedLink;
	}

	public void setSelectedLink(LinkTO selectedLink) {
		this.selectedLink = selectedLink;
	}

}
