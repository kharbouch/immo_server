package com.immo.test;

import com.immo.bean.jpa.DossierClientEntity;

public class DossierClientEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DossierClientEntity newDossierClientEntity() {

		Long id = mockValues.nextLong();

		DossierClientEntity dossierClientEntity = new DossierClientEntity();
		dossierClientEntity.setId(id);
		return dossierClientEntity;
	}
	
}
