package com.immo.test;

import com.immo.bean.UtilisateurTranche;

public class UtilisateurTrancheFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UtilisateurTranche newUtilisateurTranche() {

		Long id = mockValues.nextLong();

		UtilisateurTranche utilisateurTranche = new UtilisateurTranche();
		utilisateurTranche.setId(id);
		return utilisateurTranche;
	}
	
}
