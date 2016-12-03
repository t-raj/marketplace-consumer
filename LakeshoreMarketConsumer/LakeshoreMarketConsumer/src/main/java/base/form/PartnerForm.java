package base.form;

import java.util.List;

import base.bean.LinkTO;
import base.bean.PartnerTO;


public class PartnerForm {

	private List<PartnerTO> partnerTOList;
	private LinkTO selectedLink;
	
	public List<PartnerTO> getPartnerTOList() {
		return partnerTOList;
	}

	public void setPartnerTOList(List<PartnerTO> partnerTOList) {
		this.partnerTOList = partnerTOList;
	}

	public LinkTO getSelectedLink() {
		return selectedLink;
	}

	public void setSelectedLink(LinkTO selectedLink) {
		this.selectedLink = selectedLink;
	}
	
	
	
	
}
