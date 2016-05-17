package com.immo.test;

import com.immo.bean.Projet;

public class ProjetFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Projet newProjet() {

		Long id = mockValues.nextLong();

		Projet projet = new Projet();
		projet.setId(id);
		return projet;
	}
	
}
