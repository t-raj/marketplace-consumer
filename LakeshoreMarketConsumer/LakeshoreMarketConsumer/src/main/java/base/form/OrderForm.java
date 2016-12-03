package base.form;

import java.util.List;

import base.bean.LinkTO;
import base.bean.OrderTO;


public class OrderForm {
	
	private List<OrderTO> orderTOList;
	private LinkTO selectedLink;
	
	public List<OrderTO> getOrderTOList() {
		return orderTOList;
	}

	public void setOrderTOList(List<OrderTO> orderTOList) {
		this.orderTOList = orderTOList;
	}

	public LinkTO getSelectedLink() {
		return selectedLink;
	}

	public void setSelectedLink(LinkTO selectedLink) {
		this.selectedLink = selectedLink;
	}
	
}
