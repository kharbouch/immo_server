package com.immo.test;

import com.immo.bean.Tranche;

public class TrancheFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Tranche newTranche() {

		Long id = mockValues.nextLong();

		Tranche tranche = new Tranche();
		tranche.setId(id);
		return tranche;
	}
	
}
