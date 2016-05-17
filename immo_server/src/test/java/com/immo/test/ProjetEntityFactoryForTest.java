package com.immo.test;

import com.immo.bean.jpa.ProjetEntity;

public class ProjetEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProjetEntity newProjetEntity() {

		Long id = mockValues.nextLong();

		ProjetEntity projetEntity = new ProjetEntity();
		projetEntity.setId(id);
		return projetEntity;
	}
	
}
