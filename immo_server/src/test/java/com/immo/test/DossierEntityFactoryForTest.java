package com.immo.test;

import com.immo.bean.jpa.DossierEntity;

public class DossierEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DossierEntity newDossierEntity() {

		Long id = mockValues.nextLong();

		DossierEntity dossierEntity = new DossierEntity();
		dossierEntity.setId(id);
		return dossierEntity;
	}
	
}
