/*
 * Created on 29 d�c. 2015 ( Time 20:50:39 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.web.listitem;

import com.immo.bean.TraceDossier;
import com.immo.web.common.ListItem;

public class TraceDossierListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public TraceDossierListItem(TraceDossier traceDossier) {
		super();

		this.value = ""
			 + traceDossier.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = traceDossier.toString();
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
