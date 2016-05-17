package com.immo.test;

import com.immo.bean.Echeance;

public class EcheanceFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Echeance newEcheance() {

		Long id = mockValues.nextLong();

		Echeance echeance = new Echeance();
		echeance.setId(id);
		return echeance;
	}
	
}
