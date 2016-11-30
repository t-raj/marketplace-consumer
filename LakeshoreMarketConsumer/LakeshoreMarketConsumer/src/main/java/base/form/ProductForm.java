package base.form;

import java.util.List;

import base.bean.ProductTO;


public class ProductForm {
	
	List<ProductTO> productTOList;

	public List<ProductTO> getProductTOList() {
		return productTOList;
	}

	public void setProductTOList(List<ProductTO> productTOList) {
		this.productTOList = productTOList;
	}

}
