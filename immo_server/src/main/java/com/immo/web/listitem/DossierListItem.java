/*
 * Created on 29 d�c. 2015 ( Time 20:50:38 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.web.listitem;

import com.immo.bean.Dossier;
import com.immo.web.common.ListItem;

public class DossierListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public DossierListItem(Dossier dossier) {
		super();

		this.value = ""
			 + dossier.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = dossier.toString();
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
