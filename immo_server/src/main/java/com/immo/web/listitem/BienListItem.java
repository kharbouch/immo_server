/*
 * Created on 29 d�c. 2015 ( Time 20:50:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.web.listitem;

import com.immo.bean.Bien;
import com.immo.web.common.ListItem;

public class BienListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public BienListItem(Bien bien) {
		super();

		this.value = ""
			 + bien.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = bien.toString();
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
