package com.immo.test;

import com.immo.bean.jpa.TrancheEntity;

public class TrancheEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TrancheEntity newTrancheEntity() {

		Long id = mockValues.nextLong();

		TrancheEntity trancheEntity = new TrancheEntity();
		trancheEntity.setId(id);
		return trancheEntity;
	}
	
}
