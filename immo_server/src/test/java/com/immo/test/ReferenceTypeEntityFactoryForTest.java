package com.immo.test;

import com.immo.bean.jpa.ReferenceTypeEntity;

public class ReferenceTypeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReferenceTypeEntity newReferenceTypeEntity() {

		Long id = mockValues.nextLong();

		ReferenceTypeEntity referenceTypeEntity = new ReferenceTypeEntity();
		referenceTypeEntity.setId(id);
		return referenceTypeEntity;
	}
	
}
