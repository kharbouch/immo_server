/*
 * Created on 29 d�c. 2015 ( Time 20:53:10 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.web.listitem;

import com.immo.bean.ReferenceType;
import com.immo.web.common.ListItem;

public class ReferenceTypeListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public ReferenceTypeListItem(ReferenceType referenceType) {
		super();

		this.value = ""
			 + referenceType.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = referenceType.toString();
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