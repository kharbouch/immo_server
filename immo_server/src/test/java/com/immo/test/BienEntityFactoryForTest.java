package com.immo.test;

import com.immo.bean.jpa.BienEntity;

public class BienEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BienEntity newBienEntity() {

		Long id = mockValues.nextLong();

		BienEntity bienEntity = new BienEntity();
		bienEntity.setId(id);
		return bienEntity;
	}
	
}
