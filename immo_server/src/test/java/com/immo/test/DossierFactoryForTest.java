package com.immo.test;

import com.immo.bean.Dossier;

public class DossierFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Dossier newDossier() {

		Long id = mockValues.nextLong();

		Dossier dossier = new Dossier();
		dossier.setId(id);
		return dossier;
	}
	
}
