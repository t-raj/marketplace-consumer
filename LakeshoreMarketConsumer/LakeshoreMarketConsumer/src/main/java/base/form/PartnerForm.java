package base.form;

import java.util.List;

import base.bean.PartnerTO;


public class PartnerForm {

	private List<PartnerTO> partnerTOList;
	private String selectedLink;
	
	public List<PartnerTO> getPartnerTOList() {
		return partnerTOList;
	}

	public void setPartnerTOList(List<PartnerTO> partnerTOList) {
		this.partnerTOList = partnerTOList;
	}

	public String getSelectedLink() {
		return selectedLink;
	}

	public void setSelectedLink(String selectedLink) {
		this.selectedLink = selectedLink;
	}

	
	
}
