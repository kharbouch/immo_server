package com.immo.test;

import com.immo.bean.ReferenceType;

public class ReferenceTypeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReferenceType newReferenceType() {

		Long id = mockValues.nextLong();

		ReferenceType referenceType = new ReferenceType();
		referenceType.setId(id);
		return referenceType;
	}
	
}
