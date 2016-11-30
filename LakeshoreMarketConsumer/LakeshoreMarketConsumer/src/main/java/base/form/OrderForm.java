package base.form;

import java.util.List;

import base.bean.OrderTO;


public class OrderForm {
	
	List<OrderTO> orderTOList;

	public List<OrderTO> getOrderTOList() {
		return orderTOList;
	}

	public void setOrderTOList(List<OrderTO> orderTOList) {
		this.orderTOList = orderTOList;
	}
	
}
