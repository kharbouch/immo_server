package com.immo.test;

import com.immo.bean.jpa.UtilisateurTrancheEntity;

public class UtilisateurTrancheEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UtilisateurTrancheEntity newUtilisateurTrancheEntity() {

		Long id = mockValues.nextLong();

		UtilisateurTrancheEntity utilisateurTrancheEntity = new UtilisateurTrancheEntity();
		utilisateurTrancheEntity.setId(id);
		return utilisateurTrancheEntity;
	}
	
}
