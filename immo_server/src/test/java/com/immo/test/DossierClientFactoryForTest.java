package com.immo.test;

import com.immo.bean.DossierClient;

public class DossierClientFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DossierClient newDossierClient() {

		Long id = mockValues.nextLong();

		DossierClient dossierClient = new DossierClient();
		dossierClient.setId(id);
		return dossierClient;
	}
	
}
