package com.immo.test;

import com.immo.bean.jpa.EcheanceEntity;

public class EcheanceEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public EcheanceEntity newEcheanceEntity() {

		Long id = mockValues.nextLong();

		EcheanceEntity echeanceEntity = new EcheanceEntity();
		echeanceEntity.setId(id);
		return echeanceEntity;
	}
	
}
