package com.immo.test;

import com.immo.bean.Reference;

public class ReferenceFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Reference newReference() {

		Long id = mockValues.nextLong();

		Reference reference = new Reference();
		reference.setId(id);
		return reference;
	}
	
}
