package com.immo.test;

import com.immo.bean.jpa.ReferenceEntity;

public class ReferenceEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReferenceEntity newReferenceEntity() {

		Long id = mockValues.nextLong();

		ReferenceEntity referenceEntity = new ReferenceEntity();
		referenceEntity.setId(id);
		return referenceEntity;
	}
	
}
