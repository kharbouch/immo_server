/*
 * Created on 29 d�c. 2015 ( Time 20:50:38 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.web.listitem;

import com.immo.bean.DossierClient;
import com.immo.web.common.ListItem;

public class DossierClientListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public DossierClientListItem(DossierClient dossierClient) {
		super();

		this.value = ""
			 + dossierClient.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = dossierClient.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
