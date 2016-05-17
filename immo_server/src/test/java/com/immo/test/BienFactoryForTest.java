package com.immo.test;

import com.immo.bean.Bien;

public class BienFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Bien newBien() {

		Long id = mockValues.nextLong();

		Bien bien = new Bien();
		bien.setId(id);
		return bien;
	}
	
}
